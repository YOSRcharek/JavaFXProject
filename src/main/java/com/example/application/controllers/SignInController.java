package com.example.application.controllers;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    private UserRepository userRepository;

    public SignInController() {
        this.userRepository = new UserRepository();
    }

    @FXML
    private void signIn() {
        String userEmail = emailField.getText();
        String userPassword = passwordField.getText();

        // Attempt to authenticate the user
        boolean isAuthenticated = userRepository.authenticateUser(userEmail, userPassword);

        if (isAuthenticated) {
            // Authentication successful, perform any necessary actions
            System.out.println("User authenticated successfully!");
        } else {
            // Authentication failed, handle the error (e.g., show an error message)
            System.out.println("Authentication failed. Please check your credentials.");
        }
    }
}
