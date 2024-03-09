package com.example.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author : BG547563
 */
public class MergePDF {
    public static void mergePDFs(byte[] pdf1, byte[] pdf2) throws IOException {
        try (PDDocument document1 = PDDocument.load(new ByteArrayInputStream(pdf1));
             PDDocument document2 = PDDocument.load(new ByteArrayInputStream(pdf2))) {

            // 创建一个新文档用于存储合并后的结果
            PDDocument mergedDocument = new PDDocument();

            // 合并第一个文档的页面
            for (PDPage page : document1.getPages()) {
                mergedDocument.addPage(page);
            }

            // 合并第二个文档的页面
            for (PDPage page : document2.getPages()) {
                mergedDocument.addPage(page);
            }

            // 保存合并后的文档
            mergedDocument.save("table-x-pro.pdf");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            // 假设你有两个 PDF 文件的字节数组 pdfBytes1 和 pdfBytes2
            byte[] pdfBytes1 = getFirstPDFBytes();
            byte[] pdfBytes2 = getSecondPDFBytes();

            // 合并两个 PDF 文件
            mergePDFs(pdfBytes1, pdfBytes2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 用于获取第一个 PDF 文件的字节数组的示例方法
    private static byte[] getFirstPDFBytes() throws IOException {
        // 替换为实际的获取第一个 PDF 文件字节数组的逻辑
        Path path = Paths.get("/Users/libai/IdeaProjects/KenaiPlus4J/table.pdf");
        return Files.readAllBytes(path);
    }

    // 用于获取第二个 PDF 文件的字节数组的示例方法
    private static byte[] getSecondPDFBytes() throws IOException {
        // 替换为实际的获取第二个 PDF 文件字节数组的逻辑
        Path path = Paths.get("/Users/libai/IdeaProjects/KenaiPlus4J/table1.pdf");
        return Files.readAllBytes(path);
    }
}
