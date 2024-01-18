package com.example.common.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * @author : BG547563
 */
public class pdfUtils {
    public static void main(String[] args) throws IOException {
        // 读取本地文件
//        String filePath = "/Users/libai/Downloads/N2Y0Y2JjMzMzNzlkMTg4ZjU0Mjc3MWFjYWRiMDE2ODk=.pdf";
        String filePath = "/Users/libai/Downloads/zenggao100danwei.pdf";
        // 读取到内存
        byte[] labelBytes = FileUtil.readBytes(filePath);

        PDDocument doc = PDDocument.load(labelBytes);
        PDDocument docNew = new PDDocument();
        float pageHeight = 0f;
        float pageWidth = 0f;
        for (int p = 0; p < doc.getPages().getCount(); p++) {
            // 获取原始文档的第p页
            PDPage page = doc.getPages().get(p);
            // 获取页面的媒体框（也称为页面边界）
            PDRectangle rectangle = page.getMediaBox();
            // 获取当前页面的宽度
            pageWidth = rectangle.getWidth();
            // 模版高度加可用高度等于免单高度
            pageHeight = rectangle.getHeight() + 100;

            COSDictionary pageConfig = getPageNewDict(page, 100);
            PDPage pageNew = new PDPage(pageConfig);
            docNew.addPage(pageNew);

        }

        String outFilePath = "/Users/libai/Downloads/new7zenggao100danwei.pdf";

        File file = new File(outFilePath);

        FileOutputStream fos = new FileOutputStream(file);
        docNew.save(fos);
    }

//    private static COSDictionary getPageNewDict(PDPage page, float appendAreaHeight) {
//        COSDictionary pageDict = page.getCOSObject();
//        COSDictionary pageNewDict = new COSDictionary();
//
//        for (Map.Entry<COSName, COSBase> cosBase : pageDict.entrySet()) {
//            if (COSName.CROP_BOX.equals(cosBase.getKey())) {
//                COSArray cosArray = (COSArray) cosBase.getValue();
//                COSArray cosArrayNew = new COSArray();
//
//                // 获取原始 Y 轴底部坐标
//                float originalBottomY = ((COSNumber) cosArray.get(1)).floatValue();
//
//                for (int i = 0; i < cosArray.size(); i++) {
//                    if (i == 1) {
//                        COSBase cosBaseY = cosArray.get(i);
//                        if (cosBaseY instanceof COSFloat) {
//                            COSFloat floatY = (COSFloat) cosArray.get(i);
//                            // 计算新的 Y 轴底部坐标
//                            COSFloat floatYNew = new COSFloat(floatY.floatValue() - appendAreaHeight - originalBottomY);
//                            cosArrayNew.add(floatYNew);
//                        } else if (cosBaseY instanceof COSInteger) {
//                            COSInteger integerY = (COSInteger) cosArray.get(i);
//                            // 计算新的 Y 轴底部坐标
//                            COSInteger integerYNew = COSInteger.get(integerY.intValue() - (long) appendAreaHeight - (long) originalBottomY);
//                            cosArrayNew.add(integerYNew);
//                        }
//                    } else {
//                        cosArrayNew.add(cosArray.get(i));
//                    }
//                }
//                pageNewDict.setItem(cosBase.getKey(), cosArrayNew);
//            } else {
//                pageNewDict.setItem(cosBase.getKey(), cosBase.getValue());
//            }
//        }
//
//        return pageNewDict;
//    }


    private static COSDictionary getPageNewDict(PDPage page, float appendAreaHeight) {
        //获取原始页面字典信息：通过 getCOSObject() 方法，获取输入 PDPage 对象的底层 COS 字典。
        COSDictionary pageDict = page.getCOSObject();
        PDRectangle mediaBox = page.getMediaBox();
        PDRectangle cropBox = page.getCropBox();
        // 创建一个新的 COS 字典，用于存储修改后的页面信息。
        COSDictionary pageNewDict = new COSDictionary();
        // 使用 entrySet() 获取原始页面字典的条目集合，并通过 for 循环遍历每个条目。
        for (Map.Entry<COSName, COSBase> cosBase : pageDict.entrySet()) {
            // 判断当前条目的键是否为 CROP_BOX 或者 MEDIA_BOX，这是页面框架相关的信息。
            if (COSName.CROP_BOX.equals(cosBase.getKey()) || COSName.MEDIA_BOX.equals(cosBase.getKey())) {
                COSArray cosArray = (COSArray) cosBase.getValue();
                COSArray cosArrayNew = new COSArray();
                for (int i = 0; i < cosArray.size(); i++) {
                    if (i == 1) {
                        // 当处理到 Y 坐标（数组的第二个元素）时，根据 Y 坐标的类型（COSFloat 或 COSInteger），分别进行相应的处理。这里通过减去 appendAreaHeight，将 Y 坐标向上移动，以留出附加内容的空间。
                        COSBase cosBaseY = cosArray.get(i);
                        if (cosBaseY instanceof COSFloat) {
                            COSFloat floatY = (COSFloat) cosArray.get(i);
                            System.out.println("网上平移的Y坐标：" + JSON.toJSONString(floatY.floatValue()));
                            COSFloat floatYNew = new COSFloat(floatY.floatValue() - appendAreaHeight);
                            cosArrayNew.add(floatYNew);
                        }
                        else if (cosBaseY instanceof COSInteger) {
                            COSInteger integerY = (COSInteger) cosArray.get(i);
                            System.out.println("网上平移的Y坐标：" + JSON.toJSONString(integerY.intValue()));
                            COSInteger integerYNew = COSInteger.get(integerY.intValue() - (long) appendAreaHeight);
                            cosArrayNew.add(integerYNew);
                        }
                    }
                    else {
                        cosArrayNew.add(cosArray.get(i));
                    }
                }
                pageNewDict.setItem(cosBase.getKey(), cosArrayNew);
            }
            else {
                pageNewDict.setItem(cosBase.getKey(), cosBase.getValue());
            }
        }
        return pageNewDict;
    }
}
