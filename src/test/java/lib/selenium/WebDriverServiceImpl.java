package lib.selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import common.CustomLogger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
	private static final CustomLogger logger = CustomLogger.getInstance();
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
			logger.error("The element with locator "+locator+" not found", e);
			reportStep("The element with locator " + locator + " not found.", "FAIL");
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while finding "+locator+" with value " + locValue, e);
			reportStep("Unknown exception occured while finding " + locator + " with the value " + locValue, "FAIL");
		}
		return null;
	}

	public WebElement locateElement(String locValue) {
		WebElement element = null;
		try {
			logger.info("Attempting to locate the element with ID: " + locValue);
			element = driver.findElement(By.id(locValue));
			logger.info("Element located successfully with ID: " + locValue);
		} catch (NoSuchElementException e) {
			logger.error("Element with ID: " + locValue + " could not be located.", e);
		} catch (WebDriverException e) {
			logger.error("An error occurred while locating the element with ID: " + locValue, e);
		}
		return element;
	}

	public void type(WebElement ele, String data, String shortDesc) {
		String maskedData = "";
//		String elementDetails = String.format(
//				"[ID: %s] [Name: %s] [Class: %s] [Tag: %s] [Text: %s]",
//				ele.getAttribute("id"),
//				ele.getAttribute("name"),
//				ele.getAttribute("class"),
//				ele.getTagName(),
//				ele.getText()  // Including text content
//		);
		try {
			maskedData = shortDesc.contains("Password") ? "******" : data;
			waitForclickability(ele);
			ele.clear();
			ele.sendKeys(data);
			logger.info("The data: " + maskedData + " entered successfully in the " + shortDesc + " field ");
			reportStep("The data: " + maskedData + " entered successfully in the " + shortDesc + " field ", "PASS");
		} catch (InvalidElementStateException e) {
			logger.error("The data: " + maskedData + " could not be entered in the " + shortDesc + " field due to InvalidElementStateException", e);
			reportStep("The data: " + maskedData + " could not be entered in the " + shortDesc + " field ", "FAIL");
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while entering " + maskedData + " in the " + shortDesc + " field: " + ele, e);
			reportStep("Unknown exception occured while entering " + maskedData + " in the " + shortDesc + " field: "
					+ ele, "FAIL");
		}
	}

	public void typeAndChoose(WebElement ele, String data, String shortDesc) {
//		String elementDetails = String.format(
//				"[ID: %s] [Name: %s] [Class: %s] [Tag: %s]",
//				ele.getAttribute("id"),
//				ele.getAttribute("name"),
//				ele.getAttribute("class"),
//				ele.getTagName()
//		);
			logger.debug("Attempting to type data into the field: " + shortDesc + " using element: " + ele);
		try {
			waitForclickability(ele);
			ele.clear();
			ele.sendKeys(data);

			Thread.sleep(5000);
			ele.sendKeys(Keys.TAB);
			logger.info("The data: " + data + " entered successfully in the " + shortDesc + " field using the element"+ ele);
			reportStep("The data: " + data + " entered successfully in the " + shortDesc + " field:", "PASS");
		} catch (InvalidElementStateException e) {
			logger.error("Invalid Element State Exception occurred while entering" + data + " in the " + shortDesc + " field using the element"+ ele, e);
			reportStep("The data: " + data + " could not be entered in the " + shortDesc + " field:", "FAIL");
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while entering " + data + " in the " + shortDesc + " field using the element"+ ele, e);
			reportStep("Unknown exception occurred while entering " + data + " in the " + shortDesc + " field:", "FAIL");
		} catch (InterruptedException e) {
			logger.error("Interrupted Exception exception occurred while entering " + data + " in the " + shortDesc + " field using the element"+ ele, e);
			reportStep("Interrupted Exception exception occurred while entering " + data + " in the " + shortDesc + " field:", "FAIL");
		}
	}

	public void typeAndEnter(WebElement ele, String data, String shortDesc) {
//		String elementDetails = String.format(
//				"[ID: %s] [Name: %s] [Class: %s] [Tag: %s] [Text: %s]",
//				ele.getAttribute("id"),
//				ele.getAttribute("name"),
//				ele.getAttribute("class"),
//				ele.getTagName(),
//				ele.getText()
//		);
		try {
			waitForclickability(ele);
			ele.clear();
			ele.sendKeys(data, Keys.ENTER);
			logger.info("The data: " + data + " entered successfully in the " + shortDesc + " field using the element"+ ele);
			reportStep("The data: " + data + " entered successfully in the " + shortDesc + " field :", "PASS");
		} catch (InvalidElementStateException e) {
			logger.error("Invalid Element State Exception occurred while entering" + data + " in the " + shortDesc + " field using the element"+ ele, e);
			reportStep("The data: " + data + " could not be entered in the " + shortDesc + "  field :", "FAIL");
		} catch (WebDriverException e) {
			logger.error("Web Driver Exception occurred while entering " + data + " in the " + shortDesc + " field using the element"+ ele, e);
			reportStep("Unknown exception occured while entering " + data + " in the " + shortDesc + " field :",
					"FAIL");
		}
	}

	public void click(WebElement ele, String shortDesc) {
		String text = "";
//		String elementDetails = String.format(
//				"[ID: %s] [Name: %s] [Class: %s] [Tag: %s] [Text: %s] [Type: %s] [Href: %s]",
//				ele.getAttribute("id"),
//				ele.getAttribute("name"),
//				ele.getAttribute("class"),
//				ele.getTagName(),
//				ele.getText(),
//				ele.getAttribute("type"),
//				ele.getAttribute("href")
//		);
		try {
			logger.debug("Attempting to click the element: " + shortDesc);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));

			text = ele.getText();
			logger.debug("Retrieved text from the element: " + text);

			String originalStyle = highlightElement(ele);
			logger.debug("Element highlighted with original style stored.");

			reportStep("Clicking the element " + text, "PASS");
			logger.info("Successfully clicked the element: " + shortDesc);

			revertHighlight(ele, originalStyle);
			logger.debug("Reverted element highlighting to original style.");

			ele.click();

		} catch (InvalidElementStateException e) {
			String errorMessage = "The element: " + text + " could not be clicked due to an invalid element state for the element" + ele;
			reportStep("The element: " + text + " could not be clicked", "FAIL");
			logger.error(errorMessage, e);
		} catch (WebDriverException e) {
			String errorMessage = "An unknown exception occurred while clicking: " + shortDesc + ele;
			reportStep("Unknown exception occured while clicking " + shortDesc, "FAIL");
			logger.error(errorMessage, e);
		}
	}

	public void clickElementInsideShadowRoot(String querySelector, String eleShortDesc) {

		try {
			logger.debug("Attempting to locate and click the element inside the shadow root: " + eleShortDesc);

			Thread.sleep(5000);
			logger.debug("Waited for 5 seconds before interacting with the shadow DOM element");

			JavascriptExecutor js = (JavascriptExecutor) driver;

			WebElement ele = (WebElement) js.executeScript(querySelector);
			logger.debug("Element located inside the shadow root using querySelector: " + querySelector);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			logger.info("Element is clickable: " + eleShortDesc);

			String originalStyle = highlightElement(ele);
			logger.debug("Element highlighted for interaction.");

			reportStep("Clicking the element " + eleShortDesc, "PASS");
			logger.info("Successfully clicked the element: " + eleShortDesc);

			revertHighlight(ele, originalStyle);
			logger.debug("Reverted element highlighting to its original style.");

			ele.click();

		} catch (InvalidElementStateException e) {
			String errorMessage = "The element: " + eleShortDesc + " could not be clicked due to invalid state.";
			logger.error(errorMessage, e);
			reportStep("The element: " + eleShortDesc + " could not be clicked", "FAIL");
		} catch (JavascriptException e) {
			String errorMessage = "JavaScript execution failed for element: " + eleShortDesc;
			logger.error(errorMessage, e);
			reportStep("JavaScript execution failed: " + e.getMessage(), "FAIL");
		} catch (WebDriverException e) {
			String errorMessage = "Unknown exception occurred while clicking the element: " + eleShortDesc;
			logger.error(errorMessage, e);
			reportStep("Unknown exception occured while clicking in the field :" + eleShortDesc, "FAIL");
		}

		catch (Exception e) {
			String errorMessage = "An unexpected error occurred while clicking the element: " + eleShortDesc;
			logger.error(errorMessage, e);
			reportStep("An unexpected error occurred: " + e.getMessage(), "FAIL");

		}
	}

	public void swicthToFrameInsideShadowRoot(String frameQuerySelector, String eleShortDesc) {

		try {
			logger.debug("Attempting to locate the frame inside the shadow root: " + eleShortDesc);

			JavascriptExecutor js = (JavascriptExecutor) driver;

			WebElement ele = (WebElement) js.executeScript(frameQuerySelector);
			logger.debug("Frame element located using querySelector: " + frameQuerySelector);

			switchToFrame(ele);
			logger.info("Successfully switched to the frame: " + eleShortDesc);

			// reportStep("The frame " + eleShortDesc + " is switched", "PASS");
		} catch (InvalidElementStateException e) {
			String errorMessage = "The frame: " + eleShortDesc + " could not be interacted with due to invalid state.";
			logger.error(errorMessage, e);
			reportStep("The element: " + eleShortDesc + " could not be clicked", "FAIL");
		} catch (JavascriptException e) {
			String errorMessage = "JavaScript execution failed while locating the frame: " + eleShortDesc;
			logger.error(errorMessage, e);
			reportStep("JavaScript execution failed: " + e.getMessage(), "FAIL");
		} catch (WebDriverException e) {
			String errorMessage = "Unknown exception occurred while interacting with the frame: " + eleShortDesc;
			logger.error(errorMessage, e);
			reportStep("Unknown exception occured while clicking in the field :" + eleShortDesc, "FAIL");
		}
		catch (Exception e) {
			String errorMessage = "An unexpected error occurred while switching to the frame: " + eleShortDesc;
			logger.error(errorMessage, e);
			reportStep("An unexpected error occurred: " + e.getMessage(), "FAIL");

		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text = "";
		try {
			logger.info("Attempting to click the element without snapshot.");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			logger.debug("Element is clickable.");

			text = ele.getText();
			ele.click();
			logger.info("The element: " + text + " has been clicked successfully.");
			reportStep("The element :" + text + "  is clicked.", "PASS", false);
		} catch (InvalidElementStateException e) {
			String errorMessage = "The element: " + text + " could not be clicked due to invalid state.";
			logger.error(errorMessage, e);
			reportStep("The element: " + text + " could not be clicked", "FAIL", false);
		} catch (WebDriverException e) {
			String errorMessage = "Unknown exception occurred while clicking the element.";
			logger.error(errorMessage, e);
			reportStep("Unknown exception occured while clicking in the field :", "FAIL", false);
		} catch (Exception e) {
			String errorMessage = "An unexpected error occurred while clicking the element: " + text;
			logger.error(errorMessage, e);
			reportStep(errorMessage, "FAIL", false);
		}
	}

	public String highlightElement(WebElement ele) {
		String originalStyle = "";
		try {
			logger.debug("Attempting to highlight the element.");
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			originalStyle = ele.getAttribute("style");
			jsExecutor.executeScript("arguments[0].style.border='3px solid red'", ele);

			logger.debug("Element highlighted with a red border.");

			// Wait for a short duration to observe the highlight (e.g., 1 second)
			Thread.sleep(500);

		}
		catch (InterruptedException e) {
			String errorMessage = "InterruptedException occurred while waiting: " + e.getMessage();
			logger.error(errorMessage, e);
			Thread.currentThread().interrupt(); // Restore the interrupted status
		} catch (org.openqa.selenium.JavascriptException e) {
			String errorMessage = "JavascriptException occurred while executing JavaScript: " + e.getMessage();
			logger.error(errorMessage, e);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			String errorMessage = "NoSuchElementException: The specified element was not found: " + e.getMessage();
			logger.error(errorMessage, e);
		} catch (Exception e) {
			String errorMessage = "An unexpected exception occurred: " + e.getMessage();
			logger.error(errorMessage, e);
		}
		logger.debug("Original style of the element has been captured.");
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
			logger.debug("Attempting to retrieve text from the element");
			bReturn = ele.getText();
			logger.info("Text retrieved successfully from the element: " + ele);
		} catch (WebDriverException e) {
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}
		return bReturn;
	}

	public String getTitle() {
		String bReturn = "";
		try {
			logger.debug("Attempting to retrieve the title of the current page");
			bReturn = driver.getTitle();
		} catch (WebDriverException e) {
			String errorMessage = "Unknown Exception Occurred While fetching Title";
			logger.error(errorMessage, e);
			reportStep("Unknown Exception Occured While fetching Title", "FAIL");
		}
		return bReturn;
	}

	public String getAttribute(WebElement ele, String attribute) {
		String bReturn = "";
		try {
			bReturn = ele.getAttribute(attribute);
			logger.info("Retrieved the attribute: " + bReturn + " from the element");
		} catch (WebDriverException e) {
			String errorMessage = "The element: " + ele + " could not be found or the attribute: " + attribute + " or could not be retrieved";
			logger.error(errorMessage, e);
			reportStep("The element: " + ele + " could not be found", "FAIL");
		}
		return bReturn;
	}

	public void selectDropDownUsingVisibleText(WebElement ele, String value) {
		try {
			logger.debug("Attempting to select the dropdown with the visible text: " + value);
			new Select(ele).selectByVisibleText(value);
			logger.info("The dropdown is successfully selected with the text: " + value);
			reportStep("The dropdown is selected with text " + value, "PASS");
		} catch (WebDriverException e) {
			String errorMessage = "The element: " + ele + " could not be found or the dropdown value: " + value + " could not be selected.";
			logger.error(errorMessage, e);
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}

	}

	public void selectDropDownUsingIndex(WebElement ele, int index) {
		try {
			logger.debug("Attempting to select the dropdown with index: " + index);
			new Select(ele).selectByIndex(index);
			logger.info("The dropdown is successfully selected with the index: " + index);
			reportStep("The dropdown is selected with index " + index, "PASS");
		} catch (WebDriverException e) {
			String errorMessage = "The element: " + ele + " could not be found or the dropdown index: " + index + " could not be selected.";
			logger.error(errorMessage, e);
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}

	}

	public boolean verifyExactTitle(String title) {
		boolean bReturn = false;
		try {
			logger.debug("Verifying the page title against expected title: " + title);
			if (getTitle().equals(title)) {
				logger.info("The title of the page matches with the expected value: " + title);
				reportStep("The title of the page matches with the value :" + title, "PASS");
				bReturn = true;
			} else {
				String errorMessage = "The title of the page: '" + driver.getTitle() + "' did not match with the expected value: '" + title + "'";
				logger.error(errorMessage);
				reportStep("The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"FAIL");
			}
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while verifying the title", e);
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
		return bReturn;
	}

	public void verifyExactText(WebElement ele, String expectedText) {
		try {
			logger.debug("Verifying the text of the element with expected value: " + expectedText);
			String actualText = getText(ele);
			if (getText(ele).equals(expectedText)) {
				logger.info("The text: '" + actualText + "' matches with the expected value: '" + expectedText + "'");
				reportStep("The text: " + getText(ele) + " matches with the value :" + expectedText, "PASS");
			} else {
				String errorMessage = "The text: '" + actualText + "' doesn't match the expected value: '" + expectedText + "'";
				logger.error(errorMessage);
				reportStep("The text " + getText(ele) + " doesn't matches the actual " + expectedText, "FAIL");
			}
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while verifying the text", e);
			reportStep("Unknown exception occured while verifying the Text", "FAIL");
		}

	}

	public void verifyPartialText(WebElement ele, String expectedText) {
		try {
			logger.debug("Verifying if the expected text is contained in the actual text of the element");
			String actualText = getText(ele);
			if (getText(ele).contains(expectedText)) {
				logger.info("The actual text: '" + actualText + "' contains the expected partial text: '" + expectedText + "'");
				reportStep("The expected text contains the actual " + expectedText, "PASS");
			} else {
				String errorMessage = "The expected text: '" + expectedText + "' doesn't contain the actual text: '" + actualText + "'";
				logger.error(errorMessage);
				reportStep("The expected text doesn't contain the actual " + expectedText, "FAIL");
			}
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while verifying the partial text", e);
			reportStep("Unknown exception occured while verifying the Text", "FAIL");
		}
	}

	public void verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			logger.debug("Verifying the exact value of the attribute: " + attribute);
			String actualValue = getAttribute(ele, attribute);
			if (getAttribute(ele, attribute).equals(value)) {
				logger.info("The expected attribute: '" + attribute + "' has the correct value: '" + value + "'");
				reportStep("The expected attribute :" + attribute + " value matches the actual " + value, "PASS");
			} else {
				String errorMessage = "The expected attribute: '" + attribute + "' value: '" + value + "' does not match the actual value: '" + actualValue + "'";
				logger.error(errorMessage);
				reportStep("The expected attribute :" + attribute + " value does not matches the actual " + value,
						"FAIL");
			}
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while verifying the attribute: " + attribute, e);
			reportStep("Unknown exception occured while verifying the Attribute Text", "FAIL");
		}

	}

	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			logger.debug("Verifying if the attribute: " + attribute + " contains the value: " + value);
			String actualValue = getAttribute(ele, attribute);
			if (getAttribute(ele, attribute).contains(value)) {
				logger.info("The expected attribute: '" + attribute + "' value contains the actual value: '" + value + "'");
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "PASS");
			} else {
				String errorMessage = "The expected attribute: '" + attribute + "' value: '" + value + "' does not contain the actual value: '" + actualValue + "'";
				logger.error(errorMessage);
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"FAIL");
			}
		} catch (WebDriverException e) {
			logger.error("Unknown exception occurred while verifying the attribute: " + attribute, e);
			reportStep("Unknown exception occured while verifying the Attribute Text", "FAIL");
		}
	}

	public void verifySelected(WebElement ele) {
		logger.debug("Verifying if the element is selected: " + ele);
		try {
			if (ele.isSelected()) {
				logger.info("The element " + ele + " is selected.");
				reportStep("The element " + ele + " is selected", "PASS");
			} else {
				String errorMessage = "The element " + ele + " is not selected";
				logger.error(errorMessage);
				reportStep("The element " + ele + " is not selected", "FAIL");
			}
		} catch (WebDriverException e) {
			logger.error("WebDriverException occurred while verifying if the element is selected: " + e.getMessage());
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void verifyDisplayed(WebElement ele, String eleShortDesc) {
		try {
			logger.debug("Verifying if the element is displayed: " + eleShortDesc);
			if (ele.isDisplayed()) {
				logger.info("The element " + eleShortDesc + " is visible.");
				reportStep("The element " + eleShortDesc + " is visible", "PASS");
			} else {
				String errorMessage = "The element " + eleShortDesc + " is not visible";
				logger.error(errorMessage);
				reportStep("The element " + eleShortDesc + " is not visible", "FAIL");
			}
		} catch (WebDriverException e) {
			String errorMessage = "WebDriverException occurred while verifying visibility of the element: " + e.getMessage();
			logger.error(errorMessage);
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void switchToWindow(int index) {
		try {
			logger.debug("Attempting to switch to window with index: " + index);
			Set<String> allWindowHandles = driver.getWindowHandles();
			List<String> allHandles = new ArrayList<>();

			allHandles.addAll(allWindowHandles);
			driver.switchTo().window(allHandles.get(index));
			logger.info("Successfully switched to window with index: " + index);

		} catch (NoSuchWindowException e) {
			String errorMessage = "The driver could not move to the given window by index " + index;
			logger.error(errorMessage);
			reportStep("The driver could not move to the given window by index " + index, "PASS");
		} catch (WebDriverException e) {
			String errorMessage = "WebDriverException occurred while switching to window: " + e.getMessage();
			logger.error(errorMessage);
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void switchToFrame(WebElement ele) {
//		String elementDetails = String.format(
//				"[ID: %s] [Name: %s] [Class: %s] [Tag: %s]",
//				ele.getAttribute("id"),
//				ele.getAttribute("name"),
//				ele.getAttribute("class"),
//				ele.getTagName()
//		);
		try {
			logger.debug("Attempting to switch to frame with element: " + ele);
			driver.switchTo().frame(ele);
			logger.debug("Successfully switched to the frame with element: " + ele);
		} catch (NoSuchFrameException e) {
			String errorMessage = "NoSuchFrameException: Could not find the frame with element: " + ele;
			logger.error(errorMessage);
			reportStep("WebDriverException: " + e.getMessage(), "FAIL");
		} catch (WebDriverException e) {
			String errorMessage = "WebDriverException occurred while switching to frame: " + ele + e.getMessage();
			logger.error(errorMessage);
			reportStep("WebDriverException: " + e.getMessage(), "FAIL");
		}
	}


	public void acceptAlert() {
		String text = "";
		try {
			logger.debug("Attempting to accept alert");
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
			logger.info("Successfully accepted the alert: " + text);
			reportStep("The alert " + text + " is accepted.", "PASS");
		} catch (NoAlertPresentException e) {
			String errorMessage = "NoAlertPresentException: No alert is present to accept.";
			logger.error(errorMessage);
			reportStep("There is no alert present.", "FAIL");
		} catch (WebDriverException e) {
			String errorMessage = "WebDriverException occurred while accepting alert: " + e.getMessage();
			logger.error(errorMessage);
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}
	}

	public void dismissAlert() {
		String text = "";
		try {
			logger.debug("Attempting to dismiss alert");
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			logger.info("Successfully dismissed the alert: " + text);
			reportStep("The alert " + text + " is dismissed.", "PASS");
		} catch (NoAlertPresentException e) {
			String errorMessage = "NoAlertPresentException: No alert is present to dismiss.";
			logger.error(errorMessage, e);
			reportStep("There is no alert present.", "FAIL");
		} catch (WebDriverException e) {
			String errorMessage = "WebDriverException occurred while dismissing alert: " + e.getMessage();
			logger.error(errorMessage);
			reportStep("WebDriverException : " + e.getMessage(), "FAIL");
		}

	}

	public String getAlertText() {
		String text = "";
		try {
			logger.debug("Attempting to retrieve alert text");
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			logger.info("Successfully retrieved alert text: " + text);
		} catch (NoAlertPresentException e) {
			String errorMessage = "NoAlertPresentException: No alert present." + e.getMessage();
			logger.error(errorMessage);
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
				logger.info("The browser is closed");
				reportStep("The browser is closed", "PASS", false);
			}
		} catch (Exception e) {
			logger.error("The browser could not be closed due to exception", e);
			reportStep("The browser could not be closed", "FAIL", false);
		}
	}

	public void closeAllBrowsers() {
		try {
			if (driver != null) {
				driver.quit();
				logger.info("The opened browsers are closed");
				reportStep("The opened browsers are closed", "PASS", false);
			}
		} catch (Exception e) {
			logger.error("Unexpected error occured in Browser due to exception", e);
			reportStep("Unexpected error occured in Browser", "FAIL", false);
		}
	}

	public void selectDropDownUsingValue(WebElement ele, String value) {
		try {
			new Select(ele).selectByValue(value);
			logger.info("The dropdown is selected with text " + value);
			reportStep("The dropdown is selected with text " + value, "PASS");
		} catch (WebDriverException e) {
			logger.error("The element: " + ele + " could not be found.", e);
			reportStep("The element: " + ele + " could not be found.", "FAIL");
		}

	}

	@Override
	public boolean verifyPartialTitle(String title) {
		boolean bReturn = false;
		try {
			if (getTitle().contains(title)) {
				logger.info("The title of the page matches with the value :" + title);
				reportStep("The title of the page matches with the value :" + title, "PASS");
				bReturn = true;
			} else {
				logger.error("The title of the page:" + driver.getTitle() + " did not match with the value :" + title);
				reportStep("The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"FAIL");
			}
		} catch (WebDriverException e) {
			logger.error("Unknown exception occured while verifying the title", e);
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
		return bReturn;
	}

	public void waitForclickability(WebElement ele) {
		logger.debug("Waiting for the element to be clickable" + ele);
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(ele));
	}

}
