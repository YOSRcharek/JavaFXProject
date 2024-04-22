package Dons.Controller;
import Dons.entities.Dons;
import Dons.entities.Typedons;
import Dons.services.TypeDonsCrud;
import Dons.services.TypeDonsCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TypeDonsFormController {

    @FXML
    private Button btnInsertType;
    @FXML
    private Button btnUpdateType;
    @FXML
    private TextField tfNomType;
    private Typedons selectedType;
    private final TypeDonsCrud typeDonsCrud = new TypeDonsCrud();
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateTypeName(String typeName) {
        // Vérifie si le nom n'est pas vide
        if (typeName.isEmpty()) {
            showAlert("Nom du type de don est requis");
            return false;
        }

        // Vérifie si le nom contient uniquement des lettres
        if (!typeName.matches("[a-zA-Z]+")) {
            showAlert("Nom du type de don ne doit pas contenir des chieffres");
            return false;
        }

        // Vérifie si le nom a une longueur entre 3 et 15 caractères
        if (typeName.length() < 3 || typeName.length() > 15) {
            showAlert("Nom du type de don doit contenir entre 3 et 15 lettres");
            return false;
        }

        return true; // Si toutes les validations passent, le nom est considéré comme valide
    }


    private boolean handleInsertTypeDon() {
        String typeName = tfNomType.getText();

        // Validation du champ de saisie
        if (!validateTypeName(typeName)) {
            clearFields();
            return false;
        }

        // Si la validation passe, ajouter le type de don
        Typedons newType = new Typedons(0, typeName);
        typeDonsCrud.ajouterType(newType);
        showAlert("Type de don ajouté avec succès.");
        clearFields();
        return true;
    }



    public void setSelectedType(Typedons selectedType) {
        this.selectedType = selectedType;
        if (selectedType != null) {
            if (tfNomType != null) {
                tfNomType.setText(selectedType.getName());
            }
        }
    }

    @FXML
    private boolean handleUpdateType() {
        if (selectedType != null) {
            String newName = tfNomType.getText();
            if (!newName.isEmpty()) {
                // Validation du nouveau nom
                if (!validateTypeName(newName)) {
                    clearFields();
                    return false;
                }

                selectedType.setName(newName);
                typeDonsCrud.modifierType(selectedType);
                showAlert("Type de don mis à jour avec succès.");
                clearFields();
                return true;
            } else {
                showAlert("Veuillez entrer un nouveau nom pour le type de don.");
            }
        } else {
            showAlert("Veuillez sélectionner un type de don à mettre à jour.");
        }
        return false;
    }



    @FXML
    public void handleGoToListType (javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/typeDons.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 1000, 500); // Set scene size to 1000x500

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();  // Corrected line
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Page type des dons");
            stage.show();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateAndGoToListType(javafx.event.ActionEvent actionEvent) {
      if  (handleUpdateType()) {
          handleGoToListType(actionEvent);
      }
    }

    @FXML
    private void handleInsertAndGoToListType(javafx.event.ActionEvent actionEvent) {
        if (handleInsertTypeDon()) { // Vérifie si l'insertion a réussi
            handleGoToListType(actionEvent); // Navigue vers la liste des types de dons
        }
    }

    @FXML
    private void handleGoToTypeDon(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/typeDons.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page des types de don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tfNomType.clear();
    }
}
