package org.uma.web.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "@target/failed_scenarios/rerun.txt",
    glue = "org.uma.web.stepdefinitions", // Replace with your actual step definitions package
    monochrome = true,
    plugin = {
        "pretty",
        "html:target/rerun-reports/rerun-cucumber.html",
        "json:target/rerun-reports/rerun-cucumber.json"
    }
)
public class RerunTestrunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}