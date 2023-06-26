package com.finalproject.rentacar.controller;

import com.finalproject.rentacar.dto.UpdateUserRequest;
import com.finalproject.rentacar.dto.UserRegisterRequest;
import com.finalproject.rentacar.dto.UserResponse;
import com.finalproject.rentacar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rentacar/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Register user")
    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse userResponse = userService.saveUser(request);
        String response = String.format("User:%n %s%n %s%n was created with id %s",
                userResponse.getFirstName(), userResponse.getLastName(), userResponse.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/findUserId/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(userService.getUser(id));
    }

    @GetMapping(path = "/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(userService.getUserByEmail(email));
    }
    @GetMapping(path = "/findByRID")
    public ResponseEntity<UserResponse> getUserByRID(@RequestParam("id") Long id){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(userService.getUserByRID(id));
    }

    @DeleteMapping(path = "/deleteUser/{id}")
    public HttpStatus deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return HttpStatus.ACCEPTED;
    }
@PutMapping(path = "/updatePassword/{id}")
    public ResponseEntity<UserResponse> updatePassword(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        UserResponse userResponse = userService.updateUserDetails(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
