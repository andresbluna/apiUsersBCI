package com.restapi.users.service;

import com.restapi.users.model.User;
import com.restapi.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    public User createuser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
    }
}
