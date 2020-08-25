package com.websocket.websocket;

import com.websocket.websocket.interfaces.service.FriendService;
import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
public class FriendServiceTest {

    private final String testFriend = "test";
    private final User user = new User(1, "TestUser", "test@test.com", "test@test.com",
            User.Gender.Male, new HashSet<>());
    @Autowired
    private FriendService friendService;
    @Autowired
    private UsersService usersService;

    @BeforeEach
    void addUser() {
        if (!usersService.isExist(user.getUsername())) {
            usersService.save(user);
        }
    }

    @Test
    void getFriend() throws CloneNotSupportedException {
        User user2 = (User) user.clone();
        user2.getFriends().add("Bob the builder");

        usersService.update(user, user2);

        Assertions.assertNotEquals(user.getFriends().size(), 0);
    }

    @Test
    void removeFriend() throws CloneNotSupportedException {
        friendService.addFriend(user.getUsername(), testFriend);
        friendService.deleteFriend(user.getUsername(), testFriend);

        User root = usersService.getUserByName(user.getUsername());
        Assertions.assertEquals(user.getFriends().size(), root.getFriends().size());
        Assertions.assertFalse(root.getFriends().contains(testFriend));
    }

    @Test
    void addFriend() {
        try {
            friendService.addFriend(user.getUsername(), testFriend);

            User root = usersService.getUserByName(user.getUsername());
            Assertions.assertNotEquals(user.getFriends().size(), root.getFriends().size());
            Assertions.assertTrue(root.getFriends().contains(testFriend));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
