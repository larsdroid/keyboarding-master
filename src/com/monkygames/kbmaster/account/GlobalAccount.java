/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

// === java imports === //
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.monkygames.kbmaster.account.dropbox.Revision;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceInformation;
import com.monkygames.kbmaster.driver.DriverManager;
import java.util.ArrayList;
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
    private DriverManager driverManager;
    /**
     * The file that manages the devices.
     */
    public static final String dbFileName = "global_account.db4o";
    private Revision dropboxRev;
// ============= Constructors ============== //
    public GlobalAccount(){
	driverManager = new DriverManager();
	this.initDatabase();
	this.loadLists();
    }

    // ============= Public Methods ============== //
    public DriverManager getDriverManager() {
	return driverManager;
    }
    
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
     * Note, right now this method expects the driver to be already
     * packaged in the jar; however, the future development of this
     * method should get the driver from outside the jar.
     * @param deviceName the make:model
     * @return true on success and false otherwise (if the device is already 
     * downloaded than returns false).
     */
    public boolean downloadDevice(String deviceName){
	DeviceInformation deviceInformation = supportedDevices.get(deviceName);
	if(deviceInformation == null) return false;
	DevicePackage devicePackage = localDevices.get(deviceName);
	if(devicePackage == null){
	    Device device = instantiate(deviceInformation.getPackageName(),Device.class);
	    if(device == null){
		return false;
	    }
	    devicePackage = new DevicePackage(device);
	    devicePackage.setIsDownloaded(true);
	    localDevices.put(deviceName, devicePackage);
	    db.store(localDevices);

	List<HashMap<String,DevicePackage>> localDevicesFromDB = db.query(new Predicate<HashMap<String,DevicePackage>>(){
	    @Override
	    public boolean match(HashMap<String, DevicePackage> testList){
		return true;
	    }
	});


	}else if(devicePackage.isIsDownloaded()){
	    return false;
	}else{
	    //TODO think about what to do here - should I set the device information
	    // or does the entire driver just need to be replaced????
	    //devicePackage.getDevice().setDeviceInformation(deviceInformation);
	    ///devicePackage.setIsDownloaded(true);
	    // for now just return false
	    return false;
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
     * Removes the device from the local device list and stores to the database.
     * @device the device to remove.
     * @return true if the device was successfully removed and false otherwise.
     */
    public boolean removeDownloadedDevice(Device device){
	if(localDevices.remove(device.getDeviceInformation().getName()) == null){
	    return false;
	}
	db.store(localDevices);
	return true;
    }
    /**
     * Returns a list of locally installed devices.
     * @return a list of locally installed devices.
     */
    public ArrayList<Device> getInstalledDevices(){
	ArrayList<Device> devices = new ArrayList<>();

	for(DevicePackage devicePackage: localDevices.values()){
	    if(devicePackage.isIsDownloaded()){
		devices.add(devicePackage.getDevice());
	    }
	}
	return devices;
    }
    /**
     * Closes the database.
     */
    public void close(){
	db.store(localDevices);
	db.commit();
	db.close();
    }
    /**
     * Returns the revision and null if it doesn't exist.
     * @return the revision for the file.
     */
    public Revision getDropboxRevision(){
	return dropboxRev;
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
	config.common().objectClass(Device.class).cascadeOnUpdate(true);
	db = Db4oEmbedded.openFile(config, dbFileName);
    }
    /**
     * Loads the lists from the database.
     * If no lists exists, empty lists are created and stored in the database.
     */
    private void loadLists(){

	// get the dropbox revision if it exists
	List<Revision> revisionList = db.query(Revision.class);
	if(!revisionList.isEmpty()){
	    dropboxRev = revisionList.get(0);
	}

	List<HashMap<String,DeviceInformation>> supportedDevicesFromDB = db.query(new Predicate<HashMap<String,DeviceInformation>>(){
	    @Override
	    public boolean match(HashMap<String, DeviceInformation> testList){
		if(!testList.values().isEmpty() && testList.values().toArray()[0] instanceof DeviceInformation){
		    return true;
		};
		return false;
	    }
	});
	List<HashMap<String,DevicePackage>> localDevicesFromDB = db.query(new Predicate<HashMap<String,DevicePackage>>(){
	    @Override
	    public boolean match(HashMap<String, DevicePackage> testList){
		if(!testList.values().isEmpty() && testList.values().toArray()[0] instanceof DevicePackage){
		    return true;
		}
		return false;
	    }
	});
	// initialize the db if there are no lists
	if(supportedDevicesFromDB == null || supportedDevicesFromDB.size() == 0){
	    supportedDevices = new HashMap<String,DeviceInformation>();
	    // get all devices 
	    ArrayList<Device> list = driverManager.getDevices();
	    for(Device device: list){
		supportedDevices.put(device.getDeviceInformation().getName(), device.getDeviceInformation());
	    }
	    db.store(supportedDevices);

	}else{
	    supportedDevices = supportedDevicesFromDB.get(0);
	}
	if(localDevicesFromDB == null || localDevicesFromDB.size() == 0){
	    localDevices = new HashMap<String,DevicePackage>();
	    // don't store an empty list!
	    //db.store(localDevices);
	}else{
	    localDevices = localDevicesFromDB.get(0);
	}
    }
    private <T> T instantiate(final String className, final Class<T> type){
	try{
	    return type.cast(Class.forName(className).newInstance());
	} catch(final InstantiationException | IllegalAccessException | ClassNotFoundException e){
	    throw new IllegalStateException(e);
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
