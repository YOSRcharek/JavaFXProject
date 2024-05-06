package demo.controllers;

import java.io.IOException;

import demo.service.mailService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import demo.model.Association;
import demo.model.PDFViewer;
import demo.repository.associationRepo;


public class AssociationController {

    private static double y;
    private static double x;
    @FXML
    private Button approuverButton;
    
    @FXML
    private Button desapprouverButton;
    
   void openPDF(Association association) {
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
    }

   public void approuverDemande(Association association,Stage primaryStage) {
            if (primaryStage == null) {
                    System.out.println("Erreur : primaryStage est null.");
                    return;
                }

                try {
                    Parent root = FXMLLoader.load(AssociationController.class.getResource("../Settings.fxml"));
                    primaryStage.setScene(new Scene(root));
                    Label nomLabel = (Label) root.lookup("#nomLabel");
                    
                    Button cancel = (Button) root.lookup("#cancel");
                    Button delete = (Button) root.lookup("#delete");
                    nomLabel.setText("Êtes-vous sûr de vouloir approuver cette demande ?");
                     delete.setOnAction(event -> {
                        associationRepo.approveAssociation(association.getId());
                         primaryStage.close();
                         mailService.sendApprouvEmail(association.getEmail());
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

     public void desapprouverDemande(Association association,Stage primaryStage) {
        
                
                if (primaryStage == null) {
                    System.out.println("Erreur : primaryStage est null.");
                    return;
                }

                try {
                    Parent root = FXMLLoader.load(AssociationController.class.getResource("../Settings.fxml"));
                    primaryStage.setScene(new Scene(root));
                    Label nomLabel = (Label) root.lookup("#nomLabel");
                    
                    Button cancel = (Button) root.lookup("#cancel");
                    Button delete = (Button) root.lookup("#delete");
                    nomLabel.setText("Êtes-vous sûr de vouloir desapprouver cette demande ?");
                     delete.setOnAction(event -> {
                        

                    	 associationRepo.disapproveAssociation(association.getId());
                         primaryStage.close();
                         mailService.sendDesApprouvEmail(association.getEmail());

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
        
    
    public void detail(Association association,Stage primaryStage) {
      

        if (primaryStage == null) {
            System.out.println("Erreur : primaryStage est null.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(AssociationController.class.getResource("../detail.fxml"));
            primaryStage.setScene(new Scene(root));
            Label nomLabel = (Label) root.lookup("#nomLabel");
            Label prenomLabel = (Label) root.lookup("#prenomLabel");
            Label emailLabel = (Label) root.lookup("#emailLabel ");
            Label fonctionLabel = (Label) root.lookup("#fonctionLabel");
            Label telephoneLabel = (Label) root.lookup("#telephoneLabel");
            Label descriptionLabel = (Label) root.lookup("#descriptionLabel");
            
            Label nom = (Label) root.lookup("#nom");
            Label prenom = (Label) root.lookup("#prenom");
            Label email = (Label) root.lookup("#email");
            Label fonction = (Label) root.lookup("#fonction");
            Label telephone = (Label) root.lookup("#telephone");
            Label description = (Label) root.lookup("#description");

            nomLabel.setText("Nom : ");
            prenomLabel.setText("Domaine : ");
            emailLabel.setText("Email : ");
            fonctionLabel.setText("Adresse : " );
            telephoneLabel.setText("Telephone : ");
            descriptionLabel.setText("Description : ");

            nom.setText( association.getNom());
            prenom.setText(association.getDomaineActivite());
            email.setText(association.getEmail());
            fonction.setText(association.getAdresse());
            telephone.setText(String.valueOf(association.getTelephone()));
            description.setText(association.getDescription());
            
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
    public void modifier(Association association, Stage primaryStage) {
        if (primaryStage == null) {
            System.out.println("Erreur : primaryStage est null.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(AssociationController.class.getResource("../updateAsso.fxml"));
            primaryStage.setScene(new Scene(root));
            Label nomLabel = (Label) root.lookup("#nomLabel");
            Label prenomLabel = (Label) root.lookup("#prenomLabel");
            Label fonctionLabel = (Label) root.lookup("#fonctionLabel");
            Label telephoneLabel = (Label) root.lookup("#telephoneLabel");
            Label descriptionLabel = (Label) root.lookup("#descriptionLabel");
            Button cancel = (Button) root.lookup("#cancel");
            Button update = (Button) root.lookup("#update");

            Pane Pane = (Pane) root.lookup("#Pane");

            TextField nom = (TextField) root.lookup("#nom");
            TextField prenom = (TextField) root.lookup("#prenom");
            TextField fonction = (TextField) root.lookup("#fonction");
            TextField telephone = (TextField) root.lookup("#telephone");
            TextField description = (TextField) root.lookup("#description");

            nomLabel.setText("Nom : ");
            prenomLabel.setText("Domaine : ");
            fonctionLabel.setText("Adresse : ");
            telephoneLabel.setText("Telephone : ");
            descriptionLabel.setText("Description : ");

            nom.setText(association.getNom());
            prenom.setText(association.getDomaineActivite());
            fonction.setText(association.getAdresse());
            telephone.setText(String.valueOf(association.getTelephone()));
            description.setText(association.getDescription());

            Label nomErrorLabel = (Label) root.lookup("#nomErrorLabel");
            Label prenomErrorLabel = (Label) root.lookup("#prenomErrorLabel");
            Label fonctionErrorLabel = (Label) root.lookup("#fonctionErrorLabel");
            Label telephoneErrorLabel = (Label) root.lookup("#telephoneErrorLabel");
            Label descriptionErrorLabel = (Label) root.lookup("#descriptionErrorLabel");

            update.setOnAction(event -> {
                String nomVar = nom.getText();
                String domaine = prenom.getText();
                String fonctionVar = fonction.getText();
                String descriptionVar = description.getText();
                String telephoneVar = telephone.getText();

                nomErrorLabel.setText("");
                prenomErrorLabel.setText("");
                fonctionErrorLabel.setText("");
                telephoneErrorLabel.setText("");
                descriptionErrorLabel.setText("");

                if (nomVar.isEmpty() || domaine.isEmpty() || fonctionVar.isEmpty() || descriptionVar.isEmpty() || telephoneVar.isEmpty()) {
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
                    !descriptionErrorLabel.getText().isEmpty()) {
                    return;
                }

                // Traitement normal si aucune erreur
                associationRepo.modifierAssociation(association.getId(), nomVar, fonctionVar, domaine, descriptionVar, Integer.parseInt(telephoneVar));

                // Effacer les champs de texte après la mise à jour
                nom.setText("");
                prenom.setText("");
                fonction.setText("");
                telephone.setText("");
                description.setText("");

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

    public void supprimer(Association association,Stage primaryStage) {
    	   if (primaryStage == null) {
               System.out.println("Erreur : primaryStage est null.");
               return;
           }

           try {
               Parent root = FXMLLoader.load(getClass().getResource("../Settings.fxml"));

               primaryStage.setScene(new Scene(root));
               Label nomLabel = (Label) root.lookup("#nomLabel");
               
               Button cancel = (Button) root.lookup("#cancel");
               Button delete = (Button) root.lookup("#delete");
               nomLabel.setText("Êtes-vous sûr de vouloir supprimer cette association ?");
                delete.setOnAction(event -> {
                   

                 associationRepo.supprimerAssociation(association.getId());
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


    private void refreshMainScene(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(AssociationController.class.getResource("../Home.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
