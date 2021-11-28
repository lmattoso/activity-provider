package com.activity.provider.error.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionDefinition {

    String getErrorCode();

    String getMessage();

    HttpStatus getStatusCode();
}
