package org.uma.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.uma.web.base.Web; // Import the Web class

public class ConfirmationPage {

    @FindBy(css = ".hero-primary")
    WebElement message;

    public ConfirmationPage() {
        PageFactory.initElements(Web.getDriver(), this);
    }

    public String orderMessage() { 
        return message.getText();
    }
}