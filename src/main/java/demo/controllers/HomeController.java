package demo.controllers;


import demo.DatabaseConnection;
import demo.model.User;
import demo.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import demo.model.Association;
import demo.model.Membre;
import demo.model.Projet;
import demo.repository.associationRepo;
import demo.repository.memberRepo;
import demo.repository.projetRepo;


public class HomeController {
    
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
    private Pane pnlDons;

    @FXML
    private Pane pnlEvents;
    @FXML
    private Pane pnlUsers;

    @FXML
    private Pane pnlOffres;
    @FXML
    private Pane pnlBlog;
    @FXML
    private Button btnEvents;
    @FXML
    private Button btnBlog;

    @FXML
    private Button btnOffres;

    @FXML
    private Button btnDons;
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
    private Button ajouterButton;
    @FXML
    private Button ajouterButton2;

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
    @FXML
    private TableColumn<User, String> actionsColumn;
    @FXML
    private Button btnUsers;



    @FXML
    private TextField txtEmail;

    @FXML
    private Label name;
    @FXML
    private TextField searchemail;

    @FXML
    private CheckBox chkVerified;


    @FXML
    private Button btnSignout;

    public TableColumn<Association, String> dateDemandeColumn;

    public TableColumn<Association, String> emailColumn;
    public  TableColumn<Association, Integer> orderId1Column;
    public TableColumn<Association, String> name1Column;
    public TableColumn<Association, String> domain1Column;

    public TableColumn<Association, String> email1Column;
    public TableColumn<Association, String> adresseColumn;
    public TableColumn<Association, Void> actionsColumnAmir;
    
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
    private Pane pnlTypeDons;
    @FXML
    private Button btnTypeDons;
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
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> emailColumnAmir;

    @FXML
    private TableColumn<User, Boolean> verifiedColumn;

    @FXML
    private VBox root1;
    private UserRepository userRepository;

    public HomeController() {
        userRepository = new UserRepository(); // Instantiate your UserRepository
    }

    private double x = 0,y = 0;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private AnchorPane sideBar;
    @FXML
    private Label nbAssociation;
    @FXML
    private Label nbDemande;

    private Stage stage;
    @FXML
    private ListView<String> optionsListView;


    private final AssociationController associationController = new AssociationController();
    private final ProjetController projetController = new ProjetController();


    @FXML
    private ImageView profilImage2;
    
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

        User authenticatedUser = SignInController.getAuthenticatedUser();
        if (authenticatedUser != null) {
            name.setText(authenticatedUser.getEmail());
        }





//        System.out.println("Authenticated user email: " + authenticatedUser.getEmail());

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

        int projetsTermines = projetRepo.nbProjetTermine();
        int projetsEnCours = projetRepo.nbProjetEnCours();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Terminé", projetsTermines),
                new PieChart.Data("En cours", projetsEnCours)
        );

          pieChart.setData(pieChartData);

          int i = 0;
          for (PieChart.Data data : pieChartData) {
              String color = "";
              if (data.getName().equals("Terminé")) {
                  color = "#9CCBD6";
              } else if (data.getName().equals("En cours")) {
                  color = "#FFAB91";
              }
              pieChartData.get(i).getNode().setStyle("-fx-pie-color:" + color + "';'");
              i++;
          }

         pieChart.setLegendVisible(false);
          pieChart.setLabelLineLength(10);
          pieChart.setLabelsVisible(false);

          //amir
