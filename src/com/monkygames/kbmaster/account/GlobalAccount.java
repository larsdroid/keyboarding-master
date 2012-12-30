/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

// === java imports === //
import java.util.ArrayList;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.DeviceInformation;
import java.util.HashMap;

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
// ============= Constructors ============== //
    public GlobalAccount(){
	supportedDevices = new HashMap<String,DeviceInformation>();
	localDevices = new HashMap<String,DevicePackage>();
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
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
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
