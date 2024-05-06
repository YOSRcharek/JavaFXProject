package Application.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class ModifierService {
    @FXML
    private Button btnsauvgarde;

    @FXML
    private ComboBox<?> cmbAssociation;

    @FXML
    private ComboBox<?> cmbCategorie;

    @FXML
    private ComboBox<?> cmbCommentaire;

    @FXML
    private ComboBox<?> cmbDisponibilite;

    @FXML
    private TextField descriptionField;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNomService;

    @FXML
    private TextField nomServiceField;

}
