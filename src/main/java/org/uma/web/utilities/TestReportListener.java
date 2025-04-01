package org.uma.web.utilities;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.uma.web.base.Web;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestReportListener extends Web implements ITestListener {

    private ExtentReports extentReports;
    private ExtentSparkReporter sparkReporter;
    private ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext testContext) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedTime = dateTimeFormatter.format(localDateTime);
        String reportName = "EcommerceTestReport" + formattedTime + ".html";

        String reportPath = ConfigurationManager.getInstance().getProperty("report.path", "test-reports");
        String fullReportPath = System.getProperty("user.dir") + "\\" + reportPath + "\\" + reportName;

        sparkReporter = new ExtentSparkReporter(fullReportPath);
        sparkReporter.config().setDocumentTitle(ConfigurationManager.getInstance().getProperty("report.document.title", "Ecommerce Application Test Report"));
        sparkReporter.config().setReportName(ConfigurationManager.getInstance().getProperty("report.name", "Ecommerce E2E Test Results"));

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", ConfigurationManager.getInstance().getProperty("report.application.name", "Ecommerce Application"));
        extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", ConfigurationManager.getInstance().getProperty("report.environment", "QA"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        extentTestThreadLocal.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTestThreadLocal.get().log(Status.PASS, "Test passed");
        captureScreenshotAndAddToReport(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTestThreadLocal.get().log(Status.FAIL, "Test failed");
        extentTestThreadLocal.get().fail(result.getThrowable());
        captureScreenshotAndAddToReport(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

    private void captureScreenshotAndAddToReport(ITestResult result) {
        WebDriver driver = null;
        String filePath = null;
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
            filePath = getScreenshot(result.getMethod().getMethodName(), driver);
            extentTestThreadLocal.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        File Source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(Source, file);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }
}