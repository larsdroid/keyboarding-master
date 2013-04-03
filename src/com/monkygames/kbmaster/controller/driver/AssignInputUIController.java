/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.controller.PopupNotifyInterface;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputKeymapSwitch;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.io.ProfileManager;
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
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
    /**
     * Used for getting and setting the configuration.
     */
    private Profile profile;
    /**
     * The selected keymap.
     */
    private Keymap keymap;
    /**
     * The current button mapping for the selected index.
     */
    private Output currentOutput;
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
	// save output!
	if(currentParent == singleKeyParent){
	    //singleKeyController.getConfiguredOutput();
	}else if(currentParent == mouseButtonParent){
	    //mouseButtonController.getConfiguredOutput();
	}else if(currentParent == keymapParent){
	    //keymapController.getConfiguredOutput();
	}else if(currentParent == disabledParent){
	}
	reset();
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
    /**
     * Sets the profile to be used for configuration.
     */
    public void setProfile(Profile profile){
	this.profile = profile;
    }
    /**
     * Sets the selected keymap to be configured.
     * @param keymap the keymap to be configured.
     */
    public void setSelectedKeymap(Keymap keymap){
	this.keymap = keymap;
    }
    /**
     * Set the configuration for the specified button id.
     * @param buttonID the unique id of the button to be configured.
     */
    public void setAssignedConfig(int buttonID){
	Mapping mapping = device.getMapping(buttonID, keymap);
	currentOutput = mapping.getOutput();

	if(currentParent != null){
	    settingsPane.getChildren().remove(currentParent);
	}

	int selectionID = 0;
	// update the configurations
	if(currentOutput instanceof OutputKey){
	    currentParent = singleKeyParent;
	    singleKeyController.setConfiguredOutput(currentOutput);
	    selectionID = 0;
	}else if(currentOutput instanceof OutputMouse){
	    currentParent = mouseButtonParent;
	    mouseButtonController.setSelectedMouse(currentOutput.getKeycode());
	    selectionID = 1;
	}else if(currentOutput instanceof OutputKeymapSwitch){
	    currentParent = keymapParent;
	    OutputKeymapSwitch keymapSwitch = (OutputKeymapSwitch)currentOutput;
	    // note, we subtrack one from the keycode since the range is valid from 1 - 8 inclusive.
	    keymapController.setConfiguredOutput(keymapSwitch.getKeycode()-1,keymapSwitch.isIsSwitchOnRelease());
	    selectionID = 2;
	}else if(currentOutput instanceof OutputDisabled){
	    currentParent = disabledParent;
	    selectionID = 3;
	}

	if(currentParent != null){
	    settingsPane.getChildren().add(currentParent);

	    mappingCB.valueProperty().removeListener(this);
	    mappingCB.getSelectionModel().select(selectionID);
	    mappingCB.valueProperty().addListener(this);
	}

	//buttonMapping.getInputHardware().getID();


	/*
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
	*/
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
    @Override
    public void setStage(Stage stage){
	super.setStage(stage);
	singleKeyController.setStage(stage);
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