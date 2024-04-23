package com.example.application.controllers;

import com.example.application.model.TypeEvent;
import com.example.application.repository.TypeEventRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;



    public class AddTypeEventController {

        @FXML
        private TextField nomTextField;

        private final TypeEventRepo typeEventRepo = new TypeEventRepo();

        @FXML
        void ajouterTypeEvent(ActionEvent event) {
            // Récupérer le nom du type d'événement depuis le champ de texte
            String nom = nomTextField.getText();

            // Vérifier si le champ de texte est vide
            if (nom.isEmpty()) {
                afficherAlerte("Erreur", "Veuillez saisir un nom pour le type d'événement.");
                return;
            }

            // Créer un nouvel objet TypeEvent avec le nom récupéré
            TypeEvent typeEvent = new TypeEvent(nom);

            // Ajouter le type d'événement à la base de données
            typeEventRepo.ajouter(typeEvent);
            afficherAlerte("Succès", "Le type d'événement a été ajouté avec succès !");

            // Effacer le champ de texte après l'ajout
            nomTextField.clear();
        }

        private void afficherAlerte(String titre, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titre);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

