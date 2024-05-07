package demo.controllers;

import com.sun.prism.paint.Color;
import demo.Main;
import demo.model.Article;
import demo.model.Association;
import demo.model.Membre;
import demo.repository.associationRepo;
import demo.repository.memberRepo;
import demo.service.NewsAPIService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import netscape.javascript.JSObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
public class loginController {

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView2;
    @FXML
    private Pane connect;
    @FXML
    private Pane profilPane;
    @FXML
    private Pane newsPane;
    @FXML
    private Pane donsPane;
    @FXML
    private Pane Home;
    @FXML
    private Pane inscrire;
    @FXML
    private Pane signUp;
    @FXML
    private Pane Associations;
    @FXML
    private ScrollPane ScrollPaneNews;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane detailAsso;
    @FXML
    private WebView map;
    private WebEngine webEngine;
    private boolean mapLoaded = false;
    @FXML
    private TextField searchAssoc;
    @FXML
    private TextField adresseSearch;
    @FXML
    private Button searchBtn;
    @FXML
    private HBox hboxSearch;
    @FXML
    private VBox root;
    @FXML
    private HBox buttonBox;
    private boolean isConnected = false;
    @FXML
    private VBox root1;
    @FXML
    private WebView chatBotWebView;

    @FXML
    private ImageView profil;
    private final ObservableList<Association> associationsList = FXCollections.observableArrayList();
    @FXML
    ListView<Association> associationsListView = new ListView<>();
    @FXML
    public void initialize() {
        updateVisibility();
        Home.setVisible(true);
        root.setVisible(false);
        signUp.setVisible(false);
        associationsListView.setVisible(false);
        root.setLayoutX(71);
        root.setLayoutY(67);
        initMap();
        searchAssoc.setOnKeyReleased(event -> {
            String searchTerm = searchAssoc.getText();
            List<Association> associations = getAssociations(searchTerm);
            associationsList.setAll(associations);
            root.setVisible(!associations.isEmpty());
            associationsListView.setVisible(!associations.isEmpty());
        });

        associationsListView.setItems(associationsList);
        associationsListView.setOnMouseClicked(event -> {
            Association selectedAssociation = associationsListView.getSelectionModel().getSelectedItem();
            if (selectedAssociation != null) {
                searchAssoc.setText(selectedAssociation.getNom());
                root.setVisible(false);
                associationsListView.setVisible(false);
            }
        });
        chatBotWebView.getEngine().load("https://console.dialogflow.com/api-client/demo/embedded/3b6bf03e-8a2c-40a5-8b63-03aba37653d5");
        searchBtn.setOnAction(event -> {
            Association selectedAssociation = associationsListView.getSelectionModel().getSelectedItem();
            if (selectedAssociation != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../associationDetails.fxml"));

                    loader.setControllerFactory(controllerClass -> {
                        if (controllerClass == AssoDetails.class) {
                            return new AssoDetails(selectedAssociation.getId());
                        } else {
                            try {
                                return controllerClass.getDeclaredConstructor().newInstance();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    Parent root = loader.load();
                    Stage stage = (Stage) searchBtn.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void updateVisibility() {
        buttonBox.setVisible(!isConnected); // Afficher les boutons si l'utilisateur n'est pas connecté
        profil.setVisible(isConnected); // Afficher l'image de profil si l'utilisateur est connecté
    }


    @FXML
   private void navHome(MouseEvent events) throws IOException {
        newsPane.setVisible(false);
       profilPane.setVisible(false);
        inscrire.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(true);
       connect.setVisible(false);
        donsPane.setVisible(false);
        signUp.setVisible(false);

   }
    public List<Association> getAssociations(String searchTerm) {
        List<Association> allAssociations = associationRepo.getAssociations();

        List<Association> filteredAssociations = new ArrayList<>();
        for (Association association : allAssociations) {
            if (association.getNom().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredAssociations.add(association);
            }
        }

        return filteredAssociations;
    }

    @FXML
    private void navProfil(MouseEvent event) throws IOException {
        newsPane.setVisible(false);
        profilPane.setVisible(true);
        inscrire.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(false);
        connect.setVisible(false);
        donsPane.setVisible(false);
        signUp.setVisible(false);
        profilPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../profil.fxml"));
        Node node = loader.load();
        node.setLayoutX(1080);
        node.setLayoutY(30);
        profilPane.getChildren().add(node);
    }

    @FXML
    private void navAsso(MouseEvent event) throws IOException {
        root1.setVisible(false);
        profilPane.setVisible(false);
        newsPane.setVisible(false);
        inscrire.setVisible(false);
        Associations.setVisible(true);
        Home.setVisible(false);
        connect.setVisible(false);
        Associations.getChildren().clear();
        donsPane.setVisible(false);
        signUp.setVisible(false);
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPosition(0, 0.5);

        // Initialiser la carte une seule fois
        initMap();

        // Créer une seule instance de WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("../map.html").toExternalForm());

        // Afficher les informations des associations
        ScrollPane sp = new ScrollPane();
        VBox content = new VBox();
        pane1.getChildren().clear(); // Effacez le contenu précédent de pane1

        List<Association> associations = associationRepo.getAssociations();
        for (Association association : associations) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../associationsItem.fxml"));
            Node node = loader.load();
            Label nom = (Label) node.lookup("#nomAssoc");
            Label domaine = (Label) node.lookup("#domaine");
            Label telephoneLabel = (Label) node.lookup("#telephone");
            Label descrip = (Label) node.lookup("#descrip");
            Label emailLabel = (Label) node.lookup("#email");
            Label adresseLabel = (Label) node.lookup("#adresse");

            domaine.setText(String.valueOf(association.getDomaineActivite()));
            telephoneLabel.setText(String.valueOf(association.getTelephone()));
            emailLabel.setText(association.getEmail());
            adresseLabel.setText(association.getAdresse());
            descrip.setText(association.getDescription());
            nom.setText(association.getNom());

            content.getChildren().add(node);
            content.setOnMouseClicked(e -> {
                // Attendre que la carte soit chargée avant de zoomer sur le marqueur
                webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        // Récupérer les coordonnées de l'adresse
                        String[] latLng = getAddressLatLng(association.getAdresse());
                        // Zoom sur la position sur la carte
                        if (latLng != null && latLng.length == 2) {
                            zoomToMarker(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]), webView);
                        }
                    }
                });
            });
        }

        content.setPrefSize(900, 900);
        sp.setContent(content);

        sp.setContent(content);
        pane1.getChildren().add(sp);

        Associations.getChildren().add(splitPane);
    }

    private void zoomToMarker(double latitude, double longitude, WebView map) {
        map.getEngine().executeScript("map.setView([" + latitude + ", " + longitude + "], 15)");
    }



    @FXML
   private void navNews(MouseEvent event) {
        signUp.setVisible(false);
       newsPane.setVisible(true);
        profilPane.setVisible(false);
       inscrire.setVisible(false);
       Associations.setVisible(false);
       Home.setVisible(false);
       connect.setVisible(false);
       newsPane.getChildren().clear();
        donsPane.setVisible(false);
       // Récupérer les articles de news à partir du service de news
       NewsAPIService newsService = new NewsAPIService();
       List<Article> articles = newsService.getAssociationNews();

       // Créer une VBox pour contenir tous les articles
       VBox articleContainer = new VBox();
       articleContainer.setSpacing(20);

       // Parcourir chaque article et afficher son contenu dans articleContainer
       for (Article article : articles) {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("../newsItem.fxml"));
               Node node = loader.load();

               // Récupérer les éléments de l'interface utilisateur à l'intérieur du nœud
               Label titre = (Label) node.lookup("#titre");
               Label month = (Label) node.lookup("#month");
               Label year = (Label) node.lookup("#year");
               Label day = (Label) node.lookup("#day");
               Label descrip = (Label) node.lookup("#descrip");
               ImageView image = (ImageView) node.lookup("#image");

               titre.setText(article.getTitle());
               descrip.setText(article.getDescription());

               // Convertir la date de publication en LocalDate
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
               LocalDateTime dateTime = LocalDateTime.parse(article.getPublishedAt(), formatter);
               LocalDate publishedDate = dateTime.toLocalDate();

               day.setText(String.valueOf(publishedDate.getDayOfMonth()));

               month.setText(String.valueOf(publishedDate.getMonthValue()));
               year.setText(String.valueOf(publishedDate.getYear()));
               image.setImage(new Image(article.getImageUrl()));

               articleContainer.getChildren().add(node);
           } catch (IOException ex) {
               Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }

       ScrollPane scrollPane = new ScrollPane(articleContainer);
       scrollPane.setFitToWidth(true);
       scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
       scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
       newsPane.getChildren().add(scrollPane);
   }
   @FXML
   private void signUp(ActionEvent event) throws IOException {
       newsPane.setVisible(false);
       Associations.setVisible(false);
       Home.setVisible(false);
       connect.setVisible(false);
       inscrire.setVisible(true);
       profilPane.setVisible(false);
       signUp.setVisible(false);
       inscrire.getChildren().clear();
       donsPane.setVisible(false);
       BackgroundImage backgroundImage = new BackgroundImage(imageView2.snapshot(null, null),
               BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
               new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
       Background background = new Background(backgroundImage);
       inscrire.setBackground(background);

       FXMLLoader loader = new FXMLLoader(getClass().getResource("../inscrire.fxml"));
       Node node = loader.load();

       node.setLayoutX(1810);
       node.setLayoutY(5);

       inscrire.getChildren().add(node);
   }
    @FXML
    private void signIn(ActionEvent event) throws IOException {
        newsPane.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(false);
        inscrire.setVisible(false);
        connect.setVisible(true);
        signUp.setVisible(false);
        profilPane.setVisible(false);
        connect.getChildren().clear();
        donsPane.setVisible(false);
        BackgroundImage backgroundImage = new BackgroundImage(imageView2.snapshot(null, null),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background background = new Background(backgroundImage);
        connect.setBackground(background);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../connect.fxml"));
        Node node = loader.load();

        node.setLayoutX(1810);
        node.setLayoutY(20);

        connect.getChildren().add(node);
    }

    public static String[] getAddressLatLng(String address) {
        String[] latLng = new String[2];
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress;
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            org.apache.http.HttpResponse response = httpClient.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONArray results = new JSONArray(jsonResponse);
            if (results.length() > 0) {
                JSONObject result = results.getJSONObject(0);
                latLng[0] = result.getString("lat");
                latLng[1] = result.getString("lon");
            } else {
                System.out.println("Aucune coordonnée trouvée pour cette adresse: " + address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }


    private void initMap() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.load(getClass().getResource("../map.html").toExternalForm());
        List<Association> associations = associationRepo.getAssociations();
        Set<String> addressesProcessed = new HashSet<>();

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                for (Association association : associations) {
                    String address = association.getAdresse();
                    if (!addressesProcessed.contains(address)) {
                        addressesProcessed.add(address);
                        String[] latLng = getAddressLatLng(address);
                        if (latLng[0] != null && latLng[1] != null) {
                            System.out.println("Latitude: " + latLng[0] + ", Longitude: " + latLng[1]);
                            JSObject window = (JSObject) webEngine.executeScript("window");
                            window.call("updateMapEvent", latLng[0], latLng[1], association.getNom());

                        } else {
                            System.out.println("Coordonnées introuvables pour cette adresse: " + address);
                        }
                    }
                }
            }
        });

        pane2.getChildren().add(webView);
    }


    public void navDons(MouseEvent mouseEvent) throws IOException {
        newsPane.setVisible(false);
        profilPane.setVisible(false);
        inscrire.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(false);
        connect.setVisible(false);
        signUp.setVisible(false);
        donsPane.setVisible(true);
        donsPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../DonsFront.fxml"));
        Node node = loader.load();
        donsPane.getChildren().add(node);

    }

    public void signUpVol(ActionEvent actionEvent) throws IOException {
        newsPane.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(false);
        connect.setVisible(false);
        inscrire.setVisible(false);
        profilPane.setVisible(false);
        signUp.setVisible(true);
        inscrire.getChildren().clear();
        donsPane.setVisible(false);
        BackgroundImage backgroundImage = new BackgroundImage(imageView2.snapshot(null, null),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background background = new Background(backgroundImage);
        signUp.setBackground(background);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../signUp.fxml"));
        Node node = loader.load();

        node.setLayoutX(790);
        node.setLayoutY(10);

        signUp.getChildren().add(node);
    }
    private boolean vboxVisible = false;

    @FXML
    private void afficherOuCacherVBox() {
        vboxVisible = !vboxVisible;
        root1.setVisible(vboxVisible);
    }

    public void signOut(MouseEvent mouseEvent) {
        Stage stage = (Stage) root1.getScene().getWindow();
        stage.close();
    }
}



