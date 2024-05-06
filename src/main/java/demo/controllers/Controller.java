package demo.controllers;


import demo.model.Association;
import demo.model.Membre;
import demo.model.Projet;
import demo.repository.associationRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
public class Controller {

    @FXML
    private VBox pnItems;

    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMembers;
    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Button btnOverview;
    
    @FXML
    private Button btnMembers;
    
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
    private GridPane gridPane;
    @FXML
    private GridPane gridPane1;
    @FXML
    private GridPane gridPane2;
    @FXML
    private GridPane gridPane3;
    @FXML
    private HBox itemC1;
    
    @FXML
    private VBox pnItems1;
    @FXML
    private Label nameLabel;

    @FXML
    private Label domainLabel;

    @FXML
    private Label emailLabel;
    
    @FXML
    private Label dateLabel;
    @FXML
    private Label nomMembreLabel1;

    @FXML
    private Label prenomMembreLabel1;

    @FXML
    private Label emailMembreLabel1;
    
    @FXML
    private Label fonctionLabel1;
    @FXML
    private Button approuverButton;
    
    @FXML
    private Button desapprouverButton;
    @FXML
    private Label nameLabel1;

    @FXML
    private Label domainLabel1;

    @FXML
    private Label emailLabel1;
    
    @FXML
    private Label adresseLabel1;
    
    @FXML
    private Button supprimerButton;
    
    @FXML
    private Button modifierButton;
    
    @FXML
    private Button ajouterButton;
    @FXML
    private Button ajouterButton2;
    
    @FXML
    private Button ajouterButtonMembre;
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private PieChart pieChart;
    
    public TableView<Association> exampleTable;
    public TableView<Association> exampleTable1;
    public TableView<Projet> exampleTable2;
    public TableView<Membre> exampleTable3;
    public  TableColumn<Association, Integer> orderIdColumn;
    public TableColumn<Association, String> nameColumn;
    public TableColumn<Association, String> domainColumn;

    public TableColumn<Association, String> dateDemandeColumn;


    public TableColumn<Association, String> emailColumn;
    public  TableColumn<Association, Integer> orderId1Column;
    public TableColumn<Association, String> name1Column;
    public TableColumn<Association, String> domain1Column;



    public TableColumn<Association, String> email1Column;
    public TableColumn<Association, String> adresseColumn;
    public TableColumn<Association, Void> actionsColumn;
    
    @FXML
    private TableColumn<Association, Void> pdfColumn;

    @FXML
    private TableColumn<Association, Void> approuverColumn;

    @FXML
    private TableColumn<Association, Void> desapprouverColumn;
    @FXML
    private TableColumn<Association, Void> detailColumn;

    @FXML
    private TableColumn<Association, Void> supprimerColumn;

    @FXML
    private TableColumn<Association, Void> modifierColumn;
    public TableColumn<Projet, String> nameProjetColumn;
    public TableColumn<Projet, Date> dateDebutColumn;
    public TableColumn<Projet, Date> dateFinColumn;
    public TableColumn<Projet, String> descriptionColumn;
    public TableColumn<Projet, String> statusColumn;
    public  TableColumn<Projet, Integer> orderId2Column;
    
    @FXML
    private TableColumn<Projet, Void> detailProjetColumn;

    @FXML
    private TableColumn<Projet, Void> supprimerProjetColumn;

    @FXML
    private TableColumn<Projet, Void> modifierProjetColumn;
  
    public TableColumn<Membre, String> nameMembreColumn;
	  public TableColumn<Membre, String> prenomColumn;
	  public TableColumn<Membre, String> fonctionColumn;
	  public TableColumn<Membre, String> emailMembreColumn;
	  public  TableColumn<Membre, Integer> orderId3Column;
	  
	  @FXML
	  private TableColumn<Membre, Void> detailMembreColumn;

	  @FXML
	  private TableColumn<Membre, Void> supprimerMembreColumn;

	  @FXML
	  private TableColumn<Membre, Void> modifierMembreColumn;
    private double x = 0,y = 0;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private AnchorPane sideBar;

