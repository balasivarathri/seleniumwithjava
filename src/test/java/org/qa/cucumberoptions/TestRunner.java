package org.qa.cucumberoptions;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.qa.processor.Initialize;
import org.qa.processor.Processor;
import org.qa.processor.TearDown;
import lombok.extern.slf4j.Slf4j;
import org.qa.runner.CustomRunner;

import java.io.IOException;

@Slf4j
@RunWith(CustomRunner.class)
@CucumberOptions(
        monochrome = true,
        features = "src/test/java/org/qa/features",
        glue = {"org.qa.cucumberoptions","org.qa.stepdefinitions"},
        plugin = {"json:target/jsonReports/cucumber-reports.json"}
)
public class TestRunner {

    private static String executionDateTime;
    private static final String DATEFORMAT = "dd-MM-yyyy_hh-mm-ss";

    @Initialize
    public static void initialize() throws IOException {
        log.info("------ Initialize: START ------");
        executionDateTime = Processor.getDateAsString(DATEFORMAT);
        Processor.setTeamFeatureDirectory();
        Processor.overrideFeature();
        log.info("------ Intialize: END ------");
    }

    @TearDown
    public static void teardown(){
        log.warn("------ Teardown: START ------");
        Processor.initializeTeardown(executionDateTime);
        log.info("Teardown: END ------");
    }
}
