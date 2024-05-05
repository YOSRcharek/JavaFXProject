package Dons.Controller;
import Dons.entities.Dons;
import Dons.entities.Typedons;
import Dons.services.TypeDonsCrud;

import java.io.IOException;
import java.util.*;
import javafx.collections.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
public class TypeDonsController {
    @FXML
    private ObservableList<Typedons> observableTypeList;

    @FXML
    private Button btnDeleteType;
    @FXML
    private Button btnBack;
    @FXML
    private Button  btnOpenFormToAddType;
    @FXML
    private Button  btnOpenFormToUpdateType;
    @FXML
    TextField recherche;
    @FXML
    private ListView<Typedons> lvTypeDon;


    // Instance du service TypeDonsCrud
    private final TypeDonsCrud typeDonsCrud = new TypeDonsCrud();

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private ObservableList<Typedons> originalTypeList;

    @FXML
    private void initialize() {
        // Initialiser la liste observable pour les données originales
        originalTypeList = FXCollections.observableArrayList();

        // Charger les types de dons depuis la base de données
        List<Typedons> typeList = typeDonsCrud.afficherType();
        originalTypeList.addAll(typeList);

        // Associer les types de dons à la ListView
        lvTypeDon.setItems(originalTypeList);

        // Ajouter un écouteur de changement pour le champ de recherche
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filtrer les données sur une nouvelle liste temporaire
            ObservableList<Typedons> filteredList = filterData(newValue);

            // Afficher les données filtrées dans la ListView
            lvTypeDon.setItems(filteredList);
        });

        btnDeleteType.setOnAction(event -> handleDeleteType());
    }

    private ObservableList<Typedons> filterData(String keyword) {
        ObservableList<Typedons> filteredData = FXCollections.observableArrayList();

        for (Typedons type : originalTypeList) {
            String typeName = type.getName();

            if (typeName.toLowerCase().contains(keyword.toLowerCase())) {
                filteredData.add(type);
            }
        }

        return filteredData;
    }

    @FXML
    private void refreshTable() {
        originalTypeList.clear();
        originalTypeList.addAll(typeDonsCrud.afficherType());
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

    @FXML
    private void handleDeleteType() {
        Typedons type = lvTypeDon.getSelectionModel().getSelectedItem();
        if (type != null) {
            typeDonsCrud.supprimerType(type);
            Notifications.create().title("Done").text("Type de don supprimé avec succès.").showConfirm();
            refreshTable();
        } else {
            Notifications.create().title("Done").text("Veuillez sélectionner un type de don à supprimer.").showConfirm();
        }
    }

  /*  private void handleUpdateType() {
        Typedons selectedType = tvType.getSelectionModel().getSelectedItem();
        if (selectedType != null) {
            // Récupérer le nouveau nom du type de don à partir du champ de texte
            String newName = tfNomType.getText();

            // Assurez-vous que le nouveau nom n'est pas vide
            if (!newName.isEmpty()) {
                // Mettre à jour le nom du type de don sélectionné
                selectedType.setName(newName);

                // Appeler la méthode pour modifier le type de don dans la base de données
                typeDonsCrud.modifierType(selectedType);

                // Afficher un message indiquant que la modification a réussi
                showAlert("Type de don modifié avec succès.");

                // Rafraîchir la liste des types de dons dans la TableView
                observableTypeList.clear();
                observableTypeList.addAll(typeDonsCrud.afficherType());
            } else {
                // Afficher un message d'erreur si le champ de texte est vide
                showAlert("Veuillez entrer un nouveau nom pour le type de don.");
            }
        } else {
            // Afficher un message d'erreur si aucun type de don n'est sélectionné
            showAlert("Veuillez sélectionner un type de don à modifier.");
        }
    }*/



    @FXML
    private void handleGoToTypeDonFormToUpdate(javafx.event.ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML du formulaire de type de don
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/typeDonsForm.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur du formulaire de type de don
            TypeDonsFormController typeDonFormController = loader.getController();

            // Récupérer le type de don sélectionné dans la TableView
            Typedons selectedType = lvTypeDon.getSelectionModel().getSelectedItem();

            // Passer le type de don sélectionné au contrôleur du formulaire
            typeDonFormController.setSelectedType(selectedType);

            // Configurer la scène et la fenêtre
            Scene scene = new Scene(root, 550, 400);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Modifier un type de don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGoToFormType(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/typeDonsForm.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 550, 400); // Set scene size to 1000x500
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();  // Corrected line
            stage.setScene(scene);
            stage.setTitle("Page form des types");
            stage.show();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }

}
