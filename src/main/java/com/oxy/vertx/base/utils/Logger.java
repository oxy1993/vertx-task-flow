package com.oxy.vertx.base.utils;

import com.oxy.vertx.base.entities.BaseRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class Logger {

    static {
        String log4jConfig = System.getProperty("log4j.configurationFile");
        if (log4jConfig == null || log4jConfig.trim().equals("")) {
            System.setProperty("log4j.configurationFile", "conf/log4j2.xml");
        }
    }

    private final org.apache.logging.log4j.Logger logger;


    protected Logger(org.apache.logging.log4j.Logger logger) {
        this.logger = logger;
    }

    public static Logger getLogger(Class aClass) {
        return new Logger(LogManager.getLogger(aClass));
    }

    public void error(String message, Object... params) {
        log(Level.ERROR, message, params);
    }

    public void error(BaseRequest input, String message, Object... params) {
        log(Level.ERROR, "{}|{}|" + message,input.getRequestId().substring(0, 7), input.getUser(), params);
    }

    public void warn(String message, Object... params) {
        log(Level.WARN, message, params);
    }

    public void warn(BaseRequest input, String message, Object... params) {
        log(Level.WARN, "{}|{}|" + message,input.getRequestId().substring(0, 7), input.getUser(), params);
    }

    public void info(String message, Object... params) {
        log(Level.INFO, message, params);
    }
    public void info(BaseRequest input, String message, Object... params) {
        log(Level.INFO, "{}|{}|" + message,input.getRequestId().substring(0, 7), input.getUser(), params);
    }

    public void debug(String message, Object... params) {
        log(Level.DEBUG, message, params);
    }
    public void debug(BaseRequest input, String message, Object... params) {
        log(Level.DEBUG, "{}|{}|" + message,input.getRequestId().substring(0, 7), input.getUser(), params);
    }

    private void log(Level level, String message, Object... params) {
        logger.log(level, message, params);
    }
}
