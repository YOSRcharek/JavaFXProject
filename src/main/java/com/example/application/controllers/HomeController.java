	package com.example.application.controllers;

		import javafx.event.ActionEvent;
		import javafx.fxml.FXML;
		import javafx.fxml.FXMLLoader;
		import javafx.fxml.Initializable;
		import javafx.scene.Node;
		import javafx.scene.Parent;
		import javafx.scene.Scene;
		import javafx.scene.control.Button;
		import javafx.scene.layout.Pane;
		import javafx.scene.layout.VBox;
		import javafx.stage.Stage;

		import java.io.IOException;
		import java.net.URL;
		import java.util.ResourceBundle;

		public class HomeController {

			@FXML
			public Pane pnlEvents;
			@FXML
			public Button btnEvents;
			@FXML
			public Button btnEventsPage;
			@FXML
			public Pane pnlAllEvents;
			@FXML
			public Pane pnlTypeEvents;
			@FXML
			private VBox pnItems = null;
			@FXML
			private Button btnOverview;
			@FXML
			private Button btnEvent;
			@FXML
			private Button btnAddTypeEvent;

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
			private Pane pnlCustomer;

			@FXML
			private Pane pnlOrders;

			@FXML
			private Pane pnlOverview;

			@FXML
			private Pane pnlMenus;

			@FXML
			private void handleEventButtonClick(ActionEvent event) {
				try {
					// Charger le fichier FXML de l'interface d'ajout d'événement
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/event_form.fxml"));
					Parent root = loader.load();

					// Créer une nouvelle scène
					Scene scene = new Scene(root);

					// Créer une nouvelle fenêtre pour afficher l'interface d'ajout d'événement
					Stage stage = new Stage();
					stage.setScene(scene);

					// Afficher la fenêtre
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			@FXML
			void handleAddTypeEvent(ActionEvent event) {
				try {
					// Charger le fichier FXML de l'interface pour ajouter un nouveau type d'événement
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTypeEvent.fxml"));
					Parent root = loader.load();

					// Créer une nouvelle scène avec le contenu chargé
					Scene scene = new Scene(root);

					// Obtenir la fenêtre actuelle à partir de l'événement
					Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

					// Définir la nouvelle scène sur la fenêtre principale
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@FXML
			void navigateToEventsPage(ActionEvent event) {
				try {
					// Charger le fichier FXML de la nouvelle interface (par exemple, EventsPage.fxml)
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/events.fxml"));
					Parent root = loader.load();

					// Créer une nouvelle scène avec le contenu chargé
					Scene scene = new Scene(root);

					// Obtenir la scène actuelle à partir de l'événement
					Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

					// Définir la nouvelle scène sur la fenêtre principale
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


			public void handleClicks(ActionEvent actionEvent) throws IOException {
				if(actionEvent.getSource() == btnEventsPage){
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/events.fxml"));
					Parent root = loader.load();
					pnlAllEvents.getChildren().add(root);
					pnlAllEvents.toFront();
				}
				if(actionEvent.getSource() == btnEvents){
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/event_form.fxml"));
					Parent root = loader.load();
					pnlEvents.getChildren().add(root);
					pnlEvents.toFront();
				}
				if(actionEvent.getSource() ==  btnAddTypeEvent){
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTypeEvent.fxml"));
					Parent root = loader.load();
					pnlTypeEvents.setStyle("-fx-background-color : #0a0808");
					pnlTypeEvents.getChildren().add(root);
					pnlTypeEvents.toFront();
				}

				if (actionEvent.getSource() == btnCustomers) {
					pnlCustomer.setStyle("-fx-background-color : #080809");
					pnlCustomer.toFront();
				}
				if (actionEvent.getSource() == btnMenus) {
					pnlMenus.setStyle("-fx-background-color : #53639F");
					pnlMenus.toFront();
				}
				if (actionEvent.getSource() == btnOverview) {
					pnlOverview.setStyle("-fx-background-color : #02030A");
					pnlOverview.toFront();
				}
				if(actionEvent.getSource()==btnOrders)
				{
					pnlOrders.setStyle("-fx-background-color : #464F67");
					pnlOrders.toFront();
				}


			}


		}
