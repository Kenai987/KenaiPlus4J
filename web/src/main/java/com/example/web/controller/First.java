package com.example.web.controller;

import com.alibaba.fastjson2.JSON;
import com.example.dao.redis.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author libai
 */
@Slf4j
@RequestMapping("/call")
@RestController
public class First {

    @Resource
    private RedisDao redisDao;

    @GetMapping("/me/{keyword}")
    public String query(@PathVariable String keyword) {
        return JSON.toJSONString(redisDao.getPhoneByType(keyword));
    }

    @GetMapping("/put/{keyword}/{value}")
    public void put(@PathVariable String keyword, @PathVariable String value) {
        redisDao.put(keyword, value);
    }

    @GetMapping("/get/{keyword}")
    public String get(@PathVariable String keyword) {
        return redisDao.get(keyword);
    }
}
