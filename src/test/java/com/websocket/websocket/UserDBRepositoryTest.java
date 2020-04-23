package com.websocket.websocket;

import com.websocket.websocket.exception.UserDuplicateException;
import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.models.UserDB;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class UserDBRepositoryTest {

    @Autowired
    private UserRepository<UserDB, Long> repository;
    private UserDB testUserDB;

    public UserDBRepositoryTest() {
        testUserDB = new UserDB();
        testUserDB.setUsername("TestUser");
        testUserDB.setPassword("123456");
        testUserDB.setBirthday(new Date(System.currentTimeMillis()));
        testUserDB.setEmail("test@test.com");
        testUserDB.setGender(UserDB.Gender.Male);
    }

    @After
    public void deleteUser(){
        if(repository.isExistByName(testUserDB.getUsername())){
            repository.deleteByName(testUserDB.getUsername());
        }
    }

    @Test
    public void get_all_users(){
        List<UserDB> userDBS = Lists.newArrayList(repository.findAll());
        Assertions.assertNotEquals(userDBS.size(), 0);
    }

    @Test
    public void search_user_only_used_him_nickname(){
        UserDB userDB = new UserDB();
        userDB.setUsername("root");
        Assertions.assertTrue(repository.isExistByName(userDB.getUsername()));
    }

    @Test
    public void search_not_exist_user() {
        try {
            Assertions.assertFalse(repository.isExistByName("maslo"));
        } catch (EmptyResultDataAccessException e){
            Assertions.assertFalse(false);
        }
    }

    @Test
    public void add_new_user(){
        if(repository.isExistByName(testUserDB.getUsername())){
            return;
        }

        List<UserDB> userDBS = repository.findAll();
        try {
            repository.save(testUserDB);
        } catch (SQLIntegrityConstraintViolationException | UserDuplicateException e) {
            return;
        }

        List<UserDB> newUserDBList = repository.findAll();

        Assertions.assertEquals(userDBS.size() + 1, newUserDBList.size());
    }

    @Test
    public void delete_user(){
        if(!repository.isExistByName(testUserDB.getUsername())){
            add_new_user();
        }

        List<UserDB> userDBS = repository.findAll();
        repository.deleteByName(testUserDB.getUsername());

        List<UserDB> newUserDBS = repository.findAll();

        Assertions.assertNotEquals(userDBS.size(), newUserDBS.size());
    }

    @Test
    public void search_user(){
        if(!repository.isExistByName(testUserDB.getUsername())){
            add_new_user();
        }

        Assertions.assertTrue(repository.findByName(testUserDB.getUsername()).isPresent());
    }

    @Test
    public void throw_NoResultException_in_findByName(){
        testUserDB.setUsername("eftrhwe3rkhbghskhdlhrfbghiwe");
        Assertions.assertFalse(repository.isExistByName(testUserDB.getUsername()));
        Assertions.assertNotEquals(testUserDB, repository.findByName(testUserDB.getUsername()));

        testUserDB.setUsername("TestUser");
    }

    @Test
    public void update_user(){
        UserDB copyTestUserDB = null;
        UserDB testUserDB2 = null;
        try {
            copyTestUserDB = (UserDB) testUserDB.clone();
            testUserDB2 = (UserDB) testUserDB.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(testUserDB2).setUsername("Bob the builder");

        copyTestUserDB.merge(testUserDB2);

        Assertions.assertNotEquals(copyTestUserDB, testUserDB);
        Assertions.assertNotEquals(testUserDB2, testUserDB);

        if(!repository.isExistByName(testUserDB.getUsername())){
            add_new_user();
        }

        UserDB userDBFromDatabase = repository.findByName(testUserDB.getUsername()).get();

        repository.update(userDBFromDatabase, copyTestUserDB);

        UserDB checkUserDBFromDatabase = repository.findByName(testUserDB.getUsername()).get();

        Assertions.assertNotEquals(userDBFromDatabase, checkUserDBFromDatabase);
    }
}
