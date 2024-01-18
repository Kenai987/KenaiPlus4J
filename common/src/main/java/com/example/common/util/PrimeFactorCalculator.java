package com.example.common.util;

import com.alibaba.fastjson2.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class PrimeFactorCalculator {

    public static void main(String[] args) {

        Set<Integer> array1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> array2 = Arrays.asList(3, 4, 5);
        List<Integer> array3 = Arrays.asList(4, 5, 6, 7);
        List<Integer> array4 = Collections.emptyList();

//
//        array1.retainAll(array2);
//
//        array1.retainAll(array3);

        array1.retainAll(array4);


        System.out.println(array1);

    }

    static class PrimeFactorTask extends RecursiveTask<Long> {
        private long number;

        private long start;

        private long end;

        PrimeFactorTask(long number, long start, long end) {
            this.number = number;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start < 10000) { // 如果范围足够小，直接计算
                long sum = 0;
                for (long i = start; i <= end; i++) {
                    if (isPrime(i)) {
                        sum += i;
                    }
                }
                return sum;
            }
            else {
                long middle = (start + end) / 2;
                PrimeFactorTask left = new PrimeFactorTask(number, start, middle);
                PrimeFactorTask right = new PrimeFactorTask(number, middle + 1, end);
                left.fork();
                long rightResult = right.compute();
                long leftResult = left.join();
                return leftResult + rightResult;
            }
        }

        private boolean isPrime(long n) {
            if (n <= 1) {
                return false;
            }
            if (n <= 3) {
                return true;
            }
            if (n % 2 == 0 || n % 3 == 0) {
                return false;
            }
            for (long i = 5; i * i <= n; i += 6) {
                if (n % i == 0 || n % (i + 2) == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
