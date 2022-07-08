package com.spring.smpor.gistutorial.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 编辑接口
 *
 * @author fanlz
 * @date 2022/05/31 14:21
 **/
@Mapper
@Service
public interface EditorDao {
    String selectSeqName(@Param("table") String table, @Param("pk") String pk);

    void insert(Map sql);
}
