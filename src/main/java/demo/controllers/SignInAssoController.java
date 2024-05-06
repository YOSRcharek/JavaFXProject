package demo.controllers;

import demo.repository.associationRepo;
import demo.service.mailService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInAssoController implements Initializable {


        @FXML
        private TextField email;

        @FXML
        private TextField password;

        @FXML
        private Label emailErrorLabel;
        @FXML
        private  Label passwordErrorLabel;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            emailErrorLabel.setText("");
            passwordErrorLabel.setText("");

        }



        @FXML
        public void connect(ActionEvent event) {
            String emailValue = email.getText();
            String passwordValue = password.getText();

            try {
                if (!emailValue.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    emailErrorLabel.setText("Veuillez saisir une adresse email valide");
                }


                if (!passwordValue.matches("^(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
                    passwordErrorLabel.setText("Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule et une lettre minuscule.");
                }


                if (emailValue.isEmpty()) {
                    emailErrorLabel.setText("Veillez saisir une email");
                }
                if (passwordValue.isEmpty()) {
                    passwordErrorLabel.setText("Veuillez saisir un password");
                }


                boolean emailExists = associationRepo.emailExists(emailValue);

                if (emailExists) {
                    emailErrorLabel.setText("L'email existe déjà.");
                    return;
                }


                boolean connected = associationRepo.connect(emailValue, passwordValue);

                if (connected) {
                    System.out.println("Connexion réussie !");
                    // Afficher une boîte de dialogue d'information pour indiquer que la connexion est réussie
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Connexion réussie");
                    alert.setHeaderText(null);
                    alert.setContentText("Connexion réussie !");
                    alert.showAndWait();
                } else {
                    // Afficher un message d'erreur si la connexion échoue
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de connexion");
                    alert.setHeaderText(null);
                    alert.setContentText("L'email ou le mot de passe est incorrect.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}

