package com.monthlyexpenses.server.exceptions;

import com.monthlyexpenses.server.exceptions.response.ErrorDetails;
import com.monthlyexpenses.server.exceptions.response.ErrorObject;
import com.monthlyexpenses.server.exceptions.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorObject> errors = buildErrors(ex);
        ErrorResponse errorResponse = buildErrorResponse(ex, status, errors);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ErrorDetails> handleUniqueViolationException(UniqueViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorDetails errorDetails = buildErrorDetails(ex, request, status.value());
        return new ResponseEntity<>(errorDetails, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDetails errorDetails = buildErrorDetails(ex, request, status.value());
        return new ResponseEntity<>(errorDetails, status);
    }

    private ErrorResponse buildErrorResponse(MethodArgumentNotValidException ex, HttpStatus status, List<ErrorObject> errors) {
        return ErrorResponse.builder()
                .message("Requisição possui campos inválidos")
                .code(status.value())
                .status(status.getReasonPhrase())
                .objectName(ex.getBindingResult().getObjectName())
                .errors(errors)
                .build();
    }

    private List<ErrorObject> buildErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> ErrorObject.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField())
                        .parameter(error.getRejectedValue())
                        .build()
                ).collect(Collectors.toList());
    }

    private ErrorDetails buildErrorDetails(Exception ex, WebRequest request, int status) {
        return ErrorDetails.builder()
                .timestamp(new Date())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .status(status)
                .build();
    }
}
