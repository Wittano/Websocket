package com.websocket.websocket.repository;

import com.websocket.websocket.models.UserRegister;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserRegister, Long> {
}
