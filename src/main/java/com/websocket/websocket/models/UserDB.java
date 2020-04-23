package com.websocket.websocket.models;

import com.websocket.websocket.interfaces.Model;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserDB implements Serializable, Model<UserDB>, Cloneable {

    public enum Gender{
       Male, Female
    }

    public UserDB() {
    }

    public UserDB(@NotNull String name, @NotNull String password) {
        this.username = name;
        this.password = password;
    }

    @NotNull
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

    //#endregion

    @Override
    public String toString() {
        return "UserRegister{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDB userDB = (UserDB) o;
        return id == userDB.id &&
                username.equals(userDB.username) &&
                password.equals(userDB.password) &&
                email.equals(userDB.email) &&
                Objects.equals(birthday, userDB.birthday) &&
                gender == userDB.gender;
    }

    @Override
    public void merge(UserDB mergeObject) {
        this.username = !this.username.equals(mergeObject.getUsername()) ? mergeObject.getUsername() : this.username;
        this.password = !this.password.equals(mergeObject.getPassword()) ? mergeObject.getPassword() : this.password;
        this.email = !this.email.equals(mergeObject.getEmail()) ? mergeObject.getEmail() : this.email;
        this.birthday = !this.birthday.equals(mergeObject.getBirthday()) ? mergeObject.getBirthday() : this.birthday;
        this.gender = !this.gender.equals(mergeObject.getGender()) ? mergeObject.getGender() : this.gender;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
