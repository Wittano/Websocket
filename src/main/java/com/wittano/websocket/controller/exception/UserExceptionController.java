package com.wittano.websocket.controller.exception;

import com.wittano.websocket.exception.FriendException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * ControllerAdvice dedicate to exceptions related with user
 */
@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExceptionController {
    @ExceptionHandler({UsernameNotFoundException.class, FriendException.class, ValidationException.class})
    public String userException(Exception e) {
        return e.getMessage();
    }
}
