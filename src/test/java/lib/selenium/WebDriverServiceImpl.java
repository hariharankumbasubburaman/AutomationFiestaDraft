package lib.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import lib.listeners.WebDriverListener;

public class WebDriverServiceImpl extends WebDriverListener implements WebDriverService {

	public static String incidentNumber;
	public static String changeRequestNumber;

	public WebElement locateElement(String locator, String locValue) {

		try {

			switch (locator) {
			case "id":
				return driver.findElement(By.id(locValue));
			case "name":
				return driver.findElement(By.name(locValue));
			case "class":
				return driver.findElement(By.className(locValue));
			case "link":
				return driver.findElement(By.linkText(locValue));
			case "xpath":
				return driver.findElement(By.xpath(locValue));
			default:
				break;
			}

		} catch (NoSuchElementException e) {
			reportStep("The element with locator " + locator + " not found.", "FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while finding " + locator + " with the value " + locValue, "FAIL");
		}
		return null;
	}

	public WebElement locateElement(String locValue) {
		return driver.findElement(By.id(locValue));
	}

	public void type(WebElement ele, String data, String shortDesc) {
		String maskedData = "";
		try {
			maskedData = shortDesc.contains("Password") ? "******" : data;
			waitForclickability(ele);
			ele.clear();
			ele.sendKeys(data);

			reportStep("The data: " + maskedData + " entered successfully in the " + shortDesc + " field ", "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The data: " + maskedData + " could not be entered in the " + shortDesc + " field ", "FAIL");
		} catch (WebDriverException e) {
			e.printStackTrace();
			reportStep("Unknown exception occured while entering " + maskedData + " in the " + shortDesc + " field: "
					+ ele, "FAIL");
		}
	}

	public void typeAndChoose(WebElement ele, String data, String shortDesc) {
		try {
			waitForclickability(ele);
			ele.clear();
			ele.sendKeys(data);

			Thread.sleep(5000);
			ele.sendKeys(Keys.TAB);
			reportStep("The data: " + data + " entered successfully in the " + shortDesc + " field:", "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The data: " + data + " could not be entered in the " + shortDesc + " field:", "FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while entering " + data + " in the " + shortDesc + " field:", "FAIL");
		} catch (InterruptedException e) {

		}
	}

	public void typeAndEnter(WebElement ele, String data, String shortDesc) {
		try {
			waitForclickability(ele);
			ele.clear();
			ele.sendKeys(data, Keys.ENTER);

			reportStep("The data: " + data + " entered successfully in the " + shortDesc + " field :", "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The data: " + data + " could not be entered in the " + shortDesc + "  field :", "FAIL");
		} catch (WebDriverException e) {
			e.printStackTrace();
			reportStep("Unknown exception occured while entering " + data + " in the " + shortDesc + " field :",
					"FAIL");
		}
	}

	public void click(WebElement ele, String shortDesc) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();

			String originalStyle = highlightElement(ele);

			reportStep("Clicking the element " + text, "PASS");

			revertHighlight(ele, originalStyle);

