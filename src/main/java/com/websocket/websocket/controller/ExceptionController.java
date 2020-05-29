package com.websocket.websocket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionController {

    @ExceptionHandler(value = {CloneNotSupportedException.class})
    public ResponseEntity<String> CloneException() {
        return ResponseEntity.status(500).body("Something went wrong :(");
    }
}
