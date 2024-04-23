package com.example.aziz;

import com.example.aziz.models.Comment;
import com.example.aziz.services.CommentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/Posts.fxml"));

     //   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Admin/Posts.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();

    }
}