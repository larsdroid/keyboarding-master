/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

// === javafx imports === //
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
// === jinput imports === //

/**
 * Handles UI Events for the main window.
 * TODO, use jinput to get key presses!
 * @version 1.0
 */
public class SingleKeyController implements Initializable, EventHandler{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    @FXML
    private TextField singleKeyTF;
    @FXML
    private Label shiftL, ctrlL, altL;
    private boolean ignoreModifierRelease = false;

// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Sets the single key for the text field.
     * @param singleKey the single key to be set.
     */
    public void setSingleKey(String singleKey){
	singleKeyTF.setText(singleKey);
    }
    public void setStage(Stage stage){
	//stage.addEventHandler(KeyEvent.KEY_TYPED, this);
	//stage.addEventHandler(KeyEvent.KEY_PRESSED, this);
	stage.addEventHandler(KeyEvent.KEY_RELEASED, this);
	//rootPane.addEventHandler(KeyEvent.KEY_TYPED, this);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object obj = evt.getSource();
    }
    /**
     * Sets the key pressed from the key event.
     * @param keyEvent the key that was released.
     * @param type the type of event, could by KEY, CTRL, SHIFT, ALT, or META.
     */
    private void setKeyPressed(KeyEvent keyEvent, String type){
	System.out.println(type+" Pressed");
	String key = keyEvent.getCharacter();
	System.out.println("key: "+key+" Code: "+keyEvent.getCode()+" Text: "+keyEvent.getText());
	KeyCode code = keyEvent.getCode();
	singleKeyTF.setText(code.getName());
	if(type.equals("ALT")){
	    altL.setDisable(false);
	    ctrlL.setDisable(true);
	    shiftL.setDisable(true);
	}else if(type.equals("CTRL")){
	    altL.setDisable(true);
	    ctrlL.setDisable(false);
	    shiftL.setDisable(true);
	}else if(type.equals("SHIFT")){
	    altL.setDisable(true);
	    ctrlL.setDisable(true);
	    shiftL.setDisable(false);
	}else{
	    altL.setDisable(true);
	    ctrlL.setDisable(true);
	    shiftL.setDisable(true);
	}
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    @Override
    public void handle(Event event) {
	//System.out.println("Handle - "+event);


	if(KeyEvent.KEY_TYPED.equals(event.getEventType())){
	    KeyEvent keyEvent = (KeyEvent)event;
	    System.out.println("KEY TYPED");
	    String key = keyEvent.getCharacter();
	    System.out.println("key: "+key);
	    System.out.println("is shift down: "+keyEvent.isShiftDown());
	    System.out.println("is ctrl down: "+keyEvent.isControlDown());
	    System.out.println("is alt down: "+keyEvent.isAltDown());
	    System.out.println("is meta down: "+keyEvent.isMetaDown());
	    System.out.println("is shortcut down:: "+keyEvent.isShortcutDown());
	    singleKeyTF.setText(key);
	} else if(KeyEvent.KEY_PRESSED.equals(event.getEventType())){
	    KeyEvent keyEvent = (KeyEvent)event;

	    System.out.println("KEY Pressed");
	    String key = keyEvent.getCharacter();
	    System.out.println("key: "+key);
	    System.out.println("is shift down: "+keyEvent.isShiftDown());
	    System.out.println("is ctrl down: "+keyEvent.isControlDown());
	    System.out.println("is alt down: "+keyEvent.isAltDown());
	    System.out.println("is meta down: "+keyEvent.isMetaDown());
	    singleKeyTF.setText(key);
	} else if(KeyEvent.KEY_RELEASED.equals(event.getEventType())){
	    KeyEvent keyEvent = (KeyEvent)event;

	    if (keyEvent.getCode() == KeyCode.CONTROL && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isControlDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "CTRL");
	    }else if (keyEvent.getCode() == KeyCode.ALT && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isAltDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "ALT");
	    }else if (keyEvent.getCode() == KeyCode.SHIFT && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isShiftDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "SHIFT");
	    }else if (keyEvent.getCode() == KeyCode.META && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isMetaDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "META");
	    } else if(!ignoreModifierRelease){
		setKeyPressed(keyEvent, "KEY");
	    } else {
		ignoreModifierRelease = false;
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
