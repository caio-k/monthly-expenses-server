package com.monthlyexpenses.server.error.exception;

public class UniqueViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UniqueViolationException(String message) {
        super(message);
    }
}
