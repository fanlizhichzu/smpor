package com.spring.smpor.gistutorial.mapper;

import com.spring.smpor.gistutorial.entity.TableMetaData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公共类
 *
 * @author fanlz
 * @date 2022/05/31 10:56
 **/
@Mapper
@Service
public interface CommonDao {

    String getGeomType(@Param("schema") String schema, @Param("table") String table);

    String getGeomColumn(@Param("schema") String schema, @Param("table") String table);

    Integer getSrid(String schema, String table, String geomColumn);

    List<TableMetaData> getTableMetadata(String geotable);

    String getPrimaryKey(String schema, String table);
}
