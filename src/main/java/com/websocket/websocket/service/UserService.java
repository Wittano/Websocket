package com.websocket.websocket.service;

import com.websocket.websocket.exception.UserDuplicateException;
import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.UserDB;
import com.websocket.websocket.interfaces.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
public class UserService implements UsersService {

    private UserRepository<UserDB, Long> repo;

    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository<UserDB, Long> repo) {
        this.passwordEncoder = passwordEncoder;

        this.repo = repo;
    }

    @Override
    public boolean isUserExist(UserDB userDB) {
        return true;
    }

    @Override
    public boolean userVerify(UserDB userDB) throws UsernameNotFoundException {
        if(!isUserExist(userDB)){
            throw new UsernameNotFoundException(String.format("User %s wasn't found", userDB.getUsername()));
        }

        Optional<UserDB> dbUser = repo.findByName(userDB.getUsername());

        return dbUser.filter(value -> passwordEncoder.matches(userDB.getPassword(), value.getPassword())).isPresent();
    }

    @Override
    public void save(UserDB userDB) {
        userDB.setPassword(passwordEncoder.encode(userDB.getPassword()));
        try {
            repo.save(userDB);
        } catch (SQLIntegrityConstraintViolationException | UserDuplicateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@NotNull UserDB target, UserDB update) {
        repo.update(target, update);
    }

    @Override
    public void delete(String name) {
        repo.deleteByName(name);
    }
}