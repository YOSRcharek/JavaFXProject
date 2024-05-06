package demo.controllers;



import demo.model.Association;
import demo.model.Categorie;
import demo.model.Commentaire;
import demo.model.Service;
import demo.repository.CategorieRepository;
import demo.repository.CommentaireRepository;
import demo.repository.associationRepo;
import demo.repository.ServiceRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class AjouterService {

    @FXML
    private TextField txtnomservice;

    @FXML
    private TextField txtdescription;

    @FXML
    private ComboBox<String> categorieComboBox;

    @FXML
    private ComboBox<String> commentaireComboBox;

    @FXML
    private ComboBox<String> associationComboBox;

    @FXML
    private ComboBox<String> disponibiliteComboBox;

    @FXML
    private Button addservice;

    @FXML
    private Button annuler;

    private ServiceRepository ServiceRepository =new ServiceRepository();

    private CategorieRepository categorieRepository =new CategorieRepository();
    private associationRepo associationRepository=new  associationRepo ();
    private CommentaireRepository commentaireRepository=new CommentaireRepository();
    public AjouterService() throws SQLException {
    }

    @FXML
    void initialize() {
        List<String> categories=categorieRepository.getAllNomCategories();
        for(String categorie : categories)
        {
            categorieComboBox.getItems().add(categorie);
        }
        List<String> Associations=associationRepository.getAllNomAssociations();
        for(String Association : Associations)
        {
            associationComboBox.getItems().add(Association);
        }
        List<String> Commentaires =commentaireRepository.getAllCommentaireMessages();
        // Initialiser les ComboBox
       // categorieComboBox.getItems().addAll("sensibilisation", "palastine", "Catégorie 3");
        commentaireComboBox.getItems().addAll("commentaire 1", "commentaire2", "commentaire 3","nouveau commataire");
        associationComboBox.getItems().addAll("Association1", "Association 2", "Association 3");
        disponibiliteComboBox.getItems().addAll("Disponible", "Non disponible");

        // Initialiser le repository
    }

    @FXML
    void addservice(ActionEvent event) {
        // Récupérer les valeurs des champs
        String nomService = txtnomservice.getText();
        String description = txtdescription.getText();
        String categorie = categorieComboBox.getValue();
        String commentaire = commentaireComboBox.getValue();
        String association = associationComboBox.getValue();
        String disponibilite = disponibiliteComboBox.getValue();

        // Vérifier si tous les champs sont remplis
        if (nomService.isEmpty() || description.isEmpty() || categorie == null || commentaire == null || association == null || disponibilite == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }
        Categorie cat=categorieRepository.getbyNom(categorie);
        Association ass=associationRepository.getAssociationByNom(association);
       Commentaire comment= commentaireRepository.getCommentaireByNom(commentaire);


        //Créer un nouvel objet Service et définir ses attributs
        Service service = new Service(nomService,description,disponibilite.equals("Disponible"),cat, comment, ass);

        // Ajouter le service à la base de données
        try {
            ServiceRepository.ajouter(service);
            // Afficher une confirmation ou effectuer d'autres actions en cas de succès
            showAlert("Succès", "Service ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs d'ajout du service à la base de données
            showAlert("Erreur", "Une erreur s'est produite lors de l'ajout du service.");
        }
    }

    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthodes utilitaires pour obtenir l'ID de la catégorie, du commentaire et de l'association à partir de leur nom
    private int getCategorieId(String nomCategorie) {
        // Logique pour obtenir l'ID de la catégorie à partir de son nom
        return 0; // Placeholder, tu dois implémenter cette logique
    }

    private int getCommentaireId(String contenuCommentaire) {
        // Logique pour obtenir l'ID du commentaire à partir de son contenu
        return 0; // Placeholder, tu dois implémenter cette logique
    }

    private int getAssociationId(String nomAssociation) {
        // Logique pour obtenir l'ID de l'association à partir de son nom
        return 0; // Placeholder, tu dois implémenter cette logique
    }

    public void handleTextFieldChange(ActionEvent actionEvent) {
    }
}
