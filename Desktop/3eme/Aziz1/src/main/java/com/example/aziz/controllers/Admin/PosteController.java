package com.example.aziz.controllers.Admin;
import com.example.aziz.models.Post;
import com.example.aziz.services.PostService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
public class PosteController {
    @FXML
    private TableView<Post> tableView;
    private final PostService postService = new PostService();

    @FXML
    public void initialize() {
        refreshPosts();
    }

    private void refreshPosts() {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn<Post, Date> createdatCol = new TableColumn<>("Created At");
        createdatCol.setCellValueFactory(new PropertyValueFactory<>("createdat"));



        TableColumn<Post, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));



        TableColumn<Post, String> contentCol = new TableColumn<>("Content");
        contentCol.setCellValueFactory(new PropertyValueFactory<>("content"));

        TableColumn<Post, String> quoteCol = new TableColumn<>("Quote");
        quoteCol.setCellValueFactory(new PropertyValueFactory<>("quote"));



        TableColumn<Post, Integer> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));


/**********************************************************************************************/
        TableColumn<Post, Void> visCol = new TableColumn<>("Visibility");
        visCol.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button();

            {
                button.setOnAction(event -> {
                    Post post = getTableView().getItems().get(getIndex());
                    // Toggle visibility status
                    post.toggleVisibility();
                    // Update button text and style
                    updateButton(post.isVisible());
                    // Optionally, you can update the database here with the new visibility status
                    // postService.updateVisibility(post.getId(), post.isVisible());
                    try {
                        postService.updateVisibility(post.getId(), post.isVisible());
                        System.out.println("Visibility status updated successfully!");
                    } catch (SQLException e) {
                        showErrorAlert("Error updating visibility status: " + e.getMessage());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Post post = getTableView().getItems().get(getIndex());
                    updateButton(post.isVisible());
                    setGraphic(button);
                }
            }

            // Method to update button text and style based on visibility status
            private void updateButton(boolean isVisible) {
                if (isVisible) {
                    button.setText("Visible");
                    button.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                } else {
                   button.setText("Invisible");
                    button.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                }
            }
        });
/******************************************************************************************************/

        TableColumn<Post, Void> repondreCol = new TableColumn<>("Comennts");
        repondreCol.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Comennts");

            {
                button.setOnAction(event -> {
                    Post post = getTableView().getItems().get(getIndex());
                    Scene scene1 = tableView.getScene();
                    Stage stage1 = (Stage) scene1.getWindow();
                    stage1.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Comment.fxml"));
                    Parent root = null;
                    try {

                        root = loader.load();

                        CommentController controller = loader.getController();
                        controller.initialize(post);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Coments");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });


        tableView.getColumns().addAll(titleCol, contentCol, quoteCol, createdatCol, ratingCol,repondreCol,visCol);

        try {
            tableView.getItems().addAll(postService.getAll());
        } catch (SQLException e) {
            showErrorAlert("Error fetching posts: " + e.getMessage());
        }
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}