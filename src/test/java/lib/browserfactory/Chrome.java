package lib.browserfactory;

import java.util.logging.Logger;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Chrome implements Browser{
	
	private static final Logger logger = Logger.getLogger(Chrome.class.getName());

	@Override
	public RemoteWebDriver launchBrowser() {
		
		logger.info("Launching Chrome Browser");
		return new ChromeDriver();
		
	}

}
