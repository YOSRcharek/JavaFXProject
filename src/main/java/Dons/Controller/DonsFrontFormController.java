package Dons.Controller;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.event.ActionEvent;
import java.io.IOException;
import Dons.entities.Association;
import Dons.entities.Dons;
import Dons.entities.Typedons;
import Dons.services.DonsCrud;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
public class DonsFrontFormController {
    @FXML
    private Button btnBack;
    @FXML
    private Button btnInsert;
    @FXML
    private TextField tfMontant;

    @FXML
    private ChoiceBox<Association> cbAssociation;

    @FXML
    private ChoiceBox<Typedons> cbTypeDon;

    @FXML
    private DatePicker date;

    private final DonsCrud donsCrud = new DonsCrud();

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateMontant(String montant) {
        // Vérifier si le montant est un entier positif
        return montant.matches("\\d+") && Integer.parseInt(montant) > 0;
    }
    private boolean validateDate(LocalDate date) {
        // Vérifier si la date n'est pas null et est aujourd'hui
        return date != null && date.equals(LocalDate.now());
    }

    private boolean validateAssociation(Association association) {
        // Vérifier si l'association sélectionnée n'est pas null
        return association != null;
    }

    private boolean validateTypeDon(Typedons typeDon) {
        // Vérifier si le type de don sélectionné n'est pas null
        return typeDon != null;
    }


    public void initialize() {
        Typedons defaultType = donsCrud.getTypedonsById(1);
        cbTypeDon.getItems().addAll(donsCrud.getAllTypedons());
        cbTypeDon.setValue(defaultType);

        Association defaultAssociation = donsCrud.getAssociationById(1);
        cbAssociation.getItems().addAll(donsCrud.getAllAssociations());
        cbAssociation.setValue(defaultAssociation);

        date.setVisible(false);
        date.setValue(LocalDate.now());

    }


    private boolean handleInsertDon() {
        String montantStr = tfMontant.getText();

        // Validation du montant
        if (!validateMontant(montantStr)) {
            clearFields();
            showAlert("Montant invalide. Veuillez saisir un montant positif.");
            return false;
        }


        // Conversion du montant en int
        int realAmount = Integer.parseInt(montantStr);
        int amount = Integer.parseInt(montantStr)*100;


        // Traitement du paiement
        processPayment(amount);

        // Récupération des autres champs
        LocalDate selectedDate = date.getValue();
        Association selectedAssociation = cbAssociation.getValue();
        Typedons selectedType = cbTypeDon.getValue();

        // Validation de la date
        if (!validateDate(selectedDate)) {
            clearFields();
            showAlert("Date invalide. Veuillez sélectionner la date d'aujourd'hui.");
            return false;
        }

        // Validation de l'association
        if (!validateAssociation(selectedAssociation)) {
            showAlert("Veuillez sélectionner une association.");
            clearFields();
            return false;
        }

        // Validation du type de don
        if (!validateTypeDon(selectedType)) {
            clearFields();
            showAlert("Veuillez sélectionner un type de don.");
            return false;
        }

        // Création de l'objet Dons et ajout dans la base de données
        Date sqlDate = Date.valueOf(selectedDate);
        Dons don = new Dons(realAmount, selectedType, selectedAssociation, sqlDate);
        donsCrud.ajouterEntite(don);

        // Affichage du message de succès et nettoyage des champs
        showAlert("Don ajouté avec succès.");
        clearFields();
        return true;
    }
    private void clearFields() {
        tfMontant.clear();
        cbAssociation.getSelectionModel().clearSelection();
        cbTypeDon.getSelectionModel().clearSelection();

    }


    @FXML
    public void handleGoToListFront(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) btnInsert.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleInsertAndGoToListFront(javafx.event.ActionEvent actionEvent) {
        if (handleInsertDon()) {
            handleGoToListFront(actionEvent);
        }
    }

    public void handleGoToDonsFront(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
    private void processPayment(long amount) {
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51OnN2iI3dJx1TucUSLXejDmbjPPLTqFb4Q5btmlmfliYiDOHXlHrzLaq5OOCMJv9PRXDewithRQCah0lv1VXi21000LhIpIGIG";

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount) // Amount in cents
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }
}
