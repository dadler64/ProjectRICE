package com.rice.client.thread;

import com.rice.client.ui.FileTab;
import com.rice.client.util.Print;

import java.util.Stack;

public class TextWatcherThread extends Thread {

    private Stack<String> prevHistory = new Stack<>();
    private Stack<String> nextHistory = new Stack<>();
    private FileTab currentFile;
    private boolean run;

    /**
     * Default constructor
     *
     * @param currentFile current open file
     */
    public TextWatcherThread(FileTab currentFile) {
        this.currentFile = currentFile;
        run = true;
    }

    /**
     * Replace the current text in file with the last recorded instance of it.
     */
    public void undo() {
        nextHistory.push(this.currentFile.getTextAreaContent());
        if (!prevHistory.empty()) {
            this.currentFile.clear();
            this.currentFile.setTextArea(prevHistory.pop());
        }
    }

    /**
     * Replace replacement of the current text in file with the last recorded instance of it before its replacement.
     * Confusing eh??
     */
    public void redo() {
        prevHistory.push(this.currentFile.getTextAreaContent());
        if (!prevHistory.empty()) {
            this.currentFile.clear();
            this.currentFile.setTextArea(nextHistory.pop());
        }
    }

    /**
     * Thread to back up file every 10 seconds
     */
    @Override
    public void run() {
        while (run) {
            long start = System.currentTimeMillis();

            if (System.currentTimeMillis() - start == 10000) {
                Print.debug(">> Adding current text to history.");
                prevHistory.push(this.currentFile.getTextAreaContent());
                if (!nextHistory.empty())
                    nextHistory.clear();
            }
        }
    }

    /**
     * Stop the run loop
     */
    public void cleanUp() {
        run = false;
    }
}
