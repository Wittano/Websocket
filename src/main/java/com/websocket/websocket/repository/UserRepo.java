package com.websocket.websocket.repository;

import com.websocket.websocket.exception.UserDuplicateException;
import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.models.User;
import org.apache.lucene.search.Query;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.jboss.jdeparser.FormatPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class UserRepo implements UserRepository<User, Long> {

    private EntityManager entityManager;

    public UserRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.of((User) entityManager.createQuery("from User u where u.name = :name").
                setParameter("name", name).getSingleResult());
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User u").getResultList();
    }

    @Override
    @Transactional
    public void save(User object) throws UserDuplicateException {
        if(findByName(object.getName()).isPresent()){
            throw new UserDuplicateException("You can't save this user cause he's exist in database");
        }
        entityManager.persist(object);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        Optional<User> userOptional = findByName(name);
        if(userOptional.isPresent()){
            User user = userOptional.get();

            entityManager.remove(user);
        }
        else {
            throw new NoSuchElementException(String.format("User %s wasn't found!", name));
        }
    }

    @Override
    public boolean isExistByName(String name) throws EmptyResultDataAccessException {
        try {
            User user = (User) entityManager.createQuery("from User u where u.name = :n").
                    setParameter("n", name).getSingleResult();

            return user.getName().equals(name);
        } catch (NoResultException e){
            return false;
        }
    }
}
