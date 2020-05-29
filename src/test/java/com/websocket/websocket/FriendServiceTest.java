package com.websocket.websocket;

import com.websocket.websocket.interfaces.service.FriendService;
import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FriendServiceTest {

    private final String testFriend = "test";
    @Autowired
    private FriendService friendService;
    @Autowired
    private UsersService usersService;
    private User user;

    @BeforeEach
    public void getUser() {
        if (user == null) {
            user = usersService.getUserByName("root");
        }
    }

    @Test
    void getFriend() {
        Assertions.assertNotEquals(user.getFriends().size(), 0);
    }

    @Test
    void removeFriend() {
        try {
            friendService.addFriend(user.getUsername(), testFriend);
        } catch (Exception ignored) {
        }

        try {
            friendService.deleteFriend(user.getUsername(), testFriend);

            User root = usersService.getUserByName(user.getUsername());
            Assertions.assertNotEquals(user.getFriends().size(), root.getFriends().size());
            Assertions.assertFalse(root.getFriends().contains(testFriend));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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
