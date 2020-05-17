package com.websocket.websocket.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @NotNull
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }

    public User(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
