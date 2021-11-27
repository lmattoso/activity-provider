package com.m2w.sispj.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SISPJTokenException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 2527764483839903249L;
    private final String key;
    private final List<String> args;

    public SISPJTokenException(String message) {
        super(message);
        this.key = null;
        this.args = null;
    }

    public SISPJTokenException(String message, String key) {
        super(message);
        this.key = key;
        this.args = null;
    }

    public SISPJTokenException(String message, String key, Throwable cause) {
        super(message, cause);
        this.key = key;
        this.args = null;
    }

    public SISPJTokenException(String message, String key, List<String> args) {
        super(message);
        this.key = key;
        this.args = args;
    }

    public SISPJTokenException(String message, String key, List<String> args, Throwable cause) {
        super(message, cause);
        this.key = key;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public List<String> getArgs() {
        return args;
    }
}
