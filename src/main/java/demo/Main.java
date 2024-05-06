package demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Objects;


public class Main extends Application {

    @Override
  public void start(Stage stage) {
         try {
             FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));

             Scene scene = new Scene(fxmlLoader.load(), 920, 640);
             stage.setTitle("Hello!");
             stage.setScene(scene);
             stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    public static void main(String[] args) {
        launch(args);
    }
}