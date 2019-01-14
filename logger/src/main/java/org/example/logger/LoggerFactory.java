package org.example.logger;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerFactory {
    static {
        try {
            LogManager.getLogManager()
                    .readConfiguration(LoggerFactory.class.getResourceAsStream("/logging.properties"));
        } catch (NullPointerException | IOException ex) {
            System.out.println("WARNING: Could not open '/logging.properties' configuration file");
        }
    }

    public static Logger getLogger(Class<?> clazz) {

        return Logger.getLogger(clazz.getCanonicalName());
    }
}
