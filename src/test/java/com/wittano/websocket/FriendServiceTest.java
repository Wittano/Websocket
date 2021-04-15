package com.wittano.websocket;

import com.wittano.websocket.models.entity.User;
import com.wittano.websocket.repository.UserRepository;
import com.wittano.websocket.service.controller.FriendService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wittano.websocket.utils.TestData.*;

@SpringBootTest
public class FriendServiceTest {

    @Autowired
    UserRepository repository;

    @Autowired
    FriendService friendService;

    @Transactional
    List<User> getFriendsList() {
        List<User> friends = new LinkedList<>();
        repository.findAll().forEach(it ->
                friends.addAll(it.getFriends()
                        .stream()
                        .filter(e ->
                                e.getUsername().equals(TEST_USER_2.getUsername()))
                        .collect(Collectors.toList())));

        return friends;
    }

    @BeforeEach
    void init() {
        repository.saveAll(Arrays.asList(TEST_USER, TEST_USER_2));
        friendService.add(TEST_FRIEND_REQUEST);
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Get all friends")
    void getFriend() {
        Assertions.assertNotEquals(friendService.getAll(TEST_USER.getUsername()).size(), 0);
    }

    @Test
    @DisplayName("Remove user from friend list")
    void removeFriend() {
        friendService.delete(TEST_FRIEND_REQUEST);

        Assertions.assertEquals(0, getFriendsList().size());
    }

    @Test
    @DisplayName("Add new friend")
    void addFriend() {
        Assertions.assertEquals(1, getFriendsList().size());
    }
}
