/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class Device{

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
