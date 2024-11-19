package lib.browserfactory;

import java.util.logging.Logger;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Edge implements Browser{

	private static final Logger logger = Logger.getLogger(Edge.class.getName());

	@Override
	public RemoteWebDriver launchBrowser() {
		logger.info("Launching Edge Browser");
		return new EdgeDriver();
		
	}

}
