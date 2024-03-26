package application.controllers;

import application.DatabaseConnection;
import application.model.Association;
import application.model.PDFViewer;
import application.model.Projet;
import application.repository.associationRepo;
import application.repository.projetRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
public class HomeController {

    @FXML
    private VBox pnItems;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;
    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane gridPane1;
    @FXML
    private GridPane gridPane2;
    @FXML
    private HBox itemC1;
    
    @FXML
    private VBox pnItems1;
    @FXML
    private Label nameLabel;

    @FXML
    private Label domainLabel;

    @FXML
    private Label emailLabel;
    
    @FXML
    private Label dateLabel;
    
    @FXML
    private Button approuverButton;
    
    @FXML
    private Button desapprouverButton;
    @FXML
    private Label nameLabel1;

    @FXML
    private Label domainLabel1;

    @FXML
    private Label emailLabel1;
    
    @FXML
    private Label adresseLabel1;
    
    @FXML
    private Button supprimerButton;
    
    @FXML
    private Button modifierButton;
    
    @FXML
    private Button ajouterButton;
    
    @FXML
    public void initialize() {
        loadDemandeFromDatabase();
        btnOverview.setStyle("-fx-background-color:  #53639F");
        pnlOverview.setStyle("-fx-background-color : #02030A");
        pnlOverview.toFront();
    }

    @FXML
public void handleClicks(ActionEvent actionEvent) {
    btnCustomers.setStyle("-fx-background-color:  #05071F");
    btnMenus.setStyle("-fx-background-color:  #05071F");
    btnOverview.setStyle("-fx-background-color:  #05071F");
    btnOrders.setStyle("-fx-background-color:  #05071F");
    pnlCustomer.setStyle("-fx-background-color:  #05071F");
    pnlMenus.setStyle("-fx-background-color:  #05071F");
    pnlOverview.setStyle("-fx-background-color: #05071F");
    pnlOrders.setStyle("-fx-background-color:  #05071F");

    if (actionEvent.getSource() == btnCustomers) {
        btnCustomers.setStyle("-fx-background-color: #53639F");
        pnlCustomer.setStyle("-fx-background-color: #02030A");
        pnlCustomer.toFront();
        loadAssocFromDatabase();
    } else if (actionEvent.getSource() == btnMenus) {
        btnMenus.setStyle("-fx-background-color: #53639F");
        pnlMenus.setStyle("-fx-background-color: #02030A");
        pnlMenus.toFront();
        loadProjetFromDatabase();
    } else if (actionEvent.getSource() == btnOverview) {
        btnOverview.setStyle("-fx-background-color: #53639F");
        pnlOverview.setStyle("-fx-background-color: #02030A");
        pnlOverview.toFront();
    } else if (actionEvent.getSource() == btnOrders) {
        btnOrders.setStyle("-fx-background-color: #53639F");
        pnlOrders.setStyle("-fx-background-color: #02030A");
        pnlOrders.toFront();
    
    }
}


    	@FXML
    	private void loadDemandeFromDatabase() {
            List<Association> associations = associationRepo.getDemandes();

            int row = 0;
            for (Association association : associations) {
                Label nameLabel = new Label(association.getNom());
                Label domainLabel = new Label(association.getDomaineActivite());
                Label emailLabel = new Label(association.getEmail());
                Label dateLabel = new Label(association.getDateDemande().toString());

                nameLabel.setTextFill(Color.web("#b7c3d7")); 

                domainLabel.setTextFill(Color.web("#b7c3d7")); 

                dateLabel.setTextFill(Color.web("#b7c3d7")); 

                emailLabel.setTextFill(Color.web("#b7c3d7")); 
                Button pdfButton = new Button("PDF");
                pdfButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;"); 
                pdfButton.setOnAction(e -> {
                    try {
                        Stage stage = new Stage();
                        stage.setTitle("PDF Viewer");

                        PDFViewer pdfViewer = new PDFViewer(association.getDocument());

                        StackPane stackPane = new StackPane();
                        stackPane.getChildren().add(pdfViewer);

                        Scene scene = new Scene(stackPane, 600, 800);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                Button approuverButton = new Button("Approuver");
                approuverButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;"); 
                approuverButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Approuver la demande ?");
                    alert.setContentText("Êtes-vous sûr de vouloir approuver cette demande ?");

                    ButtonType buttonTypeOui = new ButtonType("Oui");
                    ButtonType buttonTypeNon = new ButtonType("Non");
                    alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == buttonTypeOui) {
                            associationRepo.approveAssociation(association.getId());
                            desapprouverButton.setDisable(true);
                        }
                    });
                });
                Button desapprouverButton = new Button("Désapprouver");
                desapprouverButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;");
                desapprouverButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Desapprouver la demande ?");
                    alert.setContentText("Êtes-vous sûr de vouloir desapprouver cette demande ?");

