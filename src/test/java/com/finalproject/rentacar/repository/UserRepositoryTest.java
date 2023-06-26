package com.finalproject.rentacar.repository;

import com.finalproject.rentacar.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByReservationId() {
    }

    @Test
    void checkMessage() {
        String msg = "Hello";
        String response = "Hello";

        assertEquals(msg, response);
    }

    @Test
    void findByEmail() {
        //given
        User user = buildUser("test@gmail.com");
        //when
        userRepository.save(user);
        User foundUser = userRepository.findByEmail("test@gmail.com");

        //then
        assertThat(foundUser).isNotNull();

    }
    @Test
    void findByEmailShouldFail() {
        //given
        User user = buildUser("test@gmail.com");
        //when
        userRepository.save(user);
        User foundUser = userRepository.findByEmail("test1@gmail.com");

        //then
        assertThat(foundUser).isNotNull();

    }

    private User buildUser(String email){
        User user = User.builder()
                .id(1L)
                .firstName("Alex")
                .lastName("Dimitrov")
                .password("123456")
                .email(email)
                .build();
        return user;
    }
}