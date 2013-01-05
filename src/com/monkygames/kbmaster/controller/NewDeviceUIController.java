/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
// === kbmaster imports === //
import com.monkygames.kbmaster.account.GlobalAccount;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceType;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Handles UI Events for the profile panel.
 * @version 1.0
 */
public class NewDeviceUIController implements Initializable, ChangeListener<String>{

// ============= Class variables ============== //
    @FXML
    private ComboBox deviceTypeCB;
    @FXML
    private ComboBox deviceMakeCB;
    @FXML
    private ComboBox deviceNameCB;
    @FXML
    private HBox iconImageHBox;
    @FXML
    private TextArea deviceDescriptionTA;
    @FXML
    private Label driverVersionL;
    @FXML
    private Label driverStatusL;

    private Stage stage;
    private GlobalAccount globalAccount;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setStage(Stage stage){
	this.stage = stage;
    }
    public void setAccount(GlobalAccount globalAccount){
	this.globalAccount = globalAccount;
    }
    public void cancelEventFired(ActionEvent evt){
	stage.hide();
    }
    public void addEventFired(ActionEvent evt){

    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Removes all items from the make combo box and populates with new list.
     * Also removes all items form the deviceName list.
     * @param list the new list of items to populate the combo box list.
     */
    private void setMakeComboBox(List<String> list){
	deviceMakeCB.getItems().removeAll();
	deviceNameCB.getItems().removeAll();
	deviceNameCB.setItems(FXCollections.observableArrayList());
	ObservableList<String> observableList = FXCollections.observableArrayList(list);
	deviceMakeCB.setItems(observableList);
    }
    private void setModelComboBox(DeviceType type, String make){
	deviceTypeCB.getItems().removeAll();
	List<String> list = globalAccount.getDriverManager().getDevicesByMake(type, make);
	ObservableList<String> modelsList = FXCollections.observableArrayList(list);
	deviceNameCB.setItems(modelsList);
    }
    /**
     * Set the device information in the gui from the specified information.
     * @param type the type of device.
     * @param make the make of the device.
     * @param model the model of the device.
     */
    private void setDeviceInformation(DeviceType type, String make, String model){
	// find device
	Device device = globalAccount.getDriverManager().getDevice(type, make, model);
	if(device == null){
	    // TODO - pop an error dialog
	    return;
	}
	iconImageHBox.getChildren().removeAll();
	ImageView imageView = new ImageView(new Image(device.getDeviceInformation().getDeviceIcon()));
	iconImageHBox.getChildren().add(imageView);

	deviceDescriptionTA.setText(device.getDeviceInformation().getDeviceDescription());
	driverVersionL.setText(device.getDeviceInformation().getVersion());
	//driverStatusL.setText();
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	deviceTypeCB.getItems().removeAll();
	deviceMakeCB.getItems().removeAll();
	deviceNameCB.getItems().removeAll();

	ObservableList<String> typesList = FXCollections.observableArrayList("Keyboard","Mouse");
	deviceTypeCB.setItems(typesList);
	deviceMakeCB.setItems(FXCollections.observableArrayList());
	deviceNameCB.setItems(FXCollections.observableArrayList());

	deviceTypeCB.valueProperty().addListener(this);
	deviceMakeCB.valueProperty().addListener(this);
	deviceNameCB.valueProperty().addListener(this);


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
	if(ov == deviceTypeCB.valueProperty()){
	    int index = deviceTypeCB.getSelectionModel().getSelectedIndex();
	    switch(index){
		case 1:
		    setMakeComboBox(globalAccount.getDriverManager().getMouseMakes());
		    break;
		case 0:
		default:
		    setMakeComboBox(globalAccount.getDriverManager().getKeyboardMakes());
	    }
	}else if(ov == deviceMakeCB.valueProperty()){
	    int index = deviceTypeCB.getSelectionModel().getSelectedIndex();
	    switch(index){
		case 1:
		    setModelComboBox(DeviceType.MOUSE,newValue);
		    break;
		case 0:
		default:
		    setModelComboBox(DeviceType.KEYBOARD,newValue);
	    }
	}else if(ov == deviceNameCB.valueProperty()){
	    int index = deviceTypeCB.getSelectionModel().getSelectedIndex();
	    String make = (String)deviceMakeCB.getSelectionModel().getSelectedItem();
	    switch(index){
		case 1:
		    setDeviceInformation(DeviceType.MOUSE,make,newValue);
		    break;
		case 0:
		default:
		    setDeviceInformation(DeviceType.KEYBOARD,make,newValue);
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
