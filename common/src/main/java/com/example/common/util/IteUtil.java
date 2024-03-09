package com.example.common.util;

import com.example.common.product.HuaWei;
import com.example.common.product.Phone;
import lombok.var;
import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : BG547563
 */
public class IteUtil {
    public static void main(String[] args) {
        String str = "Đồ chơi Súng Phun Nước Tự Động cho Trẻ Em Nhà Cá, Đồ Chơi Trẻ Em Sung Nước Có Sẵn (M416 Xanh)";
        str = str.trim();

        List<Long> res = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            // 3584 - 3711
            // 7680 - 7935
            // 8192 - 8207
            // 拉丁文扩展
            if ((a >= 0x0E00 && a <= 0x0E7F) || (a >= 0x1E00 && a <= 0x1EFF)
                    // 零宽字符
                    || (a >= 0x2000 && a <= 0x200F)
                    || a >= 32 && a <= 126
            ) {

            } else {
                System.out.println(a);
                res.add((long) a);
                System.out.println((long) a);
            }
        }
        System.out.println(res.stream().sorted().collect(Collectors.toList()));
        StringBuilder str1 = new StringBuilder();
        for (int i = 255; i <= 511; i++) {
            str1.append((char) i);
        }
        System.out.println(str1.toString());
    }
}

// 255 511
