package com.wittano.websocket.models.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.wittano.websocket.models.views.DefaultView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(DefaultView.External.class)
    private long id;
    @Column(unique = true, nullable = false)
    @JsonView(DefaultView.External.class)
    private String username;
    @Column(nullable = false)
    @JsonView(DefaultView.Internal.class)
    private String password;
    @Column(unique = true, nullable = false)
    @JsonView(DefaultView.External.class)
    private String email;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(nullable = false)
    @JsonView(DefaultView.External.class)
    private Date birthday;
    @Column(nullable = false)
    @JsonView(DefaultView.External.class)
    private Gender gender;
    @JsonView(DefaultView.Internal.class)
    private boolean enabled;
    @JsonView(DefaultView.Internal.class)
    private String role;
    @OneToMany(fetch = FetchType.EAGER)
    @JsonView(DefaultView.External.class)
    private Set<User> friends;

    public enum Gender {
        Male, Female
    }
}
