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
        System.out.println(userEmail);
        System.out.println(userPassword);
        authenticatedUser = userRepository.authenticateUser(userEmail, userPassword);

        if (authenticatedUser != null) {
            String roles = authenticatedUser.getRoles();
            System.out.println("User authenticated successfully! Role: " + roles);

            // Vérifiez si l'utilisateur a le rôle admin
            if (roles.contains("ROLE_ADMIN")) {
                // Charge le fichier FXML de la page d'accueil
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Home.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) signInButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace(); // Imprime la trace de la pile de l'exception
                }
            } else if (roles.contains("ROLE_ASSOCIATION")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../profil.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) signInButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace(); // Imprime la trace de la pile de l'exception
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../test.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) signInButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace(); // Imprime la trace de la pile de l'exception
                }
            }

        } else {
            System.out.println("Authentication failed. Please check your credentials.");
        }

    }

    @FXML
    private void forgetPassword() {
        try {



            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ForgetPassword.fxml"));
            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) signInButton.getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Forget password");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }
}