package com.portilo.portilo.mapper;

import com.portilo.portilo.Entity.Role;
import com.portilo.portilo.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

    public static User toEntity(RegisterRequest registerRequest) {
        return User.builder()
                .firstName (registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
    }
    public RegisterRequest fromEntity(User user) {
        return RegisterRequest.builder()
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
