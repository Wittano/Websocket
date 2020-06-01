package com.websocket.websocket.interfaces.service;

import com.websocket.websocket.models.User;

public interface UsersService {
    boolean isExist(String name);

    User getUserByName(String name);

    void save(User userDB);

    void update(User target, User update);

    void delete(String name);
}
