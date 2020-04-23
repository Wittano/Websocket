package com.websocket.websocket.interfaces;

import com.websocket.websocket.exception.UserDuplicateException;
import com.websocket.websocket.models.UserDB;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface UserRepository<T extends UserDB, ID extends Number> {
    Optional<T> findByName(String name);
    List<T> findAll();
    void save(T object) throws SQLIntegrityConstraintViolationException, UserDuplicateException;
    void deleteByName(String name);
    boolean isExistByName(String name) throws EmptyResultDataAccessException;
    UserDB update(UserDB target, UserDB update);
}
