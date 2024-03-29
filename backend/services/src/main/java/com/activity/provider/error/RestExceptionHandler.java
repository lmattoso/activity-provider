package com.activity.provider.error;


import com.activity.provider.error.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex, request.getContextPath());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleApplicationException(final HttpServletRequest request, final ApiException ex) {
        logger.error("Some error occurred: ErrorMessage = {},  Message = {}", ex);

        ApiError apiError = new ApiError(ex.getExceptionDefinition().getStatusCode(),
                ex.getExceptionDefinition().getMessage(),
                ex,
                request.getRequestURI(),
                ex.getExceptionDefinition().getErrorCode());

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        logger.error("Validation error: ErrorMessage = {}, Parameter passed = {}", ex);

        final List<ApiSubError> subErrors = new ArrayList<>();

        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            ApiValidationError subError = ApiValidationError.builder()
                .field(error.getField())
                .rejectedValue(error.getRejectedValue())
                .object(error.getObjectName())
                .message(error.getDefaultMessage())
                .build();

            subErrors.add(subError);
        }

        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            subErrors.add(ApiValidationError.builder().object(error.getObjectName()).message(error.getDefaultMessage()).build());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                "Request with missing or invalid fields.",
                ex,
                request.getContextPath(),
                subErrors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnknownException(Exception ex) {
        logger.error("Unknown Error {} \nStacktrace {}", ex);

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Oops!!! Something went wrong.", ex);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}