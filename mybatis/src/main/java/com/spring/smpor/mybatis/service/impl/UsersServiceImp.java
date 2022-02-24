package com.spring.smpor.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.spring.smpor.common.entity.PageEntity;
import com.spring.smpor.mybatis.po.UsersEntity;
import com.spring.smpor.mybatis.mapper.UsersDao;
import com.spring.smpor.mybatis.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fanlz
 * @since 2021-12-22
 */
@Service
public class UsersServiceImp extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    private final UsersDao usersDao;

    public UsersServiceImp(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public PageEntity tablePagingQuery(UsersEntity model) {
        PageEntity result = new PageEntity();
        com.github.pagehelper.Page<?> page = PageHelper.startPage(model.getCurrent(),model.getSize());
        List<UsersEntity> list = this.baseMapper.searchDataLike(model);
        result.setData(list);
        result.setCurrent(page.getPageNum());
        result.setSize(page.getPageSize());
        result.setTotal(page.getTotal());
        return result;
    }
}

