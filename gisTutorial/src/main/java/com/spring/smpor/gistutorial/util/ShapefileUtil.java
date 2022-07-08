package com.spring.smpor.gistutorial.util;

import com.spring.smpor.gistutorial.entity.dto.FeatureClassDTO;
import com.spring.smpor.gistutorial.entity.dto.FeatureDTO;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.WKTWriter2;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.*;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by swd on 2018/10/22 利用GeoTools操作Shapefile数据
 */
public class ShapefileUtil {
	private static Integer[] customWkids = { 454700 };

	/**
	 * 读取Shapefile
	 *
	 * @param filePath
	 *            Shapefile文件路径
	 * @param shapeOnly
	 *            只读几何
	 * @param toWkid
	 * @return
	 * @throws Exception
	 */
	public static FeatureClassDTO readShapefile(String filePath, Boolean shapeOnly, Integer toWkid) throws Exception {
		FeatureClassDTO featureClassDTO = new FeatureClassDTO();
		File shpFile = new File(filePath);
		ShapefileDataStore dataStore = new ShapefileDataStore(shpFile.toURI().toURL());
		dataStore.setCharset(Charset.forName("GBK"));
		CoordinateReferenceSystem coordinateReferenceSystem = null;
		try {
			SimpleFeatureType Schema = dataStore.getSchema();
			coordinateReferenceSystem = Schema.getCoordinateReferenceSystem();
		} catch (Error e) {

		}
		MathTransform mathTransform = null;
		if (!Arrays.asList(GeometryUtil.customWkids).contains(toWkid)) {
			// 如果原始数据没坐标系，不要转
			if (coordinateReferenceSystem != null && toWkid != null) {
				// 这里需要提取坐标系code然后用geotools的方法重新生成，不然直接用那坐标转换结果是不对的，不知道是什么原因
				Integer sourceEPSGCode = CRS.lookupEpsgCode(coordinateReferenceSystem, true);
				if (Objects.isNull(sourceEPSGCode)){
					sourceEPSGCode = 4490;
				}
				CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:" + sourceEPSGCode, true); // dataStore.getSchema().getCoordinateReferenceSystem();
				CoordinateReferenceSystem targetCRS = null;
				targetCRS = CRS.decode("EPSG:" + toWkid, true);
				// 两个必须是同个椭球
				String sourceCode = CRS.getEllipsoid(sourceCRS).getName().getCode();
				String targetCode = CRS.getEllipsoid(targetCRS).getName().getCode();
				if (sourceCode.equals(targetCode)) {
					mathTransform = CRS.findMathTransform(sourceCRS, targetCRS, true);
				}
			}
		}

		// 获取这个数据存储保存的类型名称数组
		// getTypeNames:获取所有地理图层
		String[] names = dataStore.getTypeNames();
		String name = names[0];
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = dataStore.getFeatureSource(name);
		FeatureCollection<SimpleFeatureType, SimpleFeature> result = featureSource.getFeatures();
		// 遍历要素

		WKTWriter2 writer = new WKTWriter2();
		FeatureIterator<SimpleFeature> features = result.features();
		while (features.hasNext()) {
			FeatureDTO featureDTO = new FeatureDTO();
			SimpleFeature feature = features.next();
			// 遍历属性
			Collection<Property> properties = feature.getProperties();
			for (Property property : properties) {
				Object value = property.getValue();
				// 几何
				if (value instanceof Geometry) {
					Geometry geometry = (Geometry) value;

					String wkt = "";
					// 坐标转换
					if (mathTransform != null) {
						Geometry transformGeometry = JTS.transform(geometry, mathTransform);
						Geometry g;
						wkt = writer.write(transformGeometry);
					} else {
						wkt = writer.write(geometry);
					}
					featureDTO.setWkt(wkt);
				}
				// 属性
				else if (!shapeOnly) {
					featureDTO.addAttribute(property.getName().toString(), String.valueOf(value));
				}
			}
			featureClassDTO.add(featureDTO);
		}
		features.close();
		dataStore.dispose();
		return featureClassDTO;
	}


