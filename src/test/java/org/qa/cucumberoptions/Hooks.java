package org.qa.cucumberoptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import io.cucumber.java.Scenario;
import org.qa.TestBase;

@Slf4j
public class Hooks {

    public Hooks() {
    }

    @Before(order = 0)
    public void beforeScenario(Scenario scenario){
        TestBase.scenario = scenario;
        log.info("------ Scenario: START ------");
        log.info("Scenario Name: " + scenario.getName());
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario){
        log.info("Scenarios Result: " + scenario.getStatus());
        log.info("------ Scenario: END ------");
    }

}
