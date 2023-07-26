package com.example.web.controller;

import com.alibaba.fastjson2.JSON;
import com.example.common.product.Phone;
import com.example.dao.redis.RedisDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author libai
 */
@RequestMapping("/call")
@RestController
public class First {

    @Resource
    private RedisDao redisDao;

    @GetMapping("/me/{keyword}")
    public String query(@PathVariable String keyword) {
        return JSON.toJSONString(redisDao.getPhoneByType(keyword));
    }
}
