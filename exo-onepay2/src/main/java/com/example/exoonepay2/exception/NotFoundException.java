package com.example.exoonepay2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is used in case if ID is empty
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "NotFoundException not found")
public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message){
        super(message);
    }

}
