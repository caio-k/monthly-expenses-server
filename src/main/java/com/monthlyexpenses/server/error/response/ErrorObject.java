package com.monthlyexpenses.server.error.response;

import lombok.Getter;

@Getter
public class ErrorObject {

    private final String message;
    private final String field;
    private final Object parameter;

    public ErrorObject(String message, String field, Object parameter) {
        this.message = message;
        this.field = field;
        this.parameter = parameter;
    }
}
