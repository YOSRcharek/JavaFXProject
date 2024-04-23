package com.example.application.controllers;

import com.example.application.model.Events;
import com.example.application.repository.EventRepo;
import com.example.application.repository.TypeEventRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventController {
@FXML
    public Button retour;
    @FXML
    private ListView<Events> eventListView;

    private EventRepo eventRepo;

    @FXML
    public void initialize() {
        // Initialiser EventRepo
        eventRepo = new EventRepo();

        // Charger et afficher tous les événements au démarrage de la vue
        chargerTousLesEvenements();
    }

    // Méthode pour charger et afficher tous les événements
    private void chargerTousLesEvenements() {
        // Récupérer tous les événements depuis la base de données
        List<Events> eventsList = eventRepo.getAllEvents();

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
                    card.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #CCCCCC; -fx-border-radius: 5;");
                    card.setPrefSize(300, 150);

                    // Conteneur pour les détails de l'événement
                    VBox details = new VBox();
                    details.setPadding(new Insets(10));
                    details.setSpacing(5);

                    // Formatter pour la date
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    // Ajouter les détails de l'événement à la carte
                    details.getChildren().addAll(
                            new Text("Nom: " + event.getNomEvent()),
                            new Text("Description: " + event.getDescription()),
                            new Text("Date Début: " + event.getDateDebut().format(formatter)),
                            new Text("Date Fin: " + event.getDateFin().format(formatter)),
                            new Text("Localisation: " + event.getLocalisation()),
                            new Text("Capacité Max: " + event.getCapaciteMax()),
                            new Text("Capacité Actuelle: " + event.getCapaciteActuelle()),
                            new Text("Type d'événement: " + event.getTypeEvent().getNom()),
                            new Text("Image: " + event.getImage())
                    );

                    // Bouton pour afficher les détails
                    Button detailsButton = new Button("Voir Détails");
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
}
