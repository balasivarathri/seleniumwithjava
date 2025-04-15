package org.qa.supporting;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.qa.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ExcelReaderForOverwrite {
    private static final Logger log = (Logger) LoggerFactory.getLogger(ExcelReaderForOverwrite.class);
    private String featureFilePath = "";
    private String sheetName = "";

    public ExcelReaderForOverwrite() {
    }

    public List getData(String featureFilePath, String excelFilePath, String sheetName) {
        this.featureFilePath = featureFilePath;
        this.sheetName = sheetName;
        FileInputStream excelFile = null;

        try {
            excelFile = new FileInputStream(excelFilePath);
        } catch (FileNotFoundException e) {
            Processor.killProcess("Error on feature file: '" + featureFilePath + "' - Unable to read excel document with path: '" + excelFilePath + "'");
        }

        Object data = new ArrayList();

        try {
            assert excelFile != null;

            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            data = this.readSheet(sheet);
        } catch (IOException e) {
            Processor.killProcess("Error on feature file: '" + featureFilePath + "' - Unable to read excel document with path: '" + excelFilePath + "'");
        } finally {
            try {
                assert excelFile != null;
                excelFile.close();
            } catch (IOException e) {
                Processor.killProcess("Error on feature file: '" + featureFilePath + "' - Unable to read excel document with path: '" + excelFilePath + "'");
            }
        }
        return (List) data;
    }

    private List<Map<String, String>> readSheet(Sheet sheet) {
        try {
            assert sheet != null;
        } catch (AssertionError Var11) {
            Processor.killProcess("Error on feature file: '" + this.featureFilePath + "' - Unable to read excel document with path: '" + this.sheetName + "'");
        }
        int totalRow = ((Sheet) Objects.requireNonNull(sheet)).getPhysicalNumberOfRows();
        List<Map<String, String>> excelRows = new ArrayList();
        int headerRowNumber = this.getHeaderRowNumber(sheet);
        if (headerRowNumber != -1) {
            int totalColumn = sheet.getRow(headerRowNumber).getLastCellNum();
            int setCurrentRow = 1;

            for (int currentRow = setCurrentRow; currentRow <= totalRow; ++currentRow) {
                Row row = this.getRow(sheet, sheet.getFirstRowNum() + currentRow);
                LinkedHashMap<String, String> columnMapData = new LinkedHashMap();
                for (int currentColumn = 0; currentColumn < totalColumn; ++currentColumn) {
                    columnMapData.putAll(this.getCellValue(sheet, row, currentColumn));
                }
                excelRows.add(columnMapData);
            }
        }
        return excelRows;
    }

    private int getHeaderRowNumber(Sheet sheet) {
        int totalRow = sheet.getLastRowNum();

        for (int currentRow = 0; currentRow <= totalRow + 1; ++currentRow) {
            Row row = this.getRow(sheet, currentRow);
            if (row != null) {
                int totalColumn = row.getLastCellNum();

                for (int currentColumn = 0; currentColumn < totalColumn; ++currentColumn) {
                    Cell cell = row.getCell(currentColumn, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    if (cell.getCellType().equals(CellType.STRING)) {
                        return row.getRowNum();
                    }

                    if (cell.getCellType().equals(CellType.NUMERIC)) {
                        return row.getRowNum();
                    }

                    if (cell.getCellType().equals(CellType.BOOLEAN)) {
                        return row.getRowNum();
                    }

                    if (cell.getCellType().equals(CellType.ERROR)) {
                        return row.getRowNum();
                    }
                }
            }
        }
        return -1;
    }

    private Row getRow(Sheet sheet, int rowNumber) {
        return sheet.getRow(rowNumber);
    }

    private LinkedHashMap<String, String> getCellValue(Sheet sheet, Row row, int currentColumn) {
        LinkedHashMap<String, String> columnMapData = new LinkedHashMap<>();
        String columnHeaderName;
        if (row == null) {
            if (sheet.getRow(sheet.getFirstRowNum()).getCell(currentColumn, MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(currentColumn).getStringCellValue();
                columnMapData.put(columnHeaderName, "");
            }
        } else {
            Cell cell = row.getCell(currentColumn, MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell.getCellType().equals(CellType.STRING)) {
                if (sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                    columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                    columnMapData.put(columnHeaderName, cell.getStringCellValue());
                }
            } else if (cell.getCellType().equals(CellType.NUMERIC)) {
                if (sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                    columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                    columnMapData.put(columnHeaderName, NumberToTextConverter.toText(cell.getNumericCellValue()));
                }

            } else if (cell.getCellType().equals(CellType.BLANK)) {
                if (sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                    columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                    columnMapData.put(columnHeaderName, "");
                }
            } else if (cell.getCellType().equals(CellType.BOOLEAN)) {
                if (sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                    columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                    columnMapData.put(columnHeaderName, Boolean.toString(cell.getBooleanCellValue()));
                }
            } else if (cell.getCellType().equals(CellType.ERROR) && sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                if (sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex(), MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType() != CellType.BLANK) {
                    columnHeaderName = sheet.getRow(sheet.getFirstRowNum()).getCell(cell.getColumnIndex()).getStringCellValue();
                    columnMapData.put(columnHeaderName, Byte.toString(cell.getErrorCellValue()));
                }
            }
        }
        return columnMapData;
    }
}
