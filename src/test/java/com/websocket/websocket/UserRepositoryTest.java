package com.websocket.websocket;

import com.websocket.websocket.interfaces.UserRepository;
import com.websocket.websocket.models.User;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository<User, Long> repository;
    private User testUser;

    public UserRepositoryTest() {
        testUser = new User();
        testUser.setName("TestUser");
        testUser.setPassword("123456");
        testUser.setBirthday(new Date(System.currentTimeMillis()));
        testUser.setEmail("test@test.com");
        testUser.setGender(User.Gender.Male);
    }

    @Test
    public void get_all_users(){
        List<User> users = Lists.newArrayList(repository.findAll());
        Assertions.assertNotEquals(users.size(), 0);
    }

    @Test
    public void search_user_only_used_him_nickname(){
        User user = new User();
        user.setName("root");
        Assertions.assertTrue(repository.isExistByName(user.getName()));
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
        if(repository.isExistByName(testUser.getName())){
            return;
        }

        List<User> users = repository.findAll();
        try {
            repository.save(testUser);
        } catch (SQLIntegrityConstraintViolationException e) {
            return;
        }

        List<User> newUserList = repository.findAll();

        Assertions.assertEquals(users.size() + 1, newUserList.size());
    }

    @Test
    public void delete_user(){
        if(!repository.isExistByName(testUser.getName())){
            add_new_user();
        }

        List<User> users = repository.findAll();
        repository.deleteByName(testUser.getName());

        List<User> newUsers = repository.findAll();

        Assertions.assertNotEquals(users.size(), newUsers.size());
    }

    @Test
    public void search_user(){
        if(!repository.isExistByName(testUser.getName())){
            add_new_user();
        }

        Assertions.assertTrue(repository.findByName(testUser.getName()).isPresent());
    }
}
