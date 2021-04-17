package com.wittano.websocket.controller;

import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.request.UserRequest;
import com.wittano.websocket.service.controller.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody UserRequest user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException("Invalid data");
        }

        return userService.save(user);
    }
}
