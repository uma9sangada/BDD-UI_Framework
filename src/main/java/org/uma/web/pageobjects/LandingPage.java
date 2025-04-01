package org.uma.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.uma.web.base.Web;

public class LandingPage {

    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy(id = "login")
    WebElement login;

    @FindBy(css = "[class*='flyInOut']")
    WebElement invalidData;

    public LandingPage() {
        PageFactory.initElements(Web.getDriver(), this);
    }

    public void enterCredentials(String mail, String password) {
        userEmail.sendKeys(mail);
        userPassword.sendKeys(password);
        login.click();
    }

    public String errorMessage() {
        waitForElementToAppear(invalidData);
        return invalidData.getText();
    }

    public void navigateToWebsiteURL() {
        Web.getDriver().get("https://rahulshettyacademy.com/client");
    }

    public void waitForElementToAppear(WebElement element) {
        // Implement your wait logic here (e.g., using WebDriverWait)
        // Example:
        // WebDriverWait wait = new WebDriverWait(Web.getDriver(), Duration.ofSeconds(10));
        // wait.until(ExpectedConditions.visibilityOf(element));
    }
}