package by.epamtc.lyskovkirill.tasklibrary.controller.logger;

import by.epamtc.lyskovkirill.tasklibrary.util.FilePathConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;

public class Log {
    private static final Logger logger = Logger.getLogger("");

    static {
        File logFile = FilePathConstructor.computeFilePath(new File(System.getProperty("user.dir")), "logger/log4j.xml");
        if (logFile != null) {
            new DOMConfigurator().doConfigure(logFile.getPath(), LogManager.getLoggerRepository());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
