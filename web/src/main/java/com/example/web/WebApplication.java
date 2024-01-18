package com.example.web;

import com.example.common.product.Phone;
import com.example.dao.redis.RedisDao;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author libai
 */
// 默认只扫描启动类所在的包及其子包
@SpringBootApplication
// 扩展并覆盖SpringBootApplication 扩展到多module
@ComponentScan(basePackages = { "com.example" })
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
