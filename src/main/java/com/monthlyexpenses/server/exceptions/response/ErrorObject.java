package com.monthlyexpenses.server.exceptions.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorObject {

    private final String message;
    private final String field;
    private final Object parameter;
}
