/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

// === kbmaster imports === //
import com.monkygames.kbmaster.driver.DeviceInformation;

/**
 * The package information about a device and its relationship to the 
 * global account.
 * IE, its used to determine if a device has been downloaded.
 * @version 1.0
 */
public class DevicePackage{

// ============= Class variables ============== //
    /**
     * The device information represented by this class.
     */
    private DeviceInformation deviceInformation;
    /**
     * True if this device has been downloaded and available locally or false
     * otherwise.
     */
    private boolean isDownloaded;
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    // ============= Constructors ============== //
    public DevicePackage(DeviceInformation deviceInformation){
	this.deviceInformation = deviceInformation;
	isDownloaded = false;
    }
    // ============= Public Methods ============== //
    public DeviceInformation getDeviceInformation() {
	return deviceInformation;
    }

    public void setDeviceInformation(DeviceInformation deviceInformation) {
	this.deviceInformation = deviceInformation;
    }

    public boolean isIsDownloaded() {
	return isDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded) {
	this.isDownloaded = isDownloaded;
    }

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
