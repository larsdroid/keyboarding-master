/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.kbmaster.controller.DriverUIController;
import com.monkygames.kbmaster.driver.Device;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Handles managing Keymap UI Panels for the device.
 * @version 1.0
 */
public class KeymapUIManager{

// ============= Class variables ============== //
    private Device device;
    private TabPane tabPane;
    private DriverUIController[] driverUIController;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setDevice(Device device){
	this.device = device;
    }
    public void setTabPane(TabPane tabPane){
	this.tabPane = tabPane;
    }
    public void updateTabs(){
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
