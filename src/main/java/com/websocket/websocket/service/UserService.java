package com.websocket.websocket.service;

import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UsersService {

    private final UserRepository<User> repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository<User> repo) {
        this.passwordEncoder = passwordEncoder;
        this.repo = repo;
    }

    @Override
    public User getUserByName(String name) {
        return repo.findByName(name).orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format("%s not found", name));
        });
    }

    @Override
    public void save(User userDB) {
        userDB.setPassword(passwordEncoder.encode(userDB.getPassword()));
        repo.save(userDB);
    }

    @Override
    public void update(User target, User update) {
        repo.update(target, update);
    }

    @Override
    public void delete(String name) {
        repo.deleteByName(name);
    }
}
