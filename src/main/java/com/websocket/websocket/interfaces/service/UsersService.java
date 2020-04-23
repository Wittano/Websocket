package com.websocket.websocket.interfaces.service;

import com.websocket.websocket.models.UserDB;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UsersService {
    boolean isUserExist(UserDB userDB);
    boolean userVerify(UserDB userDB) throws UsernameNotFoundException;
    void save(UserDB userDB);
    void update(UserDB target, UserDB update);
    void delete(String name);
}
