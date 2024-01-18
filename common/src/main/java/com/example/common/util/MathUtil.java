package com.example.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : BG547563
 */
public class MathUtil {
    public static void main(String[] args) {
        BigDecimal one = new BigDecimal(10);
        BigDecimal two = new BigDecimal(2);
        boolean b = one.compareTo(two) > 0;
        System.out.println(b);
        one = one.add(two);
        System.out.println(one);

        Long a = 6971476260715499520L;

        System.out.println(a);

        System.out.println(Long.MAX_VALUE);

        List<String> strList = new ArrayList<>();
        strList.add("a");
        strList.add("b");
        strList.add("c");
        strList.add("d");
        strList.add("e");
        strList.add("f");
        strList.add("h");
        strList.add("g");
        strList.add("i");
        strList.add("j");

        System.out.println(strList.parallelStream().filter(s -> s.contains("a")).findAny().get());
        System.out.println(strList.parallelStream().findAny().get());
        System.out.println(strList.parallelStream().findAny().get());
        System.out.println(strList.parallelStream().findAny().get());
        System.out.println(strList.parallelStream().findAny().get());
        System.out.println(strList.parallelStream().findAny().get());


    }
}
