package org.qa.supporting;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static List<Map<String, String>> getData(String filePath, String sheetName) {
        List<Map<String, String>> testData = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> rowData = new HashMap<>();
                Row currentRow = sheet.getRow(i);
                for (int j = 0; j < currentRow.getLastCellNum(); j++) {
                    rowData.put(headerRow.getCell(j).getStringCellValue(), currentRow.getCell(j).getStringCellValue());
                }
                testData.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testData;
    }
}

