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

public class DonsFrontController {
    private ObservableList<Dons> observableDonsList;
    private ObservableList<Dons> originalDonsList;
    @FXML
    private TextField recherche;

    @FXML
    private ListView<Dons> lvDon;

    private final DonsCrud donsCrud = new DonsCrud();

    @FXML
    public void initialize() {
        originalDonsList = FXCollections.observableArrayList();

        List<Dons> donsList = donsCrud.afficherEntite();

        originalDonsList.addAll(donsList);


        lvDon.setItems(originalDonsList);

        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Dons> filteredData = filterData(newValue);
            lvDon.setItems(filteredData);
        });
    }



    private ObservableList<Dons> filterData(String keyword) {
        ObservableList<Dons> filteredData = FXCollections.observableArrayList();

        for (Dons don : originalDonsList) {
            String montantString = String.valueOf(don.getMontant());

            if (montantString.contains(keyword)) {
                filteredData.add(don);
            }
        }

        return filteredData;
    }




    @FXML
    public void handleGoToForm(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DonsFrontForm.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 550, 650);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Page form dons");
            stage.show();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    @FXML
    public void handleGoToBack(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Dons.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 1000, 500); // Set scene size to 1000x500
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();  // Corrected line
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Page form dons");
            stage.show();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }
}
