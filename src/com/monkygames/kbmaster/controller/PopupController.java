/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Contains common methods useful for popups.
 * @version 1.0
 */
public class PopupController implements Initializable{


// ============= Class variables ============== //
    private Stage stage;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Sets the stage in order to hide this popup.
     * @param stage the stage that can be used to hide the windows.
     */
    public void setStage(Stage stage){
	this.stage = stage;
    }
// ============= Protected Methods ============== //
    /**
     * Hides this popup.
     */
    protected void hideStage(){
	stage.hide();
    }
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) { }
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
