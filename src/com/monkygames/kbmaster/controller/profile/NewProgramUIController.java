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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
    /**
     * The full path to the file.
     */
    private String appLogoPath = null;
    /**
     * The name of the app logo file.
     */
    private String appLogoFileName = null;
    private String devLogoPath = null;
    private String devLogoFileName = null;
    /**
     * Used for selecting logo files.
     */
    private FileChooser fileChooser;
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
	// check for app icon is set
	if(appLogoPath != null){
	    // copy file locally -- note, don't do that until "saved"
	    File dir = new File("local"+File.separator+appName);
	    dir.mkdirs();
	    File dest = new File(dir.getPath()+File.separator+appLogoFileName);
	    CopyOption options[] = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING,
						  StandardCopyOption.COPY_ATTRIBUTES};
	    File file = new File(appLogoPath);
	    try {
		Files.copy(file.toPath(), dest.toPath(), options);
	    } catch (IOException ex) {
		Logger.getLogger(NewProgramUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	if(devLogoPath != null){
	    // copy file locally -- note, don't do that until "saved"
	    File dir = new File("local"+File.separator+appName);
	    dir.mkdirs();
	    File dest = new File(dir.getPath()+File.separator+devLogoFileName);
	    CopyOption options[] = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING,
						  StandardCopyOption.COPY_ATTRIBUTES};
	    File file = new File(devLogoPath);
	    try {
		Files.copy(file.toPath(), dest.toPath(), options);
	    } catch (IOException ex) {
		Logger.getLogger(NewProgramUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }

	}
	reset();
	notifyOK(appName);
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
    public void addAppLogoEventFired(ActionEvent evt){
	fileChooser.setTitle("App Logo");
	File file = fileChooser.showOpenDialog(null);
	if(file != null){
	    try {
		// set icon in the UI
		Image image = new Image(new FileInputStream(file));
		appIV.setImage(image);
		appLogoPath = file.getAbsolutePath();
		appLogoFileName = file.getName();
	    } catch (FileNotFoundException ex) {
		Logger.getLogger(NewProgramUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    public void addDevLogoEventFired(ActionEvent evt){
	fileChooser.setTitle("Developer Logo");
	File file = fileChooser.showOpenDialog(null);
	if(file != null){
	    try {
		// set icon in the UI
		Image image = new Image(new FileInputStream(file));
		devIV.setImage(image);
		devLogoPath = file.getAbsolutePath();
		devLogoFileName = file.getName();
	    } catch (FileNotFoundException ex) {
		Logger.getLogger(NewProgramUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void reset(){
	programTF.setText("");
	appLogoPath = null;
	appLogoFileName = null;
	devLogoPath = null;
	devLogoFileName = null;
	hideStage();
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	fileChooser = new FileChooser();
	// only allow PNG files
	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)","*.png");
	fileChooser.getExtensionFilters().add(extFilter);
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
