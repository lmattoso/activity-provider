package com.m2w.sispj.error.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionDefinition {

    String getErrorCode();

    String getMessage();

    HttpStatus getStatusCode();
}
