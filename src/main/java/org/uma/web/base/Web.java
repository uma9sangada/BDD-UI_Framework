package org.uma.web.base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.uma.web.utilities.ConfigurationManager;

import java.awt.Toolkit;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.StringTokenizer;

public class Web {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    @Before
    public void setup() throws IOException {
        driver = initializeDriver();
        initializeWait();
        setInitialCookies();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }

    private WebDriver initializeDriver() throws IOException {
        String browserName = ConfigurationManager.getInstance().getBrowser().toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigurationManager.getInstance().getProperty("headless", "false"));
        boolean acceptInsecureCerts = Boolean.parseBoolean(ConfigurationManager.getInstance().getProperty("accept.insecure.certs", "true"));

        WebDriver driver;
        switch (browserName) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200");
                }
                chromeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200");
                }
                firefoxOptions.setAcceptInsecureCerts(acceptInsecureCerts);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200");
                }
                edgeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
                driver = new EdgeDriver(edgeOptions);
                break;
            case "safari":
                SafariOptions safariOptions = new SafariOptions();
                safariOptions.setAcceptInsecureCerts(acceptInsecureCerts);
                driver = new SafariDriver(safariOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigurationManager.getInstance().getProperty("delay"))));
        setWindowSize(driver);
        return driver;

    }

    private void initializeWait() {
        long delaySeconds = Long.parseLong(ConfigurationManager.getInstance().getProperty("delay"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(delaySeconds));
    }

    private void setWindowSize(WebDriver driver) {
        if (!Boolean.parseBoolean(ConfigurationManager.getInstance().getProperty("headless", "false"))) {
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            driver.manage().window().setSize(new Dimension(screenSize.width, screenSize.height));
        } else {
            driver.manage().window().setSize(new Dimension(1920, 1200));
        }
    }

    private void setInitialCookies() {
        String cookiesString = ConfigurationManager.getInstance().getProperty("cookies");
        if (cookiesString != null && !cookiesString.isEmpty()) {
            StringTokenizer st = new StringTokenizer(cookiesString, ";");
            while (st.hasMoreTokens()) {
                String cookieString = st.nextToken().trim();
                String[] cookieParts = cookieString.split("=");
                if (cookieParts.length == 2) {
                    Cookie cookie = new Cookie(cookieParts[0].trim(), cookieParts[1].trim());
                    driver.manage().addCookie(cookie);
                }
            }
        }
    }

    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        driver.manage().addCookie(cookie);
    }

    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    public void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }
}