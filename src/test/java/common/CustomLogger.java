package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

public class CustomLogger {
    private static CustomLogger instance;
    private static final Logger logger = LoggerFactory.getLogger(CustomLogger.class);

    // Private constructor to prevent instantiation
    private CustomLogger() {}

    // Singleton instance retrieval
    public static CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    public void info(final String message) {
        logger.info(message);
        Reporter.log(message + "<br>");
    }

    public void warn(final String message) {
        logger.warn(message);
        String styledMessage = "<div class=\"skipped\">" + message + "</div>";
        Reporter.log(styledMessage + "<br>");
    }

    public void debug(final String message) {
        logger.debug(message);
        String styledMessage = "<div class=\"skipped\">" + message + "</div>";
        Reporter.log(styledMessage + "<br>");
    }

    public void error(final String message) {
        logger.error(message);
        String styledMessage = "<div class=\"failedConfig\">" + message + "</div>";
        Reporter.log(styledMessage + "<br>");
    }

    public void error(final String message, final Throwable t) {
        logger.error(message, t);
        String styledMessage = "<div class=\"failedConfig\">" + message + " | Exception: " + t.getMessage() + "</div>";
        Reporter.log(styledMessage + "<br>");
    }

    public void step(int step, String message) {
        String logMessage = String.format("--------==[ Step %d - %s ]==--------", step, message);
        logger.info(logMessage);
    }

    public void logTestCaseStart(String testCaseName) {
        logger.info("====================START OF TEST CASE====================");
        logger.info("Test Case '" + testCaseName + "' has started");
    }

    public void logTestCaseEnd(String testCaseName) {
        logger.info("Test Case '" + testCaseName + "' has ended");
        logger.info("====================END OF TEST CASE====================");
    }
}
