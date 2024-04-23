    package com.example.application.controllers;

    import com.example.application.model.Events;
    import com.example.application.model.TypeEvent;
    import com.example.application.repository.EventRepo;
    import com.example.application.repository.TypeEventRepo;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.stage.Stage;
    import javafx.stage.Window;

    import java.io.IOException;
    import java.sql.Date;

    import java.time.LocalDate;
    import java.time.ZoneId;
    import java.util.List;

    public class ModifyEventController {

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
        private TextField latitudeField;

        @FXML
        private TextField longitudeField;
        @FXML
        private ComboBox<TypeEvent> typeEventComboBox;

        @FXML
        private TextField imageField;


        @FXML
        private Button modifierButton;
        private final EventRepo eventRepo = new EventRepo();
        private final TypeEventRepo typeEventRepo= new TypeEventRepo();
        private final EventController eventController = new EventController();
        private Events eventToModify;

        public void setEventToModify(Events eventToModify) {
            this.eventToModify = eventToModify;
        }



        public void initData(Events event) {
            if (event != null) {
                this.eventToModify = event;
                nomEventField.setText(event.getNomEvent());
                descriptionField.setText(event.getDescription());
                localisationField.setText(event.getLocalisation());
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, event.getCapaciteMax());
                capaciteMaxSpinner.setValueFactory(valueFactory);
                latitudeField.setText(String.valueOf(event.getLatitude()));
                longitudeField.setText(String.valueOf(event.getLongitude()));

                imageField.setText(event.getImage());

                // Convertir les dates JavaFX en Date SQL
                // Convertir les dates JavaFX en Date SQL
                dateDebutPicker.setValue(event.getDateDebut());
                dateFinPicker.setValue(event.getDateFin());
                List<TypeEvent> typeEvents = typeEventRepo.getAllTypeEvents();
                typeEventComboBox.getItems().addAll(typeEvents);
                typeEventComboBox.setValue(event.getTypeEvent());



            }
        }

        @FXML
        void saveModifiedEvent(ActionEvent event) {


                try {
                    // Récupérer les valeurs des champs
                    String nomEvent = nomEventField.getText();
                    String description = descriptionField.getText();
                    LocalDate dateDebut = dateDebutPicker.getValue();
                    LocalDate dateFin = dateFinPicker.getValue();
                    String localisation = localisationField.getText();
                    String image = imageField.getText();
                    int capaciteMax = capaciteMaxSpinner.getValue();
                    float latitude = Float.parseFloat(latitudeField.getText());
                    float longitude = Float.parseFloat(longitudeField.getText());

                    // Vérifier les données
                    if (nomEvent.isEmpty() || description.isEmpty() || dateDebut == null || dateFin == null || localisation.isEmpty() || image.isEmpty()) {
                        throw new IllegalArgumentException("Tous les champs doivent être remplis.");
                    }
                    if (dateDebut.isAfter(dateFin)) {
                        throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
                    }
                    TypeEvent selectedTypeEvent = typeEventComboBox.getValue();

                    // Mettre à jour les données de l'événement
                   // eventToModify.setId();
                   eventToModify.setNomEvent(nomEvent);
                    eventToModify.setDescription(description);
                    eventToModify.setDateDebut(dateDebut);
                    eventToModify.setDateFin(dateFin);
                    eventToModify.setLocalisation(localisation);
                    eventToModify.setCapaciteMax(capaciteMax);
                    eventToModify.setImage(image);
                    eventToModify.setLatitude(latitude);
                    eventToModify.setLongitude(longitude);
                    eventToModify.setTypeEvent(selectedTypeEvent);
                    System.out.println(eventToModify);
                    // Appeler la méthode de modification dans le repository
                    eventRepo.modifier(eventToModify);

                    // Rafraîchir la liste des événements
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/events.fxml"));
                    Parent root = loader.load();
                    EventController eventsController = loader.getController();
                    eventsController.refreshEventList();
                    Scene scene = new Scene(root);

                    // Get the current stage (window)
                    Stage currentStage = (Stage) modifierButton.getScene().getWindow();

                    // Set the scene for the current stage
                    currentStage.setScene(scene);

                    // Show the new view
                    currentStage.show();
                    // Fermer la fenêtre de modification
                    closeWindow();
                } catch (Exception e) {
                    // Afficher un message d'erreur à l'utilisateur

                    e.printStackTrace();
                }

        }


        @FXML
        void cancelModification(ActionEvent event) {
            // Fermer la fenêtre de modification sans sauvegarder les modifications
            closeWindow();
        }

        private void closeWindow() {
            // Obtenir la scène actuelle
            Scene scene = nomEventField.getScene();
            if (scene != null) {
                // Obtenir la fenêtre associée à la scène
                Window window = scene.getWindow();
                if (window != null) {
                    // Fermer la fenêtre
                    window.hide();
                }
            }
        }

    }
