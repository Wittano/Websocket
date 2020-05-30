package com.websocket.websocket.controller;

import com.websocket.websocket.interfaces.service.FriendService;
import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Set;

@RestController
@CrossOrigin
public class UserController {

    private final UsersService usersService;
    private final FriendService friendService;

    public UserController(UsersService usersService, FriendService friendService) {
        this.usersService = usersService;
        this.friendService = friendService;
    }

    @GetMapping("/user/{name}")
    public User getUser(@PathVariable("name") String name) {
        return usersService.getUserByName(name);
    }

    @GetMapping("/friend/{user}")
    public Set<String> getFriends(@PathVariable("user") String name) {
        return friendService.getFriends(name);
    }

    @PostMapping("/friend/{who}/{friend}")
    public void addFriend(@PathVariable("friend") String friend, @PathVariable("who") String name)
            throws CloneNotSupportedException {
        if (name.equals(friend)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Username and friend who want to add, can't be same person");
        }

        friendService.addFriend(name, friend);
    }

    @DeleteMapping("/friend/{who}/{friend}")
    public void deleteFriend(@PathVariable("friend") String friend, @PathVariable("who") String name)
            throws CloneNotSupportedException {
        if (name.equals(friend)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Username and friend name want to add, can't be same person");
        }

        friendService.deleteFriend(name, friend);
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@Valid @RequestBody User userDB, BindingResult result) {
        if (result.hasFieldErrors("username") && result.hasFieldErrors("password")) {
            return ResponseEntity.badRequest().body("Invalid data");
        }

        try {
            usersService.save(userDB);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User is exist!");
        }

        return ResponseEntity.ok(userDB);
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
