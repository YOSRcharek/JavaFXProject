package demo.controllers;

import java.io.IOException;
import java.io.ObjectStreamClass;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import demo.model.Association;
import demo.model.Membre;
import demo.model.Projet;
import demo.repository.associationRepo;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author oXCToo
 */
public class profilController {

    @FXML
    private Label label;

    @FXML
    private VBox pnl_scroll;

    @FXML
    private ScrollPane itemProjet;
    @FXML
    private ScrollPane itemMembre;

    @FXML
    private JFXButton projets;
    @FXML
    private JFXButton membres;
    @FXML
    private Label nomAssoc;
    @FXML
    private Label domaineAssoc;
    @FXML
    private Label nomProjet;

    @FXML
    private void handleButtonAction(ActionEvent actionEvent) {
        if (actionEvent.getSource() == projets) {
            loadProject();
            projets.setStyle("-fx-background-color:  #DDE6E8");
            membres.setStyle("-fx-background-color:  white");
            itemProjet.toFront();

        } else if (actionEvent.getSource() == membres) {
            membres.setStyle("-fx-background-color:  #DDE6E8");
            projets.setStyle("-fx-background-color:  white");
            itemMembre.toFront();
        }
    }

    public void initialize() {
        loadAssociation();
        loadProject();
        itemProjet.toFront();
        projets.setStyle("-fx-background-color:  #DDE6E8");
    }



    private void loadAssociation() {
        Association myAsso = associationRepo.getOneAssociation(); // Fetch association data

        if (myAsso != null) {
            // Set association data to labels
            nomAssoc.setText(myAsso.getNom());

            domaineAssoc.setText(myAsso.getDomaineActivite());
        } else {
            // Handle case where association data is not found
            System.out.println("Association not found!");
        }
    }

    private void loadProject() {
        pnl_scroll.getChildren().clear();
        List<Projet> projets = projetRepo.getProjetsProfil();

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
                pnl_scroll.getChildren().add(node);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        }