    private  Stage primaryStage; 

    private Stage stage;

    private final AssociationController associationController = new AssociationController();
    private final ProjetController projetController = new ProjetController();

    
    
    @FXML
    public void initialize() {
    	   sideBar.setOnMousePressed(mouseEvent -> {
               x = mouseEvent.getSceneX();
               y = mouseEvent.getSceneY();
           });

           sideBar.setOnMouseDragged(mouseEvent -> {
               stage.setX(mouseEvent.getScreenX() - x);
               stage.setY(mouseEvent.getScreenY() - y);
           });

    	

         XYChart.Series series1 = new XYChart.Series();

         series1.getData().add(new XYChart.Data("1",5));
         series1.getData().add(new XYChart.Data("2",4));
         series1.getData().add(new XYChart.Data("3",6));
         series1.getData().add(new XYChart.Data("5",3));
         series1.getData().add(new XYChart.Data("9",10));

         XYChart.Series series2 = new XYChart.Series();

         series2.getData().add(new XYChart.Data("1",2));
         series2.getData().add(new XYChart.Data("3",2));
         series2.getData().add(new XYChart.Data("4",5));

         XYChart.Series series3 = new XYChart.Series();

         series3.getData().add(new XYChart.Data("1",1));
         series3.getData().add(new XYChart.Data("2",4));
         series3.getData().add(new XYChart.Data("4",9));

         lineChart.getData().addAll(series1,series2,series3);
       // Populate pie chart with sample data 
          ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
              new PieChart.Data("Category A", 30),
              new PieChart.Data("Category B", 20),
              new PieChart.Data("Category C", 50)
          );

          // Setting data to PieChart
          pieChart.setData(pieChartData);

          // Customizing slice colors and text styles
          int i = 0;
          for (PieChart.Data data : pieChartData) {
              String color = "";
              if (data.getName().equals("Category A")) {
                  color = "#9CCBD6";
              } else if (data.getName().equals("Category B")) {
                  color = "#FFAB91";
              } else if (data.getName().equals("Category C")) {
                  color = "#29647C";
              }
              pieChartData.get(i).getNode().setStyle("-fx-pie-color:" + color + "';'");

              i++;
          }

pieChart.setLegendVisible(false);
          // Removing labels
          pieChart.setLabelLineLength(10); // Adjust label line length
          pieChart.setLabelsVisible(false); // Hide labels


