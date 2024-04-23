package Application.Controllers;

import Model.Service;
import Repository.Servicerepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.List;

public class ServiceController {

    @FXML
    private Button btnadd;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtdescription;

    @FXML
    private CheckBox chkOui;

    @FXML
    private CheckBox chkNon;

    @FXML
    private ChoiceBox<String> choiceAssociation;

    @FXML
    private ChoiceBox<String> choiceCategorie;

    @FXML
    private ChoiceBox<String> choiceCommentaire;

    @FXML
    private TableView<Service> tableView;

    @FXML
    private TableColumn<Service, String> colNomService;

    @FXML
    private TableColumn<Service, String> colDescription;

    @FXML
    private TableColumn<Service, String> colDisponibilite;

    @FXML
    private TableColumn<Service, String> colAssociation;

    @FXML
    private TableColumn<Service, String> colCategorie;

    @FXML
    private TableColumn<Service, String> colCommentaire;

    private Servicerepo serviceRepository;
    private ObservableList<Service> servicesObservableList;

    @FXML
    public void initialize() {
        try {
            serviceRepository = new Servicerepo();
            chargerServices();

            colNomService.setCellValueFactory(new PropertyValueFactory<>("nomService"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDisponibilite.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
            colAssociation.setCellValueFactory(new PropertyValueFactory<>("association"));
            colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
            colCommentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chargerServices() throws SQLException {
        List<Service> services = serviceRepository.getAll();
        servicesObservableList = FXCollections.observableArrayList(services);
        tableView.setItems(servicesObservableList);
    }

    @FXML
    void add(ActionEvent event) {
        Service service = new Service();
        service.setNomservice(txtname.getText());
        service.setDescription(txtdescription.getText());
        service.setDisponibilite(chkOui.isSelected());
        service.setAssociation_id(Integer.parseInt(choiceAssociation.getId()));
        service.setCategorie_id(Integer.parseInt(choiceCategorie.getId()));
        service.setCommentaire_id(Integer.parseInt(choiceCommentaire.getId()));

        try {
            serviceRepository.add(service);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le service a été ajouté avec succès !");
            alert.show();

            chargerServices();
            tableView.refresh();

            txtname.clear();
            txtdescription.clear();

        } catch (SQLException e) {
            afficherErreur("Erreur lors de l'ajout d'un nouveau service.", e);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Service serviceSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (serviceSelectionne != null) {
            try {
                serviceSelectionne.setNomservice(txtname.getText());
                serviceSelectionne.setDescription(txtdescription.getText());
                serviceSelectionne.setDisponibilite(chkOui.isSelected());
                serviceSelectionne.setAssociation_id(Integer.parseInt(choiceAssociation.getValue()));
                serviceSelectionne.setCategorie_id(Integer.parseInt(choiceCategorie.getValue()));
                serviceSelectionne.setCommentaire_id(Integer.parseInt(choiceCommentaire.getValue()));

                Servicerepo.update(serviceSelectionne);

                chargerServices();
                txtname.clear();
                txtdescription.clear();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Service serviceSelectionne = tableView.getSelectionModel().getSelectedItem();
        if (serviceSelectionne != null) {
            try {
                serviceRepository.delete(serviceSelectionne.getId());
                chargerServices();
                txtname.clear();
                txtdescription.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void afficherErreur(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message + "\n" + e.getMessage());
        alert.show();
    }
}
