/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.account.GlobalAccount;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
// === javafx imports === //
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class MainUIController implements Initializable, ChangeListener<Image>{


// ============= Class variables ============== //
    @FXML
    private Label versionL;
    @FXML
    private TabPane driverTabPane;
    @FXML
    private ComboBox driverComboBox;
    @FXML
    private Pane profilePane;
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
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void initDriverComboBox(){
	driverComboBox.getItems().removeAll();
	Image image = new Image("/com/monkygames/kbmaster/fxml/resources/device/add_device.png");
	//Image image = new Image("/com/monkygames/kbmaster/driver/razer/nostromo/resources/RazerNostromoIcon.png");
	ObservableList<Image> images = FXCollections.observableArrayList(image);
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
	} catch (IOException ex) {
	    Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void changed(ObservableValue<? extends Image> ov, Image t, Image t1) {
	System.out.println("Changed!!!!");
	if(ov == driverComboBox.valueProperty()){
	    System.out.println("driver combobox");
	    int index = driverComboBox.getSelectionModel().getSelectedIndex();
	    if(index == 0){
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
		    } catch (IOException ex) {
			Logger.getLogger(MainUIController.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		newDeviceStage.show();

	    }else{

	    }

	}
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
