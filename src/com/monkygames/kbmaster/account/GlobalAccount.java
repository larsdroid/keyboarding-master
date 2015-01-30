/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

// === java imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DriverManager;
import com.monkygames.kbmaster.io.XStreamManager;
import java.util.ArrayList;

/**
 * Contains a list of device drivers available for download and whats already
 * available locally.
 * @version 1.0
 */
public class GlobalAccount{

// ============= Class variables ============== //

    /**
     * Contains the local list of devices added by user.
     */
    private DeviceList deviceList;

    /**
     * Contains all drivers available.
     */
    private DriverManager driverManager;

// ============= Constructors ============== //
    public GlobalAccount(){
	driverManager = new DriverManager();

	// populate supported devices
	deviceList = XStreamManager.getStreamManager().readGlobalAccount();
    }

    // ============= Public Methods ============== //
    public DriverManager getDriverManager() {
	return driverManager;
    }

    /**
     * Writes the list out to file.
     */
    public void save(){
	XStreamManager.getStreamManager().writeGlobalAccount(deviceList);
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
	// first check if the device is already added 

	for(DevicePackage devicePackage: deviceList.getList()){
	    if(devicePackage.getDevice().getDeviceInformation().getName().equals(deviceName)){
		// already added
		return false;
	    }
	}

	// get all devices 
	for(Device device: driverManager.getDevices()){
	    if(device.getDeviceInformation().getName().equals(deviceName)){
		Device newDevice = instantiate(device.getDeviceInformation().getPackageName(),Device.class);
		if(device == null){
		    return false;
		}
		DevicePackage devicePackage = new DevicePackage(device);
		devicePackage.setIsDownloaded(true);
		deviceList.getList().add(devicePackage);
	    }
	}
	// save 
	return XStreamManager.getStreamManager().writeGlobalAccount(deviceList);
    }

    /**
     * Removes the device from the local device list and stores to the database.
     * @device the device to remove.
     * @return true if the device was successfully removed and false otherwise.
     */
    public boolean removeDownloadedDevice(Device device){
	String deviceName = device.getDeviceInformation().getName();
	for(DevicePackage devicePackage: deviceList.getList()){
	    if(devicePackage.getDevice().getDeviceInformation().getName().equals(deviceName)){
		deviceList.getList().remove(devicePackage);
		// save
		XStreamManager.getStreamManager().writeGlobalAccount(deviceList);
		return true;
	    }
	}
	return false;
    }

    /**
     * Returns a list of locally installed devices.
     * @return a list of locally installed devices.
     */
    public ArrayList<Device> getInstalledDevices(){
	ArrayList<Device> devices = new ArrayList<>();

	for(DevicePackage devicePackage: deviceList.getList()){
	    if(devicePackage.isIsDownloaded()){
		devices.add(devicePackage.getDevice());
	    }
	}
	return devices;
    }

// ============= Protected Methods ============== //
// ============= Private Methods ============== //
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
