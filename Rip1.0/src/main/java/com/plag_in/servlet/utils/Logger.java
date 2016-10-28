package com.plag_in.servlet.utils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Владимир on 28.10.2016.
 */
public class Logger {

    private static final String INFO_LOGS_DIR = "LOGS/";
    private static final String INFO_LOG_FILE_NAME = "info.log";
    private static final String ANALYTICS_LOG_FILE_NAME = "analytics.log";
    private static final String ERROR_LOG_FILE_NAME = "error.log";
    private static final String CRITICAL_LOG_FILE_NAME_PREFIX = "critical_";
    private static final String CRITICAL_LOG_FILE_NAME_SUFFIX = ".log";
    private static ServletContext mServletContext;

    public enum LogLevel { INFO, ANALYTICS, ERROR, CRITICAL }

    public static void log(final LogLevel level, final Exception ex ) {
        List<String> lines = new LinkedList<>();
        lines.add("");
        lines.add(ex.getMessage());
        for (StackTraceElement traceElement : ex.getStackTrace()) {
            lines.add(traceElement.toString());
        }
        log(level, lines);
    }

    public static void log(final LogLevel level, final String message) {
        List<String> lines = new LinkedList<>();
        lines.add("");
        lines.add(message);
        log(level, lines);
    }

    private static void log(final LogLevel level, final List<String> lines) {
        String fileName = INFO_LOGS_DIR;
        switch (level) {
            case INFO:
                fileName += INFO_LOG_FILE_NAME;
                break;
            case ANALYTICS:
                fileName += ANALYTICS_LOG_FILE_NAME;
                break;
            case ERROR:
                fileName += ERROR_LOG_FILE_NAME;
                break;
            case CRITICAL:
                fileName += CRITICAL_LOG_FILE_NAME_PREFIX +
                        Long.toString((new Date()).getTime()) +
                        CRITICAL_LOG_FILE_NAME_SUFFIX;
                break;
            default:
                break;
        }
        try {
            FileUtils.appendLinesToFile(mServletContext.getRealPath(fileName), lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void setServletContext(final ServletContext context) {
        mServletContext = context;
    }
}
