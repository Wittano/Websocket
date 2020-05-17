package com.websocket.websocket.repository;

import com.websocket.websocket.exception.UserDuplicateException;
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
        if(!isExistByName(name)){
            return Optional.empty();
        }

        return Optional.of((UserDB) entityManager.createQuery("from UserDB u where u.username = :username").
                setParameter("username", name).getSingleResult());
    }

    @Override
    public Set<UserDB> findAll() {
        List<UserDB> resultList = entityManager.createQuery("from UserDB u").getResultList();
        return new HashSet<UserDB>(resultList);
    }

    @Override
    @Transactional
    public void save(UserDB object) throws UserDuplicateException {
        if(isExistByName(object.getUsername())) {
            throw new UserDuplicateException("You can't save this user cause he's exist in database");
        }
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        Optional<UserDB> userOptional = findByName(name);
        if(userOptional.isPresent()){
            UserDB userDB = userOptional.get();

            entityManager.remove(userDB);
        }
        else {
            throw new NoSuchElementException(String.format("User %s wasn't found!", name));
        }
    }

    @Override
    public boolean isExistByName(String name) {
        try {
            UserDB userDB = (UserDB) entityManager.createQuery("from UserDB u where u.username = :name").
                    setParameter("name", name).getSingleResult();

            return userDB.getUsername().equals(name);
        } catch (NoResultException e){
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
