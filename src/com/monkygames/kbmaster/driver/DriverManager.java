/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

import com.monkygames.kbmaster.driver.razer.nostromo.RazerNostromoDevice;
import java.util.ArrayList;

/**
 * Manages drivers by creating the necessary information for all device drivers.
 * For now, the makes of the devices are stored in two lists; however,
 * later on when there becomes thousands of devices hopefully, a tree like
 * data structure will be more efficient.  For now, I went with a simpler option.
 * @version 1.0
 */
public class DriverManager{

// ============= Class variables ============== //
    ArrayList<Device> devices;
    /**
     * The list of keyboard device makes.
     */
    ArrayList<String> keyboardMakes;
    /**
     * The list of mouse device makes.
     */
    ArrayList<String> mouseMakes;
// ============= Constructors ============== //
    public DriverManager(){
	devices = new ArrayList<>();
	keyboardMakes = new ArrayList<>();
	mouseMakes = new ArrayList<>();
	// initialize all drivers by make
	createRazerDrivers();
	createBelkinDrivers();

    }
// ============= Public Methods ============== //
    public ArrayList<Device> getDevices(){
	return devices;
    }
    /**
     * Returns a list of device makes.
     */
    public ArrayList<String> getKeyboardMakes(){
	return keyboardMakes;
    }
    /**
     * Returns a list of device makes.
     */
    public ArrayList<String> getMouseMakes(){
	return mouseMakes;
    }
    /**
     * Returns the list of device model names specified by the make and the type.
     */
    public ArrayList<String> getDevicesByMake(DeviceType type, String make){
	ArrayList<String> list = new ArrayList<>();
	for(Device device: devices){
	    if(device.getDeviceInformation().getDeviceType() == type &&
	       device.getDeviceInformation().getMake().equals(make)){
		list.add(device.getDeviceInformation().getModel());
	    }
	}
	return list;
    }
    /**
     * Returns the device based on the specified information and null if
     * not found.
     * @param type the type of device.
     * @param make the make of the device.
     * @param model the model of the device.
     * @return the device if found and null if not found.
     */
    public Device getDevice(DeviceType type, String make, String model){
	for(Device device: devices){
	    if(device.getDeviceInformation().getModel().equals(model)){
		return device;
	    }
	}
	return null;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Adds a device and sets up the appropriate make information.
     * @param device the device to add.
     */
    private void addDevice(Device device){
	devices.add(device);
	if(device.getDeviceInformation().getDeviceType() == DeviceType.KEYBOARD){
	    if(!keyboardMakes.contains(device.getDeviceInformation().getMake())){
		keyboardMakes.add(device.getDeviceInformation().getMake());
	    }
	}else if(device.getDeviceInformation().getDeviceType() == DeviceType.MOUSE){
	    if(!mouseMakes.contains(device.getDeviceInformation().getMake())){
		mouseMakes.add(device.getDeviceInformation().getMake());
	    }
	}
    }
    /**
     * Handles creating all Razer drivers.
     * Includes mice and keyboards.
     */
    private void createRazerDrivers(){
	addDevice(new RazerNostromoDevice());
	addDevice(new com.monkygames.kbmaster.driver.razer.taipan.Taipan());
    }
    /**
     * Creates all Belkin devices.
     */
    private void createBelkinDrivers(){
	addDevice(new com.monkygames.kbmaster.driver.belkin.n52.N52());
	addDevice(new com.monkygames.kbmaster.driver.belkin.n52te.N52TE());
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
