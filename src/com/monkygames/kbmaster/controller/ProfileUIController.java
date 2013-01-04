/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.input.ProfileType;
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
// === kbmaster imports === //
import com.monkygames.kbmaster.io.ProfileManager;
import javafx.collections.ObservableList;

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
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	profileManager = new ProfileManager("local.prof");
	
	typeCB.setItems(FXCollections.observableArrayList("Game","Application"));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);

	updateComboBoxesOnType(ProfileType.GAME);

	/*
	typeCB.getItems().removeAll();
	Image gameImage = new Image("/com/monkygames/kbmaster/fxml/resources/sort/game.png");
	Image applicationImage = new Image("/com/monkygames/kbmaster/fxml/resources/sort/application.png");
	ObservableList<Image> images = FXCollections.observableArrayList(gameImage,applicationImage);
	typeCB.setItems(images);
	typeCB.setCellFactory(new ImageCellFactoryCallback());
	*/
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
