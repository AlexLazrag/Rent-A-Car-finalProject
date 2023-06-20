package com.finalproject.rentacar.repository;

import com.finalproject.rentacar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
}
