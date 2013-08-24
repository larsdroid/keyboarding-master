/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.io.GenerateBindingsImage;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

/**
 * Displays a single keymap with its descriptions in a popup window.
 * Note, both the setGenerateBindingsImage and setStage must be called before using.
 * @version 1.0
 */
public class DisplayKeymapUIController implements Initializable{

// ============= Class variables ============== //
    @FXML
    private Button closeB;
    @FXML
    private ImageView imageView;
    private Stage stage;

    /**
     * Handles generating the image.
     */
    private GenerateBindingsImage imageGenerator;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Generates the descriptions and sets to the label.
     * Open the window.
     */
    public void displayKeymap(Keymap keymap){
	Image image = imageGenerator.generateImage(keymap);
	BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
	bufferedImage.getGraphics().drawImage(image, 0, 0, null);
	WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
	imageView.setImage(writableImage);
	stage.show();
    }
    /**
     * Sets the image generator that generates the image to be displayed.
     */
    public void setGenerateBindingsImage(GenerateBindingsImage imageGenerator){
	this.imageGenerator = imageGenerator;
    }
    public void setStage(Stage stage){ 
	this.stage = stage;
    }
    public void closeEventFired(ActionEvent evt){
	stage.hide();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
