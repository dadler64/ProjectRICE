package com.rice.client.util;

import com.rice.client.ui.FileTab;

import java.util.Stack;

public class Compare {
    private char[] oc;
    private char[] cc = new char[0];

    public Compare(FileTab file) {
        this.oc = this.cc;
        this.cc = file.getTextAreaContent().toCharArray();
    }

    public Stack<String> getOperation() {
        // Stack to hold each operation in case for some reason multiple changes happen within a tick.
        // NOTE: ^^^ should not happen. This class should only have to handle one operation at a time.
        Stack<String> operationStack = new Stack<>();

        int minLength = Math.min(oc.length, cc.length);
//        for (int i = 0; i < minLength; i++) {
//            if (oc[i] != cc[i]) {
//               if () {
//
//               }
//            }
//        }
        return null;
    }
}
