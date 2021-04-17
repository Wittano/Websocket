package com.wittano.websocket.service.controller;

import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.request.JwtRequest;
import com.wittano.websocket.repository.UserRepository;
import com.wittano.websocket.utils.JwtToken;
import com.wittano.websocket.utils.statics.ExceptionTemplate;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager manager;
    private final JwtToken jwtToken;
    private final UserRepository repo;

    public String auth(JwtRequest request) {
        final User user = repo.findUserByUsername(request.getUsername())
                .orElseThrow(ExceptionTemplate.USER_NOT_FOUND(request.getUsername()));

        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("USER"))));

        return jwtToken.createToken(user.getUsername());
    }
}
