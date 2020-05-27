package com.websocket.websocket.repository;

import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.models.UserDB;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class UserRepo implements UserRepository<UserDB> {

    private final EntityManager entityManager;

    public UserRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UserDB> findByName(String name) {
        try{
            return Optional.of((UserDB) entityManager.createQuery("from UserDB u where u.username = :username").
                    setParameter("username", name).getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public Set<UserDB> findAll() {
        List<UserDB> resultList = entityManager.createQuery("from UserDB u").getResultList();
        return new HashSet<>(resultList);
    }

    @Override
    @Transactional
    public void save(UserDB object) {
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        UserDB userDB = findByName(name).orElseThrow(() -> {
            throw new NoSuchElementException(String.format("User %s wasn't found!", name));
        });

        entityManager.remove(userDB);
    }

    @Override
    public boolean isExistByName(String name) {
        try {
            UserDB userDB = findByName(name).orElseThrow(() -> {
                throw new NoResultException();
            });

            return userDB.getUsername().equals(name);
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public UserDB update(UserDB target, UserDB update) {
        target.merge(update);
        return target;
    }
}
