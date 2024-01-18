package com.example.common.util;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author libai
 */
public class DrawBoxWithTextExample {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            page = document.getPages().get(0);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,
                    false, true);

            float boxX = 100;   // X-coordinate of the box's top-left corner
            float boxY = 500;   // Y-coordinate of the box's top-left corner
            float boxWidth = 120;
            float boxHeight = 60;

            // Draw black rectangle (box)
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(1);
            contentStream.addRect(boxX, boxY, boxWidth, boxHeight);
            contentStream.stroke();

            // 再画一个并排表格 加一条分割线
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(1);
            contentStream.addRect(boxX + boxWidth, boxY, boxWidth, boxHeight);
            contentStream.stroke();

            contentStream.setStrokingColor(Color.BLACK);
            contentStream.moveTo(boxX + boxWidth, boxY + boxHeight / 2);
            contentStream.lineTo(boxX + 2 * boxWidth, boxY + boxHeight / 2);
            contentStream.stroke();

            // 再画一个下并排表格
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(1);
            contentStream.addRect(boxX, boxY - 2 * boxHeight, boxWidth, boxHeight * 2);
            contentStream.stroke();


            contentStream.setStrokingColor(Color.BLACK);
            contentStream.moveTo(5, 5);
            contentStream.lineTo(205, 5);
            contentStream.stroke();

            contentStream.setStrokingColor(Color.BLACK);
            contentStream.moveTo(5, 4);
            contentStream.lineTo(205, 4);
            contentStream.stroke();

            contentStream.setStrokingColor(Color.BLACK);
            contentStream.moveTo(5, 2);
            contentStream.lineTo(205, 2);
            contentStream.stroke();

            // Write text inside the box
            InputStream fontStream = Files.newInputStream(
                    Paths.get("/Users/libai/IdeaProjects/gerp/web/src/main/resources/fonts/AlibabaPuHuiTi-2-55-Regular.ttf"));

            PDType0Font font = PDType0Font.load(document, fontStream);
            contentStream.setFont(font, 12);
            contentStream.setNonStrokingColor(Color.BLACK);

            String[] textLines = { "ÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧ", "ŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſƀƁƂƃƄƅƆƇƈƉƊƋƌƍƎƏƐƑƒƓƔƕƖƗƘƙƚƛƜƝƞƟƠơƢƣƤƥƦƧƨƩƪƫƬƭƮƯưƱƲƳƴƵƶƷƸƹƺƻƼƽƾƿǀǁǂǃǄǅǆǇǈǉǊǋǌǍǎǏǐǑǒǓǔǕǖǗǘǙǚǛǜǝǞǟǠǡǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰǱǲǳǴǵǶǷǸǹǺǻǼǽǾǿ" };
            float textX = boxX + 5;   // X-coordinate for text
            float textY = boxY + 60 - 10;  // Y-coordinate for text

            for (String text : textLines) {
//                float textWidth = PDType1Font.HELVETICA.getStringWidth(text) / 1000 * 12;
//                float textHeight = PDType1Font.HELVETICA.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 12;
//
//                float textWidthTmp = PDType1Font.HELVETICA.getStringWidth(text.trim()) / 1000 * 12;
//                System.out.println("before: " + text);
//                while (textWidthTmp > 110) {
//                    text = text.substring(0, text.length() - 2);
//                    textWidthTmp = PDType1Font.HELVETICA.getStringWidth(text.trim()) / 1000 * 12;
//                }
//                System.out.println("after: " + text);

                contentStream.beginText();
                contentStream.newLineAtOffset(textX, textY);
                contentStream.showText(text);
                contentStream.endText();

                textY -= 12 * 0.75f;  // Move to the next line
                textY -= 12 * 1.2f * 0.75f;
                if (textY < 500) {
                    break;
                }
            }

            contentStream.close();

            document.save("DrawBoxWithTextExample.pdf");
            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}