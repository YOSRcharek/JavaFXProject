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


    }

    @FXML
    public void handleGoToForm(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DonsFrontForm.fxml"));
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
