package com.example.web;

import com.example.common.product.Phone;
import com.example.dao.redis.RedisDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WebApplicationTests {

    @Resource
    private RedisDao redisDao;

    @Test
    void contextLoads() {
        Phone test = redisDao.getPhoneByType("test");
        System.out.println(test);
    }

}
