/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
// === kbmaster imports === //
import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ProfileType;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controls the New Program UI.
 * @version 1.0
 */
public class NewProgramUIController extends PopupController{


// ============= Class variables ============== //
    @FXML
    private ComboBox typeCB;
    @FXML
    private TextField programTF;
    private ProfileManager profileManager;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void okEventFired(ActionEvent evt){
	ProfileType type;
	List <String> programs;
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    type = ProfileType.GAME;
	    programs = profileManager.getGameNames();
	}else{
	    type = ProfileType.APPLICATION;
	    programs = profileManager.getApplicationNames();
	}
	String program = programTF.getText();
	// check for a valid program name
	if(program == null || program.equals("")){
	    PopupManager.getPopupManager().showError("Invalid Program Name");
	}
	if(!profileManager.addProgramName(program, type)){
	    PopupManager.getPopupManager().showError("Program name already exists");
	}
	reset();
	notifyOK();
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void reset(){
	programTF.setText("");
	hideStage();
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(ProfileType.GAME),
							  ProfileTypeNames.getProfileTypeName(ProfileType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
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
