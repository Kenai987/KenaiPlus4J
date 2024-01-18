package com.example.common.util;

import com.google.common.collect.HashMultiset;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author : BG547563
 */
public class GuavaUtil {
    public static void main(String[] args) {
        HashMultiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");

        System.out.println(multiset.size());
        System.out.println(multiset.count("a"));
    }
}
