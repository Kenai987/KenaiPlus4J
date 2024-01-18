package com.example.common.util;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author libai
 */
public class Utf8Mb4Filter {

    static final int LAST_BMP = 0xFFFF;

    public static String filterUtf8mb4(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            int codePoint = str.codePointAt(i);
            if (codePoint < LAST_BMP) {
                sb.appendCodePoint(codePoint);
            }
            else {
                i++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String utf8mb4Text = "Your UTF-8 👍mb4 text here  😄🔋4⃣️";
        String filteredText = filterUtf8mb4(utf8mb4Text);

        System.out.println("Original Text: " + utf8mb4Text);
        System.out.println("Filtered Text: " + filteredText);
    }
}

