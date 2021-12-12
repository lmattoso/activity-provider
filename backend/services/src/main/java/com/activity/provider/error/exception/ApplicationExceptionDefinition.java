package com.activity.provider.error.exception;

import org.springframework.http.HttpStatus;

public enum ApplicationExceptionDefinition implements ExceptionDefinition {
    BAD_REQUEST("Bad request made, parameters not recognized", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_EXISTS("Aluno não existe!", HttpStatus.BAD_REQUEST),
    ;

    private String exceptionMessage;
    private HttpStatus statusCode;

    ApplicationExceptionDefinition(String exceptionMessage, HttpStatus statusCode){
        this.exceptionMessage = exceptionMessage;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.exceptionMessage;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return this.name();
    }

}
