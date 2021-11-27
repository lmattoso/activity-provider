package com.m2w.sispj.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Getter
class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private String debugMessage;
    private String path;
    private List<ApiSubError> subErrors;

    private ApiError() {
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this(status, message, ex, null);
    }

    ApiError(HttpStatus status, String message, Throwable ex, String path) {
        this(status, message, ex, path, "");
    }

    ApiError(HttpStatus status, String message, Throwable ex, String path, String errorCode) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.path = path;
        this.errorCode = errorCode;
    }

    ApiError(HttpStatus status, String message, Throwable ex, String path, List<ApiSubError> subErrors) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.path = path;
        this.subErrors = subErrors;
    }
}