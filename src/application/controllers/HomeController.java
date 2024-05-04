package application.controllers;

	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.layout.Pane;
	import javafx.scene.layout.VBox;


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

	   


	    @FXML
	    public void handleClicks(ActionEvent actionEvent) {
	        btnCustomers.setStyle("-fx-background-color:  #05071F");
	        btnMenus.setStyle("-fx-background-color:  #05071F");
	        btnOverview.setStyle("-fx-background-color:  #05071F");
	        btnOrders.setStyle("-fx-background-color:  #05071F");
	        pnlCustomer.setStyle("-fx-background-color:  #05071F");
	        pnlMenus.setStyle("-fx-background-color:  #05071F");
	        pnlOverview.setStyle("-fx-background-color: #05071F");
	        pnlOrders.setStyle("-fx-background-color:  #05071F");

	        if (actionEvent.getSource() == btnCustomers) {
	            btnCustomers.setStyle("-fx-background-color: #53639F");
	            pnlCustomer.setStyle("-fx-background-color: #02030A");
	            pnlCustomer.toFront();
	        } else if (actionEvent.getSource() == btnMenus) {
	            btnMenus.setStyle("-fx-background-color: #53639F");
	            pnlMenus.setStyle("-fx-background-color: #02030A");
	            pnlMenus.toFront();
	        } else if (actionEvent.getSource() == btnOverview) {
	            btnOverview.setStyle("-fx-background-color: #53639F");
	            pnlOverview.setStyle("-fx-background-color: #02030A");
	            pnlOverview.toFront();
	        } else if (actionEvent.getSource() == btnOrders) {
	            btnOrders.setStyle("-fx-background-color: #53639F");
	            pnlOrders.setStyle("-fx-background-color: #02030A");
	            pnlOrders.toFront();
	        
	        }
	    }


	}
