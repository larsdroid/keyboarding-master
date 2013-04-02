/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.LinuxEnvironmentPlugin;

/**
 * Scans the hardware and prints out the information.
 * @version 1.0
 */
public class ScanHardware{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
    public static void main(String[] args){
	Controller[] controllers = LinuxEnvironmentPlugin.getDefaultEnvironment().getControllers();
	for(Controller controller: controllers){
	    System.out.println("Name: "+controller.getName()+" Type: "+controller.getType());
	    Component[] components = controller.getComponents();
	    System.out.println("=== Inputs ===");
	    for(int i = 0; i < components.length; i++){
		System.out.println("Name["+components[i].getName()+"] Identifier["+
				    components[i].getIdentifier()+"] isAnalog["+
				    components[i].isAnalog()+"] isRelative["+components[i].isRelative()+"]");
	    }
	    System.out.println("===============");
	}
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
