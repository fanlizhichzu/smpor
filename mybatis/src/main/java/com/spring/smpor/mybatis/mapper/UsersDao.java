package com.spring.smpor.mybatis.mapper;

import com.spring.smpor.mybatis.po.UsersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fanlz
 * @since 2021-12-22
 */
@Mapper
public interface UsersDao extends BaseMapper<UsersEntity> {

    List<UsersEntity> searchDataLike(final UsersEntity model);
}
