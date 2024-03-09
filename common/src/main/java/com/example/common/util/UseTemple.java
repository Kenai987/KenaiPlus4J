package com.example.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import javax.imageio.ImageIO;
import com.google.zxing.MultiFormatWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author : BG547563
 */
public class UseTemple {
    private static final float MM_PX = 2.83F;




    public static void main(String[] args) throws IOException {
        mergePDFs(doCreate(),doCreate());
    }

    private static byte[] doCreate() throws IOException {
        String template = "/Users/libai/Downloads/Temu-pro-max.pdf";
        String labelFilePath = "/Users/libai/Downloads/create1.pdf";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("DELIVER_TIME", "2024-02-29:20:30:10");
        paramMap.put("PACKAGE_SN", "2131231231231312");
        paramMap.put("PACKAGE_SN_1", "2131231231231312");
        paramMap.put("PACKAGE_INDEX", "1");
        paramMap.put("TOTAL_PACKAGE_NUM", "8");
        paramMap.put("SIX_SLOT", "JIT");
        paramMap.put("URGENCY", "加急");
        paramMap.put("WAREHOUSE_NAME", "亚瑟云仓");
        paramMap.put("SUPPLIER_NAME", "淘宝特工队");
        paramMap.put("PRODUCT_SKU_ID", "张三");
        paramMap.put("PRODUCT_SKU_EXT_CODE", "张三");
        paramMap.put("PACKAGE_SKU_NUM", "张三");
        paramMap.put("XV_ONE", "张三");
        paramMap.put("XV_TWO", "张三");
        paramMap.put("XV_THREE", "张三");
        paramMap.put("PRODUCT_NAME", "我发i哦嗨哦hero i啊合格i哦呃啊不够啊保额hi发饿哦i过哈恶搞哈诶哦给孩哦额哈嘎哈格林卡鹅回家饿哦爱好高IE韩国i奥厄个回笼觉啊是刚开始进入皇宫企鹅后i·饿啊酷黑u哦给哈开机后五花肉i好");
        List<Image> imageList = new ArrayList<>();


        ImageData tnImageData = ImageDataFactory.create(generateBarCode128("101ewewqeqeq111", 200, calculateBarcodeHeight("sadasdwe1eawaewea".length())));
        Image bookingCodeBarCode = new Image(tnImageData);
        bookingCodeBarCode.scaleAbsolute(250, 25);
        bookingCodeBarCode.setFixedPosition(4 * MM_PX, 81 * MM_PX);
        imageList.add(bookingCodeBarCode);

        ImageData trackingNumberImageData = ImageDataFactory.create(generateBarCode128("eqweqweqweqweqwewqeqweqweqwe23123", 100, calculateBarcodeHeight("ewarefafeaf".length())));
        Image trackingNumberBarCode = new Image(trackingNumberImageData);
        trackingNumberBarCode.scaleAbsolute(150, 30);
        trackingNumberBarCode.setFixedPosition(45 * MM_PX, 6 * MM_PX);
        imageList.add(trackingNumberBarCode);

        PdfFont font = PdfFontFactory.createFont(Objects.requireNonNull(new File("/Users/libai/IdeaProjects/KenaiPlus4J/AlibabaPuHuiTi-2-55-Regular.ttf")).getPath(), PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_NOT_EMBEDDED);
        return fillTemplate(template, labelFilePath, paramMap, imageList, font);
    }


    public static byte[] fillTemplate(String template, String dirPath, Map<String, String> parameterMap, List<Image> imageList,
                                    PdfFont font)
            throws IOException {
        // --按照模板及填写数据生成PDF
//        PdfReader reader = new PdfReader(Objects.requireNonNull(UseTemple.class.getResourceAsStream(template)));
        PdfReader reader = new PdfReader(Files.newInputStream(Paths.get(template)));
        PdfDocument pdfDoc = new PdfDocument(reader);
        Document doc = new Document(pdfDoc);
        File pdfFile = getPdfFile(dirPath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        FileOutputStream fos = new FileOutputStream(pdfFile);
        PdfDocument copyPdfDoc = new PdfDocument(new PdfWriter(byteArrayOutputStream));
        copyPdfDoc.initializeOutlines();
        PdfPageFormCopier formCopier = new PdfPageFormCopier();
        pdfDoc.copyPagesTo(1, pdfDoc.getNumberOfPages(), copyPdfDoc, formCopier);
        PdfAcroForm form = PdfAcroForm.getAcroForm(copyPdfDoc, true);
        Map<String, PdfFormField> fieldMap = form.getFormFields();
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            Optional.ofNullable(fieldMap.get(entry.getKey())).ifPresent(field -> {
                // --特殊语言处理（泰文等）
                if (font != null) {
                    field.setFont(font);
                    field.setFontSize(10);
                }
                field.setValue(entry.getValue());
            });
        }
        form.flattenFields();
        if (CollectionUtil.isNotEmpty(imageList)) {
            Document newDoc = new Document(copyPdfDoc);
            imageList.forEach(newDoc::add);
            newDoc.close();
        }
        doc.close();
        copyPdfDoc.close();
        byteArrayOutputStream.close();
       return byteArrayOutputStream.toByteArray();
    }
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
            mergedDocument.save("table-x-pro-1.pdf");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File getPdfFile(String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs() && !file.getParentFile().exists()) {
                return null;
            }
        }
        return file;
    }

    public static byte[] generateBarCode128(String message, int width, int height) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            //配置条码参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //设置条码两边空白边距为0，默认为10，如果宽度不是条码自动生成宽度的倍数则MARGIN无效
            hints.put(EncodeHintType.MARGIN, 0);
            //条码放大倍数
            int codeMultiples = 1;
            //获取条码内容的宽，不含两边距，当EncodeHintType.MARGIN为0时即为条码宽度
            int codeWidth = width * codeMultiples;
            // 图像数据转换，使用了矩阵转换 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, codeWidth, height, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(image, "png", os);
        } catch (IOException | WriterException ignored) {
        }
        return os.toByteArray();
    }

    public static int calculateBarcodeHeight(Integer x) {
        if (null == x) {
            return 0;
        }
        // 条形码高度 y=0.08x^2+1.25x
        BigDecimal a = new BigDecimal("0.08");
        BigDecimal b = new BigDecimal("1.25");
        return a.multiply(BigDecimal.valueOf(x)).multiply(BigDecimal.valueOf(x)).add(b.multiply(BigDecimal.valueOf(x))).intValue();
    }
}
