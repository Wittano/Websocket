package com.websocket.websocket.models;

import com.websocket.websocket.interfaces.Model;
import org.hibernate.search.annotations.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
@Indexed
public class User implements Serializable, Model<User>, Cloneable {

    public enum Gender{
       Male, Female
    }

    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{4,30}")
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*]{4,300}")
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

    //#region Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
    //#endregion

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                name.equals(user.name) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                Objects.equals(birthday, user.birthday) &&
                gender == user.gender;
    }

    @Override
    public void merge(User mergeObject) {
        this.name = !this.name.equals(mergeObject.getName()) ? mergeObject.getName() : this.name;
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
