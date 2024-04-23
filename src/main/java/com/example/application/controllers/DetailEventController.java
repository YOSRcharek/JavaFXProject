package com.example.application.controllers;
import com.example.application.model.Events;
import com.example.application.model.TypeEvent;
import com.example.application.repository.EventRepo;
import com.example.application.controllers.EventFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DetailEventController {

    @FXML
    private Label nomLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateDebutLabel;

    @FXML
    private Label dateFinLabel;

    @FXML
    private Label localisationLabel;

    @FXML
    private Label capaciteMaxLabel;
    @FXML
    private Label capaciteActuelleLabel;
    @FXML
    private Label typeLabel;


    @FXML
    private Label imageLabel;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;
    private Events event;

    private EventController eventController;
    public void setEventController(EventController eventController) {
        this.eventController = eventController;
    }


    public void updateCapaciteActuelle(int capaciteActuelle) {
        capaciteActuelleLabel.setText(Integer.toString(capaciteActuelle));
    }

    // Méthode pour initialiser les données de l'événement dans la vue des détails de l'événement
    public void initData(Events event) {
        this.event = event;
        // Initialiser les éléments de l'interface utilisateur avec les données de l'événement
        nomLabel.setText(event.getNomEvent());
        descriptionLabel.setText(event.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dateDebutLabel.setText(event.getDateDebut().format(formatter));
        dateFinLabel.setText(event.getDateFin().format(formatter));
        localisationLabel.setText(event.getLocalisation());
        capaciteMaxLabel.setText(Integer.toString(event.getCapaciteMax()));
        updateCapaciteActuelle(event.getCapaciteActuelle());
        TypeEvent typeEvent = event.getTypeEvent();
        if (typeEvent != null) {
            typeLabel.setText(typeEvent.getNom());
        } else {
            typeLabel.setText("Type d'événement inconnu");
        }

        System.out.println("event id"+event.getId());
        System.out.println("capa actu"+event.getCapaciteActuelle());

        imageLabel.setText(event.getImage());
    }
    @FXML
    private void participer() {
        EventRepo eventRepo = new EventRepo(); // Instancier EventRepo (assurez-vous que vous avez une référence à votre classe EventRepo)
        eventRepo.participer(event); // Appeler la méthode participer avec l'objet Events en paramètre

        // Afficher un message à l'utilisateur pour indiquer qu'il a participé à l'événement
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation de participation");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez participé à l'événement !");
        alert.showAndWait();

        // Mettre à jour l'affichage de la capacité actuelle si nécessaire
        initData(event);

    }



    @FXML
    private void supprimerEvenement() {
        int eventId = this.event.getId(); // Récupérer l'ID de l'événement
        System.out.println("id" + eventId);
        EventRepo eventRepo = new EventRepo(); // Instancier EventRepo (assurez-vous que vous avez une référence à votre classe EventRepo)
        eventRepo.supprimer(eventId); // Appeler la méthode supprimer avec l'ID de l'événement

        // Rafraîchir l'affichage de la liste des événements après la suppression
        if (eventController != null) {
            eventController.refreshEventList();
        }

        Stage currentStage = (Stage) supprimerButton.getScene().getWindow(); // Fermer la fenêtre des détails de l'événement
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/events.fxml"));
            Parent root = loader.load();
            EventController eventsController = loader.getController();
            eventsController.refreshEventList();
            Scene scene = new Scene(root);

            // Get the current stage (window)


            // Set the scene for the current stage
            currentStage.setScene(scene);

            // Show the new view
            currentStage.show();
        }catch (Exception e) {
            // Afficher un message d'erreur à l'utilisateur

            e.printStackTrace();
        }
    }

    @FXML
    private void modifierEvenement() {
        try {
            // Charger le fichier FXML de la fenêtre de modification d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyEventView.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre de modification d'événement
            ModifyEventController modifyEventController = loader.getController();

            // Initialiser les données de l'événement à modifier dans le contrôleur de la fenêtre de modification
            modifyEventController.initData(event);

            // Créer une nouvelle scène avec le contenu du fichier FXML de modification
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir de n'importe quel élément de l'interface utilisateur
            Stage stage = (Stage) modifierButton.getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
