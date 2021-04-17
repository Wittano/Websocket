package com.wittano.websocket.service;

import com.wittano.websocket.models.request.JwtRequest;
import com.wittano.websocket.repository.UserRepository;
import com.wittano.websocket.service.controller.AuthService;
import com.wittano.websocket.utils.JwtToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.wittano.websocket.utils.TestData.PASSWORD;
import static com.wittano.websocket.utils.TestData.TEST_USER;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService service;

    @Autowired
    private JwtToken token;

    @DisplayName("Authorization user")
    @Test
    void authUser() {
        String jwt = service.auth(new JwtRequest(TEST_USER.getUsername(), PASSWORD));

        Assertions.assertTrue(token.validToken(jwt, TEST_USER.getUsername()));
    }
}
