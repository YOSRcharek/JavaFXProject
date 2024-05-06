package demo.controllers;

import demo.model.Service;
import demo.model.Categorie;
import demo.repository.ServiceRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.List;

public class DetailsService {

    @FXML
    private TextField txtNomService;

    @FXML
    private Button modifierid;

    @FXML
    private Button supprimerid;

    @FXML
    private TableView<Service> tableViewServices;

    @FXML
    private TextField searchField;

    @FXML
    private Button rechercherBtn;

    @FXML
    private ComboBox<String> cmbCategorie; // Ajoutez la ComboBox pour la catégorie

    private ServiceRepository serviceRepository;
    private ObservableList<Service> servicesObservableList;

    @FXML
    public void initialize() {
        try {
            serviceRepository = new ServiceRepository();
            chargerServices();

            cmbCategorie.setItems(FXCollections.observableArrayList("Toutes", "Catégorie1", "Catégorie2", "Catégorie3")); // Remplacez "Catégorie1", "Catégorie2", "Catégorie3" par les noms de vos catégories

            // Ajouter un listener à la ComboBox pour filtrer les services en fonction de la catégorie sélectionnée
            cmbCategorie.setOnAction(event -> filtrerParCategorie());

            TableColumn<Service, String> nomServiceCol = (TableColumn<Service, String>) tableViewServices.getColumns().get(0);
            nomServiceCol.setCellValueFactory(new PropertyValueFactory<>("nom_service"));

            nomServiceCol.setCellFactory(column -> {
                return new TableCell<Service, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if ("NomDuServiceAColorer".equals(item)) {
                                setTextFill(Color.RED);
                            } else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
            });

            rechercherBtn.setOnAction(this::rechercherService); // Ajoutez un gestionnaire d'événements au bouton de recherche
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chargerServices() throws SQLException {
        List<Service> services = serviceRepository.display();
        servicesObservableList = FXCollections.observableArrayList(services);
        tableViewServices.setItems(servicesObservableList);
    }

    private void rechercherService(ActionEvent event) {
        String recherche = searchField.getText().trim().toLowerCase(); // Récupérer le texte de recherche
        ObservableList<Service> servicesRecherches = FXCollections.observableArrayList();

        for (Service service : servicesObservableList) {
            String nomService = service.getNom_service().toLowerCase();
            if (nomService.contains(recherche)) { // Vérifier si le nom du service contient le texte de recherche
                servicesRecherches.add(service); // Ajouter le service correspondant aux résultats de recherche
            }
        }
        tableViewServices.setItems(servicesRecherches); // Mettre à jour l'affichage avec les résultats de recherche
    }

    // Filtrer les services en fonction de la disponibilité sélectionnée
    private void filtrerParCategorie() {
        String selectedCategorie = cmbCategorie.getValue();
        if (selectedCategorie != null) {
            ObservableList<Service> servicesFiltres = FXCollections.observableArrayList();
            if (selectedCategorie.equals("Toutes")) {
                servicesFiltres.addAll(servicesObservableList); // Afficher tous les services
            } else {
                for (Service service : servicesObservableList) {
                    if (Service.getNom_categorie().equals(selectedCategorie)) { // Remplacez getCategorie() par la méthode appropriée pour obtenir le nom de la catégorie dans votre modèle
                        servicesFiltres.add(service); // Ajouter le service s'il correspond à la catégorie sélectionnée
                    }
                }
            }
            tableViewServices.setItems(servicesFiltres); // Mettre à jour l'affichage avec les services filtrés
        }


    } }