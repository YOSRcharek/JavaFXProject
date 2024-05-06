package com.example.application.controllers;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import com.example.application.services.SendMail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class ForgetPasswordController {

    @FXML
    private TextField emailField;


    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Button verifyButton;
    @FXML
    private Button resetButton;

    @FXML
    private TextField verificationCodeField;
    private UserRepository userRepository;
    private static  String verificationCode;
private  static String userEmaill;
    public ForgetPasswordController() {
        this.userRepository = new UserRepository();
    }

    @FXML
    private void sendVerificationCode() {
         userEmaill = emailField.getText();

        // Check if the email exists in the database
        User user = userRepository.getUserByEmail(userEmaill);

        if (user != null) {
            // Generate a random verification code
            verificationCode = generateRandomCode();

            // Send the verification code to the user's email
            String subject = "Password Reset Verification Code";
            String body = "Your password reset verification code is: " + verificationCode;
            SendMail.sendMail(userEmaill, subject, body);

            showAlert("Verification Code Sent", "A verification code has been sent to your email.");

            System.out.println(verificationCode+" verification code sent");



            // Navigate to the resetpassword.fxml page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/resetpassword.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("User Not Found", "No user found with the provided email address.");
        }
    }



    @FXML
    private void verifyCode() {
        String enteredVerificationCode = verificationCodeField.getText();
        System.out.println(verificationCode+" verification code");

        // Compare entered code with generated code
        if (enteredVerificationCode.equals(verificationCode)) {
            // Proceed with resetting the password
            // You can implement the password reset logic here

            // After successful verification, make the verification code field and verify button invisible
            verificationCodeField.setVisible(false);
            verifyButton.setVisible(false);

            // Make the reset password button and new password field visible
            newPasswordField.setVisible(true);
            resetButton.setVisible(true);
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void resetPassword() {
        String newPassword = newPasswordField.getText();

        // Check if the email exists in the database
        boolean passwordUpdated = userRepository.updatePassword(userEmaill, newPassword);

        if (passwordUpdated) {
            showAlert("Password Reset Successful", "Your password has been reset successfully!");
        } else {
            showAlert("Error", "Failed to reset password. Please try again.");
        }
    }

}
