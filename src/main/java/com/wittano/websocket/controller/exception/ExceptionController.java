package com.wittano.websocket.controller.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;

/**
 * General controller to manage exceptions
 */
@RestController
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionController {
    @ExceptionHandler(CloneNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String cloneException() {
        return "Something went wrong :(";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public String jwtException() {
        return "Your JWT token was expired!";
    }

    @ExceptionHandler(NoResultException.class)
    public String resourceNotFound() {
        return "Resource which want to find, doesn't exist";
    }
}
