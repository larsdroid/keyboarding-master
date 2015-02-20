/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

// === kbmaster imports === //
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.profiles.Profile;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import net.java.games.input.Component.Identifier.Key;

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
    private DeviceState deviceState;
    /**
     * Stores the inputs used to identify the input.
     */
    protected HashMap<Integer,InputMap> inputMaps;

// ============= Constructors ============== //
    /**
     * Creates a new device with the specified information.
     * @param deviceInformation contains the information of this device.
     */
    public Device(DeviceInformation deviceInformation){
	this.deviceInformation = deviceInformation;
	deviceState = new DeviceState(null,false,false,deviceInformation.getPackageName());
	inputMaps = new HashMap<>();
    }
    /**
     * Creates a device and initializes the DeviceInformation
     * with the specified parameters.
     * @param make the manufacturer of the device.
     * @param model the model of the device.
     * @param jinputName the name of the device recognized by jinput.
     * @param deviceType the type of device (keyboard or mouse).
     * @param deviceIcon the icon for this device.
     * @param deviceDescription the description of the device.
     * @param version the version of this driver.
     * @param packageName the name of the package for loading the driver.
     * @param uiFXMLURL the url to the package in order to load the driver.
     * @param imageBindingsTemplate used for exporting descriptions to an image.
     */
    public Device(String make, String model, String jinputName, 
		   DeviceType deviceType, String deviceIcon,
		   String deviceDescription, String version,
		   String packageName, String uiFXMLURL, 
		   String imageBindingsTemplate){
	this(new DeviceInformation(make,model,jinputName,
						  deviceType, deviceIcon,
						  deviceDescription, version,
						  packageName, uiFXMLURL,
						  imageBindingsTemplate));
    }
// ============= Public Methods ============== //
    public DeviceInformation getDeviceInformation(){
	return deviceInformation;
    }
    /**
     * Sets the default mapping for the passed in profile for this device.
     * @param profile the profile to set the keymaps.
     */
    public void setDefaultKeymaps(Profile profile){
	for(int i = 0; i < 8; i++){
	    profile.setKeymap(i,this.generateDefaultKeymap(i));
	}
    }
    public Profile getProfile() {
	return deviceState.getProfile();
    }

    public void setProfile(Profile profile) {
	deviceState.setProfile(profile);
    }

    public boolean isConnected() {
	return deviceState.isIsConnected();
    }

    public void setIsConnected(boolean isConnected) {
	deviceState.setIsConnected(isConnected);
    }


    public boolean isEnabled() {
	return deviceState.isIsEnabled();
    }

    public void setIsEnabled(boolean isEnabled) {
	deviceState.setIsEnabled(isEnabled);
    }

    public void setDeviceState(DeviceState deviceState){
	this.deviceState = deviceState;
    }

    /**
     * Add a new button mapping to the keymap.
     * @param keymap they keymap to add the button mapping.
     * @param inputMap the input map of the input to add.
     * @param keyEvent the id of the key (java.awt) to add
     */
    protected void addButtonMapping(Keymap keymap, InputMap inputMap, int keyEvent){
	keymap.addButtonMapping(inputMap.getName(), 
	    new ButtonMapping(new Button(inputMap.getId(), inputMap.getName()),
	    new OutputKey(KeyEvent.getKeyText(keyEvent),keyEvent,0)));
    }

    @Override
    public String getId(int index){
	InputMap inputMap = inputMaps.get(index);
	if(inputMap == null){
	    return Key.TAB.getName();
	}
	return inputMap.getName();
    }

    /**
     * Returns the x,y pixel location to write the output and
     * the x,y pixel location to write the description for the 
     * specified binding.
     * @param mapping gets the pixel location for this mapping.
     * @return Rectangle.x and Rectangle.y are the x,y pixel coordinates to
     * write the output and Rectangle.width and Rectangle.height contain
     * the x,y pixel coordinates to write the description.
     */
    public abstract Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping);
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
