package com.websocket.websocket.controller;

import com.websocket.websocket.models.JwtRequest;
import com.websocket.websocket.models.JwtToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@CrossOrigin
public class AuthorizationController {

    private final AuthenticationManager manager;
    private final JwtToken jwtToken;
    private final UserDetailsService service;

    public AuthorizationController(AuthenticationManager manager,
                                   JwtToken jwtToken,
                                   @Qualifier("userDetailService") UserDetailsService service) {
        this.manager = manager;
        this.jwtToken = jwtToken;
        this.service = service;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("USER"))));

        UserDetails user = service.loadUserByUsername(request.getUsername());
        String token = jwtToken.createToken(user);

        return ResponseEntity.ok(token);
    }
}
