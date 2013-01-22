/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

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
import javafx.stage.Stage;

/**
 * Handles the cloning feature.
 * @version 1.0
 */
public class CloneProfileUIController extends PopupController implements ChangeListener<String>, PopupNotifyInterface{

// ============= Class variables ============== //
    @FXML
    private Label profileNameL;
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox programCB;
    @FXML
    private TextField profileTF;
    private ProfileManager profileManager;
    private Device device;
    private NewProgramUIController newProgramUIController;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
	updateComboBoxesOnType(ProfileType.GAME);
    }
    public void setDevice(Device device){
	this.device = device;
    }
    public void okEventFired(ActionEvent evt){
	try{
	    // check for a valid name
	    String newProfileName = profileTF.getText();
	    if(newProfileName == null || newProfileName.equals("")){
		PopupManager.getPopupManager().showError("Invalid profile name");
		return;
	    }
	    ProfileType type = getProfileType();
	    if(programCB.getSelectionModel().getSelectedIndex() == 0){
		this.openNewProgramPopup();
		return;
	    }
	    String program = (String)programCB.getSelectionModel().getSelectedItem();
	    
	    // check for a redundant name
	    if(profileManager.doesProfileNameExists(type, program, newProfileName)){
		PopupManager.getPopupManager().showError("Profile name already exists");
		return;
	    }
	    Profile profile = new Profile(type,program,newProfileName);
	    device.setDefaultKeymaps(profile);
	    profileManager.addProfile(profile);
	    notifyOK();

	}finally{
	    reset();
	}
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void updateComboBoxesOnType(ProfileType type){
	ObservableList<String> programs;
	programs = FXCollections.observableArrayList(profileManager.getAvailablePrograms(type));
	programs.add(0, "New");
	programCB.setItems(programs);
    }
    private void reset(){
	profileTF.setText("");
	hideStage();
    }
    /**
     * Returns the profile type based on the type combo box.
     */
    private ProfileType getProfileType(){
	ProfileType type;
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    type = ProfileType.GAME;
	}else{
	    type = ProfileType.APPLICATION;
	}
	return type;
    }
    private void openNewProgramPopup(){
	if(newProgramUIController == null){
	    try {
		// pop open add new device
		URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewProgramUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(location.openStream());
		newProgramUIController = (NewProgramUIController) fxmlLoader.getController();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		newProgramUIController.setStage(stage);
		newProgramUIController.setProfileManager(profileManager);
		newProgramUIController.addNotification(this);
	    } catch (IOException ex) {
		Logger.getLogger(CloneProfileUIController.class.getName()).log(Level.SEVERE, null, ex);
		return;
	    }
	}
	newProgramUIController.showStage();
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(ProfileType.GAME),
							  ProfileTypeNames.getProfileTypeName(ProfileType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);
	programCB.setItems(FXCollections.observableArrayList());
    }
    @Override
    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	if(ov == typeCB){
	    updateComboBoxesOnType(getProfileType());
	}
    }
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void onOK(Object src) {
	if(src == newProgramUIController){
	    updateComboBoxesOnType(getProfileType());
	}
    }

    @Override
    public void onCancel(Object src) {
	// do nothing for now
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
