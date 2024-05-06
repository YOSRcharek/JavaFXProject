package demo.controllers;
import demo.repository.associationRepo;
import demo.repository.projetRepo;
import demo.service.mailService;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

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
import java.util.Objects;
import java.util.ResourceBundle;


public class SignUpAssoController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private AnchorPane root;
    @FXML
    private StackPane stack;
    @FXML
    private TextField filePathField;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField email;
    @FXML
    private TextField nom;
    @FXML
    private TextField password;
    @FXML
    private TextField telephone;
    @FXML
    private TextField domaine;
    @FXML
    private TextField description;
    @FXML
   private Label emailErrorLabel;
    @FXML
    private  Label passwordErrorLabel;
    @FXML
    private  Label descriptionErrorLabel;
    @FXML
    private Label nomErrorLabel;
    @FXML
    private  Label domaineErrorLabel;
    @FXML
    private  Label addressErrorLabel;
    @FXML
    private  Label telephoneErrorLabel;
    @FXML
    private  Label documentErrorLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
        descriptionErrorLabel.setText("");
        nomErrorLabel.setText("");
        domaineErrorLabel.setText("");
        addressErrorLabel.setText("");
        telephoneErrorLabel.setText("");
        documentErrorLabel.setText("");
    }
    private String extractAddressNameFromURL(String url) {
        // L'adresse est souvent contenue entre les balises "/place/" et "/"
        int placeIndex = url.indexOf("/place/");
        if (placeIndex != -1) {
            int startIndex = placeIndex + "/place/".length();
            int endIndex = url.indexOf("/", startIndex);
            if (endIndex != -1) {
                String addressNameEncoded = url.substring(startIndex, endIndex);
                try {
                    // Décoder les caractères encodés en UTF-8
                    return URLDecoder.decode(addressNameEncoded, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // Gérer l'exception d'encodage non pris en charge
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private void saveAddressName(String address) {
        System.out.println("Adresse enregistrée : " + address);
    }


    @FXML
    private void addressChoose() throws IOException {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Charger Google Maps
        webEngine.load("https://www.google.com/maps");

        // Ajouter un écouteur de changement d'URL
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'URL contient des informations d'adresse
            if (newValue.startsWith("https://www.google.com/maps/place/")) {
                // Extraire le nom de l'adresse de l'URL
                String addressName = extractAddressNameFromURL(newValue);
                // Enregistrer le nom de l'adresse dans votre application
                saveAddressName(addressName);
                addressInput.setText(addressName);
            }

        });

        StackPane root = new StackPane();
        root.getChildren().add(webView);

        Scene scene = new Scene(root, 800, 600);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Google Maps in JavaFX");
        primaryStage.show();

    }

    @FXML
    private void chooseFile() throws IOException {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.pdf")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());


        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    @FXML
    public void inscrire(ActionEvent event) {
        String emailValue = email.getText();
        String passwordValue = password.getText();
        String nomValue = nom.getText();
        String domaineValue = domaine.getText();
        String addressValue = addressInput.getText();
        String telephoneText = telephone.getText();
        String descriptionValue = description.getText();
        String documentValue=filePathField.getText();
        try {
        if (!emailValue.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            emailErrorLabel.setText( "Veuillez saisir une adresse email valide");
        }
        if (documentValue.isEmpty()) {
            documentErrorLabel.setText("Veuillez sélectionner un fichier.");

        }
            if (!passwordValue.matches("^(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
                passwordErrorLabel.setText("Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule et une lettre minuscule.");
            }

            if (!nomValue.matches("^[a-zA-Z]+$")) {
            nomErrorLabel.setText( "Le nom ne doit contenir que des lettres.");
        }
        if (!domaineValue.matches("^[a-zA-Z]+$")) {
            domaineErrorLabel.setText("Le domaine ne doit pas contenir que des lettres");
        }
        if(addressValue.isEmpty()){
            addressErrorLabel.setText("Veuillez choisir une adresse .");
        }
        if(!telephoneText.matches("^\\d{8}$")){
            telephoneErrorLabel.setText("Le numero de telephone doit contenir que 8 chiffres ");
        }
        if (descriptionValue.isEmpty()){
            descriptionErrorLabel.setText("Veuillez saisir une description");
        }

        if (nomValue.isEmpty()) {
            nomErrorLabel.setText( "Veuillez saisir un nom");
        }
        if (emailValue.isEmpty()) {
           emailErrorLabel.setText("Veillez saisir une email");
        }
        if (passwordValue.isEmpty()) {
            passwordErrorLabel.setText( "Veuillez saisir un password");
        }
        if (domaineValue.isEmpty()) {
            domaineErrorLabel.setText("Veillez saisir une domaine ");
        }
        if (telephoneText.isEmpty()) {
            telephoneErrorLabel.setText( "Veuillez saisir un telephone");
        }
        if (descriptionValue.isEmpty()) {
            descriptionErrorLabel.setText("Veillez saisir une description ");
        }

         boolean emailExists = associationRepo.emailExists(emailValue);

        if (emailExists) {
                emailErrorLabel.setText("L'email existe déjà.");
                return;
        }
            File selectedFile = new File(filePathField.getText());
            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
            Blob documentBlob = new SerialBlob(fileContent);

            int tel = Integer.parseInt(telephoneText);

            associationRepo.ajouterAssociation(nomValue,domaineValue, emailValue ,passwordValue,documentBlob, addressValue, descriptionValue, tel);
            mailService.sendConfirmationEmail(emailValue);
            System.out.println("Inscription réussie !");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
