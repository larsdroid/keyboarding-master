/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;

/**
 * Handles UI Events for the profile panel.
 * @version 1.0
 */
public class ProfileUIController implements Initializable, ChangeListener<String>{


// ============= Class variables ============== //
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox programCB;
// ============= Constructors ============== //
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	//typeCB.setItems(FXCollections.observableArrayList("Local","Network"));
	typeCB.setItems(FXCollections.observableArrayList("Game","Application"));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);

	// populate the applist

	/*
	typeCB.getItems().removeAll();
	Image gameImage = new Image("/com/monkygames/kbmaster/fxml/resources/sort/game.png");
	Image applicationImage = new Image("/com/monkygames/kbmaster/fxml/resources/sort/application.png");
	ObservableList<Image> images = FXCollections.observableArrayList(gameImage,applicationImage);
	typeCB.setItems(images);
	typeCB.setCellFactory(new ImageCellFactoryCallback());
	*/
    }

    @Override
    public void changed(ObservableValue<? extends String> ov, String previousValue, String newValue) {
	if(ov == typeCB.valueProperty()){
	    System.out.println("selected index = "+typeCB.getSelectionModel().getSelectedIndex());
	}
    }

// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
