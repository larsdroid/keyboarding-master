/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public abstract class Device implements Mapper{

// ============= Class variables ============== //
    /**
     * The make of the device, ie the company name.
     */
    public String make;
    /**
     * The name of the device.
     */
    public String model;
    /**
     * The device name as seen by jinput.
     */
    public String jinputName;
    /**
     * The type of device, ie keyboard,mouse,etc.
     */
    public DeviceType deviceType;
    /**
     * The path to the device icon.
     */
    public String deviceIcon;
    /**
     * Contains information about the device.
     */
    public String deviceInformation;
    /**
     * The version of this device.
     */
    public String version;

// ============= Constructors ============== //
    public Device(String make, String model, String jinputName, 
		   DeviceType deviceType, String deviceIcon,
		   String deviceInformation, String version){
	this.make = make;
	this.model = model;
	this.jinputName = jinputName;
	this.deviceType = deviceType;
	this.deviceIcon = deviceIcon;
	this.deviceInformation = deviceInformation;
	this.version = version;
    }
// ============= Public Methods ============== //
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
