/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === javafx imports === //
import com.monkygames.kbmaster.account.GlobalAccount;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.engine.HardwareManager;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.util.DeviceEntry;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.RepeatManager;
import com.monkygames.kbmaster.util.WindowUtil;
import com.sun.javafx.scene.control.skin.TableCellSkin;
import com.sun.javafx.scene.control.skin.TableRowSkin;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Handles UI Events for managing devices.
 * @version 1.0
 */
//public class DeviceMenuUIController implements Initializable, EventHandler<CellEditEvent>{
public class DeviceMenuUIController implements Initializable, EventHandler<ActionEvent>{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    // table
    @FXML
    private TableView<DeviceEntry> deviceTV;
    @FXML
    private TableColumn<DeviceEntry, String> deviceNameCol;
    @FXML
    private TableColumn<DeviceEntry, String> profileNameCol;
    @FXML
    private TableColumn<DeviceEntry, String> isConnectedCol;
    @FXML
    private TableColumn<DeviceEntry, Boolean> isEnabledCol;
    // buttons
    @FXML
    private Button addDeviceB;
    @FXML
    private Button configureB;
    @FXML
    private Button detailsB;
    @FXML
    private Button exitB;
    @FXML
    private Button logoutB;
    @FXML
    private Button setProfileB;
    @FXML
    private CheckBox keysRepeatCB;
    /**
     * Used for displaying a new device popup.
     */
    private Stage newDeviceStage;
    /**
     * Used for displaying a configuration for a device.
     */
    private Stage configureDeviceStage;
    /**
     * Contains the device information.
     */
    private GlobalAccount globalAccount;
    /**
     * Used for configuring devices.
     */
    private ConfigureDeviceUIController configureDeviceController;
    /**
     * Used for selecting profiles without having to open the
     * configuration ui.
     */
    private SelectProfileUIController selectProfileController;
    /**
     * Used for managing engines (which do the work of remapping outputs).
     */
    private HardwareManager hardwareManager;
    private LoginUIController loginController;

// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setLoginController(LoginUIController loginController){
	this.loginController = loginController;
    }
    /**
     * Adds a device from the NewDeviceUI.
     * @param device the device to be added.
     */
    public void addDevice(Device device){
	// update global account
	if(!globalAccount.downloadDevice(device.getDeviceInformation().getName())){
	    PopupManager.getPopupManager().showError("Unable to add device.  Is it already added?");
	    return;
	}
	// Update device table!
	deviceTV.getItems().setAll(getDeviceEntryList(true));
    }
    /**
     * Sets the active profile for the specified device.
     */
    public void setActiveProfile(Device device, Profile profile){
	for(DeviceEntry deviceEntry: deviceTV.getItems()){
	    if(deviceEntry.getDevice() == device){
		// repopulate
		deviceTV.getItems().setAll(getDeviceEntryList(true));
		// update hardware manager
		if(deviceEntry.getIsEnabled()){
		    hardwareManager.startPollingDevice(device, profile);
		}
	    }
	}
    }
    /**
     * Prepares the gui and databases to populate device list.
     */
    public void initResources(){
	// initialize Global Acount first since getDeviceList uses it
	// to populate the list.
	globalAccount = new GlobalAccount();
	deviceTV.getItems().setAll(getDeviceEntryList(true));
    }
    /**
     * One or more devices has changed status and the UI
     * will be updated to reflect these changes.
     * Note, its not necessary to specify which devices have been
     * connected/disconnected since this information is set in the device
     * object which is universal throughout the program so no need
     * to pass references around.
     */
    public void updateDevices(){
	// update device list
	deviceTV.getItems().setAll(getDeviceEntryList(false));
	// update config ui if its open!
	if(configureDeviceController != null){
	    configureDeviceController.updateDeviceDetails();
	}
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Updates the table.
     * Note, makes sure that the check marks on the isEnabled column are correct.
     */
    private void updateTable(List<DeviceEntry> deviceEntries){
	deviceTV.getItems().setAll(deviceEntries);

	this.isEnabledCol.sortableProperty().setValue(Boolean.TRUE);

	/*
	// iterate through the rows
	TableRow row = (TableRow)skin.getParent();
	DeviceEntry deviceEntry = (DeviceEntry)row.getItem();
	Device device = deviceEntry.getDevice();
	// traverse through scene graph to get checkbox.
	TableCellSkin skin2 = (TableCellSkin)cell.getChildrenUnmodifiable().get(0);
	CheckBox checkBox = (CheckBox)skin2.getChildren().get(0);
	*/
    }
    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object src = evt.getSource();
	if(src == addDeviceB){
	    openNewDeviceUI();
	}else if(src == configureB){
	    openConfigureDeviceUI();
	}else if(src == keysRepeatCB){
	    handleKeysRepeat();
	}else if(src == exitB){
	    exitApplication();
	}else if(src == setProfileB){
	    openSelectProfileUI();
	}else if(src == logoutB){
	    logout();
	}
    }
    /**
     * Sets the xset r to on or off depending on the value of the
     * checkbox.
     */
    private void handleKeysRepeat(){
	RepeatManager.setRepeat(keysRepeatCB.isSelected());
    }
    /**
     * Opens a new device UI for adding a new device.
     */
    private void openNewDeviceUI(){
	if(newDeviceStage == null){
	    try {
		// pop open add new device
		URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewDeviceUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(location.openStream());
		NewDeviceUIController controller = (NewDeviceUIController) fxmlLoader.getController();
		Scene scene = new Scene(root);
		newDeviceStage = new Stage();
		newDeviceStage.setScene(scene);
		controller.setStage(newDeviceStage);
		controller.setAccount(globalAccount);
		controller.setDeviceMenuUIController(this);
	    } catch (IOException ex) {
		Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	newDeviceStage.show();
    }
    private void openConfigureDeviceUI(){
	DeviceEntry deviceEntry = deviceTV.getSelectionModel().getSelectedItem();
	if(deviceEntry == null) {
	    // pop error
	    PopupManager.getPopupManager().showError("No device selected");
	    return;
	}
	if(configureDeviceStage == null){
	    try {
		// pop open add new device
		URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/ConfigureDeviceUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(location.openStream());
		configureDeviceController = (ConfigureDeviceUIController) fxmlLoader.getController();
		configureDeviceStage = WindowUtil.createStage(root);

		configureDeviceController.setStage(configureDeviceStage);
		configureDeviceController.getProfileUIController().setDeviceMenuController(this);
	    } catch (IOException ex) {
		Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	configureDeviceController.setDevice(deviceEntry.getDevice());
	configureDeviceStage.show();

    }
    private void openSelectProfileUI(){
	DeviceEntry deviceEntry = deviceTV.getSelectionModel().getSelectedItem();
	if(deviceEntry == null) {
	    // pop error
	    PopupManager.getPopupManager().showError("No device selected");
	    return;
	}
	if(selectProfileController == null){
	    try {
		// pop open add new device
		URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/SelectProfile.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(location.openStream());
		selectProfileController = (SelectProfileUIController) fxmlLoader.getController();
		Stage stage = WindowUtil.createStage(root);

		selectProfileController.setStage(stage);
		selectProfileController.setDeviceMenuController(this);
	    } catch (IOException ex) {
		Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	selectProfileController.setDevice(deviceEntry.getDevice());
	selectProfileController.show();
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	hardwareManager = new HardwareManager(this);
	deviceNameCol.setCellValueFactory(new PropertyValueFactory<DeviceEntry, String>("deviceName"));
	profileNameCol.setCellValueFactory(new PropertyValueFactory<DeviceEntry, String>("profileName"));
	isConnectedCol.setCellValueFactory(new PropertyValueFactory<DeviceEntry, String>("isConnected"));
	isEnabledCol.setCellValueFactory(new PropertyValueFactory<DeviceEntry, Boolean>("isEnabled"));
	isEnabledCol.setCellFactory(CheckBoxTableCell.forTableColumn(isEnabledCol));
	isEnabledCol.setEditable(true);
	//isEnabledCol.addEventHandler(TableColumn.CellEditEvent<DeviceEntry, Boolean>, this);
	//isEnabledCol.setOnEditCommit(this);
	deviceTV.setEditable(true);

	// set the table cell to center for isConnected
	isConnectedCol.setCellFactory(new Callback<TableColumn<DeviceEntry, String>, TableCell<DeviceEntry, String>>() {
	    @Override
	    public TableCell call(TableColumn<DeviceEntry, String> param) {
		TableCell cell = new TableCell(){
		    @Override
		    public void updateItem (Object item, boolean empty ) {
			if (item != null) {
				setText(item.toString());
			}
		    }
		};

		//adding style class for the cell
		cell.getStyleClass().add("table-cell-center");
		return cell;
	    }
	});

	// set the table cell to center for isEnabled
	CheckboxCallback callback = new CheckboxCallback();
	callback.setCheckboxHandler(this);
	isEnabledCol.setCellFactory(callback);


	// initialize Global Acount first since getDeviceList uses it
	// to populate the list.
	//globalAccount = new GlobalAccount();
	//deviceTV.getItems().setAll(getDeviceEntryList());
    }

    /**
     * Returns a list of device entries available from the Global Account. 
     */
    private List<DeviceEntry> getDeviceEntryList(boolean pollDeviceState) {
	List<DeviceEntry> list = new ArrayList<>();
	// parse and construct User datamodel list by looping your ResultSet rs
	// and return the list   
	for (Device device : globalAccount.getInstalledDevices()) {
	    // initialize devices if not already initialized
	    if(pollDeviceState){
		if(!hardwareManager.isDeviceManaged(device)){
		    hardwareManager.addManagedDevice(device);
		}
		hardwareManager.updateConnectionState(device);
		// check if this device needs to be enabled
		if(device.isEnabled()){
		    device.setIsEnabled(true);
		    hardwareManager.startPollingDevice(device, device.getProfile());
		}
	    }
	    list.add(new DeviceEntry(device));
	}
	return list;
    }
    /**
     * Shutsdown all engines and exits the program.
     */
    private void exitApplication(){
	cleanUp();
	System.exit(1);
    }
    /**
     * Logs out of this device menu.
     * Disables all devices.
     */
    private void logout(){
	cleanUp();
	loginController.hideDeviceMenu();
    }
    /**
     * Closes all databases and prepares this gui to be closed.
     */
    private void cleanUp(){
	hardwareManager.stopPollingAllDevices();
	globalAccount.close();
    }
// ============= Extended Methods ============== //
    @Override
    public void handle(ActionEvent t) {
	CheckBoxTableCell cell = (CheckBoxTableCell)t.getSource();
	// traverse through scene graph to get device entry.
	TableRowSkin skin = (TableRowSkin)cell.getParent();
	TableRow row = (TableRow)skin.getParent();
	DeviceEntry deviceEntry = (DeviceEntry)row.getItem();
	Device device = deviceEntry.getDevice();
	// traverse through scene graph to get checkbox.
	TableCellSkin skin2 = (TableCellSkin)cell.getChildrenUnmodifiable().get(0);
	CheckBox checkBox = (CheckBox)skin2.getChildren().get(0);
	if(device.getProfile() == null){
	    // pop an error 
	    PopupManager.getPopupManager().showError("No profile selected, not enabled");
	    // not, cannot enable
	    device.setIsEnabled(false);
	    checkBox.selectedProperty().set(false);

	    hardwareManager.stopPollingDevice(device);
	    device.setIsEnabled(false);
	    return;
	}
	System.out.println("[DeviceMenuUIController:handle]");
	if(!device.isEnabled()){
	    device.setIsEnabled(true);
	    hardwareManager.startPollingDevice(device, device.getProfile());
	}else{
	    hardwareManager.stopPollingDevice(device);
	    device.setIsEnabled(false);
	}
    }


// ============= Internal Classes ============== //
    public class CheckboxCallback implements Callback<TableColumn<DeviceEntry,Boolean>, TableCell<DeviceEntry,Boolean>> {
	private EventHandler checkBoxHandler;



	@Override
	public TableCell call(TableColumn<DeviceEntry, Boolean> param) {
	    CheckboxValueCallback callbackProperty = new CheckboxValueCallback();
	    callbackProperty.setTableColumn(param);

	    CheckBoxTableCell cell = new CheckBoxTableCell(callbackProperty){
		public CheckBox checkBox;
	    };
	    //adding style class for the cell
	    cell.getStyleClass().add("table-cell-center");
	    cell.addEventHandler(ActionEvent.ACTION, checkBoxHandler);
	    return cell;
	}
	public void setCheckboxHandler(EventHandler checkBoxHandler){
	    this.checkBoxHandler = checkBoxHandler;
	}
    }
    public class CheckboxValueCallback implements Callback<Integer, ObservableValue<Boolean>> { 
	private TableColumn<DeviceEntry, Boolean> tableColumn;
	public void setTableColumn(TableColumn<DeviceEntry, Boolean> tableColumn){
	    this.tableColumn = tableColumn;
	}
	@Override
	public ObservableValue<Boolean> call(Integer p) {
	    System.out.println("[CheckboxValueCallback] "+p+" "+tableColumn.getCellObservableValue(p));
	    return tableColumn.getCellObservableValue(p);
	}
    }
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
