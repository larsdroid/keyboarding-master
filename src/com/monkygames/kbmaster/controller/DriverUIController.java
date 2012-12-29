/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === javafx imports === //
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
// === jinput imports === //
import net.java.games.input.Component.Identifier.Key;

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class DriverUIController implements Initializable{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void initButtons(){
	List<Node> nodes = rootPane.getChildren();
	//Note, in Java 8, can use Lamba Function here
	for(Node node: nodes){
	    if(node instanceof Button){
		
	    }
	}
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt){
	System.out.println("button action "+evt);
	Object obj = evt.getSource();
	if(obj instanceof Button){
	    Button button = (Button)obj;
	    button.getText();
	}
    }
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
