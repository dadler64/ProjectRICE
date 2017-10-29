package com.rice.universal;

public class CustomException extends Exception {

    CustomException() {
        super();
    }

    CustomException(String message) {
        super("Generic Exception: " + message);
    }
}
