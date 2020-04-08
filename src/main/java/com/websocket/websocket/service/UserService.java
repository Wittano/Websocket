package com.websocket.websocket.service;

import com.websocket.websocket.exception.UserDuplicateException;
import com.websocket.websocket.interfaces.service.UsersService;
import com.websocket.websocket.models.User;
import com.websocket.websocket.interfaces.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService implements UsersService {

    private UserRepository<User, Long> repo;

    private PasswordEncoder passwordEncoder;
    private Logger logger;

    public UserService(PasswordEncoder passwordEncoder, UserRepository<User, Long> repo) {
        this.passwordEncoder = passwordEncoder;

        logger = Logger.getLogger(this.getClass().getSimpleName());
        this.repo = repo;
    }

    @Override
    public boolean isUserExist(User user) {
        return true;
    }

    @Override
    public void login(User user) {
        if(isUserExist(user)){
            Optional<User> supposedUserOptional = repo.findByName(user.getName());
            User supposedUser = null;
            if(supposedUserOptional.isPresent()){
                supposedUser = supposedUserOptional.get();
            }

            passwordEncoder.matches(Objects.requireNonNull(supposedUser).getPassword(), user.getPassword());
            {
                logger.info("Udało się!");
            }
        }
        else {
            throw new UsernameNotFoundException(String.format("Username %s isn't exist", user.getName()));
        }
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            repo.save(user);
        } catch (SQLIntegrityConstraintViolationException | UserDuplicateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@NotNull User target, User update) {
        repo.update(target, update);
    }

    @Override
    public void delete(String name) {
        repo.deleteByName(name);
    }
}
