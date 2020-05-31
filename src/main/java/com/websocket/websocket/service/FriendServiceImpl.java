package com.websocket.websocket.service;

import com.websocket.websocket.interfaces.FriendCallback;
import com.websocket.websocket.interfaces.service.FriendService;
import com.websocket.websocket.models.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FriendServiceImpl implements FriendService {

    private final UserService service;

    public FriendServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public void addFriend(String who, String friend) {
        modifyUser(who, friends -> friends.add(friend));
    }

    @Override
    public Set<String> getFriends(String user) {
        return service.getUserByName(user).getFriends();
    }

    @Override
    public void deleteFriend(String who, String friend) {
        modifyUser(who, friendList -> friendList.remove(friend));
    }

    private void modifyUser(String who, FriendCallback action) {
        User user = service.getUserByName(who);

        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }

        try {
            User copyUser = (User) user.clone();
            Set<String> friends = new HashSet<>(copyUser.getFriends());

            action.friendAction(friends);
            copyUser.setFriends(friends);

            service.update(user, copyUser);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
