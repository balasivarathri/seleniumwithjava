package org.qa.supporting;

//
//
//
//


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FeatureOverwrite {
    private static final Logger log = LoggerFactory.getLogger(FeatureOverwrite.class);
    private static final String tag = System.getProperty("cucumber.filter.tags");
    private static final String[] fileExt = new String[]{"feature"};
    private static final List<File> fileOverwriteList = new ArrayList();

    public FeatureOverwrite() {
    }

    public static void overrideFeatureFiles(String parentDirectory) throws Throwable {
        try {
            setFileOverwriteList(parentDirectory);

            for (File featureFile : fileOverwriteList) {
                List<String> featureWithExcelData = setExcelDataToFeature(featureFile);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(featureFile), StandardCharsets.UTF_8));
                Throwable var5 = null;

                try {
                    for (String string : featureWithExcelData) {
                        writer.write(string);
                        writer.write("\n");
                    }
                } catch (Throwable var16) {
                    var5 = var16;
                    throw var16;
                } finally {
                    if (var5 != null) {
                        try {
                            writer.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        writer.close();
                    }
                }
            }
        } catch (Throwable var18) {
            throw var18;
        }
    }

    private static void setFileOverwriteList(String parentDirectory) throws IOException {
        File directory = new File(parentDirectory);
        Collection<File> fileList = FileUtils.listFiles(directory, fileExt, true);

        for (File f : fileList) {
            String fileData = FileUtils.readFileToString(f, Charset.defaultCharset());
            if (fileData.contains(tag)) {
                fileOverwriteList.add(f);
            }
        }
    }

    private static List<String> setExcelDataToFeature(File featureFile) throws IOException {
        List<String> fileData = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(featureFile)), StandardCharsets.UTF_8));
        Throwable var3 = null;

        try {
            boolean foundHashTag = false;
            boolean featureData = false;

            while (true) {
                String data;
                while ((data = bufferedReader.readLine()) != null) {
                    String sheetName = null;
                    String excelFilePath = null;
                    if (data.trim().contains("##@externaldata")) {
                        excelFilePath = data.substring(StringUtils.ordinalIndexOf(data, "@", 2) + 1, data.lastIndexOf("@"));
                        sheetName = data.substring(data.lastIndexOf("@") + 1);
                        foundHashTag = true;
                        fileData.add(data);
                    }
                    if (foundHashTag) {
                        List excelData = (new ExcelReaderForOverwrite()).getData(featureFile.getPath(), excelFilePath, sheetName);

                        for (int rowNumber = 0; rowNumber < excelData.size() - 1; ++rowNumber) {
                            String cellData = "";
                            Map.Entry mapData;
                            for (Iterator var12 = ((Map)excelData.get(rowNumber)).entrySet().iterator(); var12.hasNext(); cellData = cellData + "|" + (String) mapData.getValue()) {
                                mapData = (Map.Entry) var12.next();
                            }
                            fileData.add(ExcelMultiline.escapeLineBreak(cellData) + "|");
                        }
                        foundHashTag = false;
                        featureData = true;
                    } else if (!data.startsWith("|") && !data.endsWith("|")) {
                        featureData = false;
                        fileData.add(data);
                    } else if (!featureData) {
                        fileData.add(data);
                    }
                }
                return fileData;
            }
        } catch (Throwable var21) {
            var3 = var21;
            throw var21;
        } finally {
            if (var3 != null) {
                try {
                    bufferedReader.close();
                } catch (Throwable var20) {
                    var3.addSuppressed(var20);
                }
            } else {
                bufferedReader.close();
            }
        }
    }

}
