package com.monthlyexpenses.server.app.exception;

public class ResourceAlreadyExists extends RuntimeException {

    private String message;

    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
