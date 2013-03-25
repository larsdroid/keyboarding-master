/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.kbmaster.controller.DriverUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.Profile;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Handles managing Keymap UI Panels for the device.
 * @version 1.0
 */
public class KeymapUIManager implements ChangeListener{

// ============= Class variables ============== //
    private Device device;
    private TabPane tabPane;
    private DriverUIController[] driverUIController;
    private Profile profile;
    private Label keymapDescription;
// ============= Constructors ============== //
    public KeymapUIManager(){
	driverUIController = new DriverUIController[8];
    }
// ============= Public Methods ============== //
    /**
     * Sets the text for the description.
     * @param message the text to set.
     */
    public void setDescriptionText(String message){
	keymapDescription.setText(message);
    }
    public void setLabel(Label keymapDescription){
	this.keymapDescription = keymapDescription;
    }
    public void setDevice(Device device){
	this.device = device;
    }
    public void setProfile(Profile profile){
	this.profile = profile;
	// set description for keymap
	keymapDescription.setText(profile.getKeymap(0).getDescription());
    }
    public void setTabPane(TabPane tabPane){
	this.tabPane = tabPane;
	tabPane.getSelectionModel().selectedItemProperty().addListener(this);
    }
    /**
     * Populates tabs with the UI from the device.
     */
    public void initializeTabs(){
	ObservableList list = tabPane.getTabs();
	for(int i = 0; i < list.size(); i++){
	    initializeTab(i,(Tab)list.get(i));
	}

    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private boolean initializeTab(int index,Tab tab){
	if(device == null) return false;
	Parent root = null;
	try {
	    // pop open add new device
	    URL location = getClass().getResource(device.getDeviceInformation().getUIFXMLURL());
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    root = (Parent)fxmlLoader.load(location.openStream());
	    driverUIController[index] = (DriverUIController) fxmlLoader.getController();
	    tab.setContent(root);

	    //newProgramUIController.setStage(stage);
	    //newProgramUIController.setProfileManager(profileManager);
	    //newProgramUIController.addNotification(this);
	    //newProgramUIController.setNewProfileController(this);
	} catch (IOException ex) {
	    Logger.getLogger(KeymapUIManager.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}
	return true;
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void changed(ObservableValue ov, Object oldTab, Object newTab) {
	// set description for keymap
	keymapDescription.setText( profile.getKeymap(tabPane.getSelectionModel().getSelectedIndex()).getDescription());
    }
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
