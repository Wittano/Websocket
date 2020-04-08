package com.websocket.websocket.interfaces.service;

import com.websocket.websocket.models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UsersService {
    boolean isUserExist(User user);
    void login(User user) throws UsernameNotFoundException;
    void save(User user);
    void update(User target, User update);
    void delete(String name);
}
