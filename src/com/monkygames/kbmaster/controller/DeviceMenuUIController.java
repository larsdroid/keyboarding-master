/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === javafx imports === //
import com.monkygames.kbmaster.driver.Device;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * Handles UI Events for managing devices.
 * @version 1.0
 */
public class DeviceMenuUIController implements Initializable{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    // table
    @FXML
    private TableView<Device> deviceTV;
    @FXML
    private TableColumn<Device, String> deviceTC;
    @FXML
    private TableColumn<Device, String> currentProfileTC;
    @FXML
    private TableColumn<Device, String> connectedTC;
    @FXML
    private TableColumn<Device, Boolean> enabledTC;
    // buttons
    @FXML
    private Button addDeviceB;
    @FXML
    private Button configureB;
    @FXML
    private Button detailsB;
    
// ============= Constructors ============== //
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object src = evt.getSource();
	if(src == addDeviceB){
	    
	}
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	deviceTC.setCellValueFactory(new PropertyValueFactory<Device, String>("Device"));
	currentProfileTC.setCellValueFactory(new PropertyValueFactory<Device, String>("Current Profile"));
	connectedTC.setCellValueFactory(new PropertyValueFactory<Device, String>("Connected"));
	enabledTC.setCellValueFactory(new PropertyValueFactory<Device, Boolean>("Enable"));
	enabledTC.setCellFactory(CheckBoxTableCell.forTableColumn(enabledTC));

	deviceTV.getItems().setAll(parseUserList());
    }

    private List<Device> parseUserList() {
	List<Device> list = new ArrayList<>();
	// parse and construct User datamodel list by looping your ResultSet rs
	// and return the list   
	return list;
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
