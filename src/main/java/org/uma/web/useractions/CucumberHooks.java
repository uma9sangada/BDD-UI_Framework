package org.uma.web.useractions; // Replace with your package name

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;

public class CucumberHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9.-]", "_");
        ThreadContext.put("scenarioName", scenarioName);
    }

    @After
    public void afterScenario(Scenario scenario) {
        ThreadContext.remove("scenarioName");
    }
}