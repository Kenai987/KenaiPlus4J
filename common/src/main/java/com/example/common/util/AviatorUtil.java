package com.example.common.util;

import com.alibaba.fastjson2.JSON;
import com.example.common.product.HuaWei;
import com.example.common.product.Phone;
import com.googlecode.aviator.AviatorEvaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : BG547563
 */
public class AviatorUtil {
    public static void main(String[] args) {

        Map<Long,Map<Long,Phone>>  map = new HashMap<>();

        Map<Long,Phone> map1 = new HashMap<>();
        map1.put(1L,new Phone());
        map.put(1L,map1);

        System.out.println();
        String r = (String) AviatorEvaluator.execute("'hello' + ' world'");

        Double num = (Double) AviatorEvaluator.execute("100 * 23.231/34223+421");
        System.out.println(num);


        Map<String, Object> env = new HashMap<>();
        env.put("a", 20L);
        Object result = AviatorEvaluator.execute("a", env);
        System.out.println(result.toString());
    }

    private static void sout(Map<Long,Map<Long,Phone>>  map){
        System.out.println();
    }
}
