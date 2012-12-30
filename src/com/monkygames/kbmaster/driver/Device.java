/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

/**
 * Contains device information and device driver implementation.
 * @version 1.0
 */
public abstract class Device implements Mapper{

// ============= Class variables ============== //
    /**
     * The device information.
     */
    private DeviceInformation deviceInformation;

// ============= Constructors ============== //
    public Device(DeviceInformation deviceInformation){
	this.deviceInformation = deviceInformation;
    }
    public Device(String make, String model, String jinputName, 
		   DeviceType deviceType, String deviceIcon,
		   String deviceDescription, String version){
	deviceInformation = new DeviceInformation(make,model,jinputName,
						  deviceType, deviceIcon,
						  deviceDescription, version);
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