	    Stage primaryStage = new Stage();
        loadDemandeFromDatabase(primaryStage);
        btnOverview.setStyle("-fx-background-color:   #9CCBD6");
        pnlOverview.setStyle("-fx-background-color : #EFFCFF");
        pnlOverview.toFront();
    }
    
    public void handleClicks(ActionEvent actionEvent) {
	    btnCustomers.setStyle("-fx-background-color:    #DDE6E8");
	    btnMenus.setStyle("-fx-background-color:    #DDE6E8");
	    btnOverview.setStyle("-fx-background-color:    #DDE6E8");
	    btnOrders.setStyle("-fx-background-color:    #DDE6E8");
	    btnMembers.setStyle("-fx-background-color:    #DDE6E8");
	
		    if (actionEvent.getSource() == btnCustomers) {
		        btnCustomers.setStyle("-fx-background-color:  #9CCBD6");
		        pnlCustomer.setStyle("-fx-background-color: #EFFCFF");
		        pnlCustomer.toFront();
		        Stage primaryStage = new Stage();
		        loadAssocFromDatabase(primaryStage);
		    } else if (actionEvent.getSource() == btnMenus) {
		        btnMenus.setStyle("-fx-background-color:  #9CCBD6");
		        pnlMenus.setStyle("-fx-background-color:  #EFFCFF");
		        pnlMenus.toFront();
		        Stage primaryStage = new Stage();
		        loadProjetFromDatabase(primaryStage);
		    } else if (actionEvent.getSource() == btnOverview) {
		        btnOverview.setStyle("-fx-background-color:  #9CCBD6");
		        pnlOverview.setStyle("-fx-background-color:  #EFFCFF");
		        pnlOverview.toFront();
		    } else if (actionEvent.getSource() == btnOrders) {
		        btnOrders.setStyle("-fx-background-color:  #9CCBD6");
		        pnlOrders.setStyle("-fx-background-color: #EFFCFF");
		        pnlOrders.toFront();
		        Stage primaryStage = new Stage();
		        loadDemandeFromDatabase(primaryStage);
		    } else if (actionEvent.getSource() == btnMembers) {
		        btnMembers.setStyle("-fx-background-color:  #9CCBD6");
		        pnlMembers.setStyle("-fx-background-color:  #EFFCFF");
		        pnlMembers.toFront();
		        Stage primaryStage = new Stage();

		        loadMemberFromDatabase(primaryStage);
		    
		    }
	  }

    @FXML
    
    private void loadDemandeFromDatabase(Stage primaryStage) {
      /*  List<Association> associations = associationRepo.getDemandes(); 
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        domainColumn.setCellValueFactory(new PropertyValueFactory<>("domaineActivite"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateDemandeColumn.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
       pdfColumn.setCellFactory(param -> new TableCell<Association, Void>() {
        private final Button pdfButton = new Button("PDF");

        {
            pdfButton.setOnAction(event -> {
                Association association = getTableView().getItems().get(getIndex());
                associationController.openPDF(association);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(pdfButton);
            }
        }
    });

    approuverColumn.setCellFactory(param -> new TableCell<Association, Void>() {
        private final Button approuverButton = new Button("Approuver");

        {
            approuverButton.setOnAction(event -> {
                Association association = getTableView().getItems().get(getIndex());
                associationController.approuverDemande(association,primaryStage);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(approuverButton);
            }
        }
    });

    desapprouverColumn.setCellFactory(param -> new TableCell<Association, Void>() {
        private final Button desapprouverButton = new Button("Désapprouver");

        {
            desapprouverButton.setOnAction(event -> {
                Association association = getTableView().getItems().get(getIndex());
                associationController.desapprouverDemande(association,primaryStage);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(desapprouverButton);
            }
        }
    });


        ObservableList<Association> data = FXCollections.observableArrayList(associations);
        exampleTable.setItems(data);
   */ }

    @FXML
    
    
    private void loadAssocFromDatabase(Stage primaryStage) {
        List<Association> associations = associationRepo.getAssociations();

        // Utilisez une liste ordinaire pour stocker vos données
        List<Association> assocList = new ArrayList<>(associations);

        orderId1Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        name1Column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        domain1Column.setCellValueFactory(new PropertyValueFactory<>("domaineActivite"));
        email1Column.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        // Créez une liste observable à partir de votre liste ordinaire
        ObservableList<Association> data = FXCollections.observableArrayList(assocList);

        // Configurez vos colonnes comme avant
        // ...

        exampleTable1.setItems(data);
    }
         
    private void loadProjetFromDatabase(Stage primaryStage) {
	/*      List<Projet> projets = projetRepo.getProjets();

	               orderId2Column.setCellValueFactory(new PropertyValueFactory<>("id"));
	               nameProjetColumn.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
	               statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
	               dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
	               dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
	               detailProjetColumn.setCellFactory(param -> new TableCell<Projet, Void>() {

	                private final Button detailButton = new Button("Details");
	           
	                   {
	                       detailButton.setOnAction(event -> {
	                           Projet projet = getTableView().getItems().get(getIndex());
	                           projetController.detail(projet,primaryStage);
	                       });
	                   }
	           
	                   @Override
	                   protected void updateItem(Void item, boolean empty) {
	                       super.updateItem(item, empty);
	                       if (empty) {
	                           setGraphic(null);
	                       } else {
	                           setGraphic(detailButton);
	                       }
	                   }
	               });
	           
	               
	       modifierProjetColumn.setCellFactory(param -> new TableCell<Projet, Void>() {
	           private final Button modifierButton = new Button("Modifier");

	           {
	               modifierButton.setOnAction(event -> {
	                   Projet projet = getTableView().getItems().get(getIndex());
	                   projetController.modifier(projet,primaryStage);
	               });
	           }

	           @Override
	           protected void updateItem(Void item, boolean empty) {
	               super.updateItem(item, empty);
	               if (empty) {
	                   setGraphic(null);
	               } else {
	                   setGraphic(modifierButton);
	               }
	           }
	       });
	       supprimerProjetColumn.setCellFactory(param -> new TableCell<Projet, Void>() {
	           private final Button supprimerButton = new Button("Supprimer");

	           {
	               supprimerButton.setOnAction(event -> {
	                   Projet projet = getTableView().getItems().get(getIndex());
	                   projetController.supprimer(projet,primaryStage);
	               });
	           }

	           @Override
	           protected void updateItem(Void item, boolean empty) {
	               super.updateItem(item, empty);
	               if (empty) {
	                   setGraphic(null);
	               } else {
	                   setGraphic(supprimerButton);
	               }
	           }
	       });        
	                  
	       ObservableList<Projet> data = FXCollections.observableArrayList(projets);
	       exampleTable2.setItems(data);     
	       projetController.ajouter(ajouterButton,primaryStage);
	    */  }
	    
    private void loadMemberFromDatabase( Stage primaryStage) {
	/*	List<Membre> membres = memberRepo.getMembres();

		               orderId3Column.setCellValueFactory(new PropertyValueFactory<>("id"));
		               nameMembreColumn.setCellValueFactory(new PropertyValueFactory<>("nomMembre"));
		               prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenomMembre"));
		               fonctionColumn.setCellValueFactory(new PropertyValueFactory<>("fonction"));
		               emailMembreColumn.setCellValueFactory(new PropertyValueFactory<>("emailMembre"));
		               detailMembreColumn.setCellFactory(param -> new TableCell<Membre, Void>() {

		                private final Button detailButton = new Button("Details");
						private MemberController MembreController;
		           
		                   {
		                       detailButton.setOnAction(event -> {
		                           Membre membre = getTableView().getItems().get(getIndex());
		                           MemberController.detail(membre,primaryStage);
		                       });
		                   }
		           
		                   @Override
		                   protected void updateItem(Void item, boolean empty) {
		                       super.updateItem(item, empty);
		                       if (empty) {
		                           setGraphic(null);
		                       } else {
		                           setGraphic(detailButton);
		                       }
		                   }
		               });
		           
		               
		       modifierMembreColumn.setCellFactory(param -> new TableCell<Membre, Void>() {
		           private final Button modifierButton = new Button("Modifier");

		           {
		               modifierButton.setOnAction(event -> {
		                   Membre membre = getTableView().getItems().get(getIndex());
		                   MemberController.modifier(membre,primaryStage);
		               });
		           }

		           @Override
		           protected void updateItem(Void item, boolean empty) {
		               super.updateItem(item, empty);
		               if (empty) {
		                   setGraphic(null);
		               } else {
		                   setGraphic(modifierButton);
		               }
		           }
		       });
		       supprimerMembreColumn.setCellFactory(param -> new TableCell<Membre, Void>() {
		           private final Button supprimerButton = new Button("Supprimer");

		           {
		               supprimerButton.setOnAction(event -> {
		                   Membre membre = getTableView().getItems().get(getIndex());
		                   MemberController.supprimer(membre,primaryStage);
		               });
		           }

		           @Override
		           protected void updateItem(Void item, boolean empty) {
		               super.updateItem(item, empty);
		               if (empty) {
		                   setGraphic(null);
		               } else {
		                   setGraphic(supprimerButton);
		               }
		           }
		       });        
		                  
		       ObservableList<Membre> data = FXCollections.observableArrayList(membres);
		       exampleTable3.setItems(data);     
		       MemberController.ajouter(ajouterButton2,primaryStage);
		    */  }
  	

}
