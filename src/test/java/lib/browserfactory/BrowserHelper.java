package lib.browserfactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

public class BrowserHelper {
	
	private static final Logger logger = Logger.getLogger(BrowserHelper.class.getName());
	
	
	
	public static void launchUrl(WebDriver driver,String url)
	{
		driver.get(url);
		logger.info("Url launched");
	}
	
	public static void maximizeWindow(WebDriver driver)
	{
		driver.manage().window().maximize();
		logger.info("Maximized window");
	}
	
	public static void implicitlyWait(WebDriver driver,int waitSeconds)
	{
		driver.manage().timeouts().implicitlyWait(waitSeconds,TimeUnit.SECONDS);
		logger.info("waiting for load");
	}

}
