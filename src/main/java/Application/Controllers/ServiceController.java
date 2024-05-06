package Application.Controllers;

import Application.Model.Association;
import Application.Model.Categorie;
import Application.Model.Commentaire;
import Application.Repository.ServiceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Application.Model.Service;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ServiceController {
    @FXML
    private TableColumn<Association, Association> associationidColumn;

    @FXML
    private TableColumn<Categorie, Categorie> categorieIdColumn;

    @FXML
    private TableColumn<Commentaire, Commentaire> commentaireIdColumn;
    @FXML
    private TableView<Service> tableView;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Service, String> descriptionColumn;

    @FXML
    private TableColumn<Service, String> disponibiliteColumn;

    @FXML
    private TableColumn<Service, String> nom_servicecollumn;

    @FXML
    private TableColumn<Service, Integer> seriviceIdColumn;

    @FXML
    private Button updateButton;

    @FXML
    private TextField txtnomservice;

    @FXML
    private TextField txtdescription;

    @FXML
    private ComboBox<String> cmbCategorie;

    @FXML
    private ComboBox<String> cmbAssociation;

    @FXML
    private ComboBox<String> cmbCommentaire;
    @FXML
    private ComboBox<String> cmbDisponibilite;
    @FXML
    private TextField nomServiceField;

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField searchField;
    private ObservableList<Service> servicesObservableList;
    @FXML
    private TableView<Service> tableViewServices;



    @FXML
    private Button addservice;

    @FXML
    private Button annuler;


    private ServiceRepository serviceRepository;

    @FXML
    public void initialize() {
        serviceRepository = new ServiceRepository();

        // Initialiser les colonnes de la TableView
        seriviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom_servicecollumn.setCellValueFactory(new PropertyValueFactory<>("nom_service"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        disponibiliteColumn.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
        associationidColumn.setCellValueFactory(new PropertyValueFactory<>("association_id"));
        categorieIdColumn.setCellValueFactory(new PropertyValueFactory<>("categorie_id"));
        commentaireIdColumn.setCellValueFactory(new PropertyValueFactory<>("commentaire_id"));

        // Initialiser la liste observable des services avec les données récupérées de la base de données
        try {
            servicesObservableList = FXCollections.observableArrayList(serviceRepository.display());
        } catch (SQLException e) {
            showAlert("Erreur lors de la récupération des services : " + e.getMessage());
        }

        // Ajouter la liste observable des services à la TableView
        tableView.setItems(servicesObservableList);
    }


    private void initializeComboBoxes() {
        // Code pour initialiser les ComboBox avec les données nécessaires
        // Par exemple, récupérer les catégories, les associations, les commentaires, etc.
    }

    private void handleComboBoxChange() {
        // Code à exécuter lorsque la sélection d'un ComboBox change
    }

    @FXML
    void addservice() {
        // Récupérer les valeurs des champs
        String nomService = txtnomservice.getText();
        String description = txtdescription.getText();
        String categorie = cmbCategorie.getValue();
        String association = cmbAssociation.getValue();
        String commentaire = cmbCommentaire.getValue();
        String disponibilite = cmbDisponibilite.getValue();

        // Vérifier si tous les champs sont remplis
        if (nomService.isEmpty() || description.isEmpty() || categorie == null || association == null || commentaire == null || disponibilite == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Créer un objet Service avec les valeurs des champs
        // Ici, tu devras adapter cette partie en fonction de ta logique métier et de la structure de ta base de données
        Service nouveauService = new Service(/* Passer les valeurs nécessaires ici */);

        // Appeler la méthode add du serviceRepository pour ajouter le service à la base de données
        try {
            serviceRepository.add(nouveauService);
            showAlert("Le service a été ajouté avec succès !");
        } catch (SQLException e) {
            showAlert("Erreur lors de l'ajout du service : " + e.getMessage());
        }
    }

    private void afficherServices() throws SQLException {
        // Effacer les éléments existants dans la TableView
        tableView.getItems().clear();

        // Récupérer tous les services depuis la base de données
        List<Service> services = serviceRepository.display();

        // Ajouter les services à la TableView
        tableView.getItems().addAll(services);
    }
    @FXML
    void annuler() {
        // Code pour annuler l'ajout de service
        // Par exemple, vider les champs de saisie
        txtnomservice.clear();
        txtdescription.clear();
        cmbCategorie.getSelectionModel().clearSelection();
        cmbAssociation.getSelectionModel().clearSelection();
        cmbCommentaire.getSelectionModel().clearSelection();
        cmbDisponibilite.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public void handleTextFieldChange(ActionEvent actionEvent) {
    }
    private void chargerServices() throws SQLException {
        try {
            List<Service> services = serviceRepository.display();
            servicesObservableList = FXCollections.observableArrayList(services);
            tableView.setItems(servicesObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
        List<Service> services = serviceRepository.display();
        ObservableList<Service> servicesObservableList = FXCollections.observableArrayList(services);
        tableView.setItems(servicesObservableList);
    }

    @FXML
    void delete(ActionEvent event) {
        // Récupérer le service sélectionné dans la table
        Service serviceSelectionne = tableView.getSelectionModel().getSelectedItem();

        // Vérifier si un service est sélectionné
        if (serviceSelectionne != null) {
            try {
                // Appeler la méthode de suppression dans votre serviceRepository
                serviceRepository.delete(serviceSelectionne);

                // Mettre à jour l'affichage de la table pour refléter la suppression
                chargerServices();

                // Effacer les champs ou effectuer d'autres actions nécessaires après la suppression
                // Par exemple, vous pouvez effacer les champs de saisie
                if(txtnomservice != null) {
                    txtnomservice.clear();
                }
                if(txtdescription != null) {
                    txtdescription.clear();
                }
                if(cmbCategorie != null) {
                    cmbCategorie.getSelectionModel().clearSelection();
                }
                if(cmbAssociation != null) {
                    cmbAssociation.getSelectionModel().clearSelection();
                }
                if(cmbCommentaire != null) {
                    cmbCommentaire.getSelectionModel().clearSelection();
                }
                if(cmbDisponibilite != null) {
                    cmbDisponibilite.getSelectionModel().clearSelection();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'erreur de suppression
            }
        } else {
            // Aucun service sélectionné, vous pouvez afficher un message d'erreur ou effectuer d'autres actions nécessaires
        }
    }
    @FXML
    void btnModifierService(ActionEvent event) {
        // Récupérer les valeurs des champs
        String nomService =  nomServiceField.getText();
        String description = descriptionField.getText();
        String association = cmbAssociation.getValue();
        String categorie = cmbCategorie.getValue();
        String commentaire = cmbCommentaire.getValue();
        String disponibilite = cmbDisponibilite.getValue();

        // Vérifier si tous les champs sont remplis
        if (nomService.isEmpty() || description.isEmpty() || association == null || categorie == null || commentaire == null || disponibilite == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Récupérer le service sélectionné dans la table
        Service serviceSelectionne = tableView.getSelectionModel().getSelectedItem();

        // Créer un objet Service avec les nouvelles valeurs
        Service service = new Service();
        service.setId(serviceSelectionne.getId());
        service.setNomservice(nomService);
        service.setDescription(description);
        // Vous devez définir les autres attributs du service en fonction des valeurs sélectionnées dans les combobox

        // Appeler la méthode update du serviceRepository pour mettre à jour le service dans la base de données
        try {
            serviceRepository.update(service);
            showAlert("Le service a été modifié avec succès !");
        } catch (SQLException e) {
            showAlert("Erreur lors de la modification du service : " + e.getMessage());
        }
    }

        @FXML
        void update(ActionEvent event) throws IOException {
            // Charger le fichier FXML de la page ModifierService2
            Parent root = FXMLLoader.load(getClass().getResource("ModifierService2.fxml"));

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(scene);
            stage.show();
        }

    @FXML
    public void rechercherService(ActionEvent actionEvent) {
        String recherche = searchField.getText().trim().toLowerCase(); // Récupérer le texte de recherche
        ObservableList<Service> servicesRecherches = FXCollections.observableArrayList();

        for (Service service : servicesObservableList) {
            String nomService = service.getNom_service().toLowerCase();
            if (nomService.contains(recherche)) { // Vérifier si le nom du service contient le texte de recherche
                servicesRecherches.add(service); // Ajouter le service correspondant aux résultats de recherche
            }
        }
        tableView.setItems(servicesRecherches); // Mettre à jour l'affichage avec les résultats de recherche
    }


}







