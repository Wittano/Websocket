package com.websocket.websocket.service;

import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.UserDB;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserService implements UsersService {

    private final UserRepository<UserDB> repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository<UserDB> repo) {
        this.passwordEncoder = passwordEncoder;
        this.repo = repo;
    }

    @Override
    public UserDB getUserByName(String name) {
        return repo.findByName(name).orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format("%s not found", name));
        });
    }

    @Override
    public void save(UserDB userDB) {
        userDB.setPassword(passwordEncoder.encode(userDB.getPassword()));
        repo.save(userDB);
    }

    @Override
    public void update(UserDB target, UserDB update) {
        repo.update(target, update);
    }

    @Override
    public void delete(String name) {
        repo.deleteByName(name);
    }
}
