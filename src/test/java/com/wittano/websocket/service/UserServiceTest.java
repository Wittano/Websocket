package com.wittano.websocket.service;

import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.repository.UserRepository;
import com.wittano.websocket.service.controller.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.wittano.websocket.utils.TestData.PASSWORD;
import static com.wittano.websocket.utils.TestData.TEST_USER_REQUEST;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @DisplayName("Save user")
    @Test
    void saveUser() {
        User user = repository.findUserByUsername(TEST_USER_REQUEST.getUsername())
                .orElse(service.save(TEST_USER_REQUEST));

        Assertions.assertEquals(TEST_USER_REQUEST.getUsername(), user.getUsername());
        Assertions.assertNotEquals(TEST_USER_REQUEST.getPassword(), PASSWORD);
    }
}
