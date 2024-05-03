package demo.controllers;

import demo.Main;
import demo.model.Article;
import demo.model.Association;
import demo.model.Membre;
import demo.repository.associationRepo;
import demo.repository.memberRepo;
import demo.service.NewsAPIService;
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
import java.util.List;
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
    private Pane Home;
    @FXML
    private Pane inscrire;
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
    private WebView map;
    private WebEngine webEngine;
    private boolean mapLoaded = false;

    @FXML
    public void initialize() {
        Home.setVisible(true);
    }

    @FXML
    private void nav(MouseEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../profil.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
   @FXML
   private void navHome(MouseEvent event) throws IOException {
        newsPane.setVisible(false);
       profilPane.setVisible(false);
        inscrire.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(true);

       connect.setVisible(false);
    }


    @FXML
    private void navProfil(MouseEvent event) throws IOException {
        newsPane.setVisible(false);
        profilPane.setVisible(true);
        inscrire.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(false);
        connect.setVisible(false);
        profilPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../profil.fxml"));
        Node node = loader.load();
        node.setLayoutX(1080);
        node.setLayoutY(30);
        profilPane.getChildren().add(node);
    }

    @FXML
    private void navAsso(MouseEvent event) throws IOException {
    if (!mapLoaded) {
        profilPane.setVisible(false);
        newsPane.setVisible(false);
        inscrire.setVisible(false);
        Associations.setVisible(true);
        Home.setVisible(false);
        connect.setVisible(false);
        Associations.getChildren().clear();

        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPosition(0, 0.5);

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.load(getClass().getResource("../map.html").toExternalForm());

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {

            if (newState == Worker.State.SUCCEEDED) {
               List<Association> Adresses =associationRepo.getAssociations();
               for (Association association : Adresses){
                String[] latLng = getAddressLatLng(association.getAdresse());
                if (latLng[0] != null && latLng[1] != null) {
                    System.out.println("Latitude: " + latLng[0] + ", Longitude: " + latLng[1]);
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.call("updateMapEvent", latLng[0], latLng[1]);
                } else {
                    System.out.println("Coordonnées introuvables pour cette adresse.");
                }
                mapLoaded = true;
            }}
        });
        pane2.getChildren().add(webView);
        loadAssoc();

        Associations.getChildren().add(splitPane);

    }
}

    @FXML
   private void navNews(MouseEvent event) {
       // Effacer le contenu actuel de newsPane
       newsPane.setVisible(true);
        profilPane.setVisible(false);
       inscrire.setVisible(false);
       Associations.setVisible(false);
       Home.setVisible(false);
       connect.setVisible(false);
       newsPane.getChildren().clear();

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
       inscrire.getChildren().clear();

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
        profilPane.setVisible(false);
        connect.getChildren().clear();

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
            // Encodez l'adresse pour l'inclure dans l'URL
            String encodedAddress = URLEncoder.encode(address, "UTF-8");

            // Construisez l'URL de la requête
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress;

            // Créez un client HTTP
            HttpClient httpClient = HttpClients.createDefault();

            // Créez une requête HTTP GET
            HttpGet request = new HttpGet(url);

            // Envoyez la requête et obtenez la réponse
            org.apache.http.HttpResponse response = httpClient.execute(request);

            // Analysez la réponse JSON
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONArray results = new JSONArray(jsonResponse);
            if (results.length() > 0) {
                JSONObject result = results.getJSONObject(0);
                latLng[0] = result.getString("lat");
                latLng[1] = result.getString("lon");
            } else {
                System.out.println("Aucune coordonnée trouvée pour cette adresse.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }
    private void loadAssoc() {
        pane1.getChildren().clear();

        List<Association> associations = associationRepo.getAssociations();

        for (Association association : associations) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../associationsItem.fxml"));
                Node node = loader.load();
                node.setLayoutX(50);
                node.setLayoutY(30);
                Label nom = (Label) node.lookup("#nomAssoc");
                Label domaine = (Label) node.lookup("#domaine");
                Label telephoneLabel = (Label) node.lookup("#telephone");
                Label descrip = (Label) node.lookup("#descrip");
                Label emailLabel = (Label) node.lookup("#email");
                Label adresseLabel = (Label) node.lookup("#adresse");
                nom.setText(association.getNom());
                domaine.setText(String.valueOf(association.getDomaineActivite()));
                telephoneLabel.setText(String.valueOf(association.getTelephone()));
                emailLabel.setText(association.getEmail());
                adresseLabel.setText(association.getAdresse());
                descrip.setText(association.getDescription());
                ScrollPane sp = new ScrollPane();
                sp.setContent(node);
                sp.setFitToWidth(true);
                sp.setFitToHeight(true);

                pane1.getChildren().add(sp);

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}



