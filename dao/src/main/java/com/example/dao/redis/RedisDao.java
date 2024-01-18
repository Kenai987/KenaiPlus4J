package com.example.dao.redis;

import com.example.common.product.Phone;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author libai
 */
@Repository
public class RedisDao {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    public Phone getPhoneByType(String type) {
        String phoneStr = redisTemplate.opsForValue().get(type);
        if (StringUtils.hasLength(phoneStr)) {
            redisTemplate.opsForValue().set(type, type);
        }
        Phone phone = new Phone();
        phone.setType(phoneStr);
        return phone;
    }

    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
