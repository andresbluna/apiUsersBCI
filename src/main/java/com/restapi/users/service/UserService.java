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

        public User createuser(User user){
            return userRepository.save(user);

        }
        public User getUserById(Long id){
            Optional<User> optionalUser = userRepository.findById(id);
            return optionalUser.get();
        }
        public List<User> getAllUsers(){
            return userRepository.findAll();
        }

        public void deleteUser(Long id){
            userRepository.deleteById(id);
        }


    public void deleteUserById(Long id) {
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email) != null;
    }

}