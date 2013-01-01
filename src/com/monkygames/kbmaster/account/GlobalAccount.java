/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

// === java imports === //
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import java.util.ArrayList;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.DeviceInformation;
import java.util.HashMap;
import java.util.List;

/**
 * Contains a list of device drivers available for download and whats already
 * available locally.
 * @version 1.0
 */
public class GlobalAccount{

// ============= Class variables ============== //
    /**
     * The devices that are supported.
     */
    private HashMap<String,DeviceInformation> supportedDevices;
    /**
     * Contains the local list of devices.
     */
    private HashMap<String,DevicePackage> localDevices;
    /**
     * Contains the database for the device information.
     */
    private ObjectContainer db;
    private static final String dbFileName = "global_account.db4o";
// ============= Constructors ============== //
    public GlobalAccount(){
	this.loadLists();
    }
// ============= Public Methods ============== //
    /**
     * Updates the local list of supported devices with the passed in list.
     * @param newList the new list that should contain the equal number of devices
     * or more.
     * @return true if the list was updated (ie, there are new devices) and false
     * if there was no change in the list.
     */
    public boolean updateSupportedDevices(ArrayList<DeviceInformation> newList){
	boolean isChanged = false;

	for(DeviceInformation deviceInformation: newList){
	    DeviceInformation tmpInfo = supportedDevices.get(deviceInformation.getName());
	    if(tmpInfo != null){
		if(!tmpInfo.equals(deviceInformation)){
		    supportedDevices.remove(tmpInfo);
		    supportedDevices.put(deviceInformation.getName(), deviceInformation);
		    isChanged = true;
		}
	    }else{
		supportedDevices.put(deviceInformation.getName(), deviceInformation);
		isChanged = true;
	    }
	}
	return isChanged;
    }
    /**
     * Downloads the device specified by the name which is the concat
     * of make:model.
     * @param deviceName the make:model
     * @return true on success and false otherwise (if the device is already 
     * downloaded than returns false).
     */
    public boolean downloadDevice(String deviceName){
	DeviceInformation deviceInformation = supportedDevices.get(deviceName);
	if(deviceInformation == null) return false;
	DevicePackage devicePackage = localDevices.get(deviceName);
	if(devicePackage == null){
	    devicePackage = new DevicePackage(deviceInformation);
	    devicePackage.setIsDownloaded(true);
	}else if(devicePackage.isIsDownloaded()){
	    return false;
	}else{
	    devicePackage.setDeviceInformation(deviceInformation);
	    devicePackage.setIsDownloaded(true);
	}
	return true;
    }
    /**
     * Upgrades an existing downloaded device.
     * @param deviceName the device to be upgraded.
     */
    public boolean upgradeDownloadedDevice(String deviceName){
	return false;
    }
    /**
     * Returns a list of locally installed devices.
     * @return a list of locally installed devices.
     */
    public ArrayList<DeviceInformation> getInstalledDevices(){
	ArrayList<DeviceInformation> devices = new ArrayList<>();

	for(DevicePackage devicePackage: localDevices.values()){
	    if(devicePackage.isIsDownloaded()){
		devices.add(devicePackage.getDeviceInformation());
	    }
	}

	return devices;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Sets up the db4o database for holding device information.
     */
    private void initDatabase(){
	EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
	// make sure that when a profile is updated so are all the data members
	config.common().objectClass(DeviceInformation.class).cascadeOnUpdate(true);
	config.common().objectClass(DevicePackage.class).cascadeOnUpdate(true);
	db = Db4oEmbedded.openFile(config, dbFileName);
    }
    /**
     * Loads the lists from the database.
     * If no lists exists, empty lists are created and stored in the database.
     */
    private void loadLists(){
	List<HashMap<String,DeviceInformation>> supportedDevicesFromDB = db.query(new Predicate<HashMap<String,DeviceInformation>>(){
	    @Override
	    public boolean match(HashMap<String, DeviceInformation> testList){
		return true;
	    }
	});
	List<HashMap<String,DevicePackage>> localDevicesFromDB = db.query(new Predicate<HashMap<String,DevicePackage>>(){
	    @Override
	    public boolean match(HashMap<String, DevicePackage> testList){
		return true;
	    }
	});
	// initialize the db if there are no lists
	if(supportedDevicesFromDB == null || supportedDevicesFromDB.size() == 0){
	    supportedDevices = new HashMap<String,DeviceInformation>();
	    db.store(supportedDevices);

	}else{
	    supportedDevices = supportedDevicesFromDB.get(0);
	}
	if(localDevicesFromDB == null || localDevicesFromDB.size() == 0){
	    localDevices = new HashMap<String,DevicePackage>();
	    db.store(localDevices);
	}else{
	    localDevices = localDevicesFromDB.get(0);
	}
    }
// ============= Implemented Methods ============== //
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
