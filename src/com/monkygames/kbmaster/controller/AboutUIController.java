/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.util.about.AboutDB4O;
import com.monkygames.kbmaster.util.about.AboutInstallBuilder;
import com.monkygames.kbmaster.util.about.AboutJInput;
import com.monkygames.kbmaster.util.about.AboutJava;
import com.monkygames.kbmaster.util.about.AboutJavaFX;
import com.monkygames.kbmaster.util.about.AboutKBM;
import com.monkygames.kbmaster.util.about.AboutKryonet;
import com.monkygames.kbmaster.util.about.AboutLinuxGamer;
import com.monkygames.kbmaster.util.about.AboutProgram;
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Displays an error message.
 * Note, must set the stage.
 * @version 1.0
 */
public class AboutUIController implements Initializable{

    @FXML
    private ImageView logoIV;
    @FXML
    private Label titleL;
    @FXML
    private Label versionL;
    @FXML
    private Label vendorL;
    @FXML
    private Hyperlink homepageHL;
    @FXML
    private TextArea infoTA;
    @FXML
    private Button okB;
    private Stage stage;
    /**
     * Used for selecting the product to display.
     */
    public enum AboutType {DB4O, INSTALLBUILDER, JAVA,
			    JAVAFX, JINPUT, KBM, KRYONET, LINUXGAMER}

    private AboutProgram aboutDB4O, aboutInstallBuilder, aboutJInput,
			aboutJava, aboutJavaFX, aboutKBM, aboutKryonet, 
			aboutLinuxGamer;

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setStage(Stage stage){
	this.stage = stage;
    }
    /**
     * Opens the popup based on the type.
     * @param aboutType the type to display.
     */
    public void showAbout(AboutType aboutType){
	AboutProgram aboutProgram = null;
	switch(aboutType){
	    case DB4O:
		aboutProgram = aboutDB4O;
		break;
	    case INSTALLBUILDER:
		aboutProgram = aboutInstallBuilder;
		break;
	    case JAVA:
		aboutProgram = aboutJava;
		break;
	    case JINPUT:
		aboutProgram = aboutJInput;
		break;
	    case JAVAFX:
		aboutProgram = aboutJavaFX;
		break;
	    case KBM:
		aboutProgram = aboutKBM;
		break;
	    case KRYONET:
		aboutProgram = aboutKryonet;
		break;
	    case LINUXGAMER:
	    default:
		aboutProgram = aboutLinuxGamer;
	}

	logoIV.setImage(aboutProgram.getImage());
	titleL.setText(aboutProgram.getTitle());
	versionL.setText(aboutProgram.getVersion());
	vendorL.setText(aboutProgram.getVendor());
	homepageHL.setText(aboutProgram.getUrl());
	infoTA.setText(aboutProgram.getInfo());

	stage.show();
    }
    public void okEventFired(ActionEvent evt){
	stage.hide();
    }
    @FXML
    public void handleCloseEvent(ActionEvent evt){
	stage.hide();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	aboutDB4O = new AboutDB4O();
	aboutInstallBuilder = new AboutInstallBuilder();
	aboutJInput = new AboutJInput();
	aboutJava = new AboutJava();
	aboutJavaFX = new AboutJavaFX();
	aboutKBM = new AboutKBM();
	aboutKryonet = new AboutKryonet();
	aboutLinuxGamer = new AboutLinuxGamer();
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
