// DonsFormController.java
package Dons.Controller;
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

public class DonsFormController {

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Dons> tvDon;

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
        // Vérifier si le montant est un entier positif
        return montant.matches("\\d+") && Integer.parseInt(montant) > 0;
    }

    private boolean validateDate(LocalDate date) {
        // Vérifier si la date n'est pas null et est dans le passé
        return date != null && date.isBefore(LocalDate.now());
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


    }

    private boolean handleInsertDon() {
        String montantStr = tfMontant.getText();
        LocalDate selectedDate = date.getValue();
        Association selectedAssociation = cbAssociation.getValue();
        Typedons selectedType = cbTypeDon.getValue();

        // Validation des champs
        if (!validateMontant(montantStr)) {
            clearFields();
            showAlert("Montant invalide. Veuillez saisir un montant positif.");
            return false;
        }
        int montant = Integer.parseInt(montantStr);

        if (!validateDate(selectedDate)) {
            clearFields();
            showAlert("Date invalide. Veuillez sélectionner une date dans le passé.");
            return false;
        }

        if (!validateAssociation(selectedAssociation)) {
            showAlert("Veuillez sélectionner une association.");
            clearFields();
            return false;
        }

        if (!validateTypeDon(selectedType)) {
            clearFields();
            showAlert("Veuillez sélectionner un type de don.");
            return false;
        }

        // Si toutes les validations passent, ajouter le don
        Date sqlDate = Date.valueOf(selectedDate);
        Dons don = new Dons(montant, selectedType, selectedAssociation, sqlDate);
        donsCrud.ajouterEntite(don);
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
            // Convertir java.util.Date en java.sql.Date
            Date sqlDate = new Date(selectedDon.getDate_mis_don().getTime());

            LocalDate localDate = sqlDate.toLocalDate();
            date.setValue(localDate);
        }
    }


    private boolean handleUpdateDon() {
        String montantStr = tfMontant.getText();
        LocalDate selectedDate = date.getValue();
        Association selectedAssociation = cbAssociation.getValue();
        Typedons selectedType = cbTypeDon.getValue();

        // Validation des champs
        if (!validateMontant(montantStr)) {
            clearFields();
            showAlert("Montant invalide. Veuillez saisir un montant positif.");
            return false;
        }
        int montant = Integer.parseInt(montantStr);

        if (!validateDate(selectedDate)) {
            clearFields();
            showAlert("Date invalide. Veuillez sélectionner une date dans le passé.");
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

        // Si toutes les validations passent, mettre à jour le don
        Date sqlDate = Date.valueOf(selectedDate);
        selectedDon.setMontant(montant);
        selectedDon.setAssociation_id(selectedAssociation);
        selectedDon.setType_id(selectedType);
        selectedDon.setDate_mis_don(sqlDate);

        // Update the entity in the database
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

}
