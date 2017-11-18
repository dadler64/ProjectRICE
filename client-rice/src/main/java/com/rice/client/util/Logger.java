package com.rice.client.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Logger {

    public static final int NONE = 0;
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;

    private static final String LOG_FILE_NAME = "Client.log";
    private static PrintStream printStream;
    private static FileOutputStream fileOutputStream;
    private static SplitOutputStream splitOutputStream;

    private static void setLoggingLevel(int level) {
        switch (level) {
            case 0: {
                Print.setNone();
                System.out.println("Logging level set to 0");
            }
            case 1: {
                Print.setInfo();
                Print.info("Logging level set to 1");
            }
            case 2: {
                Print.setDebug();
                Print.info("Logging level set to 2");
            }
            case 3: {
                Print.setWarn();
                Print.info("Logging level set to 3");
            }
            case 4: {
                Print.setError();
                Print.info("Logging level set to 4");
            }
//            default: {
//                Print.setNone();
//                System.out.println("Logging level set to none");
//            }
        }
    }

    public static void start(int level) {
        setLoggingLevel(level);
        Print.info("Starting Logger...");
        // Set up split output stream
        try {
            final File logFile = new File(LOG_FILE_NAME);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fileOutputStream = new FileOutputStream(logFile);
            splitOutputStream = new SplitOutputStream(System.out, fileOutputStream);
            printStream = new PrintStream(splitOutputStream);
            System.setOut(printStream);
        } catch (IOException e) {
            Print.error("Failed to start logger. \n");
            e.printStackTrace();
        }
        Print.info("Logger started!");

    }

    public static void stop() {
        Print.info("Stopping Logger...");
        System.setOut(System.out);
        try {
            fileOutputStream.close();
            fileOutputStream.flush();
            splitOutputStream.close();
            splitOutputStream.flush();
            printStream.close();
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Print.info("Logger stopped!");
    }
}
