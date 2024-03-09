package com.example.common.util;

import com.alibaba.fastjson2.JSON;
import com.example.common.product.HuaWei;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Base64;

/**
 * @author : BG547563
 */
public class ThreadUtil {
    public static void main(String[] args) throws InterruptedException {

        System.out.println(StringUtils.replace("创建失败：PreparedStatementCallback; Data truncation: Data too long for column &#039;itemColor&#039;", "&#039;", "'"));

        System.out.println(StringUtils.replace(null, "&#039;", "'"));

        System.out.println(new String(Base64.getDecoder().decode("ODAzNjc=")));
        HuaWei huaWei = new HuaWei(1);

        new Thread(() -> {
            huaWei.setMeta(2);
        }).start();

        new Thread(() -> {
            huaWei.setMeta(3);
        }).start();

        Thread.sleep(2000L);
        System.out.println(JSON.toJSONString(huaWei));
    }
}
