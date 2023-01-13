package com.padawanbr.alfredfood.domain.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BussinesException extends RuntimeException {
    public BussinesException(String message) {
        super(message);
    }
}
