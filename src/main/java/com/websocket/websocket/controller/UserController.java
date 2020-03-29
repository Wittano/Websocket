package com.websocket.websocket.controller;

import com.websocket.websocket.repository.UserRepository;
import com.websocket.websocket.models.UserRegister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
public class UserController {

    private Logger log;
    private UserRepository repo;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        repo = repository;
        this.passwordEncoder = passwordEncoder;

        log = Logger.getLogger(this.getClass().getName());
    }

    @PostMapping("/register")
    public String register(@Valid UserRegister user, BindingResult result, Model model){
        if(result.hasErrors()) {
            model.addAttribute("user", new UserRegister());
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);

        return "redirect:/";
    }
}
