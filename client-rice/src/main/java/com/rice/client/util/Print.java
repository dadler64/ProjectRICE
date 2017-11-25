package com.rice.client.util;

public class Print {

    private static boolean showInfo;
    private static boolean showDebug;
    private static boolean showWarn;
    private static boolean showError;
    private static boolean showCrit;

    public static void out(Object object) {
        System.out.print(object);
    }

    public static void line(Object object) {
        System.out.print(object + "\n");
    }

    public static void info(Object object) {
        if (showInfo)
            System.out.print("0 | INFO | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void debug(Object object) {
        if (showDebug)
            System.out.print("1 | DEBUG | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void warn(Object object) {
        if (showWarn)
            System.out.print("3 | WARNING | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void error(Object object) {
        if (showError)
            System.err.print("4 | ERROR | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
    }

    public static void critical(Object object) {
        if (showCrit) {
            System.err.print("5 | CRITICAL ERROR | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
            System.exit(5);
        }
    }

    public static void setInfo() {
        showInfo = true;
        showDebug = false;
        showWarn = false;
        showError = false;
        showCrit = false;
    }

    public static void setDebug() {
        showInfo = true;
        showDebug = true;
        showWarn = false;
        showError = false;
        showCrit = false;
    }

    public static void setWarn() {
        showInfo = true;
        showDebug = true;
        showWarn = true;
        showError = false;
        showCrit = false;
    }

    public static void setError() {
        showInfo = true;
        showDebug = true;
        showWarn = true;
        showError = true;
        showCrit = false;
    }

    public static void setCrit() {
        showInfo = true;
        showDebug = true;
        showWarn = true;
        showError = true;
        showCrit = true;
    }

    public static void setNone() {
        showInfo = false;
        showDebug = false;
        showWarn = false;
        showError = false;
        showCrit = false;
    }
}
