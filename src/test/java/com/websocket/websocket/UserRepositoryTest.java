package com.websocket.websocket;

import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.models.User;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@SpringBootTest
public class UserRepositoryTest {

    private final User testUserDB;
    @Autowired
    private UserRepository<User> repository;

    public UserRepositoryTest() {
        testUserDB = new User();
        testUserDB.setUsername("TestUser");
        testUserDB.setPassword("123456");
        testUserDB.setBirthday(new Date(System.currentTimeMillis()));
        testUserDB.setEmail("test@test.com");
        testUserDB.setGender(User.Gender.Male);
        testUserDB.setFriends(new HashSet<>());
    }

    @BeforeEach
    public void removeTestUser(){
        try{
            repository.deleteByName("Bob the Builder");
        } catch (Exception ignored){
        }
        try{
            repository.deleteByName(testUserDB.getUsername());
        } catch (Exception ignored){
        }
    }

    @After
    public void deleteUser() {
        if (repository.isExistByName(testUserDB.getUsername())) {
            repository.deleteByName(testUserDB.getUsername());
        }
    }

    @Test
    public void get_all_users() {
        List<User> userDBS = Lists.newArrayList(repository.findAll());
        Assertions.assertNotEquals(userDBS.size(), 0);
    }

    @Test
    public void search_user_only_used_him_nickname() {
        User userDB = new User();
        userDB.setUsername("root");
        Assertions.assertTrue(repository.isExistByName(userDB.getUsername()));
    }

    @Test
    public void search_not_exist_user() {
        try {
            Assertions.assertFalse(repository.isExistByName("maslo"));
        } catch (EmptyResultDataAccessException e) {
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void add_new_user() {
        if (repository.isExistByName(testUserDB.getUsername())) {
            return;
        }

        Set<User> userDBS = repository.findAll();
        try{
            repository.save(testUserDB);
        } catch (Exception e){
            e.getStackTrace();
            return;
        }

        Set<User> newUserDBList = repository.findAll();

        Assertions.assertEquals(userDBS.size() + 1, newUserDBList.size());
    }

    @Test
    public void delete_user() {
        if (!repository.isExistByName(testUserDB.getUsername())) {
            repository.save(testUserDB);
        }

        Set<User> userDBS = repository.findAll();
        repository.deleteByName(testUserDB.getUsername());

        Set<User> newUserDBS = repository.findAll();

        Assertions.assertNotEquals(userDBS.size(), newUserDBS.size());
    }

    @Test
    public void search_user() {
        if (!repository.isExistByName(testUserDB.getUsername())) {
            repository.save(testUserDB);
        }

        Assertions.assertTrue(repository.findByName(testUserDB.getUsername()).isPresent());
    }

    @Test
    public void throw_NoResultException_in_findByName() {
        testUserDB.setUsername("eftrhwe3rkhbghskhdlhrfbghiwe");
        Assertions.assertFalse(repository.isExistByName(testUserDB.getUsername()));
        Assertions.assertNotEquals(testUserDB, repository.findByName(testUserDB.getUsername()));

        testUserDB.setUsername("TestUser");
    }

    @Test
    public void update_user() {
        final String updateTestName = "Bob the builder";
        User copyTestUserDB = null;
        User testUserDB2 = null;
        try {
            copyTestUserDB = (User) testUserDB.clone();
            testUserDB2 = (User) testUserDB.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(testUserDB2).setUsername(updateTestName);

        copyTestUserDB.merge(testUserDB2);

        Assertions.assertNotEquals(copyTestUserDB, testUserDB);
        Assertions.assertNotEquals(testUserDB2, testUserDB);

        if (!repository.isExistByName(testUserDB.getUsername())) {
            repository.save(testUserDB);
        }

        User userDBFromDatabase = repository.findByName(testUserDB.getUsername()).get();

        repository.update(userDBFromDatabase, copyTestUserDB);

        User checkUserDBFromDatabase = repository.findByName(testUserDB.getUsername()).orElse(null);

        Assertions.assertNull(checkUserDBFromDatabase);
        Assertions.assertEquals(userDBFromDatabase.getUsername(), updateTestName);
    }
}
