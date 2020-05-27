package com.websocket.websocket.interfaces;

import com.websocket.websocket.models.UserDB;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityExistsException;
import java.util.Optional;
import java.util.Set;

public interface UserRepository<T extends UserDB> {
    Optional<T> findByName(String name);
    Set<T> findAll();
    void save(T object) throws EntityExistsException;
    void deleteByName(String name);
    boolean isExistByName(String name) throws EmptyResultDataAccessException;
    UserDB update(UserDB target, UserDB update);
}
