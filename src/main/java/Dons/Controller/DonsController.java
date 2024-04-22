// DonsController.java
package Dons.Controller;

import Dons.entities.Association;
import Dons.entities.Dons;
import Dons.entities.Typedons;
import Dons.services.DonsCrud;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class DonsController extends Application {
    private ObservableList<Dons> observableDonsList;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGoToTypeDon;

    @FXML
    private Button btnOpenFormToAdd;

    @FXML
    private Button btnOpenFormToUpdate;

    @FXML
    private TableView<Dons> tvDon;

    @FXML
    private TableColumn<Dons, Integer> cMontant;

    @FXML
    private TableColumn<Dons, Typedons> cType;

    @FXML
    private TableColumn<Dons, Association> cAssociation;

    @FXML
    private TableColumn<Dons, Date> cDate;

    private final DonsCrud donsCrud = new DonsCrud();

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        observableDonsList = FXCollections.observableArrayList();


        // Charger les dons depuis la base de données
        List<Dons> donsList = donsCrud.afficherEntite();

        // Créer une liste observable pour les dons
        observableDonsList.addAll(donsList);

        // Ajouter les dons à la TableView
        tvDon.setItems(observableDonsList);

        // Associer les propriétés des dons aux colonnes de la TableView
        cMontant.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMontant()).asObject());
        cType.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType_id()));
        cAssociation.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAssociation_id()));
        cDate.setCellValueFactory(new PropertyValueFactory<>("date_mis_don"));

        btnDelete.setOnAction(event -> handleDeleteDon());
    }

    @FXML
    private void refreshTable() {
        observableDonsList.clear();
        observableDonsList.addAll(donsCrud.afficherEntite());
    }

    @FXML
    private void handleDeleteDon() {
        Dons don = tvDon.getSelectionModel().getSelectedItem();
        if (don != null) {
            donsCrud.supprimerEntite(don);
            showAlert("Don supprimé avec succès.");
            refreshTable();
        } else {
            showAlert("Veuillez sélectionner un don à supprimer.");
        }
    }


    @FXML
    private void handleGoToFormToUpdate(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DonsForm.fxml"));
            Parent root = loader.load();

            // Get the controller of the DonsForm
            DonsFormController donsFormController = loader.getController();

            // Pass the selected donation from the TableView to DonsFormController
            Dons selectedDon = tvDon.getSelectionModel().getSelectedItem();
            donsFormController.setSelectedDon(selectedDon);

            // Set up the stage and scene
            Scene scene = new Scene(root, 550, 650);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Modifier un don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleGoToTypeDon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/typeDons.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) btnGoToTypeDon.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page des types de don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGoToForm(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DonsForm.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 550, 650); // Set scene size to 1000x500
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();  // Corrected line
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Page form dons");
            stage.show();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dons.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Your Application Title");
        primaryStage.setScene(new Scene(root, 1000, 500));
        Scene scene = root.getScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.show();
    }
}
