package com.example.application.controllers;

import com.example.application.model.Events;
import com.example.application.model.TypeEvent;
import com.example.application.repository.EventRepo;
import com.example.application.repository.TypeEventRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventFormController {

    @FXML
    private TextField nomEventField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField localisationField;

    @FXML
    private Spinner<Integer> capaciteMaxSpinner;


    @FXML
    private TextField imageField;
    @FXML
    private ComboBox<TypeEvent> typeEventComboBox;

    private final EventRepo eventRepo = new EventRepo();
    private final TypeEventRepo typeEventRepo = new TypeEventRepo();
    @FXML
    void initialize() {
        // Configurer la SpinnerValueFactory pour le Spinner capaciteMaxSpinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        capaciteMaxSpinner.setValueFactory(valueFactory);
        chargerTypesEvenements();

    }
    private void chargerTypesEvenements() {
        // Récupérer tous les types d'événements depuis le TypeEventRepo
        List<TypeEvent> typeEvents = typeEventRepo.getAllTypeEvents();
        List<String> typeEventNames = new ArrayList<>();
        for (TypeEvent typeEvent : typeEvents) {
            typeEventNames.add(typeEvent.getNom());
        }


        // Convertir la liste en une liste observable pour l'ComboBox
        ObservableList<TypeEvent> observableTypeEvents = FXCollections.observableArrayList(typeEvents);

        // Ajouter les types d'événements à l'ComboBox
        typeEventComboBox.setItems(observableTypeEvents);
    }

    @FXML
    void saveEvent(ActionEvent event) {
        // Récupérer les valeurs des champs
        String nomEvent = nomEventField.getText();
        String description = descriptionField.getText();
        String localisation = localisationField.getText();
        String image = imageField.getText();
        int capaciteMax = capaciteMaxSpinner.getValue();

        // Récupérer les valeurs des DatePicker
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        // Vérifier que les champs obligatoires sont remplis
        if (nomEvent.isEmpty() || description.isEmpty() || localisation.isEmpty() || image.isEmpty() || dateDebut == null || dateFin == null) {
            afficherAlerte("Erreur de saisie", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Vérifier que la date de début est antérieure à la date de fin
        if (dateDebut.isAfter(dateFin)) {
            afficherAlerte("Erreur de saisie", "La date de début doit être antérieure à la date de fin.");
            return;
        }

        // Vérification des valeurs de capacité
        if (capaciteMax <= 0) {
            afficherAlerte("Erreur de saisie", "La capacité maximale doit être supérieure à zéro.");
            return;
        }




        // Vérifier que les dates ne sont pas nulles
        if (dateDebut != null && dateFin != null) {
            // Récupérer le type d'événement sélectionné
            TypeEvent selectedTypeEvent = typeEventComboBox.getValue();

            // Créer un nouvel événement avec le type d'événement sélectionné
            Events newEvent = new Events(nomEvent, description, dateDebut, dateFin, localisation, capaciteMax, image);
            newEvent.setTypeEvent(selectedTypeEvent); // Définir le type d'événement pour le nouvel événement

            // Ajouter l'événement à la base de données
            eventRepo.ajouter(newEvent);

            // Réinitialiser les champs après avoir enregistré l'événement
            clearFields();
        } else {
            // Gérer le cas où dateDebut ou dateFin est null
            System.err.println("Erreur : dateDebut ou dateFin est null");
            // Afficher un message d'erreur à l'utilisateur ou effectuer une autre action appropriée
        }
    }

    private void clearFields() {
        // Effacer le contenu des champs de texte
        nomEventField.clear();
        descriptionField.clear();
        localisationField.clear();
        imageField.clear();

        // Réinitialiser les valeurs des spinners
        capaciteMaxSpinner.getValueFactory().setValue(0);

        // Réinitialiser les DatePicker
        dateDebutPicker.setValue(null);
        dateFinPicker.setValue(null);
    }
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
