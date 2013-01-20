/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
// === kbmaster imports === //
import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.controller.ProfileUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.input.ProfileType;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * A controller for the New Profile UI.
 * @version 1.0
 */
public class NewProfileUIController extends PopupController implements ChangeListener<String>{

// ============= Class variables ============== //
    @FXML
    private Button okB;
    @FXML
    private Button cancelB;
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox programCB;
    @FXML
    private TextField profileTF;
    private ProfileManager profileManager;
    private NewProgramUIController newProgramUIController;
    private Device device;
    private ProfileUIController profileController;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileController(ProfileUIController profileController){
	this.profileController = profileController;
    }
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void setDevice(Device device){
	this.device = device;
    }
    /**
     * Re-populate the combo boxes based on the type selected.
     */
    public void updateLists(){
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    updateComboBoxesOnType(ProfileType.GAME);
	}else{
	    updateComboBoxesOnType(ProfileType.APPLICATION);
	}
    }
    public void okEventFired(ActionEvent evt){
	try{
	    if(programCB.getSelectionModel().getSelectedIndex() == 0){
		openNewProgramPopup();
		//TODO need to pass a parameter to continue
		// this action, instead of just returning.
		return;
	    }
	    ProfileType type;
	    if(typeCB.getSelectionModel().getSelectedIndex() == 0){
		type = ProfileType.GAME;
	    }else{
		type = ProfileType.APPLICATION;
	    }
	    String programName = (String)programCB.getSelectionModel().getSelectedItem();
	    String profileName = profileTF.getText();
	    // check for existing profile names
	    if(profileManager.doesProfileNameExists(type, programName, profileName)){
		PopupManager.getPopupManager().showError("Profile name already exists");
		return;
	    }
	    Profile profile = new Profile(type,programName,profileName);
	    device.setDefaultKeymaps(profile);
	    // save the profile
	    profileManager.addProfile(profile);
	    profileController.updateProfilesComboBox();
	    
	} finally{
	    reset();
	}
    }
    public void cancelEventFired(ActionEvent evt){;
	reset();
    }
// ============= Private Methods ============== //
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
		newProgramUIController.setNewProfileController(this);
	    } catch (IOException ex) {
		Logger.getLogger(NewProfileUIController.class.getName()).log(Level.SEVERE, null, ex);
		return;
	    }
	}
	newProgramUIController.showStage();
    }
    /**
     * Reset to the defaults.
     */
    private void reset(){
	profileTF.setText("");
	hideStage();

    }
    private void updateComboBoxesOnType(ProfileType type){
	ObservableList<String> programs;
	programs = FXCollections.observableArrayList(profileManager.getAvailablePrograms(type));
	programs.add(0, "New");
	programCB.setItems(programs);
    }
// ============= Implemented Methods ============== //
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
	if(ov == typeCB.valueProperty()){
	    if(typeCB.getSelectionModel().getSelectedIndex() == 0){
		updateComboBoxesOnType(ProfileType.GAME);
	    }else{
		updateComboBoxesOnType(ProfileType.APPLICATION);
	    }
	}
    }
// ============= Extended Methods ============== //
    @Override
    public void showStage(){
	super.showStage();
	updateLists();
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
