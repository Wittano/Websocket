package com.websocket.websocket.controller;

import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.UserDB;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/user/{name}")
    public UserDB getUser(@PathVariable("name") String name){
        return usersService.getUserByName(name);
    }

    @PostMapping("/user")
    public UserDB register(@Valid @ModelAttribute UserDB userDB, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User wasn't found");
        }

        usersService.save(userDB);

        return userDB;
    }

    @PutMapping("/user/{user}/{update}")
    public void updateUser(@PathVariable("user") UserDB user,
                             @PathVariable("update") UserDB update){
        usersService.update(user, update);
    }

    @DeleteMapping("/user/{name}")
    public void deleteUser(@PathVariable("name") String name){
        usersService.delete(name);
    }
}
