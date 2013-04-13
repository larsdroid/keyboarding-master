/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.controller.profile.CloneProfileUIController;
import com.monkygames.kbmaster.controller.profile.DeleteProfileUIController;
import com.monkygames.kbmaster.controller.profile.NewProfileUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.App;
import com.monkygames.kbmaster.input.Profile;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
// === kbmaster imports === //
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.input.AppType;
import com.monkygames.kbmaster.io.BindingPDFWriter;
import com.monkygames.kbmaster.io.GenerateBindingsImage;
import com.monkygames.kbmaster.util.KeymapUIManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Handles UI Events for the profile panel.
 * @version 1.0
 */
public class ProfileUIController implements Initializable, ChangeListener<String>, PopupNotifyInterface{

// ============= Class variables ============== //
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox appsCB;
    @FXML
    private ComboBox profileCB;
    @FXML
    private Button newProfileB;
    @FXML
    private Button cloneProfileB;
    @FXML
    private Button importProfileB;
    @FXML
    private Button exportProfileB;
    @FXML
    private Button printPDFB;
    @FXML
    private Button deleteProfileB;
    @FXML
    private TextArea infoTA;
    @FXML
    private TextArea appInfoTA;
    @FXML
    private ImageView appLogoIV;
    @FXML
    private ImageView devLogoIV;
    @FXML
    private Label authorL;
    @FXML
    private Label updatedL;
    private ProfileManager profileManager;
    private NewProfileUIController newProfileUIController;
    private CloneProfileUIController cloneProfileUIController;
    private DeleteProfileUIController deleteProfileUIController;
    private DisplayKeymapUIController displayKeymapUIController;
    private File profileDir;
    private Device device;
    /**
     * Used for selecting a file to write a pdf binding.
     */
    private FileChooser pdfChooser;
    private TabPane keymapTabPane;
    private KeymapUIManager keymapUIManager;
    /**
     * The currently used profile.
     */
    private Profile currentProfile;
    /**
     * True if its the first time a new device has been set.
     * Used to not re-initialize the device GUI.
     */
    private boolean isNewDevice = true;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    @FXML
    public void profileEventFired(ActionEvent evt){
	Object src = evt.getSource();
	if(src == newProfileB){
	    openNewProfilePopup();
	}else if(src == cloneProfileB){
	    openCloneProfilePopup();
	}else if(src == importProfileB){
	}else if(src == exportProfileB){
	}else if(src == printPDFB){
	    openPDFPopup();
	}else if(src == deleteProfileB){
	    openDeleteProfilePopup();
	}
	
    }
    public void setKeymapTabPane(TabPane keymapTabPane){
	this.keymapTabPane = keymapTabPane;
	keymapUIManager.setTabPane(keymapTabPane);
    }
    /**
     * Sets the device in order to get device name and to be used in
     * popups for creating profiles.
     * @param device the device to be set.
     */
    public void setDevice(Device device){
	this.device = device;
	isNewDevice = true;
	if(newProfileUIController != null){
	    newProfileUIController.setDevice(device);
	}
	String deviceName = device.getDeviceInformation().getProfileName();
	if(profileManager != null){
	    profileManager.close();
	}
	profileManager = new ProfileManager(profileDir+File.separator+deviceName);

	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);

	//  set profile on the keymaps
	// set device is required before calling initialize tabs.
	keymapUIManager.setDevice(device);
	keymapUIManager.initializeTabs();
	// initialize tabs is required before calling set profile.
	keymapUIManager.addSaveNotification(this);

