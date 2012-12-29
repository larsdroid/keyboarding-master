/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.nostromo;

import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class RazerNostromoDevice extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public RazerNostromoDevice(){
	super("Razer","Nostromo","",DeviceType.KEYBOARD,"icon_path",
		"device information","1.0");

    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public Keymap generateDefaultKeymap(int id) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getId(int index) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getIndex(String id) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
