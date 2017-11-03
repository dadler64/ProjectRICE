package com.rice.server.utils;

public class CustomException extends Exception {

    CustomException() {
        super();
    }

    CustomException(String message) {
        super("Generic Exception: " + message);
    }
}
