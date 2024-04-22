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
    private TableView<Typedons> tvType;
    @FXML
    private TableColumn<Dons,String> cNom;

    // Instance du service TypeDonsCrud
    private final TypeDonsCrud typeDonsCrud = new TypeDonsCrud();

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Méthode appelée lors du clic sur le bouton d'insertion de type
    @FXML
    private void initialize() {
        // ObservableList pour les types de dons
        observableTypeList = FXCollections.observableArrayList();

        // Charger les types de dons depuis la base de données
        List<Typedons> typeList = typeDonsCrud.afficherType();
        observableTypeList.addAll(typeList);

        // Associer les types de dons à la TableView
        tvType.setItems(observableTypeList);

        // Associer les propriétés des types de dons aux colonnes de la TableView
        cNom.setCellValueFactory(new PropertyValueFactory<>("name"));



        btnDeleteType.setOnAction(event ->
            handleDeleteType()
        );
    }

    @FXML
    private void refreshTable() {
        observableTypeList.clear();
        observableTypeList.addAll(typeDonsCrud.afficherType());
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
        Typedons type = tvType.getSelectionModel().getSelectedItem();
        if (type != null) {
            typeDonsCrud.supprimerType(type);
            showAlert("Type de don supprimé avec succès.");
            refreshTable();
        } else {
            showAlert("Veuillez sélectionner un type de don à supprimer.");
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
            Typedons selectedType = tvType.getSelectionModel().getSelectedItem();

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
