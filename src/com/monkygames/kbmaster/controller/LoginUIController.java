/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.kbmaster.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 */
public class LoginUIController implements Initializable {

    // === variables === //
    @FXML
    public ChoiceBox accessCB;
    @FXML
    public Button loginB;
    @FXML
    public Button close;

    /**
     * Used for setting animation effects.
     */
    private ButtonController buttonController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	buttonController = new ButtonController();
	buttonController.addNode(loginB);
	buttonController.addNode(close);

	accessCB.setItems(FXCollections.observableArrayList("Local","Network"));
	accessCB.getSelectionModel().selectFirst();

	/**
	 * Handle ChoiceBox events.
	 */
	accessCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> ov, String value, String newValue) {
		switch(newValue){
		    case "Network":
			accessCB.getSelectionModel().selectFirst();
			break;
		    case "Local":
			break;
		}
	    }
	});
    }    

    @FXML
    public void loginEventFired(ActionEvent evt){
	//loginButtonTimeline.play();
	System.out.println("event fired");
	
    }

    /**
     * Closes the login window and exits the program.
     */
    @FXML
    public void closeEventFired(ActionEvent evt){
	System.exit(1);
    }
}
