package com.padawanbr.alfredfood.domain.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BussinesException extends RuntimeException {
    public BussinesException(String message) {
        super(message);
    }

    public BussinesException(String message, Throwable cause) {
        super(message, cause);
    }
}
