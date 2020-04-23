package com.websocket.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping("/home")
    public String home(){
        return "main";
    }

    /* This request is exist because when you want to login in account and you'll success.
    Next user will be redirect to "/main/home" but in configuration is set POST method. */
    @PostMapping("/home")
    public String homePost(){
        return "main";
    }

}
