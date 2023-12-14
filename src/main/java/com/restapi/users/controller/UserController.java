package com.restapi.users.controller;

import com.restapi.users.repository.UserRepository;
import com.restapi.users.service.UserService;
import com.restapi.users.model.User;
import com.restapi.users.utils.AllExceptionHandler;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private AllExceptionHandler allExceptionHandler;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        if (userService.isEmailRegistered(user.getEmail())) {
            return new ResponseEntity<>
                    (Collections.singletonMap("email", "Email is already registered"),
                            HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.createuser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @GetMapping("/users/{uuid}")
    public User getUserByUuid(@PathVariable UUID uuid) {
        return userRepository.findByUuid(uuid);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}


