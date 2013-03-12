/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

// === javafx imports === //
import com.monkygames.kbmaster.controller.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
// === jinput imports === //

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class SingleKeyController implements Initializable, EventHandler{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    @FXML
    private TextField singleKeyTF;
    @FXML
    private Label shiftL, ctrL, altL;
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
	stage.addEventHandler(KeyEvent.KEY_TYPED, this);
	//stage.addEventHandler(KeyEvent.KEY_PRESSED, this);
	//rootPane.addEventHandler(KeyEvent.KEY_TYPED, this);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object obj = evt.getSource();
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    public void handleOnKeyTyped(KeyEvent keyEvent){
	System.out.println("handleOnKeyTyped "+keyEvent);
	singleKeyTF.setText(keyEvent.getCharacter());
    }
    public void handleOnKeyPressed(KeyEvent keyEvent){
	System.out.println("handleOnKeyPressed "+keyEvent);
    }
    @Override
    public void handle(Event event) {
	System.out.println("Handle - "+event);
	if(KeyEvent.KEY_TYPED.equals(event.getEventType())){
	    String key = ((KeyEvent)event).getText();
	    singleKeyTF.setText(key);
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
