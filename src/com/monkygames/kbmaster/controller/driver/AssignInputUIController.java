/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

import com.monkygames.kbmaster.controller.profile.*;
import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.controller.PopupNotifyInterface;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.input.ProfileType;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Handles the cloning feature.
 * @version 1.0
 */
public class AssignInputUIController extends PopupController implements ChangeListener<String>, PopupNotifyInterface{

// ============= Class variables ============== //
    @FXML
    private ComboBox mappingCB;
    @FXML
    private TextField descriptionTF;
    @FXML
    private Pane settingsPane;
    private ProfileManager profileManager;
    private Device device;
    private SingleKeyController singleKeyController;
    private MouseButtonController mouseButtonController;
    private KeymapController keymapController;
    private Parent singleKeyParent, mouseButtonParent, keymapParent, disabledParent;
    private Parent currentParent;
    private static final String SINGLE_KEY = "Single Key";
    private static final String MOUSE_BUTTON = "Mouse Button";
    private static final String KEYMAP = "Keymap";
    private static final String DISABLED = "Disabled";
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setDevice(Device device){
	this.device = device;
    }
    public void okEventFired(ActionEvent evt){
	reset();
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void reset(){
	hideStage();
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// set up mapping cb
	mappingCB.setItems(FXCollections.observableArrayList(SINGLE_KEY,MOUSE_BUTTON,KEYMAP,DISABLED));
	//mappingCB.getSelectionModel().selectFirst();
	mappingCB.valueProperty().addListener(this);
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/SingleKeyPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    singleKeyParent = (Parent)fxmlLoader.load(location.openStream());
	    singleKeyController = (SingleKeyController) fxmlLoader.getController();
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/MouseButtonPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    mouseButtonParent = (Parent)fxmlLoader.load(location.openStream());
	    mouseButtonController = (MouseButtonController) fxmlLoader.getController();
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/KeymapPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    keymapParent = (Parent)fxmlLoader.load(location.openStream());
	    keymapController = (KeymapController) fxmlLoader.getController();
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/DisabledPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    disabledParent = (Parent)fxmlLoader.load(location.openStream());
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    @Override
    public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
	if(currentParent != null){
	    settingsPane.getChildren().remove(currentParent);
	}
	if(newValue.equals(SINGLE_KEY)){
	    currentParent = singleKeyParent;
	}else if(newValue.equals(MOUSE_BUTTON)){
	    currentParent = mouseButtonParent;
	}else if(newValue.equals(KEYMAP)){
	    currentParent = keymapParent;
	}else if(newValue.equals(DISABLED)){
	    currentParent = disabledParent;
	}
	if(currentParent != null){
	    settingsPane.getChildren().add(currentParent);
	}
    }
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void onOK(Object src, String message) {
    }

    @Override
    public void onCancel(Object src, String message) {
	// do nothing for now
    }
    @Override
    public void showStage(){
	super.showStage();
    }
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
