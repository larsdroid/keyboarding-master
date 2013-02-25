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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
// === jinput imports === //

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class SingleKeyController implements Initializable{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
// ============= Constructors ============== //
// ============= Public Methods ============== //
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
