package org.uma.web.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.uma.web.base.Web;

import java.io.File;
import java.io.IOException;

public class ReportListener extends Web implements ITestListener {

    @Override
    public void onStart(ITestContext testContext) {
        ReportManager.getExtentReports(); 
    }

    @Override
    public void onTestStart(ITestResult result) {
        ReportManager.createTest(result.getMethod().getMethodName()); 
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportManager.logStep(com.aventstack.extentreports.Status.PASS, "Test passed"); 
        captureScreenshotAndAddToReport(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ReportManager.logStep(com.aventstack.extentreports.Status.FAIL, "Test failed"); 
        ReportManager.getExtentTest().fail(result.getThrowable()); 
        captureScreenshotAndAddToReport(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.flushReports(); 
    }

    private void captureScreenshotAndAddToReport(ITestResult result) {
        WebDriver driver = Web.getDriver();
        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(), driver);
            ReportManager.getExtentTest().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String reportPath = ConfigurationManager.getInstance().getProperty("report.path", "test-reports");
        File destination = new File(System.getProperty("user.dir") + "/" + reportPath + "/" + testCaseName + ".png");
        FileUtils.copyFile(source, destination);
        return destination.getAbsolutePath();
    }
}