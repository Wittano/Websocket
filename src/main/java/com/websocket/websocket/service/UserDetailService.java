package com.websocket.websocket.service;

import com.websocket.websocket.models.UserDB;
import com.websocket.websocket.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepo repo;

    public UserDetailService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDB user = repo.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return new User(user.getUsername(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }
}
