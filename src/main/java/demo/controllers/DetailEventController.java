package com.example.application.controllers;
import com.example.application.model.Events;
import com.example.application.model.TypeEvent;
import com.example.application.repository.EventRepo;
import com.example.application.controllers.EventFormController;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;



public class DetailEventController {
    @FXML
    public Button pdfButton;
    @FXML
    private WebView mapWebView;


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
        float latitude = event.getLatitude();
        float longitude = event.getLongitude();

        // Charger la carte dans le WebView
        loadMap(latitude, longitude);
    }
    private void loadMap(float latitude, float longitude) {
        WebEngine webEngine = mapWebView.getEngine();
        String html = "<html>"
                + "<head>"
                + "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet/dist/leaflet.css\" />"
                + "<script src=\"https://unpkg.com/leaflet/dist/leaflet.js\"></script>"
                + "</head>"
                + "<body>"
                + "<div id=\"map\" style=\"height: 400px;\"></div>"
                + "<script>"
                + "var map = L.map('map').setView([" + latitude + ", " + longitude + "], 13);"
                + "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "attribution: '© OpenStreetMap contributors'"
                + "}).addTo(map);"
                + "L.marker([" + latitude + ", " + longitude + "]).addTo(map)"
                + ".bindPopup('Your event').openPopup();"
                + "</script>"
                + "</body>"
                + "</html>";
        webEngine.loadContent(html);
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
  /*  @FXML
    private void shareOnFacebook() {
        // Configuration de Facebook4J avec les informations d'identification de l'application Facebook
        String appId = "352106534085211";
        String appSecret = "03e1fea24c26dcb382a100669a445f85";
        String accessToken = "EAAFAPTDjIlsBOy1MbLZAHPqUWT4ELUjrSEb0MEJkXGm2wvykJ5mIbjAsEZASfaZCFJQSZA801vapsfSZASBEtEkZB851ZBwrGNHUFwc1iBwhFZACBo64e5CKUmi1iS2V3n0CcIDtd3fEZBNC2p8QiSyfXOwnsnpEMq0ZAxWLJWgACpe2pmwKn7gxdVKUWjdCOgNC9bo7WjM4WopA4xkfI4oBwdezsp0mr1iWJ1n8znCfklarVHaojcT1nS4f2rZAIfitVbZA6dSWwWcWxpQZD";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessToken, null));

        // Message à partager sur Facebook
        String message = "Votre message à partager sur Facebook.";

        try {
            // Poster le message sur Facebook
            facebook.postStatusMessage(message);

            // Afficher un message de confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Partage sur Facebook");
            alert.setHeaderText(null);
            alert.setContentText("Le message a été partagé sur Facebook avec succès !");
            alert.showAndWait();
        } catch (FacebookException e) {
            e.printStackTrace();

            // En cas d'erreur, afficher une alerte à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de partage");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du partage sur Facebook.");
            alert.showAndWait();
        }
    }*/

    @FXML
    public void generateAndDownloadPDF(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Document document = new Document();
        try {
            // Créer une boîte de dialogue pour sélectionner l'emplacement de sauvegarde
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                // Créer le fichier PDF avec l'emplacement sélectionné
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Ajouter les détails de l'événement au document PDF
                document.add(new Paragraph("Détails de l'événement"));

                // Ajout du nom de l'événement
                document.add(new Paragraph("Nom de l'événement : " + nomLabel.getText()));

                // Ajout de la description de l'événement
                document.add(new Paragraph("Description de l'événement : " + descriptionLabel.getText()));

                // Ajout de la date de début de l'événement
                document.add(new Paragraph("Date de début de l'événement : " + dateDebutLabel.getText()));

                // Ajout de la date de fin de l'événement
                document.add(new Paragraph("Date de fin de l'événement : " + dateFinLabel.getText()));

                // Ajout de la localisation de l'événement
                document.add(new Paragraph("Localisation de l'événement : " + localisationLabel.getText()));

                // Ajout de la capacité maximale de l'événement
                document.add(new Paragraph("Capacité maximale de l'événement : " + capaciteMaxLabel.getText()));

                // Ajout de la capacité actuelle de l'événement
                document.add(new Paragraph("Capacité actuelle de l'événement : " + capaciteActuelleLabel.getText()));

                // Ajout du type d'événement
                document.add(new Paragraph("Type d'événement : " + typeLabel.getText()));
                // Ajoutez d'autres détails de l'événement ici...

                document.close();

                // Afficher un message de succès
                System.out.println("Le fichier PDF a été enregistré avec succès à l'emplacement : " + file.getAbsolutePath());
            } else {
                System.out.println("Aucun emplacement sélectionné pour enregistrer le fichier PDF.");
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }



}
