/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class DeviceInformation {

// ============= Class variables ============== //
    /**
     * The make of the device, ie the company name.
     */
    private String make;
    /**
     * The name of the device.
     */
    private String model;
    /**
     * The device name as seen by jinput.
     */
    private String jinputName;
    /**
     * The type of device, ie keyboard,mouse,etc.
     */
    private DeviceType deviceType;
    /**
     * The path to the device icon.
     */
    private String deviceIcon;
    /**
     * Contains information about the device.
     */
    private String deviceDescription;
    /**
     * The version of this device.
     */
    private String version;
    /**
     * The full name of the package.
     */
    private String packageName;

// ============= Constructors ============== //
    public DeviceInformation(String make, String model, String jinputName, 
		   DeviceType deviceType, String deviceIcon,
		   String deviceDescription, String version, String packageName){
	this.make = make;
	this.model = model;
	this.jinputName = jinputName;
	this.deviceType = deviceType;
	this.deviceIcon = deviceIcon;
	this.deviceDescription = deviceDescription;
	this.version = version;
	this.packageName = packageName;
    }
    
// ============= Protected Methods ============== //
// ============= Public Methods ============== //
    public String getMake() {
	return make;
    }

    public String getModel() {
	return model;
    }

    public String getJinputName() {
	return jinputName;
    }

    public DeviceType getDeviceType() {
	return deviceType;
    }

    public String getDeviceIcon() {
	return deviceIcon;
    }

    public String getDeviceDescription() {
	return deviceDescription;
    }

    public String getVersion() {
	return version;
    }

    public String getPackageName() {
	return packageName;
    }
    

    /**
     * Returns true if this device information has the same make and model.
     * @param deviceInformation to compare with this device.
     * @return true if this device is the same make and model as the passed in
     * device information and false otherwise.
     */
    public boolean isDevice(DeviceInformation deviceInformation){
	if(deviceInformation.getMake().equals(make) &&
	   deviceInformation.getModel().equals(model)){
	    return true;
	}
	return false;
    }
    @Override
    public boolean equals(Object deviceInformation){
	if(!(deviceInformation instanceof DeviceInformation)){
	    return false;
	}
	DeviceInformation info = (DeviceInformation)deviceInformation;
	if(info.getMake().equals(make) &&
	   info.getModel().equals(model) &&
	   info.getVersion().equals(version) &&
	   info.getDeviceIcon().equals(deviceIcon) &&
	   info.getDeviceDescription().equals(deviceDescription) &&
	   info.getDeviceIcon().equals(deviceIcon)){
	    return true;
	}
	return false;
    }
    /**
     * Returns the name of this device which is the make+:+model.
     * @return the make and model of the device.
     */
    public String getName(){
	return make+":"+model;
    }
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
