package by.epamtc.lyskovkirill.tasklibrary.controller.logger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log {
    private static final Logger logger = Logger.getLogger("");

    static {
        new DOMConfigurator().doConfigure("log/log4j.xml",
                LogManager.getLoggerRepository());
    }

    public static Logger getLogger() {
        return logger;
    }
}
