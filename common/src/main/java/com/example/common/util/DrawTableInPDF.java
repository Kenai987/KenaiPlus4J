package com.example.common.util;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawTableInPDF {
    public static void main(String[] args) {
        try {

            List<String> strings = new ArrayList<>();
            strings.add("a");
            strings.add("v");
            strings.add("vc");
            strings = strings.subList(2,strings.size());
            System.out.println(strings);
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            String[] dataArray = {"Row 1, Col 1", "Row 1, Col 2", "Row 2, Col 1","12", "Row 2, Col 2"};

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;

            int numRows = dataArray.length / 2 + dataArray.length % 2;

            float rowHeight = 20f;
            float tableHeight = rowHeight * numRows;
            float yPosition = yStart - tableHeight;

            int cols = 2;

            float colWidth = tableWidth / (float) cols;
            float tableWidthLeft = colWidth;
            float tableWidthRight = colWidth;

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            for (int i = 0; i < numRows; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);

                if (i * 2 < dataArray.length) {
                    contentStream.showText(dataArray[i * 2]);
                }

                contentStream.endText();

                if ((i * 2 + 1) < dataArray.length) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + tableWidthLeft, yPosition);
                    contentStream.showText(dataArray[i * 2 + 1]);
                    contentStream.endText();
                }

                // 绘制横向表格线
                contentStream.moveTo(margin, yPosition - 2);
                contentStream.lineTo(margin + tableWidth, yPosition - 2);
                contentStream.stroke();

                yPosition -= rowHeight;
            }

            // 绘制纵向表格线
            for (int i = 0; i <= cols; i++) {
                float x = margin + i * colWidth;
                contentStream.moveTo(x, yStart);
                contentStream.lineTo(x, yPosition);
                contentStream.stroke();
            }

            contentStream.close();

            document.save("table.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
