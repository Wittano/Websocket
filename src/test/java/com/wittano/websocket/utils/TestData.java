package com.wittano.websocket.utils;

import com.wittano.websocket.models.entity.Message;
import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.models.request.FriendRequest;
import com.wittano.websocket.models.request.MessageRequest;

import java.util.Date;
import java.util.HashSet;

public class TestData {
    public static User TEST_USER = new User(
            1,
            "TestUser",
            "test@test.com",
            "test@test.com",
            new Date(),
            User.Gender.Male,
            true,
            "USER",
            new HashSet<>());

    public static User TEST_USER_2 = new User(
            2,
            "TestUser2",
            "test2@test.com",
            "test2@test.com",
            new Date(),
            User.Gender.Male,
            true,
            "USER",
            new HashSet<>());

    public static Message TEST_MESSAGE = new Message(0, TEST_USER, TEST_USER_2, "Test", new Date(), "test");

    public static MessageRequest TEST_MESSAGE_REQUEST = new MessageRequest(TEST_MESSAGE.getFrom().getUsername(),
            TEST_MESSAGE.getTo().getUsername(),
            TEST_MESSAGE.getContent(),
            TEST_MESSAGE.getQueueName());

    public static FriendRequest TEST_FRIEND_REQUEST =
            new FriendRequest(TEST_USER.getUsername(), TEST_USER_2.getUsername());
}
