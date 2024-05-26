package com.example.demo.repositories;

import com.example.demo.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String FILE_PATH = "/Users/sabina/Desktop/demo/src/users.json";
    private ObjectMapper objectMapper;
    private List<User> users;

    public UserRepository() {
        objectMapper = new ObjectMapper();
        users = getUsersFromFile();
    }

    private List<User> getUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<User>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeUsersToFile(List<User> userList) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(User user) {
        user.setId(generateNextUserId());
        users.add(user);
        writeUsersToFile(users);
    }

    private int generateNextUserId() {
        if (users.isEmpty()) {
            return 1;
        } else {
            return users.get(users.size() - 1).getId() + 1;
        }
    }

    public User getUserByCnp(String cnp) {
        for (User user : users) {
            if (user.getCnp().equals(cnp)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }
}
