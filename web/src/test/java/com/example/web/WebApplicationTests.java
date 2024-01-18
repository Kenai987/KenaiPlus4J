package com.example.web;

import com.example.common.product.Phone;
import com.example.dao.redis.RedisDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    //创建日志对象
    Logger logger = LogManager.getLogger(this.getClass());

    @Test
    void logTest() {
        logger.info("我是info日志");
        logger.warn("我是warn日志");
        logger.error("我是error日志");

    }

}
