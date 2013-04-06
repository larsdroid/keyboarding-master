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
import com.monkygames.kbmaster.input.App;
import com.monkygames.kbmaster.input.AppType;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    @FXML
    private TextArea appInfoTA;
    @FXML
    private ImageView appIV;
    @FXML
    private ImageView devIV;
    private String appLogoPath = null;
    private String devLogoPath = null;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void okEventFired(ActionEvent evt){
	AppType type;
	List <App> programs;
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    type = AppType.GAME;
	    programs = profileManager.getGames();
	}else{
	    type = AppType.APPLICATION;
	    programs = profileManager.getApplications();
	}
	String appName = programTF.getText();
	// check for a valid program name
	if(appName == null || appName.equals("")){
	    PopupManager.getPopupManager().showError("Invalid App Name");
	}
	if(!profileManager.addApp(new App(appInfoTA.getText(),
					  appLogoPath,
					  devLogoPath,
					  appName,
					  type))){
	    PopupManager.getPopupManager().showError("App name already exists");
	}
	reset();
	notifyOK(appName);
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
    public void addAppLogoEventFired(ActionEvent evt){
	// file locator?
    }
    public void addDevLogoEventFired(ActionEvent evt){

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
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
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