                    ButtonType buttonTypeOui = new ButtonType("Oui");
                    ButtonType buttonTypeNon = new ButtonType("Non");
                    alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == buttonTypeOui) {
                            associationRepo.disapproveAssociation(association.getId());
                            desapprouverButton.setDisable(true);
                        }
                    });
                });

                gridPane.add(nameLabel, 0, row);
                gridPane.add(domainLabel, 1, row);
                gridPane.add(emailLabel, 3, row);
                gridPane.add(dateLabel, 2, row);
                gridPane.add(pdfButton, 4, row);
                gridPane.add(approuverButton, 5, row);
                gridPane.add(desapprouverButton, 6, row);

                row++;
            }
        }
    	private void loadAssocFromDatabase() {
            List<Association> associations = associationRepo.getAssociations();

            int row = 0;
            for (Association association : associations) {
                Label nameLabel1 = new Label(association.getNom());
                Label domainLabel1 = new Label(association.getDomaineActivite());
                Label emailLabel1 = new Label(association.getEmail());
                Label adresseLabel1 = new Label(association.getAdresse());

                nameLabel1.setTextFill(Color.web("#b7c3d7")); 

                domainLabel1.setTextFill(Color.web("#b7c3d7")); 

                adresseLabel1.setTextFill(Color.web("#b7c3d7")); 

                emailLabel1.setTextFill(Color.web("#b7c3d7")); 
                
                Button detailButton = new Button("Details");
                detailButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;");
                detailButton.setOnAction(e -> {
                Label nameLabel2 = new Label("Nom: " + association.getNom());
                Label domainLabel2 = new Label("Domaine: " + association.getDomaineActivite());
                Label emailLabel2 = new Label("Email: " + association.getEmail());
                Label adresseLabel2 = new Label("Adresse: " + association.getAdresse());
                Label descriptionLabel2 = new Label("Description: " + association.getDescription());
                Label telephoneLabel2 = new Label("Téléphone: " + String.valueOf(association.getTelephone()));

                nameLabel2.setTextFill(Color.web("#b7c3d7"));
                domainLabel2.setTextFill(Color.web("#b7c3d7"));
                adresseLabel2.setTextFill(Color.web("#b7c3d7"));
                emailLabel2.setTextFill(Color.web("#b7c3d7"));
                descriptionLabel2.setTextFill(Color.web("#b7c3d7"));
                telephoneLabel2.setTextFill(Color.web("#b7c3d7"));
                    Stage stage = new Stage();
                    stage.setTitle("Details Association");
          
                    VBox vbox = new VBox(10);
                    vbox.getChildren().addAll(
                            nameLabel2,
                            domainLabel2,
                            emailLabel2,
                            adresseLabel2,
                            descriptionLabel2,
                            telephoneLabel2
                    );
                    vbox.setPadding(new Insets(10));
                    vbox.setStyle("-fx-background-color: #02030A;");
                    Scene scene = new Scene(vbox, 300, 200);
                    stage.setScene(scene);
                    stage.show();
                });
                Button modifierButton = new Button("Modifier");
                modifierButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;"); 
                modifierButton.setOnAction(e -> {
                    Stage stage = new Stage();
                    stage.setTitle("Modifier Association");

                    TextField nomField = createStyledTextField("Nom", association.getNom());
                    TextField adresseField = createStyledTextField("Adresse", association.getAdresse());
                    TextField domaineField = createStyledTextField("Domaine", association.getDomaineActivite());
                    TextField descriptionField = createStyledTextField("Description", association.getDescription());
                    TextField telephoneField = createStyledTextField("Téléphone", String.valueOf(association.getTelephone()));

                    Button updateButton = new Button("Mettre à jour");
                    updateButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;"); 
                    updateButton.setOnAction(event -> {
                        String nom = nomField.getText();
                        String adresse = adresseField.getText();
                        String domaine = domaineField.getText();
                        String description = descriptionField.getText();
                        String telephone = telephoneField.getText();
                        
               
                        associationRepo.modifierAssociation(association.getId(), nom, adresse, domaine, description, Integer.parseInt(telephone));

                        stage.close();
                    });

                    HBox buttonContainer = new HBox(updateButton);
                    buttonContainer.setAlignment(Pos.CENTER_RIGHT);

                    VBox vbox = new VBox(10); 
                    vbox.getChildren().addAll(
                        createLabeledTextField("Nom", nomField),
                        createLabeledTextField("Adresse", adresseField),
                        createLabeledTextField("Domaine", domaineField),
                        createLabeledTextField("Description", descriptionField),
                        createLabeledTextField("Téléphone", telephoneField),
                        buttonContainer 
                    );
                    vbox.setPadding(new Insets(10)); 
                    vbox.setStyle("-fx-background-color: #02030A;"); 
                    Scene scene = new Scene(vbox, 300, 200);
                    stage.setScene(scene);
                    stage.show();
                });

             

                Button supprimerButton = new Button("Supprimer");
                supprimerButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;");
                supprimerButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Supprimer l'association ?");
                    alert.setContentText("Êtes-vous sûr de vouloir supprimer cette association ?");

                    ButtonType buttonTypeOui = new ButtonType("Oui");
                    ButtonType buttonTypeNon = new ButtonType("Non");
                    alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == buttonTypeOui) {
                            associationRepo.supprimerAssociation(association.getId());
                        }
                    });
                });

                gridPane1.add(nameLabel1, 0, row);
                gridPane1.add(domainLabel1, 1, row);
                gridPane1.add(emailLabel1, 3, row);
                gridPane1.add(adresseLabel1, 2, row);
                gridPane1.add(detailButton, 4, row);
                gridPane1.add(modifierButton, 5, row);
                gridPane1.add(supprimerButton, 6, row);

                row++;
            }
        }
    	private void loadProjetFromDatabase() {
            List<Projet> projets = projetRepo.getProjets();

            int row = 0;
            for (Projet projet : projets) {
                Label nomProjetLabel1 = new Label(projet.getNomProjet());
                Label statusLabel1 = new Label(projet.getStatus());
                Label dateDebutLabel1 = new Label(projet.getDateDebut().toString());
                Label dateFinLabel1 = new Label(projet.getDateFin().toString());
                Label descriptionLabel1 = new Label(projet.getDescription());

                nomProjetLabel1.setTextFill(Color.web("#b7c3d7")); 

                statusLabel1.setTextFill(Color.web("#b7c3d7")); 

                dateDebutLabel1.setTextFill(Color.web("#b7c3d7")); 

                dateFinLabel1.setTextFill(Color.web("#b7c3d7")); 
                
                Button detailButton = new Button("Details");
                detailButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;");
                detailButton.setOnAction(e -> {
                Label nomProjetLabel2 = new Label("Nom: " + projet.getNomProjet());
                Label statusLabel2 = new Label("Status: " + projet.getStatus());
                Label dateDebutLabel2 = new Label("DateDebut: " + projet.getDateDebut());
                Label dateFinLabel2 = new Label("DateFin: " + projet.getDateFin());
                Label descriptionLabel2 = new Label("Description: " + projet.getDescription());

                nomProjetLabel2.setTextFill(Color.web("#b7c3d7"));
                statusLabel2.setTextFill(Color.web("#b7c3d7"));
                dateFinLabel2.setTextFill(Color.web("#b7c3d7"));
                dateDebutLabel2.setTextFill(Color.web("#b7c3d7"));
                descriptionLabel2.setTextFill(Color.web("#b7c3d7"));
                    Stage stage = new Stage();
                    stage.setTitle("Details projet");
          
                    VBox vbox = new VBox(10);
                    vbox.getChildren().addAll(
                            nomProjetLabel2,
                            statusLabel2,
                            dateDebutLabel2,
                            dateFinLabel2,
                            descriptionLabel2
                    );
                    vbox.setPadding(new Insets(10));
                    vbox.setStyle("-fx-background-color: #02030A;");
                    Scene scene = new Scene(vbox, 300, 200);
                    stage.setScene(scene);
                    stage.show();
                });
                Button modifierButton = new Button("Modifier");
                modifierButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;"); 
                modifierButton.setOnAction(e -> {
                    Stage stage = new Stage();
                    stage.setTitle("Modifier projet");

                    String nomProjet = projet.getNomProjet();
                    LocalDate dateDebutActuelle = projet.getDateDebut().toLocalDate();
                    LocalDate dateFinActuelle = projet.getDateFin().toLocalDate();
                    String statusActuel = projet.getStatus();
                    String descriptionActuelle = projet.getDescription();

                    TextField nomField = createStyledTextField("Nom", nomProjet);
                    DatePicker dateDebutPicker = createStyledDatePicker("Sélectionnez la date de début", dateDebutActuelle);
                    DatePicker dateFinPicker = createStyledDatePicker("Sélectionnez la date de fin", dateFinActuelle);
                    ComboBox<String> statusComboBox = new ComboBox<>();
                    statusComboBox.getItems().addAll("En cours", "Terminé");
                    statusComboBox.setValue(statusActuel); 
                    TextField descriptionField = createStyledTextField("Description", descriptionActuelle);

                    Button updateButton = new Button("Mettre à jour");
                    updateButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;");
                    updateButton.setOnAction(event -> {
                        String nom = nomField.getText();
                        LocalDate dateDebut = dateDebutPicker.getValue();
                        LocalDate dateFin = dateFinPicker.getValue();
                        String status = statusComboBox.getValue();
                        String description = descriptionField.getText();

                        if (dateDebut != null && dateFin != null) {
                            // Conversion de LocalDate en Date
                            Date dateDebutParsed = java.sql.Date.valueOf(dateDebut);
                            Date dateFinParsed = java.sql.Date.valueOf(dateFin);

                            projetRepo.modifierProjet(projet.getId(), nom, dateDebutParsed, dateFinParsed, status, description);

                            stage.close();
                        } else {
                            System.out.println("Veuillez sélectionner une date de début et une date de fin.");
                        }
                    });

                    HBox buttonContainer = new HBox(updateButton);
                    buttonContainer.setAlignment(Pos.CENTER_RIGHT);

                    VBox vbox = new VBox(10);
                    vbox.getChildren().addAll(
                            createLabeledTextField("Nom", nomField),
                            createLabeledDatePicker("Date de début", dateDebutPicker),
                            createLabeledDatePicker("Date de fin", dateFinPicker),
                            createLabeledComboBox("Statut", statusComboBox),
                            createLabeledTextField("Description", descriptionField),
                            buttonContainer
                    );
                    vbox.setPadding(new Insets(10));
                    vbox.setStyle("-fx-background-color: #02030A;");
                    Scene scene = new Scene(vbox, 300, 250);
                    stage.setScene(scene);
                    stage.show();
                });


             

                Button supprimerButton = new Button("Supprimer");
                supprimerButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;");
                supprimerButton.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Supprimer l'projet ?");
                    alert.setContentText("Êtes-vous sûr de vouloir supprimer cette projet ?");

                    ButtonType buttonTypeOui = new ButtonType("Oui");
                    ButtonType buttonTypeNon = new ButtonType("Non");
                    alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == buttonTypeOui) {
                            projetRepo.supprimerProjet(projet.getId());
                            
                        }
                    });
                });

                gridPane2.add(nomProjetLabel1, 0, row);
                gridPane2.add(statusLabel1, 1, row);
                gridPane2.add(dateFinLabel1, 3, row);
                gridPane2.add(dateDebutLabel1, 2, row);
                gridPane2.add(detailButton, 4, row);
                gridPane2.add(modifierButton, 5, row);
                gridPane2.add(supprimerButton, 6, row);

                row++;
            }

            ajouterButton.setOnAction(e -> {
                Stage stage = new Stage();
                stage.setTitle("Ajouter projet");

                TextField nomField = createStyledTextField("Nom");
                DatePicker dateDebutPicker = createStyledDatePicker("Sélectionnez la date de début");
                DatePicker dateFinPicker = createStyledDatePicker("Sélectionnez la date de fin");
                
                ComboBox<String> statusComboBox = new ComboBox<>(); 
                statusComboBox.getItems().addAll("En cours", "Terminé"); 
                statusComboBox.setPromptText("Sélectionnez le statut"); 
                
                TextField descriptionField = createStyledTextField("Description");
                ComboBox<String> assocComboBox = new ComboBox<>(); 
                assocComboBox.setPromptText("Sélectionnez l'association"); 

                Map<String, Integer> associationMap = new HashMap<>();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT id, nom FROM association";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        int associationId = resultSet.getInt("id");
                        String associationName = resultSet.getString("nom");
                        assocComboBox.getItems().add(associationName); 
                        associationMap.put(associationName, associationId); 
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                Label nomErrorLabel = new Label();
                Label dateDebutErrorLabel = new Label();
                Label dateFinErrorLabel = new Label();
                Label statusErrorLabel = new Label();
                Label assocErrorLabel = new Label();
                Label descriptionErrorLabel = new Label();
                String errorStyle = "-fx-text-fill: red;";

                nomErrorLabel.setStyle(errorStyle);
                dateDebutErrorLabel.setStyle(errorStyle);
                dateFinErrorLabel.setStyle(errorStyle);
                statusErrorLabel.setStyle(errorStyle);
                assocErrorLabel.setStyle(errorStyle);
                descriptionErrorLabel.setStyle(errorStyle);
                Button addButton = new Button("Ajouter nouveau");
                addButton.setStyle("-fx-border-color: #2A73FF; -fx-border-radius: 20; -fx-background-color: transparent;"); 
                addButton.setOnAction(event -> {
                    // Réinitialisez les étiquettes d'erreur à chaque fois que le bouton est cliqué
                    nomErrorLabel.setText("");
                    dateDebutErrorLabel.setText("");
                    dateFinErrorLabel.setText("");
                    statusErrorLabel.setText("");
                    assocErrorLabel.setText("");
                    descriptionErrorLabel.setText("");

                    // Récupérez les valeurs des champs
                    String nom = nomField.getText();
                    LocalDate dateDebut = dateDebutPicker.getValue();
                    LocalDate dateFin = dateFinPicker.getValue();
                    String status = statusComboBox.getValue(); 
                    String associationName = assocComboBox.getValue();
                    String description = descriptionField.getText();
                    
                    if (nom.isEmpty() || dateDebut == null || dateFin == null || status == null || associationName == null || description.isEmpty()) {
                        nomErrorLabel.setText("Le nom est requis");
                        dateDebutErrorLabel.setText("La date de début est requise");
                        dateFinErrorLabel.setText("La date de fin est requise");
                        statusErrorLabel.setText("Le statut est requis");
                        assocErrorLabel.setText("L'association est requise");
                        descriptionErrorLabel.setText("La description est requise");
                        return; 
                    }
                    
                    if (dateDebut.isAfter(dateFin)) {
                        dateDebutErrorLabel.setText("La date de début doit être antérieure à la date de fin.");
                        return; 
                    }
                    
                    if (nom.length() > 10) {
                        nomErrorLabel.setText("Le nom du projet ne doit pas dépasser 10 caractères.");
                        return; 
                    }
                    Date dateDebutParsed = java.sql.Date.valueOf(dateDebut);
                    Date dateFinParsed = java.sql.Date.valueOf(dateFin);
                    int selectedAssociationId = associationMap.get(associationName);
                    
                    projetRepo.ajouterProjet(nom, dateDebutParsed, dateFinParsed, status, description, selectedAssociationId);
                    
                    stage.close();
                });


                VBox vbox = new VBox(10); 
                vbox.getChildren().addAll(
                    createLabeledTextField("Nom", nomField),
                    nomErrorLabel,
                    createLabeledDatePicker("Date de début", dateDebutPicker),
                    dateDebutErrorLabel,
                    createLabeledDatePicker("Date de fin", dateFinPicker),
                    dateFinErrorLabel,
                    createLabeledComboBox("Statut", statusComboBox),
                    statusErrorLabel,
                    createLabeledComboBox("Association", assocComboBox),
                    assocErrorLabel,
                    createLabeledTextField("Description", descriptionField),
                    descriptionErrorLabel,
                    addButton 
                );

                vbox.setPadding(new Insets(10)); 
                vbox.setStyle("-fx-background-color: #02030A;"); 
                Scene scene = new Scene(vbox, 300, 250); 
                stage.setScene(scene);
                stage.show();
            });


}
    	
    	private TextField createStyledTextField(String promptText) {
    	    TextField textField = new TextField();
    	    textField.setPromptText(promptText);
    	    textField.setPrefWidth(200); 
    	    textField.setPadding(new Insets(5));
    	    return textField;
    	}
    
    	private TextField createStyledTextField(String promptText, String defaultValue) {
    	    TextField textField = createStyledTextField(promptText);
    	    textField.setText(defaultValue); 
    	    return textField;
    	}
    	private HBox createLabeledTextField(String labelText, TextField textField) {
    	    Label label = new Label(labelText);
    	    HBox hbox = new HBox(10, label, textField); 
    	    return hbox;
    	}
    	private HBox createLabeledComboBox(String labelText, ComboBox<String> comboBox) {
    	    Label label = new Label(labelText);
    	    label.setTextFill(Color.WHITE);
    	    HBox hbox = new HBox(10); 
    	    hbox.getChildren().addAll(label, comboBox);
    	    return hbox;
    	}

    	private DatePicker createStyledDatePicker(String promptText) {
    	    DatePicker datePicker = new DatePicker();
    	    datePicker.setPromptText(promptText);
    	    datePicker.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-pref-width: 200;");
    	    return datePicker;
    	}
    	private HBox createLabeledDatePicker(String labelText, DatePicker datePicker) {
    	    Label label = new Label(labelText);
    	    label.setTextFill(Color.WHITE);
    	    HBox hbox = new HBox(10); 
    	    hbox.getChildren().addAll(label, datePicker);
    	    return hbox;
    	}
    	private DatePicker createStyledDatePicker(String promptText, LocalDate initialDate) {
    	    DatePicker datePicker = new DatePicker();
    	    datePicker.setPromptText(promptText);
    	    datePicker.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-pref-width: 200;");

    	    // Définir la date initiale si elle est fournie
    	    if (initialDate != null) {
    	        datePicker.setValue(initialDate);
    	    }

    	    return datePicker;
    	}

}
