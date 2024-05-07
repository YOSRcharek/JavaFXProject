package demo.controllers;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import demo.model.Association;
import demo.model.Membre;
import demo.model.Projet;
import demo.model.User;
import demo.repository.associationRepo;
import demo.repository.memberRepo;
import demo.repository.projetRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 *
 * @author oXCToo
 */
public class profilController {

@FXML
   private AnchorPane AnchorPane;
    @FXML
    private VBox pnl_scroll;
    @FXML
    private VBox pnl_scroll1;
    @FXML
    private VBox pnl_scroll2;
    @FXML
    private ScrollPane itemProjet;
    @FXML
    private ScrollPane itemMembre;
    @FXML
    private ScrollPane itemEvent;
    @FXML
    private JFXButton projets;
    @FXML
    private JFXButton membres;
    @FXML
    private JFXButton events;
    @FXML
    private Label nomAssoc;
    @FXML
    private Label emailAssoc;
    @FXML
    private Label domaineAssoc;
    @FXML
    private Label nomMembre;
   @FXML
   private ImageView homeBack;
private String email="";
    private final ProjetController projetController = new ProjetController();
    private final MemberController memberController = new MemberController();

    @FXML
    private void handleButtonAction(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == projets) {
            loadProject();
            projets.setStyle("-fx-background-color:  #DDE6E8");
            membres.setStyle("-fx-background-color:  white");
            events.setStyle("-fx-background-color: white");
            itemProjet.toFront();

        } else if (actionEvent.getSource() == membres) {
            membres.setStyle("-fx-background-color:  #DDE6E8");
            projets.setStyle("-fx-background-color:  white");

            events.setStyle("-fx-background-color: white");
            itemMembre.toFront();
        } else if (actionEvent.getSource() == events) {
            loadEvent();
            membres.setStyle("-fx-background-color:   white");
            projets.setStyle("-fx-background-color:  white");
            events.setStyle("-fx-background-color:  #DDE6E8");
            itemEvent.toFront();
        }

    }

    public void initialize() throws IOException {

        User authenticatedUser = SignInController.getAuthenticatedUser();
        if (authenticatedUser != null) {
          //  nomAssoc.setText(authenticatedUser.getEmail());
            email=authenticatedUser.getEmail();

        }
        loadAssociation();
        loadProject();
        loadMember();
        loadEvent();
        itemProjet.toFront();
        projets.setStyle("-fx-background-color:  #DDE6E8");
    }


    private void loadAssociation() {
        Association myAsso = associationRepo.getAssociationByEmail(String.valueOf(email)); // Fetch association data

        if (myAsso != null) {
            // Set association data to labels
            nomAssoc.setText(myAsso.getNom());
            emailAssoc.setText(String.valueOf(email));
            domaineAssoc.setText(myAsso.getDomaineActivite());
        } else {
            // Handle case where association data is not found
            System.out.println("Association not found!");
        }
    }

    private void loadProject() {
        pnl_scroll.getChildren().clear();
        List<Projet> projets = projetRepo.getProjetsProfil(email);

        for (Projet projet : projets) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../profilItem.fxml"));
                Node node = loader.load();
                Label nomProjetLabel = (Label) node.lookup("#nomProjet");
                Label dateDebutLabel = (Label) node.lookup("#dateDebut");
                Label dateFinLabel = (Label) node.lookup("#dateFin");

                Label monthDebutLabel = (Label) node.lookup("#monthDebut");
                Label monthFinLabel = (Label) node.lookup("#monthFin");
                Label yearFinLabel = (Label) node.lookup("#yearFin");
                Label yearDebutLabel = (Label) node.lookup("#yearDebut");
                Label idLabel = (Label) node.lookup("#id");
                Label descripLabel = (Label) node.lookup("#descrip");
                Label statusLabel = (Label) node.lookup("#status");
                Label detailLabel = (Label) node.lookup("#details");
                Label modifierLabel = (Label) node.lookup("#modifier");
                Label supprimerLabel = (Label) node.lookup("#supprimer");
                nomProjetLabel.setText(projet.getNomProjet());
                idLabel.setText(String.valueOf(projet.getId()));
                descripLabel.setText(projet.getDescription());

                statusLabel.setText(projet.getStatus());
                dateDebutLabel.setText(String.valueOf(projet.getDateDebut().toLocalDate().getDayOfMonth()));
                dateFinLabel.setText(String.valueOf(projet.getDateFin().toLocalDate().getDayOfMonth()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");

                monthDebutLabel.setText(projet.getDateDebut().toLocalDate().format(formatter));
                monthFinLabel.setText(projet.getDateFin().toLocalDate().format(formatter));

                yearDebutLabel.setText(String.valueOf(projet.getDateDebut().toLocalDate().getYear()));
                yearFinLabel.setText(String.valueOf(projet.getDateFin().toLocalDate().getYear()));

                detailLabel.setOnMouseClicked(event -> {
                    Stage primaryStage = new Stage();
                    projetController.detail(projet, primaryStage);
                });
                modifierLabel.setOnMouseClicked(event -> {
                    Stage primaryStage = new Stage();
                    projetController.modifier2(projet, primaryStage);
                });
                supprimerLabel.setOnMouseClicked(event -> {
                    Stage primaryStage = new Stage();
                    projetController.supprimer2(projet, primaryStage);
                });

                pnl_scroll.getChildren().add(node);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadMember() {
        pnl_scroll1.getChildren().clear();
        List<Membre> membres = memberRepo.getMembersProfil(email);

        for (Membre membre : membres) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../profilMember.fxml"));
                Node node = loader.load();
                Label nomMembreLabel = (Label) node.lookup("#nomMembre");
                Label idLabel = (Label) node.lookup("#id");
                Label prenomMembreLabel = (Label) node.lookup("#prenomMembre");
                Label telephoneLabel = (Label) node.lookup("#telephone");
                Label fonctionLabel = (Label) node.lookup("#fonction");
                Label emailLabel = (Label) node.lookup("#email");
                Label detailLabel = (Label) node.lookup("#details");
                Label modifierLabel = (Label) node.lookup("#modifier");
                Label supprimerLabel = (Label) node.lookup("#supprimer");
                nomMembreLabel.setText(membre.getNomMembre());
                idLabel.setText(String.valueOf(membre.getId()));
                prenomMembreLabel.setText(membre.getPrenomMembre());
                telephoneLabel.setText(membre.getTelephone());
                emailLabel.setText(membre.getEmailMembre());
                fonctionLabel.setText(membre.getFonction());
                detailLabel.setOnMouseClicked(event -> {
                    Stage primaryStage = new Stage();
                    memberController.detail(membre, primaryStage);
                });
                modifierLabel.setOnMouseClicked(event -> {
                    Stage primaryStage = new Stage();
                    memberController.modifier2(membre, primaryStage);
                });
                supprimerLabel.setOnMouseClicked(event -> {
                    Stage primaryStage = new Stage();
                    memberController.supprimer2(membre, primaryStage);
                });
                pnl_scroll1.getChildren().add(node);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private void loadEvent() {
        pnl_scroll2.getChildren().clear();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../events.fxml"));
                Node node = loader.load();


                pnl_scroll2.getChildren().add(node);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    public void AfficherCalendrier(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../calendrier.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 920, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void AfficherType(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../AddTypeEvent.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 920, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void newType(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../event_form.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 920, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public void signOut(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();

        Stage stage = (Stage) button.getScene().getWindow();

        // Fermer la fenêtre
        stage.close();
    }

    @FXML
    public void homeBack(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../test.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) homeBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Imprime la trace de la pile de l'exception
        }
    }
}

