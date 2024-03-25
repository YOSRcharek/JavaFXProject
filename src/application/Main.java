package application;
	
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;


public class Main extends Application {
    private double x, y;
    @Override
    public void start(Stage primaryStage) {
         try {
        	  Parent root = FXMLLoader.load(getClass().getResource("view/signUpAsso.fxml"));
              primaryStage.setScene(new Scene(root));
              primaryStage.initStyle(StageStyle.UNDECORATED);

              //drag it here
              root.setOnMousePressed(event -> {
                  x = event.getSceneX();
                  y = event.getSceneY();
              });
              root.setOnMouseDragged(event -> {

                  primaryStage.setX(event.getScreenX() - x);
                  primaryStage.setY(event.getScreenY() - y);

              });
              primaryStage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
    }
   public static void main(String[] args) {
        launch(args);
    }
}
