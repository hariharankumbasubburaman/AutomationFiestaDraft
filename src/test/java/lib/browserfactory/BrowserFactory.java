package lib.browserfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {
	
	

	public static RemoteWebDriver createBrowser(BrowserType browserType,String url) {
		RemoteWebDriver driver;
		switch (browserType) {

		case CHROME:
			driver= new Chrome().launchBrowser();
			break;
		case EDGE:
			driver= new Edge().launchBrowser();
			break;
		default:
			throw new IllegalArgumentException("Wrong browser type");

		}
		
		commonSteps(driver,url);
		return driver;
	}
	
	public static void commonSteps(RemoteWebDriver driver,String url)
	{
		BrowserHelper.maximizeWindow(driver);
		BrowserHelper.launchUrl(driver,url);
		BrowserHelper.implicitlyWait(driver,20);
		
	}

}
