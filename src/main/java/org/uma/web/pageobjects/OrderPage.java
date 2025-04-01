package org.uma.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.uma.web.base.Web; // Import the Web class

public class OrderPage {

    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> productNames; // Corrected variable name

    public OrderPage() {
        PageFactory.initElements(Web.getDriver(), this);
    }

    public Boolean verifyOrderDisplayed(String productName) {
        for (WebElement name : productNames) {
            if (name.getText().equals(productName)) {
                return true;
            }
        }
        return false;
    }
}