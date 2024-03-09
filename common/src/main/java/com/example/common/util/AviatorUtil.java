package com.example.common.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.example.common.product.HuaWei;
import com.example.common.product.Phone;
import com.googlecode.aviator.AviatorEvaluator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author : BG547563
 */
public class AviatorUtil {
    public static void main(String[] args) {
        String r = (String) AviatorEvaluator.execute("'hello' + ' world'");
        System.out.println(r);
        Double num = (Double) AviatorEvaluator.execute("100 * 23.231/34223+421");
        System.out.println(num);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 20L);
        Object result = AviatorEvaluator.execute("a", env);
        System.out.println(result.toString());
        System.out.println(Collections.unmodifiableSet(new HashSet<>(Arrays.asList("1", "2"))).contains(null));
    }
}
