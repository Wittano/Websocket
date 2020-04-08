package com.websocket.websocket.controller;

import com.websocket.websocket.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("user", new User());
        return "index";
    }

    @RequestMapping("/forget")
    public String forgetPassword(Model model){
        model.addAttribute("email", "");

        return "forget";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());

        return "register";
    }

    @RequestMapping("/main")
    public String homePage(){
        return "main";
    }
}
