package com.finalproject.rentacar.service;

import com.finalproject.rentacar.converter.UserConverter;
import com.finalproject.rentacar.dto.UserResponse;
import com.finalproject.rentacar.entity.User;
import com.finalproject.rentacar.repository.UserRepository;
import com.finalproject.rentacar.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverter userConverter;
    @Mock
    private UserService userService;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userConverter);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void saveUser() {
        //given
        User user = new User();
        when(userConverter.toUser(any())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userConverter.toResponse(user)).thenReturn(new UserResponse());

        //when
        userService.saveUser(any());

        verify(userConverter,times(1)).toUser(any());
        verify(userRepository,times(1)).save(user);
        verify(userConverter,times(1)).toResponse(user);


        //then
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void updateUserDetails() {
    }

    @Test
    void getUserByRID() {
    }

    @Test
    void deleteUser() {
    }
}