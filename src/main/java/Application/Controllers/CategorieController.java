package Application.Controllers;
import javafx.scene.control.*;
import Application.Model.Categorie;
import Application.Repository.CategorieRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.List;

public class CategorieController {

    @FXML
    private TextField txtNomCategorie;

    @FXML
    private Button addcategorie;

    @FXML
    private Button modifierid;

    @FXML
    private Button supprimerid;

    @FXML
    private TableView<Categorie> tableViewCategories;

    private CategorieRepository categorieRepository;
    private ObservableList<Categorie> categoriesObservableList;

    @FXML
    public void initialize() {
        try {
            categorieRepository = new CategorieRepository();
            chargerCategories();
            TableColumn<Categorie, String> nomCategorieCol = (TableColumn<Categorie, String>) tableViewCategories.getColumns().get(0);

            nomCategorieCol.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));

            nomCategorieCol.setCellFactory(column -> {
                return new TableCell<Categorie, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);

                            // Changer la couleur du texte en fonction du nom de la catégorie
                            if ("NomDeLaCategorieAColorer".equals(item)) {
                                setTextFill(Color.RED); // Couleur rouge pour cette catégorie
                            } else {
                                setTextFill(Color.BLACK); // Couleur noire par défaut
                            }
                        }
                    }
                };
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chargerCategories() throws SQLException {
        List<Categorie> categories = categorieRepository.getAll();
        categoriesObservableList = FXCollections.observableArrayList(categories);
        tableViewCategories.setItems(categoriesObservableList);
    }

    @FXML
    void add(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (txtNomCategorie.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vous devez remplir le nom de la catégorie.");
            alert.show();
            return; // Sortir de la méthode si le champ est vide
        }

        // Créer un objet Categorie avec le nom saisi
        Categorie nouvelleCategorie = new Categorie(txtNomCategorie.getText());

        // Appeler la méthode add de CategorieRepository
        try {
            categorieRepository.add(nouvelleCategorie);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La catégorie a été ajoutée avec succès !");
            alert.show();

            // Recharger les catégories depuis la base de données et rafraîchir la TableView
            chargerCategories();
            tableViewCategories.refresh();

            // Vider le champ de saisie
            txtNomCategorie.clear();

        } catch (SQLException e) {
            afficherErreur("Erreur lors de l'ajout d'une nouvelle catégorie.", e);
        }
    }

    private void afficherErreur(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message + "\n" + e.getMessage());
        alert.show();
    }

    private void afficherErreur(String s, SQLException e) {
    }

    @FXML
    void update(ActionEvent event) {
        Categorie categorieSelectionnee = tableViewCategories.getSelectionModel().getSelectedItem();
        if (categorieSelectionnee != null) {
            try {
                String nouveauNom = txtNomCategorie.getText();
                categorieSelectionnee.setNom_categorie(nouveauNom);
                categorieRepository.update(categorieSelectionnee, categorieSelectionnee.getId());
                chargerCategories();
                txtNomCategorie.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Categorie categorieSelectionnee = tableViewCategories.getSelectionModel().getSelectedItem();
        if (categorieSelectionnee != null) {
            try {
                categorieRepository.delete(categorieSelectionnee.getId());
                chargerCategories();
                txtNomCategorie.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleTextFieldChange(ActionEvent actionEvent) {
    }
}
