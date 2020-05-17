package com.websocket.websocket.interfaces.service;

import com.websocket.websocket.models.UserDB;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UsersService {
    UserDB getUserByName(String name);
    boolean isUserExist(UserDB userDB);
    void save(UserDB userDB);
    void update(UserDB target, UserDB update);
    void delete(String name);
}
