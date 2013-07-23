/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.taipan;

// === java imports === //
import java.awt.event.InputEvent;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.OutputMouse.MouseType;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class Taipan extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Taipan(){
	super("Razer","Taipan","Razer Razer Taipan", DeviceType.MOUSE,
		"/com/monkygames/kbmaster/driver/razer/taipan/resources/TaipanIcon.png",
		"device information","1.0",
		"com.monkygames.kbmaster.driver.razer.taipan.Taipan",
		"/com/monkygames/kbmaster/driver/razer/taipan/Taipan.fxml",
		"/com/monkygames/kbmaster/driver/razer/nostromo/resources/nostromo_bindings_template_printable.png");
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //

    @Override
    public Keymap generateDefaultKeymap(int id){
	Keymap keymap = new Keymap(id+1);
	net.java.games.input.Component.Identifier.Button jinputB;
	jinputB = net.java.games.input.Component.Identifier.Button.LEFT;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(1,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON1_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.RIGHT;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(2,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON3_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.EXTRA;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(3,jinputB.getName()),new OutputDisabled()));
	jinputB = net.java.games.input.Component.Identifier.Button.SIDE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(4,jinputB.getName()),new OutputDisabled()));
	jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(7,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_MASK,MouseType.MouseClick)));

	// wheel 
	keymap.setzUpWheelMapping(new WheelMapping(new Wheel(5),new OutputMouse("Scroll Up",-1,MouseType.MouseWheel)));
	keymap.setzDownWheelMapping(new WheelMapping(new Wheel(6),new OutputMouse("Scroll Down",1,MouseType.MouseWheel)));
	//keymap.setMiddleWheelMapping(new WheelMapping(new Wheel(7),new OutputMouse("Middle-Click",InputEvent.BUTTON2_MASK,MouseType.MouseClick)));
	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public Mapping getMapping(int index, Keymap keymap){
	switch(index){
	    case 5:
		return keymap.getzUpWheelMapping();
	    case 6:
		return keymap.getzDownWheelMapping();
	    default:
		return keymap.getButtonMapping(getId(index));
	}

    }
    @Override
    public String getId(int index){
	switch(index){
	    case 1:
		return net.java.games.input.Component.Identifier.Button.LEFT.getName();
	    case 2:
		return net.java.games.input.Component.Identifier.Button.RIGHT.getName();
	    case 7:
		return net.java.games.input.Component.Identifier.Button.MIDDLE.getName();
	    case 3:
		return net.java.games.input.Component.Identifier.Button.EXTRA.getName();
	    case 4:
		return net.java.games.input.Component.Identifier.Button.SIDE.getName();
	}
	return net.java.games.input.Component.Identifier.Button.LEFT.getName();
    }
    @Override
    public int getIndex(String id){
	if(id.equals(net.java.games.input.Component.Identifier.Button.LEFT.getName())){
	    return 1;
	}else if(id.equals(net.java.games.input.Component.Identifier.Button.RIGHT.getName())){
	    return 2;
	}else if(id.equals(net.java.games.input.Component.Identifier.Button.MIDDLE.getName())){
	    return 7;
	}else if(id.equals(net.java.games.input.Component.Identifier.Button.EXTRA.getName())){
	    return 3;
	}else if(id.equals(net.java.games.input.Component.Identifier.Button.SIDE.getName())){
	    return 4;
	}
	return 1;
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
