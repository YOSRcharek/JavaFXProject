package com.example.application.controllers;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import com.example.application.services.SendMail;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterCont {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button registerButton;

    @FXML
    private TextField verificationCodeField;

    @FXML
    private Button verifyButton; // Add this field
    @FXML
    private WebView chatbotWebView;


    private UserRepository userRepository;
    private String verificationCode; // Temporary storage for verification code





    public void initialize() {
        // Load the chatbot interface URL
        chatbotWebView.getEngine().load("https://console.dialogflow.com/api-client/demo/embedded/3b6bf03e-8a2c-40a5-8b63-03aba37653d5");
    }
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
        String roles = "[\"ROLE_USER\"]";

        // Create a new User object
        User newUser = new User(userEmail, userPassword, roles, false);

        // Attempt to register the user
        boolean userRegistered = userRepository.createUser(newUser);
        if (userRegistered) {
            verificationCode = generateRandomCode();

            // Send the verification code to the user's email
            String subject = "Account Verification Code";
            String body = "Your account verification code is: " + verificationCode;
            SendMail.sendMail(userEmail, subject, body);

            // Show verification code input field
            verificationCodeField.setVisible(true);
            verifyButton.setVisible(true);
            password.setVisible(false);
            confirmPassword.setVisible(false);
            email.setVisible(false);
            registerButton.setVisible(false);





            // Registration successful, perform any necessary actions (e.g., show a success message)
            showAlertConfirmaion("Registration Successful", "User registered successfully! a code has been sent to your email please verify your email");
        } else {
            // Registration failed, handle the error (e.g., show an error message)
            showAlert("Registration Failed", "Failed to register user.");
        }
    }

    @FXML
    private void verify() {
        String enteredVerificationCode = verificationCodeField.getText();

        // Compare entered code with generated code
        if (enteredVerificationCode.equals(verificationCode)) {
            // Find the user by email (assuming you have a method in UserRepository for this)
            User user = userRepository.getUserByEmail(email.getText());

            if (user != null) {
                // Update the verified status of the user
                user.setVerified(true);
                // Save the updated user information to the database
                boolean updated = userRepository.updateUser(user);
                if (updated) {
                    showAlertConfirmaion("Verification Successful", "Your account has been verified successfully!");
                    navigateToSignIn(); // Navigate to the sign-in screen
                } else {
                    showAlert("Error", "Failed to update user information.");
                }
            } else {
                showAlert("Error", "User not found.");
            }
        } else {
            showAlert("Verification Failed", "Invalid verification code. Please try again.");
        }
    }

    private String generateRandomCode() {
        int length = 6; // Set the length of the verification code
        String characters = "0123456789"; // Define the characters to be used in the code
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
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
    private void showAlertConfirmaion(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
