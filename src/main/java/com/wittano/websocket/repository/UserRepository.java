package com.wittano.websocket.repository;

import com.wittano.websocket.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    void deleteUserByUsername(String username);

    boolean existsUserByUsername(String username);
}
