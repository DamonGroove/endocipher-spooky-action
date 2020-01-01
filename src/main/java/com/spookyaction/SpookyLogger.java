package com.spookyaction;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SpookyLogger {
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void testLogger () {
        LOGGER.log(Level.INFO, "Logger working");
    }
}
