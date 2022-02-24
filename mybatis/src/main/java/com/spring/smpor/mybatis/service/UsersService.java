package com.spring.smpor.mybatis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.smpor.common.entity.PageEntity;
import com.spring.smpor.mybatis.po.UsersEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fanlz
 * @since 2021-12-22
 */
public interface UsersService extends IService<UsersEntity> {

    PageEntity tablePagingQuery(UsersEntity model);
}
