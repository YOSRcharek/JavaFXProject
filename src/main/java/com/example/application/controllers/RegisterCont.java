package com.example.application.controllers;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterCont {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button registerButton;

    private UserRepository userRepository;

    public RegisterCont() {
        this.userRepository = new UserRepository();
    }

    // Method to handle registration when the submit button is clicked





    @FXML
    private void register() {
        String userEmail = email.getText();
        String userPassword = password.getText();
        String userConfirmPassword = confirmPassword.getText();

        // Validate email format
        if (!isValidEmail(userEmail)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        // Validate password length
        if (userPassword.length() < 8) {
            showAlert("Invalid Password", "Password must be at least 8 characters long.");
            return;
        }

        // Check if passwords match
        if (!userPassword.equals(userConfirmPassword)) {
            showAlert("Passwords Mismatch", "The passwords do not match.");
            return;
        }

        // Set roles as a single string (assuming it's stored as such in the User class)
        String roles = "[\"ROLE_USER\",\"ROLE_ADMIN\"]";

        // Create a new User object
        User newUser = new User(userEmail, userPassword, roles, true);

        // Attempt to register the user
        boolean userRegistered = userRepository.createUser(newUser);
        if (userRegistered) {
            // Registration successful, perform any necessary actions (e.g., show a success message)
            showAlert("Registration Successful", "User registered successfully!");
        } else {
            // Registration failed, handle the error (e.g., show an error message)
            showAlert("Registration Failed", "Failed to register user.");
        }
    }
    // Method to navigate to the sign-in screen
    @FXML
    private void navigateToSignIn() {
        try {
            // Load the sign-in FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign In");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Validate email format
    private boolean isValidEmail(String email) {
        // Simple email validation regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Show alert dialog for displaying error messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
