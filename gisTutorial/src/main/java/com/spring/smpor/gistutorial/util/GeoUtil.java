package com.spring.smpor.gistutorial.util;

import lombok.extern.slf4j.Slf4j;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * geotools工具类
 *
 * @author fanlz
 * @date 2022/05/30 09:21
 **/
@Slf4j
public class GeoUtil {

    public GeoUtil() {
        // TODO document why this constructor is empty
    }

    /**
     * @param name 名称
     * @param crs 坐标系
     * @param binding point/line/polygon
     * @return org.opengis.feature.simple.SimpleFeatureType
     * @Description: 创建要素类型
     * @Auther: fanlz
     * @date 2022/05/30 09:31
     */
    private static SimpleFeatureType createFeatureType(String name, CoordinateReferenceSystem crs, Class<?> binding) {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName(name);
        builder.setCRS(crs); // <- Coordinate reference system

        // add attributes in order
        builder.add("the_geom", binding);
        builder.length(15).add("Name", String.class); // <- 15 chars width for name field
        builder.add("number", Integer.class);

        // build the type
        return builder.buildFeatureType();
    }
}
