package com.monthlyexpenses.server.app.exception;

public class InvalidArgumentException extends RuntimeException {

    private String message;

    public InvalidArgumentException(String message) {
        super(message);
    }
}
