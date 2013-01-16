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
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;

/**
 * A controller for the New Profile UI.
 * @version 1.0
 */
public class NewProfileUIController extends PopupController{

// ============= Class variables ============== //
    @FXML
    private Button okB;
    @FXML
    private Button cancelB;
    @FXML
    private TextField profileTF;
    private ProfileManager profileManager;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void okEventFired(ActionEvent evt){
	String profileName = profileTF.getText();

	// check for existing profile names
	// pop an error
	PopupManager.getPopupManager().showError("Profile name already exists");

	hideStage();
    }
    public void cancelEventFired(ActionEvent evt){
	// reset the text box.
	profileTF.setText("");
	hideStage();
    }
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
