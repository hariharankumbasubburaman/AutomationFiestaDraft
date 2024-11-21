package lib.browserfactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import common.CustomLogger;
import org.openqa.selenium.WebDriver;

public class BrowserHelper {

	private static final CustomLogger logger = CustomLogger.getInstance();
	
	public static void launchUrl(WebDriver driver,String url)
	{
		driver.get(url);
		logger.info("Browser Url launched");
	}
	
	public static void maximizeWindow(WebDriver driver)
	{
		driver.manage().window().maximize();
		logger.info("Browser window maximized");
	}
	
	public static void implicitlyWait(WebDriver driver,int waitSeconds)
	{
		driver.manage().timeouts().implicitlyWait(waitSeconds,TimeUnit.SECONDS);
		logger.info("Waiting for the page to load");
	}

}
