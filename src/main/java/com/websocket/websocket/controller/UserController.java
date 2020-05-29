package com.websocket.websocket.controller;

import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/user/{name}")
    public User getUser(@PathVariable("name") String name) {
        return usersService.getUserByName(name);
    }

    @PostMapping("/user")
    public User register(@Valid @RequestBody User userDB, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }

        usersService.save(userDB);

        return userDB;
    }

    @PutMapping("/user/{user}/{update}")
    public void updateUser(@PathVariable("user") User user,
                           @PathVariable("update") User update) {
        usersService.update(user, update);
    }

    @DeleteMapping("/user/{name}")
    public void deleteUser(@PathVariable("name") String name) {
        usersService.delete(name);
    }
}
