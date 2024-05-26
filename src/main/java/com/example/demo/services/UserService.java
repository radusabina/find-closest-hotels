package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void addUser(User user) {
        userRepository.saveUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserByCnp(String cnp) {
        return userRepository.getUserByCnp(cnp);
    }


}
