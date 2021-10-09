package com.monthlyexpenses.server.app.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    private String message;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
