package org.uma.web.useractions;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.uma.web.base.Web; // Import the Web class

import com.aventstack.extentreports.Status;

// Assuming you have 'Stock' and 'Reporter' classes defined elsewhere
// import org.uma.your.package.Stock;
// import org.uma.your.package.Reporter;
// import org.uma.your.package.Reporter.Status; // Assuming Status is an enum inside Reporter

public class WebActions {

	public FluentWait<WebDriver> wait;
	public JavascriptExecutor js;
	public WebDriver parentWindow;
	private Actions actions;
	private int totalWindows;
	private WebDriver driver;

	public WebActions() {
		this.driver = Web.getDriver();
		this.wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class);
		this.js = (JavascriptExecutor) driver;
	}

	public void enterData(WebElement web, String val) {
		try {
			wait.until(ExpectedConditions.visibilityOf(web));
			wait.until(ExpectedConditions.elementToBeClickable(web));
		} catch (TimeoutException e) {
			e.printStackTrace();
			return;
		}

		js.executeScript("arguments[0].scrollIntoView(true);", web);
		String textVal = "";
		while (!textVal.equals(val)) {
			try {
				web.clear();
				web.sendKeys(val);
				textVal = web.getAttribute("value");
				if (textVal.equals(val)) {
					break;
				}
			} catch (ElementClickInterceptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void enterPartialData(WebElement web, String val) {
		try {
			wait.until(ExpectedConditions.visibilityOf(web));
			wait.until(ExpectedConditions.elementToBeClickable(web));
		} catch (TimeoutException e) {
			e.printStackTrace();
			return;
		}
		js.executeScript("arguments[0].scrollIntoView(true);", web);
		web.sendKeys(val);
	}

	public void mouseClick(WebElement web) {
		try {
			wait.until(ExpectedConditions.visibilityOf(web));
			wait.until(ExpectedConditions.elementToBeClickable(web));
		} catch (TimeoutException e) {
			e.printStackTrace();
			return;
		}
		js.executeScript("arguments[0].scrollIntoView(true);", web);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		web.click();
	}

	public void keyboardShortcut(int value) {
		try {
			Robot rh = new Robot();
			rh.keyPress(value);
			rh.keyRelease(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doubleClick(WebElement web) {
		try {
			wait.until(ExpectedConditions.visibilityOf(web));
			wait.until(ExpectedConditions.elementToBeClickable(web));
		} catch (TimeoutException e) {
			e.printStackTrace();
			return;
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Actions actions = new Actions(driver);
		actions.moveToElement(web).doubleClick().perform();
	}

	public void doubleClick() throws Exception {
		Actions a = new Actions(driver);
		a.doubleClick().perform();
	}

	public void keyboardShortcut(int value1, int value2) {
		Robot rb;
		try {
			rb = new Robot();
			rb.keyPress(value1);
			rb.keyPress(value2);
			Thread.sleep(100);
			rb.keyRelease(value2);
			rb.keyRelease(value1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void keyboardShortcut(int value1, int value2, int value3) throws Exception {
		Robot rb = new Robot();
		rb.keyPress(value1);
		rb.keyPress(value2);
		rb.keyPress(value3);
		Thread.sleep(100);
		rb.keyRelease(value3);
		rb.keyRelease(value2);
		rb.keyRelease(value1);
	}

	public void switchToFrameByIndex(int val) {
		Web.getDriver().switchTo().frame(val);
	}

	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectDropdown(WebElement webElement, String val) {
		try {
			wait.until(ExpectedConditions.visibilityOf(webElement));
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
		} catch (TimeoutException e) {
			e.printStackTrace();
			return;
		}

		Select select = new Select(webElement);
		select.selectByVisibleText(val);
	}

	public void mouseScrollDown(int val) throws Exception {
		Robot rb = new Robot();
		rb.mouseWheel(val);
		Thread.sleep(1000);
	}

	public void mouseScrollDown(int val, WebElement webElement) throws Exception {
		actions.moveToElement(webElement).perform();
		Robot rb = new Robot();
		rb.mouseWheel(val);
		Thread.sleep(1000);
	}

	public void scrollToOtherFormsButton() {
		try {
			keyboardShortcut(KeyEvent.VK_CONTROL, KeyEvent.VK_F);
			Thread.sleep(300);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_O);
			r.keyRelease(KeyEvent.VK_O);
			r.keyPress(KeyEvent.VK_F);
			r.keyRelease(KeyEvent.VK_F);
			r.keyPress(KeyEvent.VK_F);
			r.keyRelease(KeyEvent.VK_F);
			r.keyPress(KeyEvent.VK_H);
			r.keyRelease(KeyEvent.VK_H);
			r.keyPress(KeyEvent.VK_E);
			r.keyRelease(KeyEvent.VK_E);
			r.keyPress(KeyEvent.VK_R);
			r.keyRelease(KeyEvent.VK_R);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchChildWindow() throws Exception {
		String parentWin = Web.getDriver().getWindowHandle();
		int totalWindowsNew = Web.getDriver().getWindowHandles().size();
		System.out.println("Total Windows " + totalWindowsNew);
		int timer = 1;

		while (totalWindowsNew == Web.getDriver().getWindowHandles().size() && timer < 120) {
			Thread.sleep(1000);
			timer++;
		}

		Set<String> allWindows = Web.getDriver().getWindowHandles();
		for (String childWin : allWindows) {
			if (!childWin.equals(parentWin)) {
				Web.getDriver().switchTo().window(childWin);
				break;
			}
		}
	}

	public void switchParentWindow(String parentWindow) throws Exception {
		Web.getDriver().switchTo().window(parentWindow);
	}

	public void parentWindow() throws Exception {
		Web.getDriver().getWindowHandle();
	}

	public void totalWindows() throws Exception {
		totalWindows = Web.getDriver().getWindowHandles().size();
	}

	public String windowTitle() throws Exception {
		return Web.getDriver().getTitle();
	}

//	public void compareDate(WebElement element, String format, String expectedDate) {
//		String actualDate = element.getAttribute("value").toUpperCase();
//		System.out.println("Expected = " + expectedDate);
//		System.out.println("Actual = " + actualDate);
//
//		if (actualDate.equals(expectedDate)) {
//			Reporter.LogEvent(Status.PASS, "Verify actual value = " + actualDate + " is same as = " + expectedDate,
//					"Actual value = " + actualDate + " is same as = " + expectedDate, true);
//		} else {
//			Reporter.LogEvent(Status.FAIL, "Verify actual value = " + actualDate + " is not same as = " + expectedDate,
//					"Actual value = " + actualDate + " is not same as = " + expectedDate, true);
//			throw new AssertionError();
//		}
//	}
//
//	public void compareDateText(WebElement element, String format, String expectedDate) {
//		String actualDate = element.getText().toUpperCase();
//		System.out.println("Expected = " + expectedDate);
//		System.out.println("Actual = " + actualDate);
//
//		if (actualDate.equals(expectedDate)) {
//			Reporter.LogEvent(Status.PASS, "Verify actual value = " + actualDate + " is same as = " + expectedDate,
//					"Actual value = " + actualDate + " is same as = " + expectedDate, true);
//		} else {
//			Reporter.LogEvent(Status.FAIL, "Verify actual value = " + actualDate + " is not same as = " + expectedDate,
//					"Actual value = " + actualDate + " is not same as = " + expectedDate, true);
//			throw new AssertionError();
//		}
//	}
//
//	public void verifyNotEditable(WebElement element) throws Exception {
//		if (Boolean.parseBoolean(element.getAttribute("readonly"))) {
//			Reporter.LogEvent(Status.PASS, "Element is read only", "Element is read only", true);
//		} else {
//			Reporter.LogEvent(Status.FAIL, "Verify element is read only", "Element is not read only", false);
//			throw new AssertionError("Element is not readonly.");
//		}
//	}
//
//	public void verifyReadOnly(WebElement element, String fieldName) throws Exception {
//		element.sendKeys("Test Read Only");
//		Thread.sleep(500);
//		boolean flag = element.getAttribute("value").contains("Test Read Only");
//
//		if (!flag) {
//			Reporter.LogEvent(Status.PASS, "Verify " + fieldName + " is read only", fieldName + " is read only", true);
//		} else {
//			Reporter.LogEvent(Status.FAIL, "Verify " + fieldName + " is read only", fieldName + " is not read only",
//					false);
//			throw new AssertionError(fieldName + " is not readonly.");
//		}
//	}
//
//	public void setTextAction(Keys val) {
//		actions.sendKeys(val).perform();
//	}
//
//	public int generateRandomNumber(int digit) {
//		Random rand = new Random();
//		return rand.nextInt(digit);
//	}
//
//	public boolean verifyPartialText(WebElement element, String expectedValue) {
//		String actualValue = element.getText();
//		System.out.println("Expected Value = " + expectedValue);
//		System.out.println("Actual Value = " + actualValue);
//
//		if (actualValue.contains(expectedValue)) {
//			Reporter.LogEvent(Status.PASS, "Verify actual value = " + actualValue + " is same as = " + expectedValue,
//					"Actual value = " + actualValue + " is same as = " + expectedValue, true);
//			return true;
//		} else {
//			Reporter.LogEvent(Status.FAIL,
//					"Verify actual value = " + actualValue + " is not same as = " + expectedValue,
//					"Actual value = " + actualValue + " is not same as = " + expectedValue, true);
//			throw new AssertionError();
//		}
//	}

//	public boolean verifyText(WebElement element, String expectedValue) {
//		String actualValue = element.getText();
//		System.out.println("Expected Value = " + expectedValue);
//		System.out.println("Actual Value = " + actualValue);
//
//		if (actualValue.equals(expectedValue)) {
//			Reporter.LogEvent(Status.PASS, "Verify actual value = " + actualValue + " is same as = " + expectedValue,
//					"Actual value = " + actualValue + " is same as = " + expectedValue, true);
//			return true;
//		} else {
//			Reporter.LogEvent(Status.FAIL,
//					"Verify actual value = " + actualValue + " is not same as = " + expectedValue,
//					"Actual value = " + actualValue + " is not same as = " + expectedValue, true);
//			throw new AssertionError();
//		}
//	}

	public void scrollPageBottom() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			while (true) {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			}
		} catch (Exception e) {
			// End of page reached
		}
	}

//	public void verifyValue(WebElement element, String expectedValue) {
//		String actualValue = element.getAttribute("value");
//		System.out.println("Expected Value = " + expectedValue);
//		System.out.println("Actual Value = " + actualValue);
//
//		if (actualValue.equals(expectedValue)) {
//			Reporter.LogEvent(Status.PASS,
//					"Verify ACTUAL VALUE = " + actualValue + " is same as EXPECTED VALUE = " + expectedValue,
//					"Actual value = " + actualValue + " is same as EXPECTED VALUE = " + expectedValue, true);
//		} else {
//			Reporter.LogEvent(Status.FAIL,
//					"Verify ACTUAL VALUE = " + actualValue + " is not same as EXPECTED VALUE = " + expectedValue,
//					"Actual value = " + actualValue + " is not same as EXPECTED VALUE = " + expectedValue, true);
//			throw new AssertionError();
//		}
//	}

	public String currentDate(String format) {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(currentDate);
	}

	public String addDays(String dateBefore, String format, int val) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(dateBefore));
		} catch (Exception e) {
			e.printStackTrace();
		}

		cal.add(Calendar.DAY_OF_MONTH, val);
		String dateAfter = sdf.format(cal.getTime());
		System.out.println("After Date = " + dateAfter);
		return dateAfter;
	}

	public void switchToDefaultWindow() {
		Web.getDriver().switchTo().defaultContent();
	}

	public void waitForElementForValues(WebElement w) {
		try {
			if (w.getTagName().equals("input")) {
				new WebDriverWait(Web.getDriver(), Duration.ofSeconds(20)).ignoring(NoSuchElementException.class)
						.until(ExpectedConditions.attributeToBeNotEmpty(w, "value"));
			} else {
				new WebDriverWait(Web.getDriver(), Duration.ofSeconds(20)).ignoring(NoSuchElementException.class)
						.until(wd -> !w.getText().trim().isEmpty());
			}
		} catch (TimeoutException e) {
			System.out.println("Values not fetched for the web element \n");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void highlighterMethod(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) Web.getDriver();
		// Unhighlight all elements
		js.executeScript(
				"var elements = document.getElementsByTagName('*');" + "for (var i = 0; i < elements.length; i++) {"
						+ "   elements[i].style.border = '';" + "   elements[i].style.backgroundColor = '';" + "}");

		js.executeScript("arguments[0].setAttribute('style', 'border: 1px solid red; background: yellow')", element);
	}

	public boolean buttonClick(String name) {
		WebElement ele = Web.getDriver().findElement(By.xpath("//button[contains(text(), '" + name + "')]"));
		waitForElementToBeClickable(ele); // Using a wait method for clickability
		scrollIntoView(ele); // Using a scrollIntoView method.
		return actionsClickOnElement(ele); // Using a action click method.
	}

	// Helper methods (if not already present in WebActions or Web class)
	public void waitForElementToBeClickable(WebElement element) {
		new WebDriverWait(Web.getDriver(), Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) Web.getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public boolean actionsClickOnElement(WebElement element) {
		try {
			element.click();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean pressKeyMultipleTimes(String key, int times) throws Exception {
		for (int i = 0; i < times; i++) {
			userPressKeyboardShortcut(key);
		}
		return true;
	}

	public void userPressKeyboardShortcut(String s) throws Exception {
		Actions action = new Actions(Web.getDriver());

		switch (s.toUpperCase()) {
		case "TAB":
			action.sendKeys(Keys.TAB).build().perform();
			break;
		case "ENTER":
			action.sendKeys(Keys.ENTER).build().perform();
			break;
		case "F9":
			action.sendKeys(Keys.F9).build().perform();
			break;
		case "F3":
			action.sendKeys(Keys.F3).build().perform();
			Thread.sleep(2000);
			break;
		case "F7":
			action.sendKeys(Keys.F7).build().perform();
			Thread.sleep(2000);
			break;
		case "F8":
			action.sendKeys(Keys.F8).build().perform();
			Thread.sleep(2000);
			break;
		case "BACK_SPACE":
			action.sendKeys(Keys.BACK_SPACE).build().perform();
			Thread.sleep(2000);
			break;
		case "CTRL+F2":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F2).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+H":
			action.keyDown(Keys.CONTROL).sendKeys("h").keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+J":
			action.keyDown(Keys.CONTROL).sendKeys("j").keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+F12":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F12).keyUp(Keys.CONTROL).perform();
			break;
		case "SHIFT+F1":
			action.keyDown(Keys.SHIFT).sendKeys(Keys.F1).keyUp(Keys.SHIFT).perform();
			break;
		case "SHIFT+F2":
			action.keyDown(Keys.SHIFT).sendKeys(Keys.F2).keyUp(Keys.SHIFT).perform();
			break;
		case "SHIFT+F3":
			action.keyDown(Keys.SHIFT).sendKeys(Keys.F3).keyUp(Keys.SHIFT).perform();
			break;
		case "SHIFT+F4":
			action.keyDown(Keys.SHIFT).sendKeys(Keys.F4).keyUp(Keys.SHIFT).perform();
			break;
		case "SHIFT+F9":
			action.keyDown(Keys.SHIFT).sendKeys(Keys.F9).keyUp(Keys.SHIFT).perform();
			break;
		case "CTRL+F1":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F1).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+F3":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F3).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+F4":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F4).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+F5":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+F6":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F6).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+S":
			action.keyDown(Keys.CONTROL).sendKeys("s").keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+P":
			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_P);
				robot.keyRelease(KeyEvent.VK_P);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "CTRL+M":
			action.keyDown(Keys.CONTROL).sendKeys("m").keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+U":
			action.keyDown(Keys.CONTROL).sendKeys("u").keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+T":
			action.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+A":
			action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
			break;
		case "ALT+N":
			action.keyDown(Keys.ALT).sendKeys("n").keyUp(Keys.ALT).perform();
			break;
		case "ALT+T":
			action.keyDown(Keys.ALT).sendKeys("t").keyUp(Keys.ALT).perform();
			break;
		case "CTRL+DOWN":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.DOWN).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+UP":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.UP).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+R":
			action.keyDown(Keys.CONTROL).sendKeys("r").keyUp(Keys.CONTROL).perform();
			break;
		case "ALT+M":
			action.keyDown(Keys.ALT).sendKeys("m").keyUp(Keys.ALT).perform();
			break;
		case "SHIFT+CTRL+D":
			try {
				Thread.sleep(2000);
				action.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys("d").keyUp(Keys.SHIFT).keyUp(Keys.CONTROL)
						.perform();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "CTRL+ALT+F5":
			try {
				Thread.sleep(2000);
				action.keyDown(Keys.CONTROL).keyDown(Keys.ALT).sendKeys(Keys.F5).keyUp(Keys.ALT).keyUp(Keys.CONTROL)
						.perform();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "ALT+SHIFT+D":
			try {
				Thread.sleep(2000);
				action.keyDown(Keys.ALT).keyDown(Keys.SHIFT).sendKeys("d").keyUp(Keys.SHIFT).keyUp(Keys.ALT).perform();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "F4":
			action.sendKeys(Keys.F4).build().perform();
			break;
		case "F6":
			action.sendKeys(Keys.F6).build().perform();
			break;
		case "SHIFT+F6":
			action.keyDown(Keys.SHIFT).sendKeys(Keys.F6).keyUp(Keys.SHIFT).perform();
			break;
		case "SHIFT+TAB":
			action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
			break;
		case "DOWNARROW":
			action.sendKeys(Keys.DOWN).build().perform();
			break;
		case "LEFTARROW":
			action.sendKeys(Keys.LEFT).build().perform();
			break;
		case "UPARROW":
			action.sendKeys(Keys.UP).build().perform();
			break;
		case "ESCAPE":
			action.keyDown(Keys.ESCAPE).keyUp(Keys.ESCAPE).perform();
			break;
		case "PRINT":
			try {
				Robot robot = new Robot();
				Thread.sleep(2000);
				Web.getDriver().switchTo().activeElement().click();
				Thread.sleep(2000);
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				System.out.println("ESC key pressed");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "BACKSPACE":
			action.sendKeys(Keys.BACK_SPACE).build().perform();
			break;
		case "CTRL+F9":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.F9).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+SHIFT+F4":
			try {
				action.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys(Keys.F4).keyUp(Keys.SHIFT).keyUp(Keys.CONTROL)
						.perform();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "CTRL+DOWNARROW":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.DOWN).keyUp(Keys.CONTROL).perform();
			break;
		case "CTRL+UPARROW":
			action.keyDown(Keys.CONTROL).sendKeys(Keys.UP).keyUp(Keys.CONTROL).perform();
			break;
		default:
			System.out.println("Invalid keyboard shortcut: " + s);
		}
	}

}
