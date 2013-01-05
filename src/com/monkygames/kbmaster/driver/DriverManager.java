/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

import com.monkygames.kbmaster.driver.razer.nostromo.RazerNostromoDevice;
import java.util.ArrayList;

/**
 * Manages drivers by creating the necessary information for all device drivers.
 * @version 1.0
 */
public class DriverManager{

// ============= Class variables ============== //
    ArrayList<Device> devices;
    /**
     * The list of device makes.
     */
    ArrayList<String> makes;
// ============= Constructors ============== //
    public DriverManager(){
	devices = new ArrayList<>();
	makes = new ArrayList<>();
	// initialize all drivers by make
	createRazerDrivers();

    }
// ============= Public Methods ============== //
    public ArrayList<Device> getDevices(){
	return devices;
    }
    /**
     * Returns a list of device makes.
     */
    public ArrayList<String> getMakes(){
	return makes;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Adds a device and sets up the appropriate make information.
     * @param device the device to add.
     */
    private void addDevice(Device device){
	devices.add(device);
	if(!makes.contains(device.getDeviceInformation().getMake())){
	    makes.add(device.getDeviceInformation().getMake());
	}
    }
    /**
     * Handles creating all Razer drivers.
     */
    private void createRazerDrivers(){
	addDevice(new RazerNostromoDevice());
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
