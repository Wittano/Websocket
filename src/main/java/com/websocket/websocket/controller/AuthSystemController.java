package com.websocket.websocket.controller;

import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.User;
import com.websocket.websocket.models.UserDB;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthSystemController {

    private final UsersService usersService;
    private String errorMessage;

    public AuthSystemController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/verify")
    public String login(@Valid @ModelAttribute User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "index";
        }

        model.addAttribute("user", new User());

        user.setPassword(StringEscapeUtils.escapeHtml4(user.getPassword()));

        if(!usersService.isUserExist(new UserDB(user.getUsername(), user.getPassword()))){
            errorMessage = "Nie ma takiego użytkownika";
        } else if(usersService.userVerify(new UserDB(user.getUsername(), user.getPassword()))){
            errorMessage = "Błędny login lub hasło";
        }

        return "index";
    }

    @PostMapping("/fail")
    public String failLogin(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("userError", errorMessage);

        return "index";
    }

}
