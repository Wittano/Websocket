package com.wittano.websocket.models.request;

import com.wittano.websocket.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    @Pattern(regexp = "[A-Za-z]{4,}")
    private String username;
    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*()]{4,}")
    private String password;
    @Email
    private String email;
    @NotNull
    private Gender gender;
    @NotNull
    private Date birthday;
}
