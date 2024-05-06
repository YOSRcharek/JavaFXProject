package demo.controllers;

import demo.model.Article;
import demo.service.NewsAPIService;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class NewsController {
    @FXML
    public void initialize() {
        Stage primaryStage= new Stage();
        loadNews(primaryStage);
    }
    private static final int SLIDE_WIDTH = 400;
    private static final int SLIDE_HEIGHT = 300;
    private static final Duration ANIMATION_DURATION = Duration.seconds(1);

    static NewsAPIService newsService = new NewsAPIService();
    static List<Article> articles = newsService.getAssociationNews();
    private static int currentIndex = 0;
    public static void loadNews(Stage primaryStage) {
        VBox carousel = new VBox();
        carousel.setSpacing(20);
        carousel.setPadding(new Insets(20));

        for (Article article : articles) {
            HBox slide = createSlide(article);
            carousel.getChildren().add(slide);
        }

        showSlide(carousel, 0);

        ScrollPane scrollPane = new ScrollPane(carousel);
        scrollPane.setFitToWidth(true); // Make sure the scroll bar appears only when needed
        scrollPane.setPrefSize(800, 600); // Set preferred size for the scroll pane

        primaryStage.setScene(new Scene(new StackPane(scrollPane)));
        primaryStage.setTitle("Carousel Demo");
        primaryStage.show();
    }

    private static HBox createSlide(Article article) {
        HBox slide = new HBox();
        slide.setPrefSize(SLIDE_WIDTH, SLIDE_HEIGHT);

        // Image
        ImageView imageView = new ImageView(new Image(article.getImageUrl()));
        imageView.setFitWidth(SLIDE_WIDTH);
        imageView.setFitHeight(SLIDE_HEIGHT);

        // Title
        Label titleLabel = new Label(article.getTitle());

        slide.getChildren().addAll(imageView, titleLabel);

        return slide;
    }

    private static void showSlide(VBox carousel, int index) {
        if (index < 0 || index >= articles.size()) {
            return;
        }

        TranslateTransition transition = new TranslateTransition(ANIMATION_DURATION, carousel);
        transition.setToX(-index * (SLIDE_WIDTH + carousel.getSpacing()));
        transition.play();

        currentIndex = index;
    }

}
