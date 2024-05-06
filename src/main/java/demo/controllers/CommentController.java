package demo.controllers;

import demo.model.Comment;
import demo.model.Post;
import demo.service.CommentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class CommentController {
    @FXML
    private TableView<Comment> tableView;
    @FXML
    private Label TitleF;

    @FXML
    private Button arreterbtn;
    @FXML
    private Label contentf;

    @FXML
    private ImageView imageview;

    @FXML
    private Button jouerbtn;

    @FXML
    private Button pausebtn;

    @FXML
    private Label quoteF;
    @FXML
    private MediaView videomidia;


    private final CommentService commentService = new CommentService();
    private Post post =new Post();
    private String imgpath;
    private String videopath;
    @FXML
    public void initialize(Post post) {
        this.post = post;
        TitleF.setText("Title :"+post.getTitle());
        contentf.setText("Post Content  :"+post.getContent());
        quoteF.setText("Quote  :"+post.getQuote());
        imgpath = post.getImage();
        if (imgpath != null) {
            File imageFile = new File(imgpath);
            if (imageFile.exists()) {
                String imageUrl = imageFile.toURI().toString();
                imageview.setImage(new Image(imageUrl));
                imageview.setVisible(true);
            }
        }

        videopath = post.getVideo();
        if (videopath != null && !videopath.isEmpty()) {
            videomidia.setVisible(true);
            jouerbtn.setVisible(true);
            pausebtn.setVisible(true);
            arreterbtn.setVisible(true);
            File file1 = new File(videopath);
            Media video = new Media(file1.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(video);
            videomidia.setMediaPlayer(mediaPlayer);
            jouerbtn.setOnAction(event -> mediaPlayer.play());
            pausebtn.setOnAction(event -> mediaPlayer.pause());
            arreterbtn.setOnAction(event -> mediaPlayer.stop());

            // Release MediaPlayer resources when it's no longer needed
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            });
        }
        refreshComments();
    }

    private void refreshComments() {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn<Comment, String> contentCol = new TableColumn<>("Content");
        contentCol.setCellValueFactory(new PropertyValueFactory<>("contentcomment"));

        TableColumn<Comment, Integer> upvotesCol = new TableColumn<>("Upvotes");
        upvotesCol.setCellValueFactory(new PropertyValueFactory<>("upvotes"));

        TableColumn<Comment, Date> createdatCol = new TableColumn<>("Created At");
        createdatCol.setCellValueFactory(new PropertyValueFactory<>("createdatcomment"));

        tableView.getColumns().addAll(contentCol, upvotesCol, createdatCol);

        try {
            tableView.getItems().addAll(commentService.getByIdUser(this.post.getId()));
        } catch (SQLException e) {
            showErrorAlert("Error fetching comments: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void gohome(ActionEvent event) throws IOException {
        Scene scene1 = tableView.getScene();
        Stage stage1 = (Stage) scene1.getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Posts.fxml"));
        Parent root = null;
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Posts");
            stage.show();


    }

}
