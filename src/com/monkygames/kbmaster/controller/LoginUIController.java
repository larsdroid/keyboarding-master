/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.kbmaster.controller;

import insidefx.undecorator.Undecorator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    public Pane loginPane;

    /**
     * Used for setting animation effects.
     */
    private ButtonController buttonController;
    private Parent root;

    /**
     * Used for closing the window.
     */
    private Stage loginStage;

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
	//TODO check if local or network and take apropriate action
	// right now, just concider local

	// hide login gui
	loginStage.hide();

	// create main gui
	try {
	    /*
	    root = FXMLLoader.load(getClass().getResource("/com/monkygames/kbmaster/fxml/MainUI.fxml"));
	    Scene scene = new Scene(root);
	    Stage mainStage = new Stage();
	    mainStage.setScene(scene);
	    mainStage.show();
	    */


	    /*
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/MainUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    root = (Parent)fxmlLoader.load(location.openStream());
	    MainUIController controller = (MainUIController) fxmlLoader.getController();
	    Scene scene = new Scene(root);
	    */

	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/DeviceMenuUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    root = (Parent)fxmlLoader.load(location.openStream());
	    DeviceMenuUIController controller = (DeviceMenuUIController) fxmlLoader.getController();

	    Stage stage = new Stage();
	    Undecorator undecorator = new Undecorator(stage, (Region) root, 
						      null, StageStyle.UNDECORATED);
	    // Default theme
	    undecorator.getStylesheets().add("/com/monkygames/kbmaster/skin/undecorator.css");
	    
	    Scene scene = new Scene(undecorator);

	    // Transparent scene and stage
	    scene.setFill(Color.TRANSPARENT);
	    stage.initStyle(StageStyle.TRANSPARENT);
	    
	    stage.setScene(scene);
	    // accomodate undecorator style
	    double width = 832+25*2;
	    double height = 400+25*2;	
	    stage.setMinWidth(width);
	    stage.setMinHeight(height);
	    stage.setMaxWidth(width);
	    stage.setMaxHeight(height);
	    stage.setResizable(false);

	    stage.show();

	    
	} catch (IOException ex) {
	    Logger.getLogger(LoginUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }

    /**
     * Closes the login window and exits the program.
     */
    @FXML
    public void closeEventFired(ActionEvent evt){
	System.exit(1);
    }

    public void setStage(Stage loginStage){
	this.loginStage = loginStage;
    }
}
