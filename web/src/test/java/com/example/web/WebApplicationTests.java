package com.example.web;

import com.example.common.product.HuaWei;
import com.example.common.product.Phone;
import com.example.dao.redis.RedisDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<HuaWei> huaWeiList = new ArrayList<>();
        huaWeiList.add(new HuaWei(1));
        huaWeiList.add(new HuaWei(21));
        huaWeiList.add(new HuaWei(231));
        huaWeiList.add(new HuaWei(31));
        huaWeiList.add(new HuaWei(51));
        logger.info("huaWei:{}", CollectionUtils.isEmpty(huaWeiList) ? null : huaWeiList.stream().map(HuaWei::toString).collect(Collectors.joining(",")));
        logger.info("我是info日志");
        logger.warn("我是warn日志");
        logger.error("我是error日志");

    }

}