	public static void insertData(String filePath, List<Geometry> geometries, List<Map<String, Object>> attributes)
			throws Exception {

		File shpFile = new File(filePath);
		ShapefileDataStore dataStore = new ShapefileDataStore(shpFile.toURI().toURL());
		dataStore.setCharset(StandardCharsets.UTF_8);

		// 这是最快的写入方式
		try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer = dataStore
				.getFeatureWriter(dataStore.getTypeNames()[0], Transaction.AUTO_COMMIT)) {
			for (Geometry geometry : geometries) {
				int index = geometries.indexOf(geometry);
				SimpleFeature feature = writer.next();
				feature.setAttribute("the_geom", geometry);
				if (attributes != null) {
					Map<String, Object> shpAttributes = attributes.get(index);
					for (String key : shpAttributes.keySet()) {
						try {
							feature.setAttribute(key, shpAttributes.get(key));
						} catch (Exception ex) {

						}
					}
				}
				writer.write();
			}
		}
		dataStore.dispose();
	}

	/**
	 * 创建shapefile
	 *
	 * @param filePath
	 *            路径
	 * @param geometryType
	 *            几何类型
	 * @param fields
	 *            字段类型
	 * @param fieldLengths
	 *            字段长度
	 * @param wkid
	 * @throws Exception
	 */
	public static void makeShapefile(String filePath, String geometryType, Map<String, Class<?>> fields,
			Map<String, Integer> fieldLengths, Integer wkid) throws Exception {
		File shpFile = new File(filePath);

		ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

		Map<String, Serializable> params = new HashMap<>();
		params.put("url", shpFile.toURI().toURL());
		params.put("create spatial index", Boolean.TRUE);

		SimpleFeatureType simpleFeatureType = createFeatureType(geometryType, fields, fieldLengths, wkid);
		ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
		newDataStore.setCharset(StandardCharsets.UTF_8);
		newDataStore.createSchema(simpleFeatureType);
	}

	/**
	 * shapefile构建参数
	 *
	 * @param geometryType
	 *            Point,Polygon,LineString,MultiPolygon
	 * @param wkid
	 *            4528等
	 * @param fields
	 *            字段类型
	 * @param fieldLengths
	 *            字段长度
	 * @return SimpleFeatureType
	 * @throws Exception
	 */
	private static SimpleFeatureType createFeatureType(String geometryType, Map<String, Class<?>> fields,
			Map<String, Integer> fieldLengths, Integer wkid) throws Exception {

		Class typeClass = null;
		if ("Point".equals(geometryType)) {
			typeClass = Point.class;
		} else if ("Polygon".equals(geometryType)) {
			typeClass = Polygon.class;
		} else if ("LineString".equals(geometryType)) {
			typeClass = LineString.class;
		} else if ("MultiLineString".equals(geometryType)) {
			typeClass = MultiLineString.class;
		} else if ("MultiPolygon".equals(geometryType)) {
			typeClass = MultiPolygon.class;
		}
		org.opengis.feature.type.AttributeType a;
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName(geometryType);
		if (wkid != null && !Arrays.asList(GeometryUtil.customWkids).contains(wkid)) {
			try {
				builder.setCRS(CRS.decode("EPSG:" + wkid));

			} catch (Exception ex) {
				// <- Coordinate reference system
			} finally {

			}
		} else {
			if (Arrays.asList(GeometryUtil.customWkids).contains(wkid)) {
				builder.setCRS(CRS.parseWKT(
						"PROJCS[\"CGCS2000_3_Degree_GK_CM_113E\",GEOGCS[\"GCS_China_Geodetic_Coordinate_System_2000\",DATUM[\"D_China_2000\",SPHEROID[\"CGCS2000\",6378137.0,298.257222101]],PRIMEM[\"Greenwich\",0.0],UNIT[\"Degree\",0.0174532925199433]],PROJECTION[\"Gauss_Kruger\"],PARAMETER[\"False_Easting\",700000.0],PARAMETER[\"False_Northing\",0.0],PARAMETER[\"Central_Meridian\",113.0],PARAMETER[\"Scale_Factor\",1.0],PARAMETER[\"Latitude_Of_Origin\",0.0],UNIT[\"Meter\",1.0]]"));
			}
		}

		// add attributes in order
		builder.add("the_geom", typeClass);
		if (fields == null || fields.size() == 0) {
			builder.length(50).add("NAME", String.class);
		} else {
			for (Map.Entry<String, Class<?>> entry : fields.entrySet()) {
				String key = entry.getKey();
				if (fieldLengths != null) {
					Integer length = fieldLengths.get(key);
					if (length != null) {
						builder.length(length).add(key, entry.getValue());
					} else {
						builder.add(key, entry.getValue());
					}
				} else {
					builder.add(key, entry.getValue());
				}
			}
		}

		// build the type
		final SimpleFeatureType simpleFeatureType = builder.buildFeatureType();
		return simpleFeatureType;
	}
}
