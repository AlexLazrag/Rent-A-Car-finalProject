package com.finalproject.rentacar.service.impl;

import com.finalproject.rentacar.converter.UserConverter;
import com.finalproject.rentacar.dto.UpdateUserRequest;
import com.finalproject.rentacar.dto.UserRegisterRequest;
import com.finalproject.rentacar.dto.UserResponse;
import com.finalproject.rentacar.entity.User;
import com.finalproject.rentacar.exceptions.DuplicateEntityException;
import com.finalproject.rentacar.exceptions.NotFoundException;
import com.finalproject.rentacar.repository.UserRepository;
import com.finalproject.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserResponse saveUser(UserRegisterRequest request) {
        User user = userConverter.toUser(request);
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new DuplicateEntityException("Email '" + existingUser.getEmail() + "' is already registered");
        }
        User savedUser = userRepository.save(user);
        return userConverter.toResponse(savedUser);
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found"));
        return userConverter.toResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userConverter.toResponse(user);
    }

    @Override
    public UserResponse updateUserDetails(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow();

        if (request.getPassword() != null && !request.getPassword().isBlank() && user.getPassword() != request.getPassword()){
            user.setPassword(request.getPassword());
        }
        return userConverter.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserByRID(Long id) {
        User user = userRepository.findByReservationId(id);
        return userConverter.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
