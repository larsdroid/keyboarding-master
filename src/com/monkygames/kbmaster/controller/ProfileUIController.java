/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.controller.profile.CloneProfileUIController;
import com.monkygames.kbmaster.controller.profile.DeleteProfileUIController;
import com.monkygames.kbmaster.controller.profile.NewProfileUIController;
import com.monkygames.kbmaster.driver.Device;
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
import com.monkygames.kbmaster.input.ProfileType;
import com.monkygames.kbmaster.io.BindingPDFWriter;
import com.monkygames.kbmaster.io.GenerateBindingsImage;
import com.monkygames.kbmaster.util.KeymapUIManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
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
    private ComboBox programCB;
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
    private DriverUIController driverUIController;
    private ProfileManager profileManager;
    private NewProfileUIController newProfileUIController;
    private CloneProfileUIController cloneProfileUIController;
    private DeleteProfileUIController deleteProfileUIController;
    private File profileDir;
    private Device device;
    /**
     * Used for selecting a file to write a pdf binding.
     */
    private FileChooser pdfChooser;
    private TabPane keymapTabPane;
    private KeymapUIManager keymapUIManager;
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
	keymapUIManager.setDevice(device);
	if(newProfileUIController != null){
	    newProfileUIController.setDevice(device);
	}
	String deviceName = device.getDeviceInformation().getProfileName();
	if(profileManager != null){
	    profileManager.close();
	}
	profileManager = new ProfileManager(profileDir+File.separator+deviceName);

	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(ProfileType.GAME),
							  ProfileTypeNames.getProfileTypeName(ProfileType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);


	updateComboBoxes();
    }
    /**
     * Only updates the profiles combo box.
     */
    public void updateProfilesComboBox(){
	ProfileType type;
	String programName;
	ObservableList<Profile> profiles = null;
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    type = ProfileType.GAME;
	}else{
	    type = ProfileType.APPLICATION;
	}
	programName = (String)programCB.getSelectionModel().getSelectedItem();
	if(programName == null){
	    return;
	}

	profiles = FXCollections.observableArrayList(profileManager.getProfile(type, programName));
	profileCB.setItems(profiles);

	// TODO set profile on the keymaps
	keymapUIManager.initializeTabs();
    }
    /**
     * Updates the type, programs, and profiles combo boxes.
     */
    public void updateComboBoxes(){
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    updateComboBoxesOnType(ProfileType.GAME);
	}else{
	    updateComboBoxesOnType(ProfileType.APPLICATION);
	}
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Updates the combo box by type and always selects the first program
     * to populate the profiles list.
     * @param type the type of profile to sort on.
     */
    private void updateComboBoxesOnType(ProfileType type){
	ObservableList<String> programs;
	ObservableList<Profile> profiles = null;
	if(type == ProfileType.APPLICATION){
	    programs = FXCollections.observableArrayList(profileManager.getApplicationNames());
	}else{
	    programs = FXCollections.observableArrayList(profileManager.getGameNames());
	}
	if(programs.size() > 0 && programs.get(0) != null){
	    profiles = FXCollections.observableArrayList(profileManager.getProfile(type, programs.get(0)));
	}

	programCB.valueProperty().removeListener(this);
	programCB.setItems(programs);
	if(profiles == null){
	    profileCB.setItems(FXCollections.observableArrayList());
	}else{
	    profileCB.setItems(profiles);
	}
	programCB.valueProperty().addListener(this);
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
	PopupController popupController = null;
	try {
	    // pop open add new device
	    URL location = getClass().getResource(fxmlURL);
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    Parent root = (Parent)fxmlLoader.load(location.openStream());
	    popupController = (PopupController)fxmlLoader.getController();
	    Scene scene = new Scene(root);
	    Stage stage = new Stage();
	    stage.setScene(scene);
	    popupController.setStage(stage);
	    popupController.addNotification(this);
	} catch (IOException ex) {
	    Logger.getLogger(ProfileUIController.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
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
    private void populateTabPane(){
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	//profileManager = new ProfileManager("local.db4o");
	
	typeCB.setItems(FXCollections.observableArrayList());
	profileCB.setItems(FXCollections.observableArrayList());
	programCB.setItems(FXCollections.observableArrayList());

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
    public void changed(ObservableValue<? extends String> ov, String previousValue, String newValue) {
	if(ov == typeCB.valueProperty()){
	    updateComboBoxes();
	}else if(ov == programCB.valueProperty()){
	    updateProfilesComboBox();
	}

    }

// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void onOK(Object src, String message) {
	updateComboBoxes();
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
