package com.websocket.websocket.repository;

import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class UserRepo implements UserRepository<User> {

    private final EntityManager entityManager;

    public UserRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByName(String name) {
        try {
            return Optional.of((User) entityManager.createQuery("from User u where u.username = :username").
                    setParameter("username", name).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Set<User> findAll() {
        List<User> resultList = entityManager.createQuery("from User u").getResultList();
        return new HashSet<>(resultList);
    }

    @Override
    @Transactional
    public void save(User object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        User userDB = findByName(name).orElseThrow(() -> {
            throw new NoSuchElementException(String.format("User %s wasn't found!", name));
        });

        entityManager.remove(userDB);
    }

    @Override
    public boolean isExistByName(String name) {
        try {
            User userDB = findByName(name).orElseThrow(() -> {
                throw new NoResultException();
            });

            return userDB.getUsername().equals(name);
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public User update(User target, User update) {
        target.merge(update);
        return target;
    }
}
