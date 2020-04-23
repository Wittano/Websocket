package com.websocket.websocket.controller;

import com.websocket.websocket.models.User;
import com.websocket.websocket.models.UserDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("userError", "");
        return "index";
    }

    @RequestMapping("/forget")
    public String forgetPassword(Model model){
        model.addAttribute("email", "");

        return "forget";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new UserDB());

        return "register";
    }
}
