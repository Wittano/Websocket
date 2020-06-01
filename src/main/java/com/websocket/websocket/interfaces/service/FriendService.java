package com.websocket.websocket.interfaces.service;

import java.util.Set;

public interface FriendService {
    void addFriend(String who, String friend) throws CloneNotSupportedException;

    Set<String> getFriends(String user);

    void deleteFriend(String who, String friend) throws CloneNotSupportedException;
}
