package com.wittano.websocket.controller;

import com.wittano.websocket.models.request.JwtRequest;
import com.wittano.websocket.service.controller.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public String generateToken(@RequestBody JwtRequest request) {
        return authService.auth(request);
    }
}
