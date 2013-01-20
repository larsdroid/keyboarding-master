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
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void updateLists(){
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    updateComboBoxesOnType(ProfileType.GAME);
	}else{
	    updateComboBoxesOnType(ProfileType.APPLICATION);
	}
    }
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void okEventFired(ActionEvent evt){
	if(programCB.getSelectionModel().getSelectedIndex() == 0){
	    //pop new program ui.
	}
	String profileName = profileTF.getText();

	// check for existing profile names
	// pop an error
	PopupManager.getPopupManager().showError("Profile name already exists");

	reset();
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
    }
// ============= Private Methods ============== //
    private void openNewProgramePopup(){
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
