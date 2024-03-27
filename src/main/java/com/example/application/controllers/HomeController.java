package com.example.application.controllers;

	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.fxml.FXMLLoader;
	import javafx.fxml.Initializable;
	import javafx.scene.Node;
	import javafx.scene.control.Button;
	import javafx.scene.layout.Pane;
	import javafx.scene.layout.VBox;

	import java.io.IOException;
	import java.net.URL;
	import java.util.ResourceBundle;

	public class HomeController {

	    @FXML
	    private VBox pnItems = null;
	    @FXML
	    private Button btnOverview;

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

	   


	    public void handleClicks(ActionEvent actionEvent) {
	        if (actionEvent.getSource() == btnCustomers) {
	            pnlCustomer.setStyle("-fx-background-color : #1620A1");
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
