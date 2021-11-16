package by.epamtc.lyskovkirill.tasklibrary.controller.logger;

import by.epamtc.lyskovkirill.tasklibrary.util.FilePathConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;

public class Log {
    private static Logger logger;

    static {
        FilePathConstructor filePathConstructor = FilePathConstructor.getInstance();
        File logFile = filePathConstructor.computeFilePath(new File(System.getProperty("user.dir")), "logger/log4j.xml");
        if (logFile != null) {
            new DOMConfigurator().doConfigure(logFile.getPath(), LogManager.getLoggerRepository());
        }
    }

    public static synchronized Logger getLogger() {
        if (logger == null)
            logger = Logger.getLogger("");
        return logger;
    }
}
