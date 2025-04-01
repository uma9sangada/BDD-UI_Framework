package org.uma.web.runners;

import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
	    features = "src/test/java/Features",
	    glue = "StepDefinitions",
	    monochrome = true,
	    tags = "@sanity or @Regression",
	    plugin = {
	        "pretty",
	        "html:target/cucumber-reports/cucumber.html",
	        "json:target/cucumber-reports/cucumber.json",
	        "rerun:target/failed_scenarios/rerun.txt"
	    }
	)
	public class TestRunner extends AbstractTestNGCucumberTests {

	    @Override
	    @DataProvider(parallel = false)
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
}