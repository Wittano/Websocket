package com.websocket.websocket.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
public class UserRegister implements Serializable {

    public enum Gender{
       Male, Female
    }

    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @NotNull
    @Size(min = 4, max = 30)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotNull
    @Size(min = 4, max = 300)
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
    private Gender gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "UserRegister{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                '}';
    }
}
