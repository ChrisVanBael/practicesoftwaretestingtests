package com.tesuqa.practicesoftwaretestingtests.utilities;

import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestLogger - A utility class that combines SLF4J logging with Serenity reporting.
 * This provides a centralized logging mechanism for the entire test framework.
 */
public class TestLogger {

    /**
     * Gets a logger instance for the specified class.
     *
     * @param clazz The class to get the logger for
     * @return TestLogger instance
     */
    public static TestLogger getLogger(Class<?> clazz) {
        return new TestLogger(LoggerFactory.getLogger(clazz));
    }

    public static TestLogger auto() {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        try {
            return new TestLogger(LoggerFactory.getLogger(Class.forName(className)));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not auto-resolve logger class", e);
        }
    }


    private final Logger logger;

    private TestLogger(Logger logger) {
        this.logger = logger;
    }

    // ===== INFO =====
    public void info(String contents) {
        info(getCallingClassName(), contents);
    }

    public void info(String format, Object... args) {
        info(getCallingClassName(), format, args);
    }

    public void info(String title, String contents) {
        logger.info(title + ": {}", contents);
        Serenity.recordReportData().withTitle(title).andContents(contents);
    }

    public void info(String title, String format, Object... args) {
        String contents = String.format(format, args);
        logger.info(title + ": {}", contents);
        Serenity.recordReportData().withTitle(title).andContents(contents);
    }

    // ===== DEBUG =====
    public void debug(String contents) {
        debug(getCallingClassName(), contents);
    }

    public void debug(String format, Object... args) {
        debug(getCallingClassName(), format, args);
    }

    public void debug(String title, String contents) {
        logger.debug(title + ": {}", contents);
        Serenity.recordReportData().withTitle("[DEBUG] " + title).andContents(contents);
    }

    public void debug(String title, String format, Object... args) {
        String contents = String.format(format, args);
        logger.debug(title + ": {}", contents);
        Serenity.recordReportData().withTitle("[DEBUG] " + title).andContents(contents);
    }

    // ===== WARN =====
    public void warn(String contents) {
        warn(getCallingClassName(), contents);
    }

    public void warn(String format, Object... args) {
        warn(getCallingClassName(), format, args);
    }

    public void warn(String title, String contents) {
        logger.warn(title + ": {}", contents);
        Serenity.recordReportData().withTitle("[WARNING] " + title).andContents(contents);
    }

    public void warn(String title, String format, Object... args) {
        String contents = String.format(format, args);
        logger.warn(title + ": {}", contents);
        Serenity.recordReportData().withTitle("[WARNING] " + title).andContents(contents);
    }

    // ===== ERROR =====
    public void error(String contents) {
        error(getCallingClassName(), contents);
    }

    public void error(String format, Object... args) {
        error(getCallingClassName(), format, args);
    }

    public void error(String title, String contents) {
        logger.error(title + ": {}", contents);
        Serenity.recordReportData().withTitle("[ERROR] " + title).andContents(contents);
    }

    public void error(String title, String format, Object... args) {
        String contents = String.format(format, args);
        logger.error(title + ": {}", contents);
        Serenity.recordReportData().withTitle("[ERROR] " + title).andContents(contents);
    }

    // ===== EXCEPTION =====
    public void exception(String message, Throwable throwable) {
        exception(getCallingClassName(), message, throwable);
    }

    public void exception(String title, String message, Throwable throwable) {
        logger.error(title + ": {} - {}", message, throwable.getMessage(), throwable);
        Serenity.recordReportData().withTitle("[EXCEPTION] " + title)
            .andContents(message + "\n" + throwable.getMessage() + "\n" + getStackTraceAsString(throwable));
    }

    private String getCallingClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            if (!className.equals(TestLogger.class.getName())
                && !className.startsWith("java.lang.Thread")) {
                return className.substring(className.lastIndexOf('.') + 1);
            }
        }
        return "UnknownClass";
    }

    private String getStackTraceAsString(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("\n    at ").append(element.toString());
        }
        return sb.toString();
    }
}
