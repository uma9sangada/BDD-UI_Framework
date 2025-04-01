package org.uma.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.uma.web.base.Web; // Import the Web class
import java.time.Duration;

public class ProductCatalog {

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement spinner;

    By productLocation = By.cssSelector(".mb-3"); // Corrected variable name
    By toastMessage = By.id("toast-container"); // Corrected variable name
    By addToCart = By.cssSelector(".card-body button:last-of-type"); // Corrected variable name

    public ProductCatalog() {
        PageFactory.initElements(Web.getDriver(), this);
    }

    public List<WebElement> getProductsList() {
        waitForElementToAppear(productLocation);
        return products;
    }

    public WebElement getProductByName(String productName) {
        for (WebElement product : getProductsList()) {
            if (product.findElement(By.cssSelector("b")).getText().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public void addProductToCart(String productName) {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear();
    }

    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(Web.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForElementToDisappear() {
        WebDriverWait wait = new WebDriverWait(Web.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(spinner));
    }
}