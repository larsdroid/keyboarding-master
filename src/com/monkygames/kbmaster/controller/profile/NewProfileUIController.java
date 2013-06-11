/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

// === kbmaster imports === //
import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.controller.PopupNotifyInterface;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.App;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.input.AppType;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import com.monkygames.kbmaster.util.WindowUtil;
// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * A controller for the New Profile UI.
 * @version 1.0
 */
public class NewProfileUIController extends PopupController implements ChangeListener<String>, PopupNotifyInterface{

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
    @FXML
    private TextField authorTF;
    @FXML
    private TextArea infoTA;
    private ProfileManager profileManager;
    private NewProgramUIController newProgramUIController;
    private Device device;
    private List<App> appsList;
// ============= Constructors ============== //
// ============= Public Methods ============== //
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
	updateComboBoxesOnType(getProfileType());
    }
    public void okEventFired(ActionEvent evt){
	try{
	    if(programCB.getSelectionModel().getSelectedIndex() == 0){
		openNewProgramPopup();
		return;
	    }
	    App app = (App)programCB.getSelectionModel().getSelectedItem();
	    String profileName = profileTF.getText();
	    // check for a valid program name
	    if(app == null){
		PopupManager.getPopupManager().showError("Invalid program");
		return;
	    }
	    // check for valid profileName
	    if(profileName == null || profileName.equals("")){
		PopupManager.getPopupManager().showError("Invalid profile name");
		return;
	    }
	    // check for existing profile names
	    if(profileManager.doesProfileNameExists(app, profileName)){
		PopupManager.getPopupManager().showError("Profile name already exists");
		return;
	    }
	    String author = authorTF.getText();
	    if(author == null){
		author = "";
	    }
	    String info = infoTA.getText();
	    if(info == null){
		info = "";
	    }
	    // get the current time
	    long time = Calendar.getInstance().getTimeInMillis();;
	    Profile profile = new Profile(app,profileName,author,info,time);
	    device.setDefaultKeymaps(profile);
	    // save the profile
	    profileManager.addProfile(profile);
	    notifyOK(profileName);
	} finally{
	    reset();
	}
    }
    public void cancelEventFired(ActionEvent evt){;
	reset();
	notifyCancel(null);
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
		Stage stage = WindowUtil.createStage(root);
		newProgramUIController.setStage(stage);
		newProgramUIController.setProfileManager(profileManager);
		newProgramUIController.addNotification(this);
		//newProgramUIController.setNewProfileController(this);
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
	authorTF.setText("");
	infoTA.setText("");
	programCB.getSelectionModel().selectFirst();
	hideStage();
    }
    private void updateComboBoxesOnType(AppType type){
	ObservableList<App> apps;
	appsList = profileManager.getAvailableApps(type);
	apps = FXCollections.observableArrayList(appsList);
	apps.add(0, new App("",null,null,"New",AppType.APPLICATION));
	programCB.setItems(apps);
    }
    /**
     * Returns the profile type based on the type combo box.
     */
    private AppType getProfileType(){
	AppType type;
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    type = AppType.GAME;
	}else{
	    type = AppType.APPLICATION;
	}
	return type;
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);
	programCB.setItems(FXCollections.observableArrayList());
    }
    @Override
    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	if(ov == typeCB.valueProperty()){
	    updateComboBoxesOnType(getProfileType());
	}
    }
    @Override
    public void onOK(Object src, String message) {
	this.showStage();
	if(message != null || !message.equals("")){
	    for(App app: appsList){
		if(app != null && app.getName().equals(message)){
		    programCB.getSelectionModel().select(app);
		    break;
		}
	    }
	}
    }
    @Override
    public void onCancel(Object src, String message) {
	// do nothing
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
