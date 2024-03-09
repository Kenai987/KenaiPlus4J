package com.example.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : BG547563
 */
public class ExcelUtil {

    private static final String EXCEL_NAME = "Styled导入模板.xlsx";

    public static void main(String[] args) {
//        copyExcel();
        // 解析
        parseCustomExcel();
        // 生成自定义字段excel
        // creatCustomExcel();
    }

    private static void parseCustomExcel() {
        try {
            // 打开要解析的Excel文件
            FileInputStream inputStream = new FileInputStream(EXCEL_NAME);
            Workbook workbook = new XSSFWorkbook(inputStream);

            // 获取工作表
            // 假设导入模板在第一个工作表中
            Sheet sheet = workbook.getSheetAt(0);

            // 获取表头行
            Row headerRow = sheet.getRow(0);

            // 创建一个数组来存储表头字段
            String[] headerFields = new String[headerRow.getLastCellNum()];

            // 遍历表头行，获取表头字段
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    headerFields[i] = cell.getStringCellValue();
                }
            }

            // 存储解析后的数据
            List<List<String>> data = new ArrayList<>();

            // 从表头下一行开始遍历数据行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);

                // 创建一个数组来存储一行数据
                List<String> rowData = new ArrayList<>();

                // 遍历每个单元格，将数据添加到rowData中
                for (int cellIndex = 0; cellIndex < headerFields.length; cellIndex++) {
                    Cell cell = dataRow.getCell(cellIndex);
                    if (cell != null) {
                        if (cell.getCellType() == CellType.STRING) {
                            rowData.add(cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            // 将数值转换为字符串并添加到rowData中
                            rowData.add(BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros().toPlainString());
                        } else {
                            // 处理其他类型的单元格，例如日期等
                            // 这里可以根据需要进行处理
                            rowData.add("");
                        }
                    } else {
                        // 如果单元格为空，添加空字符串
                        rowData.add("");
                    }
                }

                // 将rowData添加到数据列表中
                data.add(rowData);
            }

            // 在这里可以使用data列表中的数据进行后续的业务逻辑操作
            // 例如，将数据存储到内存中的数据结构中，进行处理
            for (String headerField : headerFields) {
                System.out.print(headerField);
                System.out.print("|");

            }
            System.out.println();
            System.out.println("================");
            // 示例：输出解析后的数据
            for (List<String> rowData : data) {
                for (String rowDatum : rowData) {
                    System.out.print(rowDatum);
                    System.out.print("|");
                }
                System.out.println();
                System.out.println("================");

            }

            // 关闭输入流
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void creatCustomExcel() {
        // 创建一个新的工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建一个工作表
        Sheet sheet = workbook.createSheet("导入模板");

        // 创建一个表头行
        Row headerRow = sheet.createRow(0);
        // 设置行高为30个点
        headerRow.setHeightInPoints(50);

        // 创建样式并应用于表头
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillPattern(FillPatternType.NO_FILL);
        // 设置水平居中和垂直居中对齐方式
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 创建字体对象并设置加粗
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // 添加系统商品ID列（固定列）并应用样式
        Cell idHeaderCell = headerRow.createCell(0);

        // 创建一个 RichTextString，仅将星号设置为红色
        RichTextString richText = workbook.getCreationHelper().createRichTextString("*系统商品ID");
        int starIndex = richText.getString().indexOf("*");
        int endIndex = starIndex + 1;
        richText.applyFont(starIndex, endIndex, headerFont);
        idHeaderCell.setCellValue(richText);
        idHeaderCell.setCellStyle(headerStyle);

        headerFont.setColor(IndexedColors.BLACK1.getIndex());
        sheet.autoSizeColumn(0);

        // 添加其他可能的字段列（不固定列）并应用样式，你可以根据需要添加更多列
        String[] possibleFields = {"A", "B", "C", "G", "F", "H"};
        for (int i = 0; i < possibleFields.length; i++) {
            // 从第二列开始
            Cell fieldHeaderCell = headerRow.createCell(i + 1);
            fieldHeaderCell.setCellValue(possibleFields[i]);
            fieldHeaderCell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(i + 1);
        }

        // 保存生成的Excel文件
        try {
            FileOutputStream outputStream = new FileOutputStream(EXCEL_NAME);
            workbook.write(outputStream);
            outputStream.close();
            System.out.println("Styled导入模板已生成成功！");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void copyExcel() {
        try {
            // 打开要解析的Excel文件
            FileInputStream inputStream = new FileInputStream(EXCEL_NAME);
            Workbook workbook = new XSSFWorkbook(inputStream);

            // 获取工作表
            // 假设导入模板在第一个工作表中
            Sheet sheet = workbook.getSheetAt(0);

            // 获取表头行
            Row headerRow = sheet.getRow(0);

            // 创建一个新的工作簿
            Workbook newWorkbook = new XSSFWorkbook();

            // 创建一个新的工作表
            Sheet newSheet = newWorkbook.createSheet("NEW_" + EXCEL_NAME);

            // 复制表头行到新工作表
            Row newHeaderRow = newSheet.createRow(0);
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell newCell = newHeaderRow.createCell(i);
                Cell oldCell = headerRow.getCell(i);
                if (0 == i) {
                    Font headerFont = workbook.createFont();
                    headerFont.setBold(true);
                    headerFont.setColor(IndexedColors.RED.getIndex());
                    // 创建一个 RichTextString，仅将星号设置为红色
                    RichTextString richText = workbook.getCreationHelper().createRichTextString(oldCell.getStringCellValue());
                    int starIndex = richText.getString().indexOf("*");
                    if (starIndex >= 0) {
                        int endIndex = starIndex + 1;
                        richText.applyFont(starIndex, endIndex, headerFont);
                        newCell.setCellValue(richText);
                    } else {
                        newCell.setCellValue(oldCell.getStringCellValue());
                    }
                } else {

                    if (oldCell != null) {
                        newCell.setCellValue(oldCell.getStringCellValue());
                    }
                }

            }
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.RED.getIndex());
            RichTextString richText = workbook.getCreationHelper().createRichTextString("错误信息");
            richText.applyFont(headerFont);
            Cell newCellTmp = newHeaderRow.createCell(headerRow.getLastCellNum());
            newCellTmp.setCellValue(richText);

            // 复制数据行到新工作表
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row oldDataRow = sheet.getRow(rowIndex);
                Row newDataRow = newSheet.createRow(rowIndex);

                for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
                    Cell newCell = newDataRow.createCell(cellIndex);
                    Cell oldCell = oldDataRow.getCell(cellIndex);
                    if (oldCell != null) {
                        if (oldCell.getCellType() == CellType.STRING) {
                            newCell.setCellValue(oldCell.getStringCellValue());
                        } else if (oldCell.getCellType() == CellType.NUMERIC) {
                            newCell.setCellValue(oldCell.getNumericCellValue());
                        } else {
                            // 处理其他类型的单元格，例如日期等
                            // 这里可以根据需要进行处理
                        }
                    } else {
                        // 如果单元格为空，添加空字符串
                        newCell.setCellValue("");
                    }
                }
            }

            // 保存修改后的Excel文件
            FileOutputStream outputStream = new FileOutputStream("新导入模板.xlsx");
            newWorkbook.write(outputStream);
            outputStream.close();

            // 关闭输入流
            inputStream.close();

            System.out.println("新导入模板已生成成功！");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
