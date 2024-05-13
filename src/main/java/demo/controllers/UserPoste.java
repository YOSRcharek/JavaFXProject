package demo.controllers;
import demo.HelloApplication;
import  demo.model.Comment;
import demo.model.Post;
import demo.service.CommentService;
import demo.service.PostService;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class UserPoste {
    private static final String API_URL = "https://neutrinoapi.net/bad-word-filter";
    private static final String API_KEY = "9eOCO78zUJQOFaJcnz63ULfe79SZmrJfaeGvFo5r1iasXllt";
    @FXML
    ScrollPane ScrollePan;
    @FXML
    private TextField recherchefld;

    int choix=1;
   int iduser=1;

    private final PostService postService=new PostService();
    private final CommentService commentService=new CommentService();

    @FXML
    public void initialize() {
        recherchefld.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            if(recherchefld.getText().isEmpty()){loadPosts(getposte(choix));}
            else{List<Post> posts;
                posts=rechercher(getposte(choix),newValue);
                loadPosts(posts);}
        });
        loadPosts(getposte(choix));    }



    public static List<Post> rechercher(List<Post> liste, String recherche) {
        List<Post> resultats = new ArrayList<>();

        for (Post element : liste) {
            if (element.getTitle().contains(recherche) ||element.getContent().contains(recherche)) {
                resultats.add(element);
            }
        }

        return resultats;
    }

   List<Post> getposte(int choix){
       List<Post> postList = null;
       try {if (choix==1){
           postList = postService.getAll();}
       else{postList = postService.getByIdUser(1);}

       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return postList;
   }


    @FXML
    void loaAllPoste(){
      choix=1;
      loadPosts(getposte(choix));
    }

    @FXML
    void loaUserPoste(){
        choix=2;
        loadPosts(getposte(choix));
    }
    private void loadPosts(List<Post> postList) {
        FlowPane reclamationFlowPane = new FlowPane();
        reclamationFlowPane.setStyle("-fx-pref-width: 950px; " +
                "-fx-pref-height: 547px;");
        for (Post post : postList) {
            VBox cardContainer = createPostVBox(post,choix);
            reclamationFlowPane.getChildren().add(cardContainer);
            FlowPane.setMargin(cardContainer, new Insets(10));
        }
        ScrollePan.setContent(reclamationFlowPane);
    }

    private VBox createPostVBox(Post post, int choix) {
        String imagepth = post.getImage();
        String viseopth = post.getVideo();
        VBox cardContainer = new VBox();
        cardContainer.setStyle(
                "-fx-padding: 15px 15px 15px 15px; " +
                        "-fx-background-color: #EFFCFF; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: #9CCBD6; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-width: 1px; ");

        Pane pane = new Pane();
        pane.setLayoutX(403.0);
        pane.setLayoutY(130.0);
        pane.setPrefHeight(543.0);
        pane.setPrefWidth(631.0);
        pane.setStyle(
                "-fx-padding: 10px;");

        MediaView mediaView = new MediaView();
        mediaView.setFitWidth(400.0);
        mediaView.setFitHeight(200.0);
        mediaView.setLayoutX(109.0);
        mediaView.setLayoutY(132.0);
        mediaView.setVisible(false);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(400.0);
        imageView.setFitHeight(200.0);
        imageView.setLayoutX(109.0);
        imageView.setLayoutY(132.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setVisible(false);



        Button jouerbtn = new Button();
        jouerbtn.setLayoutX(512.0);
        jouerbtn.setLayoutY(174.0);
        jouerbtn.setMnemonicParsing(false);
        jouerbtn.setPrefHeight(26.0);
        jouerbtn.setPrefWidth(52.0);
        jouerbtn.setText("Play");
        jouerbtn.setVisible(false);

        Button pausebtn = new Button();
        pausebtn.setLayoutX(512.0);
        pausebtn.setLayoutY(219.0);
        pausebtn.setMnemonicParsing(false);
        pausebtn.setPrefHeight(26.0);
        pausebtn.setPrefWidth(52.0);
        pausebtn.setText("Pause");
        pausebtn.setVisible(false);

        Button arreterbtn = new Button();
        arreterbtn.setLayoutX(512.0);
        arreterbtn.setLayoutY(264.0);
        arreterbtn.setMnemonicParsing(false);
        arreterbtn.setPrefHeight(26.0);
        arreterbtn.setPrefWidth(52.0);
        arreterbtn.setText("Areet");
        arreterbtn.setVisible(false);
/********************************************************************************************/
        Button videoBtn = new Button();
        videoBtn.setLayoutX(14.0);
        videoBtn.setLayoutY(175.0);
        videoBtn.setMnemonicParsing(false);
        videoBtn.setPrefHeight(25.0);
        videoBtn.setPrefWidth(81.0);
        videoBtn.setText("Video");

        Button imageBtn = new Button();
        imageBtn.setLayoutX(14.0);
        imageBtn.setLayoutY(220.0);
        imageBtn.setMnemonicParsing(false);
        imageBtn.setPrefHeight(25.0);
        imageBtn.setPrefWidth(81.0);
        imageBtn.setText("Image");



        if (imagepth != null) {
            File imageFile = new File(imagepth);
            if (imageFile.exists()) {
                String imageUrl = imageFile.toURI().toString();
                imageView.setImage(new Image(imageUrl));
                imageView.setVisible(true);
            } else {
                System.out.println("Fichier d'image introuvable : " + imagepth);
            }
        } else {
            imageBtn.setVisible(false);
        }
        videoBtn.setOnAction(actionEvent -> {
            imageView.setVisible(false);
            mediaView.setVisible(true);
            jouerbtn.setVisible(true);
            pausebtn.setVisible(true);
            arreterbtn.setVisible(true);
            File file1 = new File(viseopth);
            Media video = new Media(file1.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(video);
            mediaView.setMediaPlayer(mediaPlayer);

            jouerbtn.setOnAction(event -> mediaPlayer.play());
            pausebtn.setOnAction(event -> mediaPlayer.pause());
            arreterbtn.setOnAction(event -> mediaPlayer.stop());
        });


/****************************************************************************************************/
        Label userLabel = new Label();
        userLabel.setLayoutX(43.0);
        userLabel.setLayoutY(14.0);
        userLabel.setPrefHeight(17.0);
        userLabel.setPrefWidth(99.0);
        userLabel.setText(post.getTitle());

        Label dateLabel = new Label();
        dateLabel.setLayoutX(54.0);
        dateLabel.setLayoutY(31.0);
        dateLabel.setPrefHeight(17.0);
        dateLabel.setPrefWidth(99.0);
        dateLabel.setText(String.valueOf(post.getCreatedat()));

        Label contentLabel = new Label();
        contentLabel.setLayoutX(66.0);
        contentLabel.setLayoutY(57.0);
        contentLabel.setPrefHeight(75.0);
        contentLabel.setPrefWidth(538.0);
        contentLabel.setText(post.getContent());
        Comment c=new Comment();
        try {
            c=commentService.getOneById(post.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label commentLabel = new Label();
        commentLabel.setLayoutX(103.0);
        commentLabel.setLayoutY(371.0);
        commentLabel.setPrefHeight(17.0);
        commentLabel.setPrefWidth(490.0);

        Label commentDataLabel = new Label();
        commentDataLabel.setLayoutX(114.0);
        commentDataLabel.setLayoutY(381.0);
        commentDataLabel.setPrefHeight(42.0);
        commentDataLabel.setPrefWidth(490.0);
        if(c!=null){commentLabel.setText("Lasted comment at "+String.valueOf(c.getCreatedatcomment()));commentDataLabel.setText("Comment data : "+c.getContentcomment());}

        TextArea commentTextArea = new TextArea();
        commentTextArea.setLayoutX(114.0);
        commentTextArea.setLayoutY(445.0);
        commentTextArea.setPrefHeight(59.0);
        commentTextArea.setPrefWidth(410.0);
        commentTextArea.setPromptText("New Comment");

        Label CheckComment = new Label("*Insert a Comment");
        CheckComment.setLayoutX(114.0);
        CheckComment.setLayoutY(504.0);
        CheckComment.setTextFill(Color.RED);
        CheckComment.setVisible(false);

        Button addBtn = new Button();
        addBtn.setLayoutX(538.0);
        addBtn.setLayoutY(462.0);
        addBtn.setMnemonicParsing(false);
        addBtn.setPrefHeight(25.0);
        addBtn.setPrefWidth(81.0);
        addBtn.setText("ADD");
        addBtn.setOnAction(actionEvent -> {
            if (commentTextArea.getText()!=""){
                if (!containsBadWords(commentTextArea.getText())){
                    Comment comment=new Comment();
                    comment.setIdpost_id(post.getId());
                    comment.setUsername_id(iduser);
                    comment.setContentcomment(commentTextArea.getText());
                    comment.setCreatedatcomment(Date.valueOf(LocalDate.now()));
                    try {

                        commentService.ajouter(comment);
                        showSuccessAlert("Comment added successfully");
                        String body = "Dear " + postService.getusermail(post.getUsername_id()) + ",\n\n" +
                                postService.getusermail(iduser) + " has commented on your post" + ".\n\n" +
                                "The comment was made on " + LocalDateTime.now() +".\n\n" +
                                "Post content: " + post.getContent() +".\n\n" +
                                "Comment: "+commentTextArea.getText()+".\n\n" +
                                "Thank you for your trust.";
                        System.out.println(body);
                        sendEmail(postService.getusermail(post.getUsername_id()), "Notification", body);


                        loadPosts(getposte(choix));

                    } catch (SQLException e) {
                        showErrorAlert("Error adding the Comment : " + e.getMessage());
                    }

                }
                else showErrorAlert("Vous avez utiliser de movaise maux interdit ");
            }
            else{CheckComment.setVisible(true);}
        });
        ImageView like = new ImageView(new Image(getClass().getResourceAsStream("/image/like.png")));
        like.setFitHeight(30.0);
        like.setFitWidth(30.0);
        like.setLayoutX(578.0);
        like.setLayoutY(10.0);
        like.setPickOnBounds(true);
        like.setPreserveRatio(true);

        ImageView dislike = new ImageView(new Image(getClass().getResourceAsStream("/image/dislike.png")));
        dislike.setFitHeight(30.0);
        dislike.setFitWidth(30.0);
        dislike.setLayoutX(519.0);
        dislike.setLayoutY(10.0);
        dislike.setPickOnBounds(true);
        dislike.setPreserveRatio(true);

        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/edit.png")));
        editImageView.setFitHeight(42.0);
        editImageView.setFitWidth(35.0);
        editImageView.setLayoutX(519.0);
        editImageView.setLayoutY(10.0);
        editImageView.setPickOnBounds(true);
        editImageView.setPreserveRatio(true);

        editImageView.setOnMouseClicked(event -> {
            modifierPost(post);
        });

        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/delete.png")));
        deleteImageView.setFitHeight(35.0);
        deleteImageView.setFitWidth(42.0);
        deleteImageView.setLayoutX(578.0);
        deleteImageView.setLayoutY(10.0);
        deleteImageView.setPickOnBounds(true);
        deleteImageView.setPreserveRatio(true);

        deleteImageView.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette réservation ?");
            alert.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    postService.supprimer(post.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Afficher le nombre de likes et dislikes
        Label likeCountLabel = new Label();
        likeCountLabel.setLayoutX(610.0);
        likeCountLabel.setLayoutY(10.0);
        try {
            likeCountLabel.setText(String.valueOf(postService.getNumberOfLikesOrDislikes(post.getId(), "like")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label dislikeCountLabel = new Label();
        dislikeCountLabel.setLayoutX(549.0);
        dislikeCountLabel.setLayoutY(10.0);
        try {
            dislikeCountLabel.setText(String.valueOf(postService.getNumberOfLikesOrDislikes(post.getId(), "dislike")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Mettre à jour le nombre de likes et dislikes
        like.setOnMouseClicked(event -> {

            try {
                postService.AddLikesOrDislikes(post.getId(),iduser,"like");
                likeCountLabel.setText(String.valueOf(postService.getNumberOfLikesOrDislikes(post.getId(), "like")));
                dislikeCountLabel.setText(String.valueOf(postService.getNumberOfLikesOrDislikes(post.getId(), "dislike")));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }        });

        dislike.setOnMouseClicked(event -> {

            try {
                postService.AddLikesOrDislikes(post.getId(),iduser,"dislike");
                likeCountLabel.setText(String.valueOf(postService.getNumberOfLikesOrDislikes(post.getId(), "like")));
                dislikeCountLabel.setText(String.valueOf(postService.getNumberOfLikesOrDislikes(post.getId(), "dislike")));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }        });

        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        if (choix == 1) {
            pane.getChildren().addAll(mediaView, imageView, jouerbtn, pausebtn, arreterbtn, videoBtn, imageBtn, userLabel, like, dislike,
                    dateLabel, contentLabel, separator, commentLabel, commentDataLabel, commentTextArea, addBtn, CheckComment,
                    likeCountLabel, dislikeCountLabel);
        } else {
            pane.getChildren().addAll(mediaView, imageView, jouerbtn, pausebtn, arreterbtn, videoBtn, imageBtn, userLabel, editImageView, deleteImageView,
                    dateLabel, contentLabel, separator, commentLabel, commentDataLabel, commentTextArea, addBtn, CheckComment,
                    likeCountLabel, dislikeCountLabel);
        }

        cardContainer.getChildren().add(pane);
        return cardContainer;
    }
   void modifierPost(Post post){
       Scene scene1 = ScrollePan.getScene();
       Stage stage1 = (Stage) scene1.getWindow();
       stage1.close();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ModifierPost.fxml"));
       Parent root = null;
       try {
           root = loader.load();
           ModifierPostController controller = loader.getController();
           controller.initialize(post);
           Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.setTitle("Modifier Activiter");
           stage.show();
       } catch (IOException e) {
           e.printStackTrace();
       }
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
    @FXML
    void goAddPost() throws IOException {
        Scene scenefer = ScrollePan.getScene();
        Stage stagefer = (Stage) scenefer.getWindow();
        stagefer.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/AddPosts.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
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

    public void sendEmail(String recipient, String subject, String body) {
        // Paramètres SMTP
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "issmailchouikhi6@gmail.com";
        String password = "xreo usju fqar hvqi";

        // Propriétés de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            // Ajouter les détails de la réservation dans le corps de l'e-mail
            message.setText(body);

            // Envoyer le message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}
