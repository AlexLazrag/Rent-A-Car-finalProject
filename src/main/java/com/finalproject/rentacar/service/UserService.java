package com.finalproject.rentacar.service;

import com.finalproject.rentacar.dto.UserRegisterRequest;
import com.finalproject.rentacar.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponse saveUser(UserRegisterRequest request);

    UserResponse getUser(Long id);

    void deleteUser(Long id);
}
