/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.controller.profile.NewProfileUIController;
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
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Handles UI Events for the profile panel.
 * @version 1.0
 */
public class ProfileUIController implements Initializable, ChangeListener<String>{

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
    private ProfileManager profileManager;
    private NewProfileUIController newProfileUIController;
    private File profileDir;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    @FXML
    public void profileEventFired(ActionEvent evt){
	Object src = evt.getSource();
	if(src == newProfileB){
	}else if(src == cloneProfileB){
	}else if(src == importProfileB){
	}else if(src == exportProfileB){
	}else if(src == printPDFB){
	}else if(src == deleteProfileB){
	}
	
    }
    /**
     * Sets the device based on the device name.
     * @param deviceName the device name to be set.
     */
    public void setDevice(String deviceName){
	if(profileManager != null){
	    profileManager.close();
	}
	profileManager = new ProfileManager(profileDir+File.separator+deviceName);
	updateComboBoxes();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void updateComboBoxesOnType(ProfileType type){
	ObservableList<String> programs;
	if(type == ProfileType.APPLICATION){
	    programs = FXCollections.observableArrayList(profileManager.getApplicationNames());
	}else{
	    programs = FXCollections.observableArrayList(profileManager.getGameNames());
	}

	programCB.setItems(programs);
	profileCB.setItems(FXCollections.observableArrayList());
    }
    /**
     * Updates combo boxes based on Profile Type.
     */
    private void updateComboBoxes(){
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    updateComboBoxesOnType(ProfileType.GAME);
	}else{
	    updateComboBoxesOnType(ProfileType.APPLICATION);
	}
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
    public void openNewProfilePopup(){
	if(newProfileUIController == null){
	    try {
		// pop open add new device
		URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewProfileUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(location.openStream());
		newProfileUIController = (NewProfileUIController) fxmlLoader.getController();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		newProfileUIController.setStage(stage);
		newProfileUIController.setProfileManager(profileManager);
	    } catch (IOException ex) {
		Logger.getLogger(ProfileUIController.class.getName()).log(Level.SEVERE, null, ex);
		return;
	    }
	}
	newProfileUIController.showStage();
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	//profileManager = new ProfileManager("local.db4o");
	
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(ProfileType.GAME),
							  ProfileTypeNames.getProfileTypeName(ProfileType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);

	profileDir = new File("profiles");
	if(!profileDir.exists()){
	    profileDir.mkdir();
	}
	

	//updateComboBoxesOnType(ProfileType.GAME);

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
    }

    @Override
    public void changed(ObservableValue<? extends String> ov, String previousValue, String newValue) {
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
