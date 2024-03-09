package com.example.common.util;

import java.util.HashMap;
import java.util.Map;

public class FieldAllocationCalculator {
    public static Map<String, Double> calculateFieldAllocations(int x, boolean no, boolean info, boolean qty) {
        Map<String, Double> allocations = new HashMap<>();

        if (no && info && qty) {
            // 同时存在no, info, qty的情况
            double totalAllocation = 1 + 8 + 1;
            allocations.put("no", x * (1.0 / totalAllocation));
            allocations.put("info", x * (8.0 / totalAllocation));
            allocations.put("qty", x * (1.0 / totalAllocation));
        } else if ((no && info) || (qty && info)) {
            // 同时存在no, info或者qty, info的情况
            double totalAllocation = 1 + 10;
            allocations.put("no", x * (1.0 / totalAllocation));
            allocations.put("info", x * (10.0 / totalAllocation));
        } else if (info) {
            // 只有info字段存在的情况
            allocations.put("info", (double) x);
        } else if (no || qty) {
            // qty或no单独出现的情况，或者同时存在qty和no的情况
            double totalAllocation = 1 + 8 + 1;
            allocations.put("no", x * (1.0 / totalAllocation));
            allocations.put("info", x * (8.0 / totalAllocation));
            allocations.put("qty", x * (1.0 / totalAllocation));
        }

        return allocations;
    }

    public static void main(String[] args) {
        int x = 100;
        boolean no = true;
        boolean info = true;
        boolean qty = true;

        Map<String, Double> allocations = calculateFieldAllocations(x, no, info, qty);
        for (Map.Entry<String, Double> entry : allocations.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
