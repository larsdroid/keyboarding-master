/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.LinuxEnvironmentPlugin;

/**
 * Scans the hardware and prints out the information.
 * @version 1.0
 */
public class ScanHardware implements Runnable{

// ============= Class variables ============== //
    private Controller[] pollControllers;
// ============= Constructors ============== //
    public ScanHardware(String deviceName, boolean doPoll){
	pollControllers = scanHardware(deviceName);
	if(doPoll){
	    if(pollControllers == null){
		System.out.println("Device: "+deviceName+" not found");
		return;
	    }
	    System.out.println("====== Polling ====== ");
	    Thread thread = new Thread(this);
	    thread.start();
	}
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Scans all hardware and writes out the components to standard out.
     * @param deviceName the name of the device.
     * @return the controllers specified by the devicename.
     */
    private Controller[] scanHardware(String deviceName){
	ArrayList<Controller> returnControllers = new ArrayList<>();
	Controller[] controllers = LinuxEnvironmentPlugin.getDefaultEnvironment().getControllers();
	for(Controller controller: controllers){
	    // Check if this controller should be returned for polling.
	    if(deviceName != null && controller.getName().equals(deviceName)){
		returnControllers.add(controller);
	    }
	    System.out.println("Name: "+controller.getName()+" Type: "+controller.getType());
	    Component[] components = controller.getComponents();
	    System.out.println("=== Inputs ===");
	    System.out.println("Number of Inputs: "+components.length);
	    for(Component component: components){
		if(component.getName().equals("Unknown")){
		    continue;
		}
		String data = getComponentDetails(component);
		System.out.println(data);
	    }
	    System.out.println("===============");
	}
	if(deviceName == null || returnControllers.size()== 0){
	    return null;
	}
	Controller[] returnControllersArray = new Controller[returnControllers.size()];
	for(int i = 0; i < returnControllers.size(); i++){
	    returnControllersArray[i] = returnControllers.get(i);
	}
	return returnControllersArray;
    }
    /**
     * Gets the component details as a string.
     * @param component gets the details from this object.
     * @return the details of the component.
     */
    private String getComponentDetails(Component component){
	String data = "Name["+component.getName()+
		      "] Identifier["+component.getIdentifier().getName();
	if(component.isAnalog()){
	    data += "] Analog";
	}else{
	    data += "] Digital";
	}
	if(component.isRelative()){
	    data += " Relative";
	}else{
	    data += " Absolute";
	}
	String className = component.getIdentifier().getClass().getName();
	data += " Class["+className+"]";
	return data;
    }
// ============= Implemented Methods ============== //
    @Override
    public void run(){
	Event event = new Event();
	while(true){
	    for(Controller controller: pollControllers){
		controller.poll();
		String out = "["+controller.getName()+":"+controller.getType()+"] ";
		String data = null;
		EventQueue queue = controller.getEventQueue();
		while(queue.getNextEvent(event)){
		    Component component = event.getComponent();
		    data += "\n"+(out + getComponentDetails(component));
		}
		if(data != null){
		    System.out.print(out+data);
		}
	    }
	    try {
		// print out every 0.1 seconds
		Thread.sleep(100);
	    } catch (InterruptedException ex) {
		Logger.getLogger(ScanHardware.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
    public static void main(String[] args){
	boolean doPoll = true;
	String deviceName = null;
	if(args.length == 0){
	    doPoll = false;
	}else{
	    deviceName = args[0];
	}
	ScanHardware scanHardware = new ScanHardware(deviceName,doPoll);
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