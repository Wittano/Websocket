package com.websocket.websocket.interfaces;

import com.websocket.websocket.exception.UserDuplicateException;
import com.websocket.websocket.models.UserDB;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.Set;

public interface UserRepository<T extends UserDB> {
    Optional<T> findByName(String name);
    Set<T> findAll();
    void save(T object) throws SQLIntegrityConstraintViolationException, UserDuplicateException;
    void deleteByName(String name);
    boolean isExistByName(String name) throws EmptyResultDataAccessException;
    UserDB update(UserDB target, UserDB update);
}
