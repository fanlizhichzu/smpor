package com.spring.smpor.saToken.controller;

import com.spring.smpor.common.annotation.ResponseResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ceshi
 *
 * @author fanlz
 * @date 2022/05/24 13:29
 **/
@RestController
@RequestMapping("/Redis")
@ResponseResult
public class RedisController {
    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @PostMapping(value = "/set")
    public void stringIntoTest(){
        String key = "fanlz";
        String value = "GIS开发";
        redisTemplate.opsForValue().set(key,value);
    }
}
