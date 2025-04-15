package org.qa.processor;

import lombok.extern.slf4j.Slf4j;
import org.qa.report.ReportHelper;
import org.qa.supporting.FeatureOverwrite;
import org.qa.util.CoreDateTime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Processor {

    private static final String startDateTime = CoreDateTime.getTimeStamp("dd-MM-yyyy_hh-mm-ss");
    public static String requestId="";
    private static String teamFeatureDirectory="";
    public static Properties prop;

    public Processor(){
    }

    public static String getDateAsString(String format){
        return CoreDateTime.getTimeStamp(format);
    }


    public static void setTeamFeatureDirectory() throws IOException {
        prop = new Properties();
        FileInputStream fs = new FileInputStream("src/main/java/config/config.properties");
        prop.load(fs);
        String var0 = prop.getProperty("team").toLowerCase();
        byte var1 = -1;
        if (var0.hashCode() == 3016322){
            if (var0.equals("baps")){
                var1 = 0;
            }
        }
        if (var1 == 0){
            teamFeatureDirectory = prop.getProperty("featureFilePath").toLowerCase();
        }
    }

    public static void overrideFeature() {
        try{
            log.info("Recreating feature files in team target folder: " + teamFeatureDirectory);
            try{
                FeatureOverwrite.overrideFeatureFiles(teamFeatureDirectory);
            } catch (IllegalArgumentException var1) {
                killProcess("Whoops.... unable to create feature files for " + System.getProperty("team").toUpperCase() + " at folder location");
            }
        } catch (Throwable var2){
            try{
                throw var2;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void initializeTeardown(String executionDateTime) {
        log.info("Unique Run id generated: " + requestId);
        log.info("Initializing Teardown");
        log.info("Generating local execution report");
        generateReport();
        log.info("Cucumber execution report genereated scuccessfully");
        log.info("Handing off teardown to controller with values: ");
        log.info("Ignoring teardown as no features have been executed");
    }

    public static void generateReport(){
        ReportHelper.generateCucumberReport();
    }
    public static void killProcess(String error){
        log.error(error.replace("[", "").replace("]", ""));
        log.info("Vm has been terminated");
        System.exit(0);
    }
}