			ele.click();

		} catch (InvalidElementStateException e) {
			reportStep("The element: " + text + " could not be clicked", "FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while clicking " + shortDesc, "FAIL");
		}
	}

	public void clickElementInsideShadowRoot(String querySelector, String eleShortDesc) {

		try {

			Thread.sleep(5000);

			JavascriptExecutor js = (JavascriptExecutor) driver;

			WebElement ele = (WebElement) js.executeScript(querySelector);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));

			String originalStyle = highlightElement(ele);

			reportStep("Clicking the element " + eleShortDesc, "PASS");

			revertHighlight(ele, originalStyle);

			ele.click();

		} catch (InvalidElementStateException e) {
			reportStep("The element: " + eleShortDesc + " could not be clicked", "FAIL");
		} catch (JavascriptException e) {
			System.out.println(e.getMessage());
			reportStep("JavaScript execution failed: " + e.getMessage(), "FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while clicking in the field :" + eleShortDesc, "FAIL");
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			reportStep("An unexpected error occurred: " + e.getMessage(), "FAIL");

		}
	}

	public void swicthToFrameInsideShadowRoot(String frameQuerySelector, String eleShortDesc) {

		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;

			WebElement ele = (WebElement) js.executeScript(frameQuerySelector);

			switchToFrame(ele);

			// reportStep("The frame " + eleShortDesc + " is switched", "PASS");
		} catch (InvalidElementStateException e) {
			reportStep("The element: " + eleShortDesc + " could not be clicked", "FAIL");
		} catch (JavascriptException e) {
			System.out.println(e.getMessage());
			reportStep("JavaScript execution failed: " + e.getMessage(), "FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while clicking in the field :" + eleShortDesc, "FAIL");
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			reportStep("An unexpected error occurred: " + e.getMessage(), "FAIL");

		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
			reportStep("The element :" + text + "  is clicked.", "PASS", false);
		} catch (InvalidElementStateException e) {
			reportStep("The element: " + text + " could not be clicked", "FAIL", false);
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while clicking in the field :", "FAIL", false);
		}
	}

	public String highlightElement(WebElement ele) {
		String originalStyle = "";
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			originalStyle = ele.getAttribute("style");
			jsExecutor.executeScript("arguments[0].style.border='3px solid red'", ele);

			// Wait for a short duration to observe the highlight (e.g., 1 second)
			Thread.sleep(500);

		}

		catch (InterruptedException e) {
			System.out.println("InterruptedException occurred while waiting: " + e.getMessage());
			Thread.currentThread().interrupt(); // Restore the interrupted status
		} catch (org.openqa.selenium.JavascriptException e) {
			System.out.println("JavascriptException occurred while executing JavaScript: " + e.getMessage());
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("NoSuchElementException: The specified element was not found: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An unexpected exception occurred: " + e.getMessage());
		}
		return originalStyle;

	}

	public void revertHighlight(WebElement ele, String originalStyle) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		// Revert the highlight to original style
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", ele, originalStyle);

	}

	public String getText(WebElement ele) {
		String bReturn = "";
		try {
			bReturn = ele.getText();
		} catch (WebDriverException e) {
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}
		return bReturn;
	}

	public String getTitle() {
		String bReturn = "";
		try {
			bReturn = driver.getTitle();
		} catch (WebDriverException e) {
			reportStep("Unknown Exception Occured While fetching Title", "FAIL");
		}
		return bReturn;
	}

	public String getAttribute(WebElement ele, String attribute) {
		String bReturn = "";
		try {
			bReturn = ele.getAttribute(attribute);
		} catch (WebDriverException e) {
			e.printStackTrace();
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}
		return bReturn;
	}

	public void selectDropDownUsingVisibleText(WebElement ele, String value) {
		try {
			new Select(ele).selectByVisibleText(value);
			reportStep("The dropdown is selected with text " + value, "PASS");
		} catch (WebDriverException e) {
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}

	}

	public void selectDropDownUsingIndex(WebElement ele, int index) {
		try {
			new Select(ele).selectByIndex(index);
			reportStep("The dropdown is selected with index " + index, "PASS");
		} catch (WebDriverException e) {
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}

	}

	public boolean verifyExactTitle(String title) {
		boolean bReturn = false;
		try {
			if (getTitle().equals(title)) {
				reportStep("The title of the page matches with the value :" + title, "PASS");
				bReturn = true;
			} else {
				reportStep("The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
		return bReturn;
	}

	public void verifyExactText(WebElement ele, String expectedText) {
		try {
			if (getText(ele).equals(expectedText)) {
				reportStep("The text: " + getText(ele) + " matches with the value :" + expectedText, "PASS");
			} else {
				reportStep("The text " + getText(ele) + " doesn't matches the actual " + expectedText, "FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the Text", "FAIL");
		}

	}

	public void verifyPartialText(WebElement ele, String expectedText) {
		try {
			if (getText(ele).contains(expectedText)) {
				reportStep("The expected text contains the actual " + expectedText, "PASS");
			} else {
				reportStep("The expected text doesn't contain the actual " + expectedText, "FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the Text", "FAIL");
		}
	}

	public void verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if (getAttribute(ele, attribute).equals(value)) {
				reportStep("The expected attribute :" + attribute + " value matches the actual " + value, "PASS");
			} else {
				reportStep("The expected attribute :" + attribute + " value does not matches the actual " + value,
						"FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the Attribute Text", "FAIL");
		}

	}

	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if (getAttribute(ele, attribute).contains(value)) {
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "PASS");
			} else {
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the Attribute Text", "FAIL");
		}
	}

	public void verifySelected(WebElement ele) {
		try {
			if (ele.isSelected()) {
				reportStep("The element " + ele + " is selected", "PASS");
			} else {
				reportStep("The element " + ele + " is not selected", "FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void verifyDisplayed(WebElement ele, String eleShortDesc) {
		try {
			if (ele.isDisplayed()) {
				reportStep("The element " + eleShortDesc + " is visible", "PASS");
			} else {
				reportStep("The element " + eleShortDesc + " is not visible", "FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void switchToWindow(int index) {
		try {
			Set<String> allWindowHandles = driver.getWindowHandles();
			List<String> allHandles = new ArrayList<>();
			allHandles.addAll(allWindowHandles);
			driver.switchTo().window(allHandles.get(index));
		} catch (NoSuchWindowException e) {
			reportStep("The driver could not move to the given window by index " + index, "PASS");
		} catch (WebDriverException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void switchToFrame(WebElement ele) {
		try {
			driver.switchTo().frame(ele);
			// reportStep("switch In to the Frame " + ele, "PASS");
		} catch (NoSuchFrameException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		} catch (WebDriverException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void acceptAlert() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
			reportStep("The alert " + text + " is accepted.", "PASS");
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.", "FAIL");
		} catch (WebDriverException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void dismissAlert() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			reportStep("The alert " + text + " is dismissed.", "PASS");
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.", "FAIL");
		} catch (WebDriverException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}

	}

	public String getAlertText() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.", "FAIL");
		} catch (WebDriverException e) {
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
		return text;
	}

	public void closeActiveBrowser() {
		try {
			if (driver != null) {
				driver.close();
				reportStep("The browser is closed", "PASS", false);
			}
		} catch (Exception e) {
			reportStep("The browser could not be closed", "FAIL", false);
		}
	}

	public void closeAllBrowsers() {
		try {
			if (driver != null) {
				driver.quit();
				reportStep("The opened browsers are closed", "PASS", false);
			}
		} catch (Exception e) {
			reportStep("Unexpected error occured in Browser", "FAIL", false);
		}
	}

	public void selectDropDownUsingValue(WebElement ele, String value) {
		try {
			new Select(ele).selectByValue(value);
			reportStep("The dropdown is selected with text " + value, "PASS");
		} catch (WebDriverException e) {
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}

	}

	@Override
	public boolean verifyPartialTitle(String title) {
		boolean bReturn = false;
		try {
			if (getTitle().contains(title)) {
				reportStep("The title of the page matches with the value :" + title, "PASS");
				bReturn = true;
			} else {
				reportStep("The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"FAIL");
			}
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
		return bReturn;
	}

	public void waitForclickability(WebElement ele) {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(ele));
	}

}
