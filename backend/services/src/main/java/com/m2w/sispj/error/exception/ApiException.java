package com.m2w.sispj.error.exception;

public abstract class ApiException extends RuntimeException {

    public ApiException(String exceptionDefinitionMessage) {
        super(exceptionDefinitionMessage);
    }

    public abstract ExceptionDefinition getExceptionDefinition();
}
