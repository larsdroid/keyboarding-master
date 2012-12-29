/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class MainUIController implements Initializable{


// ============= Class variables ============== //
    @FXML
    private Label versionL;
    @FXML
    private TabPane driverTabPane;
    @FXML
    private ComboBox driverComboBox;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void deviceComboBoxChanged(ActionEvent evt){

    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void initDriverComboBox(){
	//driverComboBox.setItems(list);

    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// get available drivers and load them in list.
	initDriverComboBox();
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
