package demo.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author oXCToo
 */
public class profilController {
    
    @FXML
    private Label label;
    
      @FXML
    private VBox pnl_scroll;
      
      @FXML
      private ScrollPane itemProjet;
      @FXML
      private ScrollPane itemMembre;
      
      @FXML
      private JFXButton projets;
      @FXML
      private JFXButton membres;
    
    @FXML
    private void handleButtonAction(ActionEvent actionEvent) {        
      
  
	
		    if (actionEvent.getSource() == projets) {
		    	projets.setStyle("-fx-background-color:  #DDE6E8");
		    	membres.setStyle("-fx-background-color:  white");
		    	itemProjet.toFront();
		    } else if (actionEvent.getSource() == membres) {
		    	membres.setStyle("-fx-background-color:  #DDE6E8");
		    	projets.setStyle("-fx-background-color:  white");
		    	 itemMembre.toFront();
		    } 
    }
    
    public void initialize() {
    	itemProjet.toFront();
    	projets.setStyle("-fx-background-color:  #DDE6E8");
    }    
 

    private void refreshNodes()
    {
        pnl_scroll.getChildren().clear();
        
        Node [] nodes = new  Node[15];
        
        for(int i = 0; i<10; i++)
        {
            try {
                nodes[i] = (Node)FXMLLoader.load(getClass().getResource("Item.fxml"));
               pnl_scroll.getChildren().add(nodes[i]);
                
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }  
    }
    
}
