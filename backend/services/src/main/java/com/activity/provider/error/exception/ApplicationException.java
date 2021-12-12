package com.activity.provider.error.exception;

public class ApplicationException extends ApiException {

    private final ApplicationExceptionDefinition exceptionDefinition;

    public ApplicationException(ApplicationExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition.getMessage());
        this.exceptionDefinition = exceptionDefinition;
    }

    public ApplicationExceptionDefinition getExceptionDefinition() {
        return exceptionDefinition;
    }
}
