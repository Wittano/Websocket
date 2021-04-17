package com.wittano.websocket.service.controller;

import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.request.UserRequest;
import com.wittano.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Save user with hashed password
     *
     * @param user User, which will be saved
     * @return User, which was added to database
     */
    public User save(UserRequest user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(new User(0,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getBirthday(),
                user.getGender(),
                true,
                "USER",
                new HashSet<>()));
    }
}
