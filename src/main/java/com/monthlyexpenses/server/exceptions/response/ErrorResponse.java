package com.monthlyexpenses.server.exceptions.response;

import lombok.*;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private int code;
    private String status;
    private String objectName;
    private List<ErrorObject> errors;
}
