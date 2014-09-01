/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.kbmaster.controller.login;

import com.monkygames.kbmaster.account.CloudAccount;
import com.monkygames.kbmaster.account.DropBoxAccount;
import com.monkygames.kbmaster.controller.ButtonController;
import com.monkygames.kbmaster.controller.DeviceMenuUIController;
import com.monkygames.kbmaster.util.WindowUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 */
public class LoginUIController implements Initializable {

    // === variables === //
    @FXML
    public ChoiceBox accessCB;
    @FXML
    public Button loginB;
    @FXML
    public Button close;
    @FXML
    public Pane loginPane;

    /**
     * Used for setting animation effects.
     */
    private ButtonController buttonController;
    private Parent root;

    /**
     * Used for closing the window.
     */
    private Stage loginStage;
    private Stage deviceMenuStage;
	private Stage dropBoxStage;
	/**
	 * Controller for device menu.
	 */
    private DeviceMenuUIController deviceMenuController;
	/**
	 * Controller for dropbox menu.
	 */
	private DropBoxUIController dropBoxController;
	/**
	 * Network enabled accounts.
	 */
	private CloudAccount cloudAccount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		buttonController = new ButtonController();
		buttonController.addNode(loginB);
		buttonController.addNode(close);

		accessCB.setItems(FXCollections.observableArrayList("Local","Dropbox"));
		accessCB.getSelectionModel().selectFirst();

		/**
		 * Handle ChoiceBox events.
		 */
		accessCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String value, String newValue) {
				switch(newValue){
					case "Dropbox":
						break;
					case "Local":
						break;
				}
			}
		});
		// so that stages can hide without being terminated.
		Platform.setImplicitExit(false);
    }

    @FXML
    public void loginEventFired(ActionEvent evt){

		// hide login gui
		loginStage.hide();

		// TODO check if local or network and take apropriate action
		switch(accessCB.getSelectionModel().getSelectedIndex()){
			// local
			case 0:
				// create main gui
				if(deviceMenuController == null){
					try {
						URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/DeviceMenuUI.fxml");
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(location);
						fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
						root = (Parent)fxmlLoader.load(location.openStream());
						deviceMenuController = (DeviceMenuUIController) fxmlLoader.getController();
						deviceMenuStage = WindowUtil.createStage(root);
						deviceMenuController.setLoginController(this);
					} catch (IOException ex) {
						Logger.getLogger(LoginUIController.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				deviceMenuController.initResources();
				deviceMenuStage.show();
				break;
			// dropbox
			case 1:
				// create main gui
				if(dropBoxController == null){
					try{
						URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/login/DropBoxUI.fxml");
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(location);
						fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
						Parent dropBoxRoot = (Parent)fxmlLoader.load(location.openStream());
						dropBoxController = (DropBoxUIController) fxmlLoader.getController();
						dropBoxStage = WindowUtil.createStage(dropBoxRoot);
						//dropBoxController.setLoginController(this);
					} catch (IOException ex) {
						Logger.getLogger(LoginUIController.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				dropBoxStage.show();
				break;
		}



    }

    /**
     * Closes the login window and exits the program.
     */
    @FXML
    public void closeEventFired(ActionEvent evt){
	System.exit(1);
    }

    public void setStage(Stage loginStage){
	this.loginStage = loginStage;
    }

    /**
     * The device controller has called to be hiden.
     * @param showLogin true if the login should also be shown and false otherwise.
     */
    public void hideDeviceMenu(boolean showLogin){
	deviceMenuStage.hide();
	if(showLogin){
	    loginStage.show();
	}
    }
    /**
     * Shows the device menu.
     * @param hideLogin true if the login should also be hidden and false otherwise.
     */
    public void showDeviceMenu(boolean hideLogin){
	deviceMenuStage.show();
	if(hideLogin){
	    loginStage.hide();
	}
    }
    /**
     * Shows the device menu.
     * @param hideLogin true if the login should also be hidden and false otherwise.
     */
    public void showDeviceMenuFromNonJavaFXThread(){
	try{
	    deviceMenuStage.show();
	}catch(Exception e){
	    Platform.runLater(new Runnable() {
		@Override
		public void run() {
		    deviceMenuStage.show();
		}
	    });
	}
    }
}
