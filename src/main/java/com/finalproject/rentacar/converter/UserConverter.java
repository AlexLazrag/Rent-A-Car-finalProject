package com.finalproject.rentacar.converter;

import com.finalproject.rentacar.dto.UserRegisterRequest;
import com.finalproject.rentacar.dto.UserResponse;
import com.finalproject.rentacar.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    public User toUser(UserRegisterRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public UserResponse toResponse(User savedUser) {
        return new UserResponse(
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName());

    }
}
