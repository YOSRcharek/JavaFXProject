package com.example.application.controllers;

import com.example.application.model.Events;
import com.example.application.repository.EventRepo;
import com.example.application.repository.TypeEventRepo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EventController {
    @FXML
    public Button retour;
    @FXML
    private ListView<Events> eventListView;
    @FXML
    private TextField searchField;
    @FXML
    private Button triAlphabetiqueButton;
    private int currentPage = 0;
    @FXML
    private Label currentPageLabel;
    private List<List<Events>> eventPages;





    private EventRepo eventRepo;


    @FXML
    public void initialize() {
        // Initialiser EventRepo
        eventRepo = new EventRepo();
        searchField.setOnKeyReleased(event -> {
            rechercherEvenements();

        });
        currentPage = 0;
        // Charger et afficher tous les événements au démarrage de la vue
        chargerTousLesEvenements();

    }

    // Méthode pour charger et afficher tous les événements
    private void chargerTousLesEvenements() {
        // Récupérer tous les événements depuis la base de données
        List<Events> eventsList = eventRepo.getAllEvents();

        eventPages = partition(eventsList, 3);
        afficherPage(currentPage);

        // Ajouter les événements à la ListView
        eventListView.getItems().addAll(eventsList);

        // Configurer la ListView pour utiliser un modèle de cellule personnalisé
        eventListView.setCellFactory(param -> new ListCell<Events>() {
            @Override
            protected void updateItem(Events event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Créer une carte pour afficher les informations de l'événement
                    StackPane card = new StackPane();

                    card.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #CCCCCC; -fx-border-radius: 5; -fx-padding: 10px;");
                    card.setPrefSize(300, 150);

                    // Conteneur pour les détails de l'événement
                    VBox details = new VBox();
                    details.setAlignment(Pos.CENTER); // Centrer le contenu verticalement et horizontalement
                    details.setSpacing(10);

                    // Formatter pour la date
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


                    String imagePath = event.getImage(); // Chemin de l'image
                    Image image = new Image(new File(imagePath).toURI().toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(200); // Ajuster la largeur de l'image selon vos besoins
                    imageView.setFitHeight(200); // Ajuster la hauteur de l'image selon vos besoins
                    details.getChildren().add(imageView);
                    String boldTextStyle = "-fx-font-weight: bold;";

                    // Ajouter les détails de l'événement à la carte
                    details.getChildren().addAll(

                            new Text("Nom: " + event.getNomEvent()),
                            new Text("Description: " + event.getDescription()),
                            new Text("Date Début: " + event.getDateDebut().format(formatter)),
                            new Text("Date Fin: " + event.getDateFin().format(formatter)),
                            new Text("Localisation: " + event.getLocalisation())


                    );
                    details.getChildren().forEach(node -> node.setStyle(boldTextStyle));



                    // Bouton pour afficher les détails
                    Button detailsButton = new Button("Voir Détails");
                    detailsButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                    detailsButton.setOnAction(e -> afficherDetails(event));

                    // Ajouter le bouton aux détails
                    details.getChildren().add(detailsButton);

                    // Ajouter les détails à la carte
                    card.getChildren().add(details);

                    // Définir la carte comme graphique de la cellule
                    setGraphic(card);
                }
            }
        });
    }
    @FXML
    private void afficherPage(int pageNumber) {
        if (pageNumber >= 0 && pageNumber < eventPages.size()) {
            // Effacer les éléments précédents de la ListView
            eventListView.getItems().clear();

            // Ajouter les événements de la page spécifiée à la ListView
            eventListView.getItems().addAll(eventPages.get(pageNumber));
        }
    }
    @FXML
    private void updateCurrentPageLabel() {
        currentPageLabel.setText("Page " + (currentPage + 1)); // Ajoutez 1 car les pages commencent à partir de 1
    }

    @FXML
    private void nextPage() {
        if (currentPage < eventPages.size() - 1) {
            currentPage++;
            afficherPage(currentPage);
        }
    }
    @FXML
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            afficherPage(currentPage);
        }
    }
    private List<List<Events>> partition(List<Events> list, int pageSize) {
        List<List<Events>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += pageSize) {
            partitions.add(list.subList(i, Math.min(i + pageSize, list.size())));
        }
        return partitions;
    }

    // Méthode pour afficher les détails de l'événement
    private void afficherDetails(Events event) {
        try {
            // Charger la vue des détails de l'événement depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailEvent.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur associé à la vue des détails de l'événement
            DetailEventController detailController = loader.getController();

            // Passer l'événement sélectionné au contrôleur des détails de l'événement
            detailController.initData(event);

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour afficher les détails de l'événement
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Détails de l'événement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void retourAccueil() throws IOException {
        Stage stage = (Stage) retour.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    // Méthode pour rafraîchir la liste des événements
    public void refreshEventList() {
        // Effacer les éléments existants de la ListView
        eventListView.getItems().clear();

        // Recharger la liste des événements depuis la base de données
        List<Events> eventsList = eventRepo.getAllEvents();

        // Ajouter la liste mise à jour des événements à la ListView
        eventListView.getItems().addAll(eventsList);
    }
    @FXML
    private void rechercherEvenements() {
        // Effacer les éléments existants de la ListView
        eventListView.getItems().clear();

        // Récupérer le terme de recherche saisi par l'utilisateur
        String searchTerm = searchField.getText().trim().toLowerCase();

        // Récupérer tous les événements depuis la base de données
        List<Events> eventsList = eventRepo.getAllEvents();

        // Filtrer les événements en fonction du terme de recherche
        List<Events> filteredEvents = eventsList.stream()
                .filter(event -> event.getNomEvent().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        // Ajouter les événements filtrés à la ListView
        eventListView.getItems().addAll(filteredEvents);
    }

    @FXML
    private void trierParOrdreAlphabetique() {
        // Récupérer tous les événements depuis la ListView
        ObservableList<Events> events = eventListView.getItems();

        // Trier les événements par ordre alphabétique du nom
        events.sort(Comparator.comparing(Events::getNomEvent));

        // Rafraîchir la ListView avec les événements triés
        eventListView.setItems(events);
    }

}
