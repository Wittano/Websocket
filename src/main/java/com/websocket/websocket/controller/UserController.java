package com.websocket.websocket.controller;

import com.websocket.websocket.models.UserRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @PostMapping("/register")
    public String register(@Valid UserRegister user, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("user", new UserRegister());
            return "register";
        }

        return "redirect:/";
    }

}
