package com.rice.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class FileSaveRunnable implements Runnable {

    private File file;

    private String contents;

    public FileSaveRunnable(final File file, final String contents) {
        this.file = file;
        this.contents = contents;
    }

    @Override
    public void run() {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final PrintStream printStream = new PrintStream(file);
            printStream.println(contents);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
