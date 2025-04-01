package org.uma.web.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.uma.web.base.Web; // Import the Web class

public class CheckoutPage {
	
	  public CheckoutPage() {
	        PageFactory.initElements(Web.getDriver(), this);
	    }

    @FindBy(xpath = "//button[contains(@class,'ta-item')][2]")
    WebElement selectCountry; // Corrected variable name

    @FindBy(css = ".action__submit")
    WebElement submitButton; // Corrected variable name

    @FindBy(xpath = "//input[@placeholder='Select Country']")
    WebElement country;

    By popDown = By.cssSelector(".ta-results "); // Corrected variable name

  

    public void selectCountry(String val) { // Corrected method name
        Actions actions = new Actions(Web.getDriver());
        actions.sendKeys(country, val).build().perform();
       // waitForElementToAppear(popDown);
        selectCountry.click();
    }



}