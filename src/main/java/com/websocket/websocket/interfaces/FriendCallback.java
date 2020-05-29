package com.websocket.websocket.interfaces;

import java.util.Set;

//This interface is only use in FriendServiceImpl
@FunctionalInterface
public interface FriendCallback {
    void friendAction(Set<String> friends);
}
