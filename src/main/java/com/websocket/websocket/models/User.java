package com.websocket.websocket.models;

import com.websocket.websocket.interfaces.Model;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable, Model<User>, Cloneable {

    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @NotNull
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    @NotNull
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    @Column(name = "birthday", nullable = false)
    private Date birthday;
    @NotNull
    @Column(name = "gender", nullable = false)
    private Gender gender = Gender.Male;
    @Column(name = "enabled")
    private boolean enabled = true;
    @Column(name = "role")
    private String role = "USER";
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Friends", joinColumns = @JoinColumn(name = "username"))
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Column(name = "friends")
    private Set<String> friends;

    public User() {
    }

    public User(@NotNull long id,
                @NotNull String username,
                @NotNull String password,
                @NotNull @Email String email,
                @NotNull Gender gender,
                Set<String> friends) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = new Date();
        this.gender = gender;
        this.role = "USER";
        this.friends = friends != null ? friends : new HashSet<>();
    }


    //#region Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getFriends() {
        return friends;
    }

    public void setFriends(Set<String> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", enabled=" + enabled +
                ", role='" + role + '\'' +
                ", friends=" + friends +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User userDB = (User) o;
        return this.id == userDB.id &&
                this.username.equals(userDB.username) &&
                this.password.equals(userDB.password) &&
                this.email.equals(userDB.email) &&
                this.birthday.equals(userDB.birthday) &&
                this.gender.equals(userDB.gender) &&
                this.friends.containsAll(userDB.friends) &&
                this.friends.size() == userDB.friends.size();
    }

    @Override
    public void merge(User mergeObject) {
        this.username = !this.username.equals(mergeObject.getUsername()) ? mergeObject.getUsername() : this.username;
        this.password = !this.password.equals(mergeObject.getPassword()) ? mergeObject.getPassword() : this.password;
        this.email = !this.email.equals(mergeObject.getEmail()) ? mergeObject.getEmail() : this.email;
        this.birthday = !this.birthday.equals(mergeObject.getBirthday()) ? mergeObject.getBirthday() : this.birthday;
        this.gender = !this.gender.equals(mergeObject.getGender()) ? mergeObject.getGender() : this.gender;
        this.friends = !this.friends.equals(mergeObject.getFriends()) ? mergeObject.getFriends() : this.friends;
    }

    //#endregion
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public enum Gender {
        Male, Female
    }
}
