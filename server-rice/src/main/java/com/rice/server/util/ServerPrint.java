package com.rice.server.util;

public class ServerPrint {

    private static boolean showInfo;
    private static boolean showDebug;
    private static boolean showWarn;
    private static boolean showError;
    public static boolean verbose = false;

    public static void out(Object object) {
        System.out.print(object);
    }

    public static void line(Object object) {
        System.out.print(object + "\n");
    }

    public static void info(Object object) {
        if (showInfo)
            if (verbose) {
                System.out.print("INFO | 0 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
            } else {
                System.out.print("INFO | " + object + "\n");
            }
    }

    public static void debug(Object object) {
        if (showDebug)
            if (verbose) {
                System.out.print("DEBUG | 1 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
            } else {
                System.out.print("DEBUG | " + object + "\n");
            }
    }

    public static void warn(Object object) {
        if (showWarn)
            if (verbose) {
                System.out.print("WARNING | 2 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
            } else {
                System.out.print("WARNING | " + object + "\n");
            }
    }

    public static void error(Object object) {
        if (showError)
            if (verbose) {
                System.err.print("ERROR | 3 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
            } else {
                System.err.print("ERROR | " + object + "\n");
            }
    }

    public static void critical(Object object) {
        if (showError) {
            if (verbose) {
                System.err.print("CRITICAL ERROR | 4 | " + Util.getDate() + " | " + Util.getThreadInfo() + " | " + object + "\n");
                System.exit(4);
            } else {
                System.err.print("CRITICAL ERROR | " + object + "\n");
            }
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

    public static void setVerbose(boolean verbose) {
        ServerPrint.verbose = verbose;
    }
}
