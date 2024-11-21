package lib.browserfactory;

import java.util.logging.Logger;

import common.CustomLogger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Chrome implements Browser{

	private static final CustomLogger logger = CustomLogger.getInstance();

	@Override
	public RemoteWebDriver launchBrowser() {
		
		logger.info("Launching Chrome Browser");
		return new ChromeDriver();
		
	}

}
