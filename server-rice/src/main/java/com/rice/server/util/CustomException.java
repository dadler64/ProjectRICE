package com.rice.server.util;

public class CustomException extends Exception {

    CustomException() {
        super();
    }

    CustomException(String message) {
        super("Generic Exception: " + message);
    }
}
