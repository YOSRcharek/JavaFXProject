package demo.controllers;


	import demo.DatabaseConnection;
    import javafx.scene.control.Button;
    import javafx.scene.control.ComboBox;
	import javafx.scene.control.Label;
	import javafx.scene.control.TextField;
    import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
    import javafx.scene.Parent;
import javafx.scene.Scene;
	import javafx.scene.paint.Color;
	import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.HashMap;
	import java.util.Map;

import javafx.fxml.FXMLLoader;
    import demo.model.Membre;
    import demo.repository.memberRepo;


public class MemberController {
	    private static double x;
	    private static double y;
	    public static void detail(Membre membre, Stage primaryStage) {
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
	            prenomLabel.setText("Prenom : ");
	            emailLabel.setText("Email : ");
	            fonctionLabel.setText("Fonction : " );
	            telephoneLabel.setText("Telephone : ");

	            nom.setText( membre.getNomMembre());
	            prenom.setText( membre.getPrenomMembre());
	            email.setText(membre.getEmailMembre());
	            fonction.setText( membre.getFonction());
	            telephone.setText( membre.getTelephone());
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


	   public static void modifier(Membre membre, Stage primaryStage) {

        if (primaryStage == null) {
            System.out.println("Erreur : primaryStage est null.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(MemberController.class.getResource("../update.fxml"));
            primaryStage.setScene(new Scene(root));
            Label nomLabel = (Label) root.lookup("#nomLabel");
            Label prenomLabel = (Label) root.lookup("#prenomLabel");
            Label fonctionLabel = (Label) root.lookup("#fonctionLabel");

            Label telephoneLabel = (Label) root.lookup("#telephoneLabel");
            Label emailLabel = (Label) root.lookup("#descriptionLabel");
            Button cancel = (Button) root.lookup("#cancel");
            Button update = (Button) root.lookup("#update");

            Pane Pane = (Pane) root.lookup("#Pane");

            TextField nom = (TextField) root.lookup("#nom");
            TextField prenom = (TextField) root.lookup("#prenom");
            ComboBox<String> fonction = (ComboBox<String>) root.lookup("#fonction");

            fonction.getItems().addAll("Volontaire", "Assistant","Coordinateur des événements","Traducteur","Gestionnaire des bénévoles");


            TextField telephone = (TextField) root.lookup("#telephone");
            TextField email = (TextField) root.lookup("#description");

            nomLabel.setText("Nom : ");
            prenomLabel.setText("Prenom : ");
            fonctionLabel.setText("Fonction : ");
            telephoneLabel.setText("Telephone : ");
            emailLabel.setText("email : ");

            nom.setText(membre.getNomMembre());
            prenom.setText(membre.getPrenomMembre());
            telephone.setText(membre.getTelephone());
            email.setText(membre.getEmailMembre());
            fonction.setValue(membre.getFonction());

            Label nomErrorLabel = (Label) root.lookup("#nomErrorLabel");
            Label prenomErrorLabel = (Label) root.lookup("#prenomErrorLabel");
            Label fonctionErrorLabel = (Label) root.lookup("#fonctionErrorLabel");
            Label telephoneErrorLabel = (Label) root.lookup("#telephoneErrorLabel");
            Label emailErrorLabel = (Label) root.lookup("#descriptionErrorLabel");

            update.setOnAction(event -> {
                String nomVar = nom.getText();
                String prenomVar = prenom.getText();
                String fonctionVar = fonction.getValue();
                String emailVar = email.getText();
                String telephoneVar = telephone.getText();

                nomErrorLabel.setText("");
                prenomErrorLabel.setText("");
                fonctionErrorLabel.setText("");
                telephoneErrorLabel.setText("");
                emailErrorLabel.setText("");

                if (nomVar.isEmpty() || prenomVar.isEmpty() || fonctionVar.isEmpty() || emailVar.isEmpty() || telephoneVar.isEmpty()) {
                    nomErrorLabel.setText("Veuillez remplir tous les champs.");
                }

                if (nomVar.length() < 3) {
                    nomErrorLabel.setText("Le nom doit comporter au moins 3 caractères.");
                }
                
                if (!nomVar.matches("[a-zA-Z]+")) {
                    nomErrorLabel.setText("Le nom ne peut contenir que des lettres.");
                }

                if (fonctionVar.length() < 3) {
                    fonctionErrorLabel.setText("L'adresse doit comporter au moins 3 caractères.");
                }

                if (!telephoneVar.matches("\\d{8}")) {
                    telephoneErrorLabel.setText("Le numéro de téléphone doit comporter exactement 8 chiffres.");
                }
                if (!telephoneVar.matches("\\d+")) {
                    telephoneErrorLabel.setText("Le numéro de téléphone ne peut contenir que des chiffres.");
                }

                if (!nomErrorLabel.getText().isEmpty() || !prenomErrorLabel.getText().isEmpty() || 
                    !fonctionErrorLabel.getText().isEmpty() || !telephoneErrorLabel.getText().isEmpty() || 
                    !emailErrorLabel.getText().isEmpty()) {
                    return;
                }

                // Traitement normal si aucune erreur
                memberRepo.modifierMembre(membre.getId(), nomVar,prenomVar, fonctionVar, telephoneVar, emailVar);

              

                // Fermer la fenêtre après la mise à jour
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

	    
	   public static void supprimer(Membre membre,Stage primaryStage) {

            
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
                nomLabel.setText("Êtes-vous sûr de vouloir supprimer ce Membre ? ");
                 delete.setOnAction(event -> {
                 memberRepo.supprimerMembre(membre.getId());
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

	public static void ajouter(Button ajouterButton, Stage primaryStage) {
    ajouterButton.setOnAction(e -> {
        
        if (primaryStage == null) {
            System.out.println("Erreur : primaryStage est null.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(MemberController.class.getResource("../addMembre.fxml"));
            primaryStage.setScene(new Scene(root));
           
          
            Button cancel = (Button) root.lookup("#cancel");
            Button update = (Button) root.lookup("#update");
            
            TextField nom = (TextField) root.lookup("#nom");
            TextField prenom = (TextField) root.lookup("#prenom");
           // TextField fonction = (TextField) root.lookup("#fonction");
            ComboBox<String> fonction = (ComboBox<String>) root.lookup("#fonction");
            fonction.getItems().addAll("Volontaire", "Assistant","Coordinateur des événements","Traducteur","Gestionnaire des bénévoles");
            TextField telephone = (TextField) root.lookup("#telephone");
            TextField email = (TextField) root.lookup("#description");
            
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
            Label emailErrorLabel = (Label) root.lookup("#descriptionErrorLabel");
            Label associationErrorLabel = (Label) root.lookup("#associationErrorLabel");
   
            update.setOnAction(event -> {
                String nomVar = nom.getText();
                Object associationName = association.getValue();
                String prenomVar = prenom.getText();
                String emailVar = email.getText();

                String fonctionVar = fonction.getValue();
                String telephoneVar = telephone.getText();

                  if (nomVar.isEmpty() || prenomVar.isEmpty()|| fonctionVar.isEmpty() || emailVar.isEmpty() || telephoneVar.isEmpty()) {
                    nomErrorLabel.setText("Veuillez remplir tous les champs.");
                }

                if (nomVar.length() < 3) {
                    nomErrorLabel.setText("Le nom doit comporter au moins 3 caractères.");
                    return;
                }
                 if (fonctionVar.length() < 3) {
                    fonctionErrorLabel.setText("L'adresse doit comporter au moins 3 caractères.");
                }

                if (!telephoneVar.matches("\\d{8}")) {
                    telephoneErrorLabel.setText("Le numéro de téléphone doit comporter exactement 8 chiffres.");
                }
                if (!telephoneVar.matches("\\d+")) {
                    telephoneErrorLabel.setText("Le numéro de téléphone ne peut contenir que des chiffres.");
                }
              
                int selectedAssociationId = associationMap.get(associationName);
                   memberRepo.ajouterMembre(nomVar,prenomVar,fonctionVar, telephoneVar,emailVar, selectedAssociationId);
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



	 
	}
