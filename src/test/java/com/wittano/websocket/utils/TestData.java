package com.wittano.websocket.utils;

import com.wittano.websocket.models.entity.Message;
import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.enums.Gender;
import com.wittano.websocket.models.request.FriendRequest;
import com.wittano.websocket.models.request.JwtRequest;
import com.wittano.websocket.models.request.MessageRequest;
import com.wittano.websocket.models.request.UserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;

public class TestData {
    public static final String PASSWORD = "password";
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    public static User TEST_USER = new User(
            1,
            "TestUser",
            ENCODER.encode(PASSWORD),
            "test@test.com",
            new Date(),
            Gender.Male,
            true,
            "USER",
            new HashSet<>());
    public static User TEST_USER_2 = new User(
            2,
            "TestUserB",
            ENCODER.encode(PASSWORD),
            "test2@test.com",
            new Date(),
            Gender.Male,
            true,
            "USER",
            new HashSet<>());
    public static Message TEST_MESSAGE = new Message(0,
            TEST_USER,
            TEST_USER_2,
            "Test",
            new Date(),
            "test");
    public static MessageRequest TEST_MESSAGE_REQUEST = new MessageRequest(TEST_MESSAGE.getFrom().getUsername(),
            TEST_MESSAGE.getTo().getUsername(),
            TEST_MESSAGE.getContent(),
            TEST_MESSAGE.getQueueName());
    public static FriendRequest TEST_FRIEND_REQUEST =
            new FriendRequest(TEST_USER.getUsername(), TEST_USER_2.getUsername());
    public static UserRequest TEST_USER_REQUEST = new UserRequest(TEST_USER.getUsername() + "123",
            PASSWORD,
            TEST_USER.getEmail() + "123",
            TEST_USER.getGender(),
            TEST_USER.getBirthday());
    public static JwtRequest TEST_JWT_REQUEST = new JwtRequest(TEST_USER.getUsername(), PASSWORD);
}
