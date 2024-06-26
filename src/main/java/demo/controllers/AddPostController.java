package demo.controllers;

import demo.HelloApplication;
import demo.model.Post;
import demo.service.PostService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddPostController {
    @FXML
    private TextField TitleF;

    @FXML
    private Label checkContent;

    @FXML
    private Label checkQuote;

    @FXML
    private Label checkTilte;

    @FXML
    private TextArea contentf;

    @FXML
    private ImageView imageview;

    @FXML
    private TextArea quoteF;

    @FXML
    private MediaView videomidia;
    @FXML
    private Button jouerbtn;

    @FXML
    private Button pausebtn;

    @FXML
    private Button arreterbtn;

    private String imgpath;
    private String videopath;
    private final PostService postService=new PostService();
    private static final String API_URL = "https://neutrinoapi.net/bad-word-filter";
    private static final String API_KEY = "9eOCO78zUJQOFaJcnz63ULfe79SZmrJfaeGvFo5r1iasXllt";
    boolean checkTilte( ){
        if(TitleF.getText()!=""){

            return true;
        }else{
            checkTilte.setVisible(true);
            return false;
        }}
    boolean checkQuote( ){
        if(quoteF.getText()!=""){

            return true;
        }else{
            checkQuote.setVisible(true);
            return false;
        }}
    boolean checkContent( ){
        if(contentf.getText()!=""){

            return true;
        }else{
            checkContent.setVisible(true);
            return false;
        }}
    @FXML
    void addimg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a picture");
        // Set file extension filter to only allow image files
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null && isImageFile(selectedFile)) {
            imgpath = selectedFile.getAbsolutePath().replace("\\", "/");
            System.out.println("File path stored: " + imgpath);

            Image image = new Image(selectedFile.toURI().toString());
            imageview.setImage(image);
        } else {
            System.out.println("Please select a valid image file.");
        }

    }
    public String getFilePath() {
        return imgpath;
    }
    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return image.isError() ? false : true;
        } catch (Exception e) {
            return false;
        }
    }
    /////
    @FXML
    void addVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a video");
        // Set file extension filter to only allow video files
        FileChooser.ExtensionFilter videoFilter =
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv");
        fileChooser.getExtensionFilters().add(videoFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null && isVideoFile(selectedFile)) {
            String videoPath = selectedFile.getAbsolutePath().replace("\\", "/");
            videopath = videoPath;
            File file1 = new File(videoPath);
            Media video=new Media(file1.toURI().toString());
            MediaPlayer m1=new MediaPlayer(video);
            videomidia.setMediaPlayer(m1);
            MediaPlayer mediaPlayer = new MediaPlayer(video);
            videomidia.setMediaPlayer(mediaPlayer);
            jouerbtn.setOnAction(event -> mediaPlayer.play());
            pausebtn.setOnAction(event -> mediaPlayer.pause());
            arreterbtn.setOnAction(event -> mediaPlayer.stop());
            videomidia.setVisible(true);
            jouerbtn.setVisible(true);
            pausebtn.setVisible(true);
            arreterbtn.setVisible(true);
        } else {
            System.out.println("Please select a valid video file.");
        }
    }

    // Method to check if the selected file is a video file
    private boolean isVideoFile(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("mp4") || extension.equals("avi") || extension.equals("mkv");
    }
    @FXML
    void ajouter(){
        checkTilte();checkQuote( );checkContent( );
        if(checkTilte() && checkQuote() && checkContent()) {
            if (!containsBadWords(TitleF.getText()) && !containsBadWords(contentf.getText()) &&!containsBadWords(quoteF.getText())){
                Post post = new Post();
            post.setUsername_id(1);
            post.setTitle(TitleF.getText());
            post.setContent(contentf.getText());
            post.setQuote(quoteF.getText());
            post.setImage(imgpath);
            post.setVideo(videopath);
            post.setCreatedat(Date.valueOf(LocalDate.now()));
            try {
                postService.ajouter(post);
                showSuccessAlert("Post added successfully");
                Scene scenefer = quoteF.getScene();
                Stage stagefer = (Stage) scenefer.getWindow();
                stagefer.close();
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/Posts.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                showErrorAlert("Error adding the Post : " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        } else showErrorAlert("Vous avez utiliser de movaise maux interdit ");

    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static boolean containsBadWords(String text) {
        try {
            URL url = new URL(API_URL + "?content=" + text);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-ID", "IsmailChouikhi");
            connection.setRequestProperty("API-Key", API_KEY);
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                JSONObject jsonResponse = new JSONObject(response.toString());
                boolean isBad = jsonResponse.getBoolean("is-bad");
                return isBad;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