	updateComboBoxes();
    }
    /**
     * Set the description label for keymaps.
     */
    public void setDescriptionLabel(Label descriptionLabel){
	keymapUIManager.setLabel(descriptionLabel);
    }
    /**
     * Only updates the profiles combo box.
     */
    public void updateProfilesComboBox(){
	App app;
	ObservableList<Profile> profiles = null;
	app = (App)appsCB.getSelectionModel().getSelectedItem();
	if(app == null){
	    return;
	}
	updateAppUIInfo(app);

	profiles = FXCollections.observableArrayList(profileManager.getProfile(app));
	profileCB.setItems(profiles);
	profileCB.getSelectionModel().selectFirst();
	currentProfile = profiles.get(0);
	updateProfileUIInfo(currentProfile);

	/*
	//  set profile on the keymaps
	// set device is required before calling initialize tabs.
	keymapUIManager.setDevice(device);
	keymapUIManager.initializeTabs();
	// initialize tabs is required before calling set profile.
	keymapUIManager.setProfile(currentProfile);
	keymapUIManager.addSaveNotification(this);
	*/
	keymapUIManager.setProfile(currentProfile);
    }
    /**
     * The profiles combo box selected a new profile.
     */
    public void profileSelected(){
	Profile selectedProfile;
	App app = (App)appsCB.getSelectionModel().getSelectedItem();
	if(app == null){
	    return;
	}

	selectedProfile = (Profile)profileCB.getSelectionModel().getSelectedItem();
	if(selectedProfile != null){
	    currentProfile = selectedProfile;
	    //set the profile to the keymap controller
	    keymapUIManager.setProfile(currentProfile);
	    updateProfileUIInfo(selectedProfile);
	}
    }
    /**
     * Updates the type, programs, and profiles combo boxes.
     */
    public void updateComboBoxes(){
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    updateComboBoxesOnType(AppType.GAME);
	}else{
	    updateComboBoxesOnType(AppType.APPLICATION);
	}
    }
    /**
     * Set the description for the currently selected keymap.
     * @param keymapID the id of the keymap to set the description.
     * @param description the description of the keymap.
     */
    public void setKeymapDescription(int keymapID, String description){
	currentProfile.getKeymap(keymapID).setDescription(description);
	keymapUIManager.setDescriptionText(description);
    }
    /**
     * Opens the display keymap popup with the specified keymap id.
     * @param keymapID from 0 to 7.
     */ 
    public void openDisplayKeymapPopup(int keymapID){
	if(!checkDevice()) return;
	try{
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/DisplayKeymapUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    Parent root = (Parent)fxmlLoader.load(location.openStream());
	    displayKeymapUIController = (DisplayKeymapUIController) fxmlLoader.getController();
	    Scene scene = new Scene(root);
	    Stage stage = new Stage();
	    stage.setScene(scene);
	    displayKeymapUIController.setStage(stage);
	}catch(IOException e){}
	GenerateBindingsImage generator = new GenerateBindingsImage(device);
	displayKeymapUIController.setGenerateBindingsImage(generator);
	displayKeymapUIController.displayKeymap(currentProfile.getKeymap(keymapID));
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Updates the combo box by type and always selects the first program
     * to populate the profiles list.
     * @param type the type of profile to sort on.
     */
    private void updateComboBoxesOnType(AppType type){
	ObservableList<App> apps;
	ObservableList<Profile> profiles = null;
	if(type == AppType.APPLICATION){
	    apps = FXCollections.observableArrayList(profileManager.getApplications());
	}else{
	    apps = FXCollections.observableArrayList(profileManager.getGames());
	}
	if(apps.size() > 0 && apps.get(0) != null){
	    profiles = FXCollections.observableArrayList(profileManager.getProfile(apps.get(0)));
	}

	appsCB.valueProperty().removeListener(this);
	appsCB.setItems(apps);
	if(apps.size() > 0){
	    appsCB.getSelectionModel().selectFirst();
	    // set app ui
	    updateAppUIInfo((App)appsCB.getSelectionModel().getSelectedItem());
	}
	if(profiles == null){
	    profileCB.setItems(FXCollections.observableArrayList());
	}else{
	    profileCB.setItems(profiles);
	    profileCB.getSelectionModel().selectFirst();
	    profileSelected();
	}
	// I usually have a listener for this class but
	// typeCB and profileCB both contain Strings while programCB contains Apps.
	// So its necessary to extend a generic listener here
	appsCB.valueProperty().addListener(new ChangeListener<App>(){
	    @Override
	    public void changed(ObservableValue<? extends App> ov, App previousValue, App newValue) {
		if(ov == appsCB.valueProperty()){
		    updateProfilesComboBox();
		}
	    }
	});
    }
    /**
     * Sets the tool tip with the string by the specified information.
     * @param button the button to set the tooltip.
     * @param toolString the string to on the tooltip.
     */
    private void setButtonToolTip(Button button, String toolString){
	Tooltip tooltip = new Tooltip();
	tooltip.setText(toolString);
	button.setTooltip(tooltip);
    }
    private void openNewProfilePopup(){
	if(!checkDevice()) return;
	if(newProfileUIController == null){
	    newProfileUIController = (NewProfileUIController)openPopup("/com/monkygames/kbmaster/fxml/popup/NewProfileUI.fxml");
	    if(newProfileUIController == null) return;
	    newProfileUIController.setProfileManager(profileManager);
	    newProfileUIController.setDevice(device);
	}
	newProfileUIController.showStage();
    }
    private void openCloneProfilePopup(){
	if(!checkDevice()) return;
	// check if a profile has been selected
	Profile profile = (Profile)profileCB.getSelectionModel().getSelectedItem();
	if(profile == null){
	    PopupManager.getPopupManager().showError("No Profile selected");
	    return; 
	}
	if(cloneProfileUIController == null){
	    cloneProfileUIController = (CloneProfileUIController) openPopup("/com/monkygames/kbmaster/fxml/popup/CloneProfileUI.fxml");
	    if(cloneProfileUIController == null) return;
	    cloneProfileUIController.setProfileManager(profileManager);
	    cloneProfileUIController.setDevice(device);
	}
	cloneProfileUIController.showStage();
    }
    private void openPDFPopup(){
	Profile profile = (Profile)profileCB.getSelectionModel().getSelectedItem();
	if(profile == null){
	    PopupManager.getPopupManager().showError("No Profile selected");
	    return; 
	}
	File file = pdfChooser.showSaveDialog(null);
	if(file != null){
	    GenerateBindingsImage generator = new GenerateBindingsImage(device);
	    BindingPDFWriter pdfWriter = new BindingPDFWriter(generator.generateImages(profile),
							      profile.getProfileName(),
							      file.getPath());
	}
    }
    private void openDeleteProfilePopup(){
	if(!checkDevice()) return;
	Profile profile = (Profile)profileCB.getSelectionModel().getSelectedItem();
	if(profile == null){
	    PopupManager.getPopupManager().showError("No Profile selected");
	    return; 
	}
	if(deleteProfileUIController == null){
	    deleteProfileUIController = (DeleteProfileUIController)openPopup("/com/monkygames/kbmaster/fxml/popup/DeleteProfileUI.fxml");
	    if(deleteProfileUIController == null)return;
	    deleteProfileUIController.setProfileManager(profileManager);
	}
	deleteProfileUIController.setProfile(profile);
	deleteProfileUIController.showStage();
    }
    /**
     * Opens a popup specified by the url.
     * @param fxmlURL the url of the fxml file to open.
     * @return the controller associated with the fxml file.
     */
    private PopupController openPopup(String fxmlURL){
	PopupController popupController = PopupManager.getPopupManager().openPopup(fxmlURL);
	if(popupController == null) return null;
	popupController.addNotification(this);
	return popupController;
    }
    /**
     * Checks if a device has been selected and pops an error if it has not.
     * @return true if the device has been selected and false if no device has been selected.
     */
    private boolean checkDevice(){
	if(device == null){
	    PopupManager.getPopupManager().showError("No device selected");
	    return false;
	}
	return true;
    }
    /**
     * Updates the UI with the app information.
     * @param app the app to be updated.
     */
    private void updateAppUIInfo(App app){
	appInfoTA.setText(app.getInfo());
	if(app.getAppLogoPath() == null){
	    appLogoIV.setImage(new Image("/com/monkygames/kbmaster/fxml/resources/profile/app_logo.png"));
	}else{
	    try {
		appLogoIV.setImage(new Image(new FileInputStream(new File(app.getAppLogoPath()))));
	    } catch (FileNotFoundException ex) {
		Logger.getLogger(ProfileUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	if(app.getDevLogoPath() == null){
	    devLogoIV.setImage(new Image("/com/monkygames/kbmaster/fxml/resources/profile/dev_logo.png"));
	}else{
	    try {
		devLogoIV.setImage(new Image(new FileInputStream(new File(app.getDevLogoPath()))));
	    } catch (FileNotFoundException ex) {
		Logger.getLogger(ProfileUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    /**
     * Updates the UI with the profile information.
     * @param profile the information to update the UI with.
     */
    private void updateProfileUIInfo(Profile profile){
	infoTA.setText(profile.getInfo());
	authorL.setText(profile.getAuthor());
	Calendar cal = Calendar.getInstance();
	cal.setTimeInMillis(profile.getLastUpdatedDate());
	SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
	updatedL.setText(date_format.format(cal.getTime()));
    }
	
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	//profileManager = new ProfileManager("local.db4o");
	
	typeCB.setItems(FXCollections.observableArrayList());
	profileCB.setItems(FXCollections.observableArrayList());
	appsCB.setItems(FXCollections.observableArrayList());

	profileDir = new File("profiles");
	if(!profileDir.exists()){
	    profileDir.mkdir();
	}
	
	/*
	typeCB.getItems().removeAll();
	Image gameImage = new Image("/com/monkygames/kbmaster/fxml/resources/sort/game.png");
	Image applicationImage = new Image("/com/monkygames/kbmaster/fxml/resources/sort/application.png");
	ObservableList<Image> images = FXCollections.observableArrayList(gameImage,applicationImage);
	typeCB.setItems(images);
	typeCB.setCellFactory(new ImageCellFactoryCallback());
	*/
	setButtonToolTip(newProfileB, "New Profile");
	setButtonToolTip(cloneProfileB, "Clone Profile");
	setButtonToolTip(importProfileB, "Import Profile");
	setButtonToolTip(exportProfileB, "Export Profile");
	setButtonToolTip(printPDFB, "Print PDF");
	setButtonToolTip(deleteProfileB, "Delete Profile");

	pdfChooser = new FileChooser();
	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
	pdfChooser.getExtensionFilters().add(extFilter);

	keymapUIManager = new KeymapUIManager();
    }

    @Override
    public void changed(ObservableValue<? extends String> ov,  String previousValue, String newValue) {
	if(ov == typeCB.valueProperty()){
	    updateComboBoxes();
	}else if(ov == profileCB.valueProperty()){
	    // load new profile
	    // set configurations!
	    profileSelected();
	}
    }

// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void onOK(Object src, String message) {
	if(message != null && message.equals("Save")){
	    // save the profile
	    profileManager.updateProfile(currentProfile);
	}else{
	    updateComboBoxes();
	}
    }

    @Override
    public void onCancel(Object src, String message) {
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
