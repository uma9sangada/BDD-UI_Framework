package org.uma.web.utilities;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportManager {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            initializeExtentReports();
        }
        return extentReports;
    }

    private static void initializeExtentReports() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedTime = dateTimeFormatter.format(localDateTime);
        String reportName = "EcommerceTestReport" + formattedTime + ".html";

        String reportPath = ConfigurationManager.getInstance().getProperty("report.path", "test-reports");
        String fullReportPath = System.getProperty("user.dir") + "/" + reportPath + "/" + reportName;

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fullReportPath);
        sparkReporter.config().setDocumentTitle(ConfigurationManager.getInstance().getProperty("report.document.title", "Ecommerce Application Test Report"));
        sparkReporter.config().setReportName(ConfigurationManager.getInstance().getProperty("report.name", "Ecommerce E2E Test Results"));

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", ConfigurationManager.getInstance().getProperty("report.application.name", "Ecommerce Application"));
        extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", ConfigurationManager.getInstance().getProperty("report.environment", "QA"));
    }

    public static void createTest(String testName) {
        ExtentTest test = getExtentReports().createTest(testName);
        extentTestThreadLocal.set(test);
    }

    public static void logStep(Status status, String message) {
        extentTestThreadLocal.get().log(status, message);
    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    public static ExtentTest getExtentTest() {
        return extentTestThreadLocal.get();
    }
}