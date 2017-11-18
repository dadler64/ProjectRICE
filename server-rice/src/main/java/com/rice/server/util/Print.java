package com.rice.server.util;

public class Print {

    private static boolean showInfo;
    private static boolean showDebug;
    private static boolean showWarn;
    private static boolean showError;

    public static void out(Object object) {
        System.out.print(object);
    }

    public static void line(Object object) {
        System.out.print(object + "\n");
    }

    public static void debug(Object object) {
        if (showDebug)
            System.out.print("DEBUG | 0 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void info(Object object) {
        if (showInfo)
            System.out.print("INFO | 1 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void warn(Object object) {
        if (showWarn)
            System.out.print("WARNING | 3 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void error(Object object) {
        if (showError)
            System.err.print("ERROR | 4 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void critical(Object object) {
        if (showError) {
            System.err.print("CRITICAL ERROR | 5 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
            System.exit(5);
        }
    }

    public static void setInfo() {
        showInfo = true;
        showDebug = false;
        showWarn = false;
        showError = false;
    }

    public static void setDebug() {
        showInfo = true;
        showDebug = true;
        showWarn = false;
        showError = false;
    }

    public static void setWarn() {
        showInfo = true;
        showDebug = true;
        showWarn = true;
        showError = false;
    }

    public static void setError() {
        showInfo = true;
        showDebug = true;
        showWarn = true;
        showError = true;
    }

    public static void setNone() {
        showInfo = false;
        showDebug = false;
        showWarn = false;
        showError = false;
    }
}
