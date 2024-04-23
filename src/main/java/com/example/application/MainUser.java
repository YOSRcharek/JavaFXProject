package com.example.application;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import java.util.List;

import java.util.Arrays;

public class MainUser {

    public static void main(String[] args) {
        // Create a UserRepository instance
        UserRepository userRepository = new UserRepository();

//        // Test creating a new user
//        User newUser = new User("testssss@example.com", "password", "[\"ROLE_USER\",\"ROLE_ADMIN\"]", true);
//        boolean userCreated = userRepository.createUser(newUser);
//        System.out.println("User created: " + userCreated);

        // Test getting all users
        List<User> allUsers = userRepository.getAllUsers();
        System.out.println("All users:");
        for (User user : allUsers) {
            System.out.println(user);
        }
////
//        // Test updating an existing user (assuming there's a user with id = 1)
//        User existingUser = userRepository.getUserById(1);
//        if (existingUser != null) {
//            existingUser.setEmail("updated@example.com");
//            boolean userUpdated = userRepository.updateUser(existingUser);
//            System.out.println("User updated: " + userUpdated);
//        } else {
//            System.out.println("User with id 1 not found.");
//        }
//
//        // Test deleting an existing user (assuming there's a user with id = 2)
//        boolean userDeleted = userRepository.deleteUser(2);
//        System.out.println("User deleted: " + userDeleted);
    }
}
