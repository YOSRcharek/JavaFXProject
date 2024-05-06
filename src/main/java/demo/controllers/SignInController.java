package demo.controllers;

import demo.model.User;
import demo.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SignInController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    private UserRepository userRepository;
    private static User authenticatedUser; // Store the authenticated user

    public SignInController() {
        this.userRepository = new UserRepository();
    }

    @FXML
    private void signIn() {
        String userEmail = emailField.getText();
        String userPassword = passwordField.getText();

        // Attempt to authenticate the user
        authenticatedUser = userRepository.authenticateUser(userEmail, userPassword);

        if (authenticatedUser != null) {
            // Authentication successful
            String roles = authenticatedUser.getRoles();
            System.out.println("User authenticated successfully! Role: " + roles);

            // Check if the user has the role admin
            if (roles.contains("ROLE_ADMIN")) {
                // Load the home page FXML file
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) signInButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/profile.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) signInButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Authentication failed, handle the error (e.g., show an error message)
            System.out.println("Authentication failed. Please check your credentials.");
        }
    }

    @FXML
    private void forgetPassword() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgetPassword.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }
}
