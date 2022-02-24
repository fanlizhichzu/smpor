package com.spring.smpor.mybatis.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.smpor.common.annotation.ResponseResult;
import com.spring.smpor.common.entity.PageEntity;
import com.spring.smpor.mybatis.po.UsersEntity;
import com.spring.smpor.mybatis.service.UsersService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fanlz
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/mybatis/users")
@ResponseResult
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(value = "/getList")
    public PageEntity getTableList(@RequestBody UsersEntity model) {
        return usersService.tablePagingQuery(model);
    }
}
