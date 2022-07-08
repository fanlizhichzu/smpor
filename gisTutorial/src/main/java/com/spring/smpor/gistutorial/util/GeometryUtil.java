package com.spring.smpor.gistutorial.util;

import com.alibaba.fastjson.JSONArray;
import com.vividsolutions.jts.io.ParseException;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.*;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swd on 2018/10/24
 */
public class GeometryUtil {

	public static Integer[] customWkids={454700};
	public static void sortWKT(List<String> wktList, List<Geometry> points, List<Geometry> lineStrings,
			List<Geometry> polygons) throws ParseException, org.locationtech.jts.io.ParseException {
		WKTReader2 reader = new WKTReader2();
		for (String wkt : wktList) {
			Geometry geometryFromWKT = reader.read(wkt);
			String geometryType = geometryFromWKT.getGeometryType();
			if ("Point".equals(geometryType)) {
				points.add(geometryFromWKT);
			}
			if ("LineString".equals(geometryType) || "MultiLineString".equals(geometryType)) {
				lineStrings.add(geometryFromWKT);
			}
			if ("Polygon".equals(geometryType) || "MultiPolygon".equals(geometryType)) {
				polygons.add(geometryFromWKT);
			}
		}
	}

	public static double getArea(String wkt, Integer wkid, Integer wkidProject) throws Exception {
		WKTReader2 reader = new WKTReader2();
		Geometry geometryFromWKT = reader.read(wkt);
		return getArea(geometryFromWKT, wkid, wkidProject);
	}

	public static double getArea(List<String> wkts, Integer wkid, Integer projectWkid) throws Exception {
		double sum = 0;
		WKTReader2 reader = new WKTReader2();
		for (String it : wkts) {
			Geometry geometry = reader.read(it);
			sum += getArea(geometry, wkid, projectWkid);
		}
		return sum;
	}

	public static double getArea(Geometry geometry, Integer wkid, Integer projectWkid)
			throws Exception {
		Geometry geome = projectGeometry(geometry, wkid, projectWkid);
		return geome.getArea();
	}

	public static Geometry projectGeometry(Geometry geometry, Integer wkid,
                                           Integer projectWkid) throws Exception {
		if (!wkid.equals(projectWkid)) {
			CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:" + wkid, true);
			CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:" + projectWkid, true);
			MathTransform mathTransform = CRS.findMathTransform(sourceCRS, targetCRS);
			geometry = JTS.transform(geometry, mathTransform);
		}
		return geometry;

	}

	/**
	 * geojson数组转geometry对象
	 *
	 * @param geojsonArray
	 * @return
	 * @throws IOException
	 */
	public static List<Geometry> geomFromGeoJson(JSONArray geojsonArray) throws IOException {
		if (geojsonArray == null || geojsonArray.size() == 0)
			return null;
		GeometryJSON geometryJson = new GeometryJSON(6);
		List<Geometry> geometryList = new ArrayList<>();
		for (int i = 0; i < geojsonArray.size(); i++) {
			Reader reader = new StringReader(geojsonArray.getString(i));
			Geometry geo = geometryJson.read(reader);
			geometryList.add(geo);
		}
		return geometryList;
	}

	/**
	 * 多个geom合并成一个
	 *
	 * @param geoms
	 * @return
	 */
	public static Geometry union(List<Geometry> geoms) {
		if (geoms == null || geoms.size() == 0) {
			return null;
		}
		Geometry unionGeom = null;
		for (Geometry geom : geoms) {
			if (geom == null || geom.isEmpty())
				continue;
			if (unionGeom == null)
				unionGeom = geom;
			else
				unionGeom = unionGeom.union(geom);
		}
		return unionGeom;
	}

	/**
	 * geometry转geojson
	 *
	 * @param geom
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String geomAsGeojson(Geometry geom) throws IOException {
		GeometryJSON geometryJson = new GeometryJSON(6);
		StringWriter writer = new StringWriter();
		geometryJson.write(geom, writer);
		return writer.toString();
	}

	/**
	 * 使用jts的两线相交方法解决线的自相交问题
	 * @param geometry1
	 * @return
	 */
	public static Polygon getPolygonByInteriorPoint(Polygon geometry1) {
		LineString exteriorRing = geometry1.getExteriorRing();
		Coordinate[] coordinates = exteriorRing.union(exteriorRing).getCoordinates();
		return geometry1.getFactory().createPolygon(coordinates);
	}

	public static void makeNoIntersect(Geometry geometry1){
		if(geometry1 instanceof MultiPolygon){
			int numGeometries = geometry1.getNumGeometries();
			List<Polygon> polygons=new ArrayList<>();
			for(int i=0;i<numGeometries;i++){
				Geometry geometryN = geometry1.getGeometryN(i);
				Polygon polygon = getPolygonByInteriorPoint((Polygon) geometryN);
				polygons.add(polygon);
			}
			geometry1=geometry1.getFactory().createMultiPolygon(polygons.toArray(new Polygon[]{}));
		}else if(geometry1 instanceof  Polygon){
			geometry1 = getPolygonByInteriorPoint((Polygon) geometry1);
		}
	}

}
