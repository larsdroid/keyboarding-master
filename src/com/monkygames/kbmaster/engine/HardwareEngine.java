/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.engine;

// === jinput imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputKeymapSwitch;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.OutputMouse.MouseType;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.input.WheelMapping;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.Keyboard;
import net.java.games.input.LinuxEnvironmentPlugin;
import net.java.games.input.Mouse;

/**
 * Handles initializing and managing hardware.
 * Also is responsible for polling the devices (as a thread).
 * @version 1.0
 */
public class HardwareEngine implements Runnable{

// ============= Class variables ============== //
    private Device device;
    private Keyboard keyboard;
    private Mouse mouse;
    private PollEventQueue keyboardEventQueue;
    private PollEventQueue mouseEventQueue;
    /**
     * True if polling and false otherwise.
     */
    private boolean isPolling = false;
    /**
     * Controls the thread loop for polling.
     */
    private boolean poll = false;
    /**
     * Used for polling the devices.
     */
    private Thread thread;
    /**
     * Used for getting events from the controllers.
     */
    private Event event;
    /**
     * True if the device hardware exists and false otherwise.
     */
    private boolean doesHardwareExist;
    /**
     * The profile used for polling.
     */
    private Profile profile;
    /**
     * The currently used keymap.
     */ 
    private Keymap keymap;
    /**
     * Used for when a keymap is being temporary used and switched back 
     * when a key is released.
     */
    private Keymap previousKeymap;
    /**
     * The previous name of the component for a temporary keymap switch.
     */
    private String previousComponentName = "";
    /**
     * True if should switch back on release and false otherwise.
     */
    private boolean isKeymapOnRelease = false;
    /**
     * Controls the forwarding of key presses and scroll wheel.
     */
    private Robot robot;
    /**
     * A list of listeners for hardware status change.
     */
    private final ArrayList<HardwareListener> hardwareListeners;
    /**
     * The amount of time to sleep between polling.
     */
    private static final long sleepActive = 20;
    /**
     * The amount of time to check for new devices.
     */
    private static final long sleepPassive = 1000;
    private long sleepTime = sleepActive;
    /**
     * True if in edit mode and false otherwise.
     */
    private boolean isEditMode = false;
    private Profile defaultProfile = new Profile();
    private Profile profileSwitch = null;
    private boolean isMouseEvent = false;
    /**
     * True if the hardware should be "grabbed" or false otherwise.
     */
    private boolean isEnabled = false;
    /**
     * Mice are normally relative.
     */
    private boolean isMouseRelative = true;
    private float unit_width, unit_height;
// ============= Constructors ============== //
    public HardwareEngine(Device device){
	this.device = device;
	doesHardwareExist = scanHardware();
	event = new Event();
	try {
	    robot = new Robot();
	} catch (AWTException ex) {
	    Logger.getLogger(HardwareEngine.class.getName()).log(Level.SEVERE, null, ex);
	}
	hardwareListeners = new ArrayList<>();
    }
// ============= Public Methods ============== //
    /**
     * Sets that this hardware should be grabbed if already detected.
     */
    public void setEnabled(boolean isEnabled){
	if(isEnabled){
	    if(keyboard != null){
		keyboard.grab();
	    }
	    if(mouse != null){
		mouse.grab();
	    }
	}else{
	    if(keyboard != null){
		keyboard.ungrab();
	    }
	    if(mouse != null){
		mouse.ungrab();
	    }

	}
	this.isEnabled = isEnabled;
    }
    public void setEditMode(boolean editMode){
	this.isEditMode = editMode;
	if(isEditMode){
	    startPolling(defaultProfile);
	}else{
	    startPolling(profileSwitch);
	}
    }
    public boolean isEditMode(){
	return isEditMode;
    }
    /**
     * Returns true if the hardware is found.
     * @return true if the hardware exists on the system and false otherwise.
     */
    public boolean hardwareExist(){
	return doesHardwareExist;
    }
    /**
     * Starts polling the devices.
     * Must check that hardware exists before starting.
     * Note, null can be passed in as long as its not enabled!
     */
    public void startPolling(Profile profile){
	//System.out.println("Profiling with "+profile);
	this.profile = profile;
	this.profileSwitch = profile;
	if(profile != null){
	    this.keymap = profile.getKeymap(0);
	}
	this.isKeymapOnRelease = false;
	if(this.doesHardwareExist){
	    sleepTime = sleepActive;
	}else{
	    sleepTime = sleepPassive;
	}
	if(isPolling){
	    // kill current thread
	    poll = false;
	    // wait for thread to die
	    while(isPolling){
		try{
		    Thread.sleep(sleepPassive);
		}catch(Exception e){}
	    }
	}
	poll = true;
	thread = new Thread(this);
	thread.start();
    }
    public void stopPolling(){
	poll = false;
    }
    public Controller getKeyboard(){
	return keyboard;
    }
    public Controller getMouse(){
	return mouse;
    }
    public void addHardwareListener(HardwareListener listener){
	hardwareListeners.add(listener);
    }
    public Device getDevice(){
	return device;
    }

// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Attempt to find and initialize the device hardware.
     * @return true if the hardware has been found and false otherwise.
     */
    private boolean rescanHardware(){
	return scanHardware(LinuxEnvironmentPlugin.getDefaultEnvironment().rescanControllers());
    }
    private boolean scanHardware(){
	return scanHardware(LinuxEnvironmentPlugin.getDefaultEnvironment().getControllers());
    }
    private boolean scanHardware(Controller[] controllers){
	keyboard = null;
	mouse = null;
	for(Controller controller: controllers){
	    if(controller.getType() == Controller.Type.KEYBOARD && 
	       controller.getName().equals(device.getDeviceInformation().getJinputName())){
		keyboard = (Keyboard)controller;
		if(isEnabled){
		    keyboard.grab();
		}
		// create a new event queue
		keyboardEventQueue = new PollEventQueue(keyboard.getComponents());
		    
	    }else if(controller.getType() == Controller.Type.MOUSE && 
	       controller.getName().equals(device.getDeviceInformation().getJinputName())){
		mouse = (Mouse)controller;
		if(mouse.getX() != null && !mouse.getX().isRelative()){
		    isMouseRelative = false;
		}else{
		    isMouseRelative = true;
		}
		float width = MouseInfo.getPointerInfo().getDevice().getDisplayMode().getWidth();
		float height = MouseInfo.getPointerInfo().getDevice().getDisplayMode().getHeight();
		unit_width = 1f/width;
		unit_height = 1f/height;
		if(isEnabled){
		    mouse.grab();
		}
		mouseEventQueue = new PollEventQueue(mouse.getComponents());
	    }
	}	

	if(keyboard == null && mouse == null){
	    return false;
	}
	return true;
    }
    /**
     * Set if hardware has been disconnected.
     */
    private void hardwareDisconnected(Controller controller){
	boolean isHardware = false;
	if(controller == keyboard){
	    keyboard = null;
	    isHardware = true;
	}else if(controller == mouse){
	    mouse = null;
	    isHardware = true;
	}
	if(isHardware){
	    doesHardwareExist = false;
	    for(HardwareListener listener:hardwareListeners){
		listener.hardwareStatusChange(false,device.getDeviceInformation().getJinputName());
	    }
	    sleepTime = sleepPassive;
	}
    }
    private void hardwareConnected(){
	if(keyboard != null && mouse != null){
	    doesHardwareExist = true;
	    for(HardwareListener listener:hardwareListeners){
		listener.hardwareStatusChange(true,device.getDeviceInformation().getJinputName());
	    }
	    sleepTime = sleepActive;
	}
    }
    /**
     * Poll the hardware and generate system key calls.
     */
    private void pollNormalMode(){
	while(poll){
	    try{
		Thread.sleep(sleepTime);
	    }catch(Exception e){}
	    if(!doesHardwareExist){
		doesHardwareExist = rescanHardware();
		if(doesHardwareExist){
		    hardwareConnected();
		}
		continue;
	    }
	    // poll keyboard
	    if(keyboard != null && !keyboard.poll()){
		hardwareDisconnected(keyboard);
		continue;
	    }
	    // poll mouse
	    if(mouse != null && !mouse.poll()){
		hardwareDisconnected(mouse);
		continue;
	    }

	    // handles just detecting if a device is connected or not.
	    if(!isEnabled){
		continue;
	    }

	    // handle keyboard events
	    for(Event event: keyboardEventQueue.getEvents()){
		Component component = event.getComponent();
		//System.out.println("component = "+component);
		String name = component.getIdentifier().getName();
		ButtonMapping mapping = keymap.getButtonMapping(name);
		processOutput(name, mapping.getOutput(), event.getValue());
	    }
	    /*
	    EventQueue queue = keyboard.getEventQueue();
	    while(queue.getNextEvent(event)){
		Component component = event.getComponent();
		System.out.println("component = "+component);
		String name = component.getIdentifier().getName();
		ButtonMapping mapping = keymap.getButtonMapping(name);
		processOutput(name, mapping.getOutput(), event.getValue());
	    }
	    */
	    // handle mouse events
	    for(Event event: mouseEventQueue.getEvents()){
	    //queue = mouse.getEventQueue();
	    //while(queue.getNextEvent(event)){
		//System.out.println("===== New Event Queue =====");
		Component component = event.getComponent();
		String name = component.getIdentifier().getName();

		WheelMapping mapping = null;
		if(component.getIdentifier() == Axis.X){
		    Point point = MouseInfo.getPointerInfo().getLocation();
		    float rel = component.getPollData();
		    float x = point.x + rel;
		    robot.mouseMove((int)x, point.y);
		    //System.out.println("X: rel = "+rel);
		    /*
		    // note, have to modify the relative
		    float pol_dat = component.getPollData();
		    float rel = component.getPollData() * unit_width;
		    // between (0, 1)
		    if(rel > 0 && rel < 1){
			rel = 1;
		    // between (-1, 0)
		    }else if(rel < 0 && rel > -1){
			rel = -1;
		    }
		    float x = point.x + rel;
		    robot.mouseMove((int)x, point.y);
		    */
		    //System.out.println("Mouse Move: [pol,rel,x_o,x_n,x_i] ["+pol_dat+","+rel+","+point.x+","+x+","+(int)x);
		}else if(component.getIdentifier() == Axis.Y){
		    Point point = MouseInfo.getPointerInfo().getLocation();
		    float rel = component.getPollData();
		    float y = point.y + rel;
		    robot.mouseMove(point.x, (int)y);
		    /*
		    float rel = component.getPollData() * unit_height;
		    if(rel > 0 && rel < 1){
			rel = 1;
		    // between (-1, 0)
		    }else if(rel < 0 && rel > -1){
			rel = -1;
		    }
		    float y = point.y + rel;
		    robot.mouseMove(point.x, (int)y);
		    */
		}else if(component.getIdentifier() == Axis.Z && event.getValue() == 1){
		    mapping = keymap.getzUpWheelMapping();
		    if(mapping.getOutput() instanceof OutputKey || mapping.getOutput() instanceof OutputKeymapSwitch){
			processOutput(name, mapping.getOutput(),1);
			robot.delay(10);
			processOutput(name, mapping.getOutput(),0);
		    }else{
			processOutput(name, mapping.getOutput(),event.getValue());
		    }
		}else if(component.getIdentifier() == Axis.Z && event.getValue() == -1){
		    mapping = keymap.getzDownWheelMapping();
		    if(mapping.getOutput() instanceof OutputKey || mapping.getOutput() instanceof OutputKeymapSwitch){
			processOutput(name, mapping.getOutput(),1);
			robot.delay(10);
			processOutput(name, mapping.getOutput(),0);
		    }else{
			processOutput(name, mapping.getOutput(),event.getValue());
		    }
		}else{
		    ButtonMapping bMapping = keymap.getButtonMapping(name);
		    processOutput(name, bMapping.getOutput(),event.getValue());
		}
	    }
	}
    }
    /**
     * Poll the hardware in edit mode.
     */
    private void pollEditMode(){
	while(poll){
	    try{
		Thread.sleep(sleepTime);
	    }catch(Exception e){}
	    if(!doesHardwareExist){
		doesHardwareExist = rescanHardware();
		if(doesHardwareExist){
		    hardwareConnected();
		}
		continue;
	    }
	    // poll keyboard
	    if(!keyboard.poll()){
		hardwareDisconnected(keyboard);
		continue;
	    }
	    EventQueue queue = keyboard.getEventQueue();
	    while(queue.getNextEvent(event)){
		Component component = event.getComponent();
		String name = component.getIdentifier().getName();
		ButtonMapping mapping = keymap.getButtonMapping(name);
		if(event.getValue() == 0){
		    for(HardwareListener listener: this.hardwareListeners){
			listener.eventIndexPerformed(mapping.getInputHardware().getID());
		    }
		}
	    }
	    // poll mouse
	    if(!mouse.poll()){
		hardwareDisconnected(mouse);
		continue;
	    }
	    queue = mouse.getEventQueue();
	    while(queue.getNextEvent(event)){
		Component component = event.getComponent();

		WheelMapping mapping = null;
		isMouseEvent = false;
		if(component.getIdentifier() == Axis.Z && event.getValue() == 1){
		    mapping = keymap.getzUpWheelMapping();
		    isMouseEvent = true;
		}else if(component.getIdentifier() == Axis.Z && event.getValue() == -1){
		    mapping = keymap.getzDownWheelMapping();
		    isMouseEvent = true;
		}
		/*
		}else{
		    // must be middle mouse click
		    mapping = keymap.getMiddleWheelMapping();
		    if(event.getValue() == 0){
			isMouseEvent = true;
		    }
		}
		*/
		if(isMouseEvent){
		    for(HardwareListener listener: this.hardwareListeners){
			listener.eventIndexPerformed(mapping.getInputHardware().getID()+20);
		    }
		}
	    }
	}
    }
    /**
     * Processes the proper response to the output.
     * Checks for mouse, keyboard, or keymap events.
     * @param name the name of the input component.
     * @param output the output to process.
     * @param the event's value.
     */
    private void processOutput(String name, Output output, float eventValue){
	// test for a release on a switch on release keymap event.
	if(isKeymapOnRelease && name.equals(previousComponentName) && eventValue == 0){
	    isKeymapOnRelease = false;
	    keymap = previousKeymap;
	    return;
	}
	if(output instanceof OutputKey){
	    if(eventValue == 1){
		// handle modifiers
		if(output.getModifier() != 0){
		    robot.keyPress(output.getModifier());
		}
		robot.keyPress(output.getKeycode());
	    }else if(eventValue == 0){
		// note, don't do anything if
		// the value is 2 (which means repeat)
		robot.keyRelease(output.getKeycode());
		// release the modifier after the key has been released
		if(output.getModifier() != 0){
		    robot.keyRelease(output.getModifier());
		}
	    }
	}else if(output instanceof OutputMouse){
	    OutputMouse outputM = (OutputMouse)output;
	    if(outputM.getMouseType() == MouseType.MouseClick){
		if(eventValue == 1){
		    robot.mousePress(outputM.getKeycode());
		}else if(eventValue == 0){
		    robot.mouseRelease(outputM.getKeycode());
		}
	    }else if(outputM.getMouseType() == MouseType.MouseDoubleClick){
		if(eventValue == 1){
		    robot.mousePress(outputM.getKeycode());
		    robot.delay(10);
		    robot.mouseRelease(outputM.getKeycode());
		    robot.delay(10);
		    robot.mousePress(outputM.getKeycode());
		    robot.delay(10);
		    robot.mouseRelease(outputM.getKeycode());
		}
	    }else if(outputM.getMouseType() == MouseType.MouseWheel){
		robot.mouseWheel(outputM.getKeycode());
	    }
	}else if(output instanceof OutputKeymapSwitch){
	    if(output.getKeycode() > 0 && output.getKeycode() <= 8){
		OutputKeymapSwitch outputSwitch = (OutputKeymapSwitch)output;
		// don't allow new keymap events if keymap is switch on held
		if(!isKeymapOnRelease){
		    if(eventValue == 1){
			if(outputSwitch.isIsSwitchOnRelease()){
			    isKeymapOnRelease = true;
			    previousKeymap = keymap;
			    previousComponentName = name;
			}
			this.keymap = profile.getKeymap(output.getKeycode()-1);
		    }
		}
	    }
	}
	// don't do anything if output instanceof OutputDisabled
    }
// ============= Implemented Methods ============== //
    @Override
    public void run(){
	isPolling = true;
	if(this.isEditMode()){
	    pollEditMode();
	}else{
	    pollNormalMode();
	}
	isPolling = false;
    }
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
