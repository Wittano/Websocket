package com.websocket.websocket.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;

@RestController
public class ExceptionController {

    @ExceptionHandler(value = {CloneNotSupportedException.class})
    public ResponseEntity<String> CloneException() {
        return ResponseEntity.status(500).body("Something went wrong :(");
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<String> ExpireJwtException() {
        return ResponseEntity.badRequest().body("Your JWT token was expired!");
    }

    @ExceptionHandler({NoResultException.class})
    public ResponseEntity<String> ResourceNotFound() {
        return ResponseEntity.badRequest().body("Resource which want to find, doesn't exist");
    }
}
