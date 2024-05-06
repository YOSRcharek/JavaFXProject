package Dons.Controller;

import Dons.entities.Association;
import Dons.entities.Dons;
import Dons.entities.Typedons;
import Dons.services.DonsCrud;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.Pagination;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.scene.layout.VBox;
import java.util.Comparator;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.net.URI;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart;
import java.util.Map;
import java.util.HashMap;




public class DonsController extends Application {
    private ObservableList<Dons> observableDonsList;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGoToTypeDon;

    @FXML
    private Button btnOpenFormToAdd;

    @FXML
    private Button btnOpenFormToUpdate;
    @FXML
    private Button btnFront;

    @FXML
    private Pagination pagination;
    private final int itemsPerPage = 15;
    private List<Dons> donsList;

    @FXML
    private ListView<Dons> lvDon;

    private final DonsCrud donsCrud = new DonsCrud();
    private static final Logger logger = Logger.getLogger(DonsController.class.getName());


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        observableDonsList = FXCollections.observableArrayList();

        donsList = donsCrud.afficherEntite();


        // Configure pagination
        setupDonPagination(donsList);


        btnDelete.setOnAction(event -> handleDeleteDon());
    }

    private void setupDonPagination(List<Dons> dons) {
        final List<Dons> finalDons = dons; // Déclarer comme effectivement final

        pagination.setLayoutX(357); // Position X
        pagination.setLayoutY(74); // Position Y
        int pageCount = (int) Math.ceil((double) dons.size() / itemsPerPage);
        pagination.setPageCount(pageCount);

        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * itemsPerPage;
            int toIndex = Math.min(fromIndex + itemsPerPage, finalDons.size());

            // Créer une sous-liste pour la page actuelle
            List<Dons> subList = finalDons.subList(fromIndex, toIndex);

            // Effacer la ListView et mettre à jour avec les dons de la page actuelle
            lvDon.getItems().clear();
            lvDon.setItems(FXCollections.observableArrayList(subList));

            // Retourner la ListView mise à jour
            return lvDon;
        });
    }


    @FXML
    private void handleSortDon() {

        donsList.sort(Comparator.comparing(Dons::getMontant));
        setupDonPagination(donsList);
    }


    @FXML
    public void refreshTable() {
        observableDonsList.clear();
        observableDonsList.addAll(donsCrud.afficherEntite());
    }

    @FXML
    private void handleDeleteDon() {
        Dons don = lvDon.getSelectionModel().getSelectedItem();
        if (don != null) {
            donsCrud.supprimerEntite(don);
            showAlert("Don supprimé avec succès.");
            refreshTable(); // Rafraîchir la liste des dons après la suppression
            donsList = donsCrud.afficherEntite(); // Mettre à jour donsList après la suppression
            setupDonPagination(donsList); // Mettre à jour la pagination avec la liste mise à jour
        } else {
            showAlert("Veuillez sélectionner un don à supprimer.");
        }
    }




    @FXML
    private void handleGoToFormToUpdate(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DonsForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 550, 650);
            DonsFormController donsFormController = loader.getController();

            Dons selectedDon = lvDon.getSelectionModel().getSelectedItem(); // Change here
            donsFormController.setSelectedDon(selectedDon);


            Stage newStage = new Stage();  // Créer une nouvelle fenêtre
            newStage.setScene(scene);
            newStage.setTitle("Modifier un don");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToTypeDon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/typeDons.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) btnGoToTypeDon.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page des types de don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToFront() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DonsFront.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 500);
            scene.getStylesheets().add(getClass().getResource("/Front.css").toExternalForm());
            Stage stage = (Stage) btnFront.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page des types de don");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGoToForm(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DonsForm.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 550, 650); // Set scene size to 1000x500
            DonsFormController formController = loader.getController();
            formController.setDonsController(this); // Pass the instance of DonsController to DonsFormController
            Stage newStage = new Stage();  // Créer une nouvelle fenêtre
            newStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            newStage.setTitle("Page form dons");
            newStage.show();
        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }

    @FXML
    void gotofacebook(javafx.event.ActionEvent actionEvent) {
        try {
            String facebookUrl = "https://www.facebook.com/profile.php?id=61555736206742";
            openWebpage(facebookUrl);
        } catch (Exception e) {
            logger.severe("An error occurred while opening Facebook profile page: " + e.getMessage());
        }
    }

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleShowChart() {
        // Créez les axes X et Y pour le graphique
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Définissez les libellés des axes
        xAxis.setLabel("Date");
        yAxis.setLabel("Montant");

        // Créez le graphique
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Évolution des montants");

        // Ajoutez les données au graphique
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        // Remplacez data par votre liste de données réelle
        for (int i = 0; i < donsList.size(); i++) {
            series.getData().add(new XYChart.Data<>(i, donsList.get(i).getMontant()));
        }

        // Ajoutez la série de données au graphique
        lineChart.getData().add(series);

        // Créez une nouvelle fenêtre pour afficher le graphique
        Stage chartStage = new Stage();
        chartStage.setTitle("Évolution des montants");
        chartStage.setScene(new Scene(lineChart, 800, 600));
        chartStage.show();
    }

    @FXML
    private void handleShowPieChart() {
        // Créer une carte pour stocker les montants par association
        Map<String, Double> associationAmountMap = new HashMap<>();

        // Calculer le total des montants
        double totalMontant = 0.0;
        for (Dons don : donsList) {
            Association association = don.getAssociation_id();
            if (association != null) {
                String associationName = association.getName();
                double montant = don.getMontant();
                // Ajouter le montant au total pour cette association
                associationAmountMap.merge(associationName, montant, Double::sum);
                // Ajouter le montant au total général
                totalMontant += montant;
            }
        }

        // Créer une liste de sections de pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Créer une copie finale de la variable totalMontant
        final double finalTotalMontant = totalMontant;

        // Ajouter les montants de chaque association à la liste
        associationAmountMap.forEach((associationName, montant) -> {
            // Calculer le pourcentage de ce montant par rapport au total
            double percentage = (montant / finalTotalMontant) * 100;
            // Ajouter les données de section avec le nom de l'association et le pourcentage
            pieChartData.add(new PieChart.Data(associationName + " (" + String.format("%.2f", percentage) + "%)", montant));
        });

        // Créer le pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Répartition des montants par association");

        // Créer une nouvelle fenêtre pour afficher le pie chart
        Stage chartStage = new Stage();
        chartStage.setTitle("Répartition des montants par association");
        chartStage.setScene(new Scene(pieChart, 800, 600));
        chartStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dons.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Your Application Title");
        primaryStage.setScene(new Scene(root, 1000, 500));
        Scene scene = root.getScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.show();
    }
}
