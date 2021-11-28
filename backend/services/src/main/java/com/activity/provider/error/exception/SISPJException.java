package com.activity.provider.error.exception;

public class SISPJException extends ApiException {

    private final SISPJExceptionDefinition exceptionDefinition;

    public SISPJException(SISPJExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition.getMessage());
        this.exceptionDefinition = exceptionDefinition;
    }

    public SISPJExceptionDefinition getExceptionDefinition() {
        return exceptionDefinition;
    }
}
