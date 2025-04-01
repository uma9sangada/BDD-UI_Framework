package org.uma.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.uma.web.base.Web;

public class CartPage {

	public CartPage() {
		PageFactory.initElements(Web.getDriver(), this);
	}

	@FindBy(css = ".cartWrap .cartSection h3")
	List<WebElement> productTitles;

	@FindBy(css = ".subtotal .btn")
	WebElement checkout;

	By textLabels = By.cssSelector(".cartWrap .cartSection h3");

	public Boolean verifyProductDisplayed(String productName) {
		for (WebElement title : productTitles) {
			if (title.getText().equals(productName)) {
				return true;
			}
		}
		return false;
	}
}