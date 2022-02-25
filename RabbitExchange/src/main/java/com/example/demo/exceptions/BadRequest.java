package com.example.demo.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException {

    public BadRequest(String message) {
        super(message);
    }

    public BadRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public static BadRequest get(String message, Throwable cause) {
        return new BadRequest(message, cause);
    }

    public static BadRequest get(String message) {
        return new BadRequest(message);
    }

}
