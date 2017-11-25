package com.rice.server.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@SuppressWarnings("Duplicates")
public class ServerLogger {

    public static final int NONE = 0;
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;

    private static final String LOG_FILE_NAME = "Server.log";
    private static PrintStream printStream;
    private static FileOutputStream fileOutputStream;
    private static SplitOutputStream splitOutputStream;

    private static void setLoggingLevel(int level, boolean verbose) {
        ServerPrint.setVerbose(verbose);
        switch (level) {
            case 0: {
                ServerPrint.setNone();
                System.out.println("Logging level set to 0");
            }
            case 1: {
                ServerPrint.setInfo();
                ServerPrint.info("Logging level set to 1");
            }
            case 2: {
                ServerPrint.setDebug();
                ServerPrint.info("Logging level set to 2");
            }
            case 3: {
                ServerPrint.setWarn();
                ServerPrint.info("Logging level set to 3");
            }
            case 4: {
                ServerPrint.setError();
                ServerPrint.info("Logging level set to 4");
            }
        }
    }

    public static void start(int level, boolean verbose) {
        setLoggingLevel(level, verbose);
        ServerPrint.info("Starting Logger...");
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
            ServerPrint.error("Failed to start Logger. \n");
            e.printStackTrace();
        }
        ServerPrint.info("ServerLogger started!");

    }

    public static void stop() {
        ServerPrint.info("Stopping ServerLogger...");
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
        ServerPrint.info("ServerLogger stopped!");
    }
}
