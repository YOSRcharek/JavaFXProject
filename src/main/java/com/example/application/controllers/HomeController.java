package com.example.application.controllers;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomeController {




	@FXML
	private TextField txtEmail;
	@FXML
	private TextField searchemail;

	@FXML
	private CheckBox chkVerified;

	@FXML
	private TableColumn<User, String> actionsColumn;

	@FXML
	private VBox pnItems;

	@FXML
	private Button btnOverview;

	@FXML
	private Button btnOrders;

	@FXML
	private Button btnUsers;

	@FXML
	private Button btnMenus;

	@FXML
	private Button btnPackages;

	@FXML
	private Button btnSettings;

	@FXML
	private Button btnSignout;

	@FXML
	private Pane pnlUsers;

	@FXML
	private Pane pnlOrders;

	@FXML
	private Pane pnlOverview;

	@FXML
	private Pane pnlMenus;

	@FXML
	private TableView<User> userTable;

	@FXML
	private TableColumn<User, Integer> idColumn;

	@FXML
	private TableColumn<User, String> emailColumn;

	@FXML
	private TableColumn<User, Boolean> verifiedColumn;



	private UserRepository userRepository;

	public HomeController() {
		userRepository = new UserRepository(); // Instantiate your UserRepository
	}

	public void initialize() {


		// Populate the table with data from UserRepository
		List<User> userList = userRepository.getAllUsers();
		userTable.getItems().addAll(userList);

		// Set up a filtered list to filter based on email
		FilteredList<User> filteredList = new FilteredList<>(userTable.getItems());

		// Bind the filtered list to the TableView
		userTable.setItems(filteredList);

		// Add listener to the text field to filter based on email
		txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(user -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				// Filter based on email
				String lowerCaseFilter = newValue.toLowerCase();
				return user.getEmail().toLowerCase().contains(lowerCaseFilter);
			});
		});


//		actionsColumn.setCellValueFactory(cellData -> new SimpleStringProperty("Actions"));


//		actionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));

		// Set focus to the Overview button (optional)
		btnOverview.requestFocus();

		// Show the Overview pane by default
		pnlOverview.setStyle("-fx-background-color : #53639F");
		pnlOverview.toFront();

		// Initialize the table columns
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		verifiedColumn.setCellValueFactory(new PropertyValueFactory<>("verified"));
//
//		// Populate the table with data from UserRepository
//		List<User> userList = userRepository.getAllUsers();
//		userTable.getItems().addAll(userList);

	}






	@FXML
	public void handleClicks(ActionEvent actionEvent) {
		if (actionEvent.getSource() == btnUsers) {
			pnlUsers.setStyle("-fx-background-color : #53639F");
			pnlUsers.toFront();
		}
		if (actionEvent.getSource() == btnMenus) {
			pnlMenus.setStyle("-fx-background-color : #53639F");
			pnlMenus.toFront();
		}
		if (actionEvent.getSource() == btnOverview) {
			pnlOverview.setStyle("-fx-background-color : #53639F");
			pnlOverview.toFront();
		}
		if (actionEvent.getSource() == btnOrders) {
			pnlOrders.setStyle("-fx-background-color : #53639F");
			pnlOrders.toFront();
		}

	}


	@FXML
	public void handleDeleteUser(ActionEvent actionEvent) {
		// Get the selected user from the TableView
		User selectedUser = userTable.getSelectionModel().getSelectedItem();

		if (selectedUser == null) {
			// If no user is selected, show an error message
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a user to delete.");
			alert.showAndWait();
			return;
		}

		// Confirm with the user before deleting
		Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Confirm Deletion");
		confirmationAlert.setHeaderText(null);
		confirmationAlert.setContentText("Are you sure you want to delete the selected user?");

		confirmationAlert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				// User confirmed deletion, proceed with deletion
				boolean deleted = userRepository.deleteUser(selectedUser.getId());
				if (deleted) {
					// User successfully deleted, remove from TableView
					userTable.getItems().remove(selectedUser);
					// Show success message
					Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
					successAlert.setTitle("Success");
					successAlert.setHeaderText(null);
					successAlert.setContentText("User deleted successfully.");
					successAlert.showAndWait();
				} else {
					// Show error message if deletion fails
					Alert errorAlert = new Alert(Alert.AlertType.ERROR);
					errorAlert.setTitle("Error");
					errorAlert.setHeaderText(null);
					errorAlert.setContentText("Failed to delete user. Please try again.");
					errorAlert.showAndWait();
				}
			}
		});
	}



	@FXML
	public void handleUpdate(ActionEvent event) {
		User selectedUser = userTable.getSelectionModel().getSelectedItem();

		if (selectedUser != null) {
			// Populate the update fields with the selected user's data
			txtEmail.setText(selectedUser.getEmail());
			chkVerified.setSelected(selectedUser.isVerified());
		} else {
			// If no user is selected, show an error message
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a user to update.");
			alert.showAndWait();
		}
	}

	@FXML
	public void handleUpdateConfirm(ActionEvent event) {
		User selectedUser = userTable.getSelectionModel().getSelectedItem();

		if (selectedUser != null) {
			// Get updated data from the fields
			String updatedEmail = txtEmail.getText();
			boolean updatedVerified = chkVerified.isSelected();

			// Validate the email format
			if (!isValidEmail(updatedEmail)) {
				// Show an error message if the email format is invalid
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a valid email address.");
				alert.showAndWait();
				return;
			}

			// Update the selected user's data
			selectedUser.setEmail(updatedEmail);
			selectedUser.setVerified(updatedVerified);

			// Call UserRepository.updateUser(selectedUser) to update the user in the database
			boolean success = userRepository.updateUser(selectedUser);

			if (success) {
				// If update is successful, show a success message
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("User updated successfully.");
				alert.showAndWait();

				// Clear the update fields
				txtEmail.clear();
				chkVerified.setSelected(false);

				// Refresh the TableView to reflect the changes
				refreshUserTable();
			} else {
				// If update fails, show an error message
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Failed to update user.");
				alert.showAndWait();
			}
		} else {
			// If no user is selected, show an error message
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a user to update.");
			alert.showAndWait();
		}
	}

	// Method to validate email format
	private boolean isValidEmail(String email) {
		// Regular expression for email validation
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(emailRegex);
	}

	// Method to refresh the TableView with updated data
	private void refreshUserTable() {
		userTable.getItems().clear();
		userTable.getItems().addAll(userRepository.getAllUsers());
	}


	@FXML
	private void signOut() {
		// Clear any session-related data (if applicable)
		// For example, if you're storing authentication status, clear it here

		// Navigate to the sign-in screen
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = (Stage) btnSignout.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}









}
