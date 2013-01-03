/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class MainUIController implements Initializable{


// ============= Class variables ============== //
    @FXML
    private Label versionL;
    @FXML
    private TabPane driverTabPane;
    @FXML
    private ComboBox driverComboBox;
    @FXML
    private AnchorPane profilesAnchorPane;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    @FXML
    public void deviceComboBoxChanged(ActionEvent evt){

    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void initDriverComboBox(){
	driverComboBox.getItems().removeAll();
	Image image = new Image("/com/monkygames/kbmaster/driver/razer/nostromo/resources/RazerNostromoIcon.png");
	ObservableList<Image> images = FXCollections.observableArrayList(image);
	driverComboBox.setItems(images);
	driverComboBox.setCellFactory(new ImageCellFactoryCallback());
	driverComboBox.setButtonCell(new ListCellImage());
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// get available drivers and load them in list.
	initDriverComboBox();


	/**
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource( "/com/monkygames/kbmaster/fxml/ProfileUI.fxml"));
	ProfileUIController profileController = (ProfileUIController)fxmlLoader.getController();
	System.out.println("Profile Controller = "+profileController);
	*/
	URL profileLocation = getClass().getResource( "/com/monkygames/kbmaster/fxml/ProfileUI.fxml");
	FXMLLoader profileFxmlLoader = new FXMLLoader(profileLocation);
	ProfileUIController profileController = (ProfileUIController)profileFxmlLoader.getController();
	System.out.println("Profile Controller = "+profileController);

	/**
	    System.out.println("Handling button action");
	    loadableContent.getChildren().clear();
	    loadableContent.getChildren().add((Node)FXMLLoader.load(getClass().getResource("DB4OUI.fxml")));
	**/
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
