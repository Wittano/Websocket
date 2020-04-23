package com.websocket.websocket.controller;

import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.UserDB;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/user")
    public String register(@Valid @ModelAttribute UserDB userDB, BindingResult result){
        if(result.hasErrors()) {
            return "register";
        }

        usersService.save(userDB);

        return "redirect:/";
    }

    @DeleteMapping("/user/{name}")
    public String deleteUser(@PathVariable("name") String name){
        usersService.delete(name);

        return "index";
    }
}
