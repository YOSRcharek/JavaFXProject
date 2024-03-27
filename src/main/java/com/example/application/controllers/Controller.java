package com.example.application.controllers;

import com.example.application.repository.associationRepo;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Controller {
	    @FXML
	    private ListView<String> listView;

	    private associationRepo associationRepository;

	    public Controller() {
	        associationRepository = new associationRepo();
	    }

	    @FXML
	    public void initialize() {
	        populateListView();
	    }

	    private void populateListView() {
	        listView.getItems().addAll(associationRepository.getAllAssociationNames());
	    }
	}
