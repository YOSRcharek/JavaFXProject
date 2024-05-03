package demo.controllers;

import demo.Main;
import demo.model.Article;
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

public class loginController {

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView2;
    @FXML
    private Pane connect;
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
        inscrire.setVisible(false);
        Associations.setVisible(false);
        Home.setVisible(true);

       connect.setVisible(false);
    }


   @FXML
   private void navAsso(MouseEvent event) throws IOException {
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
       webEngine.load("file:///C:/Users/yosry/eclipse-workspace/IDE_2024/demo/src/main/resources/demo/map.html");

       webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
           if (newState == Worker.State.SUCCEEDED) {
               webEngine.executeScript("ajouterMarqueurJS(36.850015154741065, 10.216590673622838);");


           }
       });
       pane2.getChildren().add(webView);
       Associations.getChildren().add(splitPane);
    }

   @FXML
   private void navNews(MouseEvent event) {
       // Effacer le contenu actuel de newsPane
       newsPane.setVisible(true);
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


}



