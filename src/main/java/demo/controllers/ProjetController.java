package demo.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import demo.model.Projet;
import demo.repository.projetRepo;
import demo.DatabaseConnection;
public class ProjetController{

    private static double y;
    private static double x;
	  public void detail(Projet projet, Stage primaryStage) {
        if (primaryStage == null) {
            System.out.println("Erreur : primaryStage est null.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(MemberController.class.getResource("../detail.fxml"));
            primaryStage.setScene(new Scene(root));
            Label nomLabel = (Label) root.lookup("#nomLabel");
            Label prenomLabel = (Label) root.lookup("#prenomLabel");
            Label emailLabel = (Label) root.lookup("#emailLabel ");
            Label fonctionLabel = (Label) root.lookup("#fonctionLabel");
            Label telephoneLabel = (Label) root.lookup("#telephoneLabel");
            
            
            Label nom = (Label) root.lookup("#nom");
            Label prenom = (Label) root.lookup("#prenom");
            Label email = (Label) root.lookup("#email");
            Label fonction = (Label) root.lookup("#fonction");
            Label telephone = (Label) root.lookup("#telephone");

            nomLabel.setText("Nom : ");
            prenomLabel.setText("Status : ");
            emailLabel.setText("From : ");
            fonctionLabel.setText(" To: " );
            telephoneLabel.setText("Description : ");

            nom.setText(projet.getNomProjet());
            prenom.setText(projet.getStatus());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateDebut = dateFormat.format(projet.getDateDebut());
            String dateFin = dateFormat.format(projet.getDateFin());
            email.setText( dateDebut);
            fonction.setText( dateFin);
            telephone.setText(projet.getDescription());
            
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
  public void modifier(Projet projet, Stage primaryStage) {
    if (primaryStage == null) {
        System.out.println("Erreur : primaryStage est null.");
        return;
    }

    try {
        Parent root = FXMLLoader.load(ProjetController.class.getResource("../updateProjet.fxml"));
        primaryStage.setScene(new Scene(root));

        Button cancel = (Button) root.lookup("#cancel");
        Button update = (Button) root.lookup("#update");

        TextField nom = (TextField) root.lookup("#nom");
        ComboBox<String> prenom = (ComboBox) root.lookup("#prenom");
        prenom.getItems().addAll("En cours", "Terminé");

        DatePicker fonction = (DatePicker) root.lookup("#fonction");
        DatePicker telephone = (DatePicker) root.lookup("#telephone");
        TextField description = (TextField) root.lookup("#description");

        nom.setText(projet.getNomProjet());
        prenom.setValue(projet.getStatus());
        fonction.setValue(projet.getDateDebut().toLocalDate());
        telephone.setValue(projet.getDateFin().toLocalDate());
        description.setText(projet.getDescription());

        Label nomErrorLabel = (Label) root.lookup("#nomErrorLabel");
        Label prenomErrorLabel = (Label) root.lookup("#prenomErrorLabel");
        Label fonctionErrorLabel = (Label) root.lookup("#fonctionErrorLabel");
        Label telephoneErrorLabel = (Label) root.lookup("#telephoneErrorLabel");
        Label descriptionErrorLabel = (Label) root.lookup("#descriptionErrorLabel");

        update.setOnAction(event -> {
            String nomVar = nom.getText();
            String status = prenom.getValue();
            LocalDate dateDebut = fonction.getValue();
            LocalDate dateFin = telephone.getValue();
            String descriptionVar = description.getText();

            // Reset error labels
            nomErrorLabel.setText("");
            prenomErrorLabel.setText("");
            fonctionErrorLabel.setText("");
            telephoneErrorLabel.setText("");
            descriptionErrorLabel.setText("");

            // Validation
            if (nomVar.isEmpty() || status == null || dateDebut == null || dateFin == null || descriptionVar.isEmpty()) {
                if (nomVar.isEmpty()) {
                    nomErrorLabel.setText("Veuillez remplir ce champ.");
                }
                if (status == null) {
                    prenomErrorLabel.setText("Veuillez sélectionner un statut.");
                }
                if (dateDebut == null) {
                    fonctionErrorLabel.setText("Veuillez sélectionner une date de début.");
                }
                if (dateFin == null) {
                    telephoneErrorLabel.setText("Veuillez sélectionner une date de fin.");
                }
                if (descriptionVar.isEmpty()) {
                    descriptionErrorLabel.setText("Veuillez remplir ce champ.");
                }
            } else {
                // Pas d'erreur, effectuer la modification du projet
                Date dateDebutParsed = java.sql.Date.valueOf(dateDebut);
                Date dateFinParsed = java.sql.Date.valueOf(dateFin);

                projetRepo.modifierProjet(projet.getId(), nomVar, dateDebutParsed, dateFinParsed, status, descriptionVar);
                primaryStage.close();
                refreshMainScene(primaryStage);
            }
        });

        cancel.setOnAction(event -> {
            primaryStage.close();
        });

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
 public void supprimer(Projet projet,Stage primaryStage) {

                
                if (primaryStage == null) {
                    System.out.println("Erreur : primaryStage est null.");
                    return;
                }

                try {
                    Parent root = FXMLLoader.load(ProjetController.class.getResource("../Settings.fxml"));
                    primaryStage.setScene(new Scene(root));
                    Label nomLabel = (Label) root.lookup("#nomLabel");
                    
                    Button cancel = (Button) root.lookup("#cancel");
                    Button delete = (Button) root.lookup("#delete");
                    nomLabel.setText("Êtes-vous sûr de vouloir supprimer ce projet ?");
                     delete.setOnAction(event -> {
                	 projetRepo.supprimerProjet(projet.getId());
                         primaryStage.close();
                         refreshMainScene(primaryStage);
                     });
                     cancel.setOnAction(event -> {
                         primaryStage.close();
                     });

                     root.setOnMousePressed(event -> {
                         x = event.getSceneX();
                         y = event.getSceneY();
                     });
                     root.setOnMouseDragged(event -> {
                         primaryStage.setX(event.getScreenX() - x);
                         primaryStage.setY(event.getScreenY() - y);
                     });
                     primaryStage.show();
                 } catch (IOException e) {
                     e.printStackTrace();
                 
         }
        
        
    }
      public void ajouter(Button ajouterButton, Stage primaryStage) {
        ajouterButton.setOnAction(e -> {
            
            if (primaryStage == null) {
                System.out.println("Erreur : primaryStage est null.");
                return;
            }

            try {
                Parent root = FXMLLoader.load(ProjetController.class.getResource("../add.fxml"));
                primaryStage.setScene(new Scene(root));
               
              
                Button cancel = (Button) root.lookup("#cancel");
                Button update = (Button) root.lookup("#update");

                TextField nom = (TextField) root.lookup("#nom");
                ComboBox<String> prenom = (ComboBox<String>) root.lookup("#prenom");
                prenom.getItems().addAll("En cours", "Terminé");
                DatePicker fonction = (DatePicker) root.lookup("#fonction");
                DatePicker telephone = (DatePicker) root.lookup("#telephone");
                TextField description = (TextField) root.lookup("#description");
                ComboBox<String> association = (ComboBox<String>) root.lookup("#association");
                
                Map<String, Integer> associationMap = new HashMap<>();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT a.id, a.nom FROM association a JOIN user u ON a.email = u.email WHERE u.is_verified = 1 AND a.status = true AND u.roles = '[\"ROLE_ASSOCIATION\"]';";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        int associationId = resultSet.getInt("id");
                        String associationName = resultSet.getString("nom");
                        association.getItems().add(associationName);
                        associationMap.put(associationName, associationId);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            
                Label nomErrorLabel = (Label) root.lookup("#nomErrorLabel");
                Label prenomErrorLabel = (Label) root.lookup("#prenomErrorLabel");
                Label fonctionErrorLabel = (Label) root.lookup("#fonctionErrorLabel");
                Label telephoneErrorLabel = (Label) root.lookup("#telephoneErrorLabel");
                Label descriptionErrorLabel = (Label) root.lookup("#descriptionErrorLabel");
                Label associationErrorLabel = (Label) root.lookup("#associationErrorLabel");
       
                update.setOnAction(event -> {
                    String nomVar = nom.getText();
                    Object status = prenom.getValue();
                    Object associationName = association.getValue();
                    LocalDate dateDebut = fonction.getValue();
                    LocalDate dateFin = telephone.getValue();
                    String descriptionVar = description.getText();
                    

                    if (nomVar.isEmpty() || dateDebut == null || dateFin == null || status == null || descriptionVar.isEmpty() || associationName == null) {
                        nomErrorLabel.setText("Veuillez remplir tous les champs.");
                        return;
                    }

                    if (nomVar.length() < 3) {
                        nomErrorLabel.setText("Le nom doit comporter au moins 3 caractères.");
                        return;
                    }

                    if (dateDebut.isAfter(dateFin)) {
                        fonctionErrorLabel.setText("La date de début doit être antérieure à la date de fin.");
                        return;
                    }

                  

                    // Traitement si tout est correct
                    int selectedAssociationId = associationMap.get(associationName);
                    Date dateDebutParsed = java.sql.Date.valueOf(dateDebut);
                    Date dateFinParsed = java.sql.Date.valueOf(dateFin);
                    projetRepo.ajouterProjet(nomVar, dateDebutParsed, dateFinParsed,(String) status, descriptionVar, selectedAssociationId);
                    primaryStage.close();
                    refreshMainScene(primaryStage);
                });
                
                cancel.setOnAction(event -> {
                    primaryStage.close();
                });

                root.setOnMousePressed(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    primaryStage.setX(event.getScreenX() - x);
                    primaryStage.setY(event.getScreenY() - y);
                });
                primaryStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    private static void refreshMainScene(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(AssociationController.class.getResource("../Home.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void modifier2(Projet projet, Stage primaryStage) {
        if (primaryStage == null) {
            System.out.println("Erreur : primaryStage est null.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(ProjetController.class.getResource("../updateProjet.fxml"));
            primaryStage.setScene(new Scene(root));

            Button cancel = (Button) root.lookup("#cancel");
            Button update = (Button) root.lookup("#update");

            TextField nom = (TextField) root.lookup("#nom");
            ComboBox<String> prenom = (ComboBox) root.lookup("#prenom");
            prenom.getItems().addAll("En cours", "Terminé");

            DatePicker fonction = (DatePicker) root.lookup("#fonction");
            DatePicker telephone = (DatePicker) root.lookup("#telephone");
            TextField description = (TextField) root.lookup("#description");

            nom.setText(projet.getNomProjet());
            prenom.setValue(projet.getStatus());
            fonction.setValue(projet.getDateDebut().toLocalDate());
            telephone.setValue(projet.getDateFin().toLocalDate());
            description.setText(projet.getDescription());

            Label nomErrorLabel = (Label) root.lookup("#nomErrorLabel");
            Label prenomErrorLabel = (Label) root.lookup("#prenomErrorLabel");
            Label fonctionErrorLabel = (Label) root.lookup("#fonctionErrorLabel");
            Label telephoneErrorLabel = (Label) root.lookup("#telephoneErrorLabel");
            Label descriptionErrorLabel = (Label) root.lookup("#descriptionErrorLabel");

            update.setOnAction(event -> {
                String nomVar = nom.getText();
                String status = prenom.getValue();
                LocalDate dateDebut = fonction.getValue();
                LocalDate dateFin = telephone.getValue();
                String descriptionVar = description.getText();

                // Reset error labels
                nomErrorLabel.setText("");
                prenomErrorLabel.setText("");
                fonctionErrorLabel.setText("");
                telephoneErrorLabel.setText("");
                descriptionErrorLabel.setText("");

                // Validation
                if (nomVar.isEmpty() || status == null || dateDebut == null || dateFin == null || descriptionVar.isEmpty()) {
                    if (nomVar.isEmpty()) {
                        nomErrorLabel.setText("Veuillez remplir ce champ.");
                    }
                    if (status == null) {
                        prenomErrorLabel.setText("Veuillez sélectionner un statut.");
                    }
                    if (dateDebut == null) {
                        fonctionErrorLabel.setText("Veuillez sélectionner une date de début.");
                    }
                    if (dateFin == null) {
                        telephoneErrorLabel.setText("Veuillez sélectionner une date de fin.");
                    }
                    if (descriptionVar.isEmpty()) {
                        descriptionErrorLabel.setText("Veuillez remplir ce champ.");
                    }
                } else {
                    // Pas d'erreur, effectuer la modification du projet
                    Date dateDebutParsed = java.sql.Date.valueOf(dateDebut);
                    Date dateFinParsed = java.sql.Date.valueOf(dateFin);

                    projetRepo.modifierProjet(projet.getId(), nomVar, dateDebutParsed, dateFinParsed, status, descriptionVar);
                    primaryStage.close();
                    refreshMainScene2(primaryStage);
                }
            });

            cancel.setOnAction(event -> {
                primaryStage.close();
            });

            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void supprimer2(Projet projet,Stage primaryStage) {


        if (primaryStage == null) {
            System.out.println("Erreur : primaryStage est null.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(ProjetController.class.getResource("../Settings.fxml"));
            primaryStage.setScene(new Scene(root));
            Label nomLabel = (Label) root.lookup("#nomLabel");

            Button cancel = (Button) root.lookup("#cancel");
            Button delete = (Button) root.lookup("#delete");
            nomLabel.setText("Êtes-vous sûr de vouloir supprimer ce projet ?");
            delete.setOnAction(event -> {
                projetRepo.supprimerProjet(projet.getId());
                primaryStage.close();
                refreshMainScene2(primaryStage);
            });
            cancel.setOnAction(event -> {
                primaryStage.close();
            });

            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            });
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }
    private static void refreshMainScene2(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(AssociationController.class.getResource("../test.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
