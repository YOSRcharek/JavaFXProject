// DonsFormController.java
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
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class DonsFormController {

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;



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
    private Dons selectedDon; // Added field

    private boolean validateMontant(String montant) {

        return montant.matches("\\d+") && Integer.parseInt(montant) > 0;
    }

    private boolean validateDate(LocalDate date) {

        return date != null && date.equals(LocalDate.now());
    }

    private boolean validateAssociation(Association association) {

        return association != null;
    }

    private boolean validateTypeDon(Typedons typeDon) {

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

        if (selectedDon == null && btnUpdate != null) {
            btnUpdate.setVisible(false);
        }
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


    public void setSelectedDon(Dons selectedDon) {
        this.selectedDon = selectedDon;
        if (selectedDon != null) {
            tfMontant.setText(String.valueOf(selectedDon.getMontant()));
            cbAssociation.setValue(selectedDon.getAssociation_id());
            cbTypeDon.setValue(selectedDon.getType_id());

            Date sqlDate = new Date(selectedDon.getDate_mis_don().getTime());
            LocalDate localDate = sqlDate.toLocalDate();
            date.setValue(localDate);

            // Show btnUpdate and hide btnInsert if selectedDon is not null

        }
        if (btnInsert != null && btnUpdate != null) {

            btnInsert.setVisible(false);
            btnUpdate.setVisible(true);
        }
        else   {
            btnInsert.setVisible(true);
            btnUpdate.setVisible(false);
        }
    }

    private boolean handleUpdateDon() {
        String montantStr = tfMontant.getText();
        Association selectedAssociation = cbAssociation.getValue();
        Typedons selectedType = cbTypeDon.getValue();

        // Validation des champs
        if (!validateMontant(montantStr)) {
            clearFields();
            showAlert("Montant invalide. Veuillez saisir un montant positif.");
            return false;
        }
        int montant = Integer.parseInt(montantStr);
        LocalDate selectedDate = LocalDate.now();

        if (!validateDate(selectedDate)) {
            clearFields();
            showAlert("Date invalide. Veuillez sélectionner la date d'aujourd'hui.");
            return false;
        }

        if (!validateAssociation(selectedAssociation)) {
            clearFields();
            showAlert("Veuillez sélectionner une association.");
            return false;
        }

        if (!validateTypeDon(selectedType)) {
            clearFields();
            showAlert("Veuillez sélectionner un type de don.");
            return false;
        }


        Date sqlDate = Date.valueOf(selectedDate);
        selectedDon.setMontant(montant);
        selectedDon.setAssociation_id(selectedAssociation);
        selectedDon.setType_id(selectedType);
        selectedDon.setDate_mis_don(sqlDate);


        donsCrud.modifierEntite(selectedDon);
        showAlert("Don mis à jour avec succès.");
        clearFields();
        return true;
    }



    private void clearFields() {
        tfMontant.clear();
        cbAssociation.getSelectionModel().clearSelection();
        cbTypeDon.getSelectionModel().clearSelection();
        date.setValue(null);
    }

    @FXML
    public void handleGoToList(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Dons.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 1000, 500); // Set scene size to 1000x500
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();  // Corrected line
            stage.setScene(scene);
            stage.setTitle("Page Dons");
            stage.show();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateAndGoToList(javafx.event.ActionEvent actionEvent) {
        if (handleUpdateDon()) {
            handleGoToList(actionEvent);
        }
    }


    @FXML
    private void handleInsertAndGoToList(javafx.event.ActionEvent actionEvent) {
        if (handleInsertDon()) {

            handleGoToList(actionEvent);
        }
    }

    public void handleGoToDons(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Dons.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 500); // Set scene size to 1000x500
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page dons");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
