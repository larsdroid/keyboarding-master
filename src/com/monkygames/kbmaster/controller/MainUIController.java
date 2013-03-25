/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === kbmaster imports === //
import com.monkygames.kbmaster.account.GlobalAccount;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.util.PopupManager;
// === java imports === //
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
// === javafx imports === //
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class MainUIController implements Initializable, ChangeListener<Image>, PopupNotifyInterface{


// ============= Class variables ============== //
    @FXML
    private Label versionL;
    @FXML
    private TabPane driverTabPane;
    @FXML
    private ComboBox driverComboBox;
    @FXML
    private Pane profilePane;
    @FXML
    private Button descriptionB;
    @FXML
    private Label keymapDescriptionL;
    private ProfileUIController profileUIController;
    /**
     * Contains the device information.
     */
    private GlobalAccount globalAccount;
    /**
     * Used for displaying a new device popup.
     */
    private Stage newDeviceStage;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    @FXML
    public void deviceComboBoxChanged(ActionEvent evt){

    }

    public ProfileUIController getProfileUIController() {
	return profileUIController;
    }

    /**
     * Adds the following device to the local account.
     * Note, if the device is already added, then an error pops
     * indicating that the device already is added.
     * @param device the device to add.
     */
    public void addDevice(Device device){
	// update global account
	if(!globalAccount.downloadDevice(device.getDeviceInformation().getName())){
	    PopupManager.getPopupManager().showError("Unable to add device.  Is it already added?");
	    return;
	}
	// update the pull down
	driverComboBox.valueProperty().removeListener(this);
	driverComboBox.getItems().add(new Image(device.getDeviceInformation().getDeviceIcon()));
	driverComboBox.valueProperty().addListener(this);
    }
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void initDriverComboBox(){
	driverComboBox.getItems().removeAll();
	Image image = new Image("/com/monkygames/kbmaster/fxml/resources/device/add_device.png");
	ObservableList<Image> images = FXCollections.observableArrayList(null,image);

	for(Device dPackage: globalAccount.getInstalledDevices()){
	    Image newImage = new Image(dPackage.getDeviceInformation().getDeviceIcon());
	    images.add(newImage);
	}

	driverComboBox.setItems(images);
	driverComboBox.setCellFactory(new ImageCellFactoryCallback());
	driverComboBox.setButtonCell(new ListCellImage());
	driverComboBox.valueProperty().addListener(this);
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	globalAccount = new GlobalAccount();

	// get available drivers and load them in list.
	initDriverComboBox();

	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/ProfileUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    Parent profileRoot = (Parent)fxmlLoader.load(location.openStream());
	    profileUIController = (ProfileUIController) fxmlLoader.getController();
	    profilePane.getChildren().add(profileRoot);
	    profileUIController.setKeymapTabPane(driverTabPane);
	    profileUIController.setDescriptionLabel(keymapDescriptionL);
	} catch (IOException ex) {
	    Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object obj = evt.getSource();
	// handle the description button action
	if(obj == descriptionB){
	    PopupController descriptionController = PopupManager.getPopupManager().openPopup("/com/monkygames/kbmaster/fxml/popup/SetDescriptionUI.fxml");
	    descriptionController.addNotification(this);
	    descriptionController.showStage();
	}
    }

    @Override
    public void changed(ObservableValue<? extends Image> ov, Image t, Image t1) {
	if(ov == driverComboBox.valueProperty()){
	    int index = driverComboBox.getSelectionModel().getSelectedIndex();
	    if(index == 1){
		if(newDeviceStage == null){
		    try {
			// pop open add new device
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewDeviceUI.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			Parent root = (Parent)fxmlLoader.load(location.openStream());
			NewDeviceUIController controller = (NewDeviceUIController) fxmlLoader.getController();
			Scene scene = new Scene(root);
			newDeviceStage = new Stage();
			newDeviceStage.setScene(scene);
			controller.setStage(newDeviceStage);
			controller.setAccount(globalAccount);
			controller.setMainUIController(this);
		    } catch (IOException ex) {
			Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		newDeviceStage.show();
		driverComboBox.valueProperty().removeListener(this);
		driverComboBox.getSelectionModel().selectFirst();
		driverComboBox.valueProperty().addListener(this);
		//driverComboBox.setValue(null);
	    }else if(index != 0){
		// this means a driver has been selected, so we need to populate
		// the profiles and keymaps entries
		Device device = globalAccount.getInstalledDevices().get(index-2);
		profileUIController.setDevice(device);
	    }
	}
    }
    @Override
    public void onOK(Object src, String message){
	// description for keymap has been set
	profileUIController.setKeymapDescription(driverTabPane.getSelectionModel().getSelectedIndex(),message);
    }
    @Override
    public void onCancel(Object src, String message){

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