//        List<User> userList =  userRepository.getAllUsers();
//        userTable.getItems().addAll(userList);
//
//        // Set up a filtered list to filter based on email
//        FilteredList<User> filteredList = new FilteredList<>(userTable.getItems());
//
//        // Bind the filtered list to the TableView
//        userTable.setItems(filteredList);
//
//        // Add listener to the text field to filter based on email
//        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredList.setPredicate(user -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                // Filter based on email
//                String lowerCaseFilter = newValue.toLowerCase();
//                return user.getEmail().toLowerCase().contains(lowerCaseFilter);
//            });
//        });
//
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        emailColumnAmir.setCellValueFactory(new PropertyValueFactory<>("email"));
//        verifiedColumn.setCellValueFactory(new PropertyValueFactory<>("verified"));

        List<User> userList = userRepository.getAllUsers();
        userTable.getItems().addAll(userList);



        //amir
        Stage primaryStage = new Stage();
        loadDemandeFromDatabase(primaryStage);
        loadStatistique();
        btnOverview.setStyle("-fx-background-color:   #9CCBD6");
        pnlOverview.setStyle("-fx-background-color : #EFFCFF");
        pnlOverview.toFront();
    }
    private boolean vboxVisible = false;

    @FXML
    private void afficherOuCacherVBox() {
        vboxVisible = !vboxVisible;
        root1.setVisible(vboxVisible);
    }



        public void handleClicks(ActionEvent actionEvent) {
	    btnCustomers.setStyle("-fx-background-color:    #DDE6E8");
	    btnMenus.setStyle("-fx-background-color:    #DDE6E8");
	    btnOverview.setStyle("-fx-background-color:    #DDE6E8");
	    btnOrders.setStyle("-fx-background-color:    #DDE6E8");
	    btnMembers.setStyle("-fx-background-color:    #DDE6E8");
	    btnDons.setStyle("-fx-background-color: #DDE6E8 ");
        btnEvents.setStyle("-fx-background-color: #DDE6E8 ");
        btnOffres.setStyle("-fx-background-color: #DDE6E8");
        btnUsers.setStyle("-fx-background-color : #DDE6E8");
        btnBlog.setStyle("-fx-background-color : #DDE6E8");
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
		    
		    }else if (actionEvent.getSource() == btnEvents) {
                btnEvents.setStyle("-fx-background-color:  #9CCBD6");
                pnlEvents.setStyle("-fx-background-color:  #EFFCFF");
                loadEventFromDatabase();
                pnlEvents.toFront();

            }else if (actionEvent.getSource() == btnUsers) {
            pnlUsers.setStyle("-fx-background-color : #EFFCFF");
                btnUsers.setStyle("-fx-background-color:  #9CCBD6");
                pnlUsers.toFront();
            }

            else if (actionEvent.getSource() == btnTypeDons) {
                btnTypeDons.setStyle("-fx-background-color:  #9CCBD6");
                pnlTypeDons.setStyle("-fx-background-color:  #EFFCFF");
                pnlTypeDons.toFront();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/typeDons.fxml"));
                try {
                    Parent root = loader.load();
                    pnlTypeDons.getChildren().clear();
                    pnlTypeDons.getChildren().add(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getSource() == btnDons) {
                btnDons.setStyle("-fx-background-color:  #9CCBD6");
                pnlDons.setStyle("-fx-background-color:  #EFFCFF");
                pnlDons.toFront();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dons.fxml"));
                try {
                    Parent root = loader.load();
                    pnlDons.getChildren().clear();
                    pnlDons.getChildren().add(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (actionEvent.getSource() == btnBlog) {
                btnBlog.setStyle("-fx-background-color:  #9CCBD6");
                pnlBlog.setStyle("-fx-background-color:  #EFFCFF");
                pnlBlog.toFront();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/Comment.fxml"));
                try {
                    Parent root = loader.load();
                    pnlBlog.getChildren().clear();
                    pnlBlog.getChildren().add(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getSource() == btnOffres) {
                btnOffres.setStyle("-fx-background-color:  #9CCBD6");
                pnlOffres.setStyle("-fx-background-color:  #EFFCFF");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../datailsService.fxml"));
                try {
                    Parent root = loader.load();
                    pnlOffres.getChildren().clear();
                    root.setLayoutX(360);
                    root.setLayoutY(120);
                    pnlOffres.getChildren().add(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pnlOffres.toFront();
            }
            else if (actionEvent.getSource()==btnOverview){
                btnOverview.setStyle("-fx-background-color:  #9CCBD6");
                pnlOverview.setStyle("-fx-background-color:  #EFFCFF");
                pnlOverview.toFront();
                loadStatistique();
            }
	  }
    @FXML
    private void loadStatistique() {
        int nbAssoc = associationRepo.nombreAssoc();
        System.out.println("Nombre d'associations : " + nbAssoc);
        nbAssociation.setText(String.valueOf(nbAssoc));
        int nbDem = associationRepo.nombreDem();
        nbDemande.setText(String.valueOf(nbDem));

    }

    private void loadDemandeFromDatabase(Stage primaryStage) {
        List<Association> associations = associationRepo.getDemandes();
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
    }

    @FXML
    private void loadAssocFromDatabase(Stage primaryStage) {
               List<Association> associations = associationRepo.getAssociations();
               orderId1Column.setCellValueFactory(new PropertyValueFactory<>("id"));
               name1Column.setCellValueFactory(new PropertyValueFactory<>("nom"));
               domain1Column.setCellValueFactory(new PropertyValueFactory<>("domaineActivite"));
               email1Column.setCellValueFactory(new PropertyValueFactory<>("email"));
               adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
               detailColumn.setCellFactory(param -> new TableCell<Association, Void>() {
                   private final Button detailButton = new Button("Details");
           
                   {
                       detailButton.setOnAction(event -> {
                           Association association = getTableView().getItems().get(getIndex());
                           associationController.detail(association,primaryStage);
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


       modifierColumn.setCellFactory(param -> new TableCell<Association, Void>() {
           private final Button modifierButton = new Button("Modifier");

           {
               modifierButton.setOnAction(event -> {
                   Association association = getTableView().getItems().get(getIndex());
                   associationController.modifier(association,primaryStage);
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
       supprimerColumn.setCellFactory(param -> new TableCell<Association, Void>() {
           private final Button supprimerButton = new Button("Supprimer");

           {
               supprimerButton.setOnAction(event -> {
                   Association association = getTableView().getItems().get(getIndex());
                   associationController.supprimer(association,primaryStage);
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
                  
       ObservableList<Association> data = FXCollections.observableArrayList(associations);
       exampleTable1.setItems(data);           
      }
           
    private void loadProjetFromDatabase(Stage primaryStage) {
	      List<Projet> projets = projetRepo.getProjets();

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
	      }
	    
    private void loadMemberFromDatabase( Stage primaryStage) {
		List<Membre> membres = memberRepo.getMembres();

		               orderId3Column.setCellValueFactory(new PropertyValueFactory<>("id"));
		               nameMembreColumn.setCellValueFactory(new PropertyValueFactory<>("nomMembre"));
		               prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenomMembre"));
		               fonctionColumn.setCellValueFactory(new PropertyValueFactory<>("fonction"));
		               emailMembreColumn.setCellValueFactory(new PropertyValueFactory<>("emailMembre"));
		               detailMembreColumn.setCellFactory(param -> {
                           return new TableCell<Membre, Void>() {

                               private void handle(ActionEvent event) {
                                   Membre membre = getTableView().getItems().get(getIndex());

                                   demo.controllers.MemberController.detail(membre, primaryStage);
                               }

                               private final Button detailButton = new Button("Details");
                               private MemberController MemberController;

                               {
                                   detailButton.setOnAction(this::handle);
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
                           };
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
		      }

    private void loadEventFromDatabase() {
        pnlEvents.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../events.fxml"));
            Node node = loader.load();
            node.setLayoutX(260);
            node.setLayoutY(100);

            pnlEvents.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    public void handleDeleteUser(ActionEvent actionEvent) {
        // Get the selected user from the TableView
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            // If no user is selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
            return;
        }

        // Confirm with the user before deleting
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete the selected user?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User confirmed deletion, proceed with deletion
                boolean deleted = userRepository.deleteUser(selectedUser.getId());
                if (deleted) {
                    // User successfully deleted, remove from TableView
                    userTable.getItems().remove(selectedUser);
                    // Show success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("User deleted successfully.");
                    successAlert.showAndWait();
                } else {
                    // Show error message if deletion fails
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Failed to delete user. Please try again.");
                    errorAlert.showAndWait();
                }
            }
        });
    }



    @FXML
    public void handleUpdate(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Populate the update fields with the selected user's data
            txtEmail.setText(selectedUser.getEmail());
            chkVerified.setSelected(selectedUser.isVerified());
        } else {
            // If no user is selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to update.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleUpdateConfirm(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Get updated data from the fields
            String updatedEmail = txtEmail.getText();
            boolean updatedVerified = chkVerified.isSelected();

            // Validate the email format
            if (!isValidEmail(updatedEmail)) {
                // Show an error message if the email format is invalid
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid email address.");
                alert.showAndWait();
                return;
            }

            // Update the selected user's data
            selectedUser.setEmail(updatedEmail);
            selectedUser.setVerified(updatedVerified);

            // Call UserRepository.updateUser(selectedUser) to update the user in the database
            boolean success = userRepository.updateUser(selectedUser);

            if (success) {
                // If update is successful, show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User updated successfully.");
                alert.showAndWait();

                // Clear the update fields
                txtEmail.clear();
                chkVerified.setSelected(false);

                // Refresh the TableView to reflect the changes
                refreshUserTable();
            } else {
                // If update fails, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update user.");
                alert.showAndWait();
            }
        } else {
            // If no user is selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to update.");
            alert.showAndWait();
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Method to refresh the TableView with updated data
    private void refreshUserTable() {
        userTable.getItems().clear();
        userTable.getItems().addAll(userRepository.getAllUsers());
    }


    @FXML
    private void signOut() {
        Stage stage = (Stage) root1.getScene().getWindow();

        // Fermez la scène
        stage.close();
       /* try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SignIn.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnSignout.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }









}
