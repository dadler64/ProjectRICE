package com.rice.server;

public class CustomException extends Exception {

    CustomException() {
        super();
    }

    CustomException(String message) {
        super("Generic Exception: " + message);
    }
}
