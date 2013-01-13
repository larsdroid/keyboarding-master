/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.nostromo;

// === java imports === //
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
// === jinput imports === //
import net.java.games.input.Component.Identifier.Key;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.OutputMouse.MouseType;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class RazerNostromoDevice extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public RazerNostromoDevice(){
	super("Razer","Nostromo","",DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/razer/nostromo/resources/RazerNostromoIcon.png",
		"device information","1.0",
		"com.monkygames.kbmaster.driver.razer.nostromo.RazerNostromoDevice");
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //

    @Override
    public Keymap generateDefaultKeymap(int id){
	Keymap keymap = new Keymap(id);
	// add 01 - 20
	keymap.addButtonMapping(Key.TAB.getName(), new ButtonMapping(new Button(1,Key.TAB.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_TAB),KeyEvent.VK_TAB)));
	keymap.addButtonMapping(Key.Q.getName(), new ButtonMapping(new Button(2,Key.Q.getName()),new OutputKey("Q",KeyEvent.VK_Q)));
	keymap.addButtonMapping(Key.W.getName(), new ButtonMapping(new Button(3,Key.W.getName()),new OutputKey("W",KeyEvent.VK_W)));
	keymap.addButtonMapping(Key.E.getName(), new ButtonMapping(new Button(4,Key.E.getName()),new OutputKey("E",KeyEvent.VK_E)));
	keymap.addButtonMapping(Key.R.getName(), new ButtonMapping(new Button(5,Key.R.getName()),new OutputKey("R",KeyEvent.VK_R)));
	keymap.addButtonMapping(Key.CAPITAL.getName(), new ButtonMapping(new Button(6,Key.CAPITAL.getName()),new OutputKey("CAPS_LOCK",KeyEvent.VK_CAPS_LOCK)));
	keymap.addButtonMapping(Key.A.getName(), new ButtonMapping(new Button(7,Key.A.getName()),new OutputKey("A",KeyEvent.VK_A)));
	keymap.addButtonMapping(Key.S.getName(), new ButtonMapping(new Button(8,Key.S.getName()),new OutputKey("S",KeyEvent.VK_S)));
	keymap.addButtonMapping(Key.D.getName(), new ButtonMapping(new Button(9,Key.D.getName()),new OutputKey("D",KeyEvent.VK_D)));
	keymap.addButtonMapping(Key.F.getName(), new ButtonMapping(new Button(10,Key.F.getName()),new OutputKey("F",KeyEvent.VK_F)));
	keymap.addButtonMapping(Key.LSHIFT.getName(), new ButtonMapping(new Button(11,Key.LSHIFT.getName()),new OutputKey("SHIFT",KeyEvent.VK_SHIFT)));
	keymap.addButtonMapping(Key.Z.getName(), new ButtonMapping(new Button(12,Key.Z.getName()),new OutputKey("Z",KeyEvent.VK_Z)));
	keymap.addButtonMapping(Key.X.getName(), new ButtonMapping(new Button(13,Key.X.getName()),new OutputKey("X",KeyEvent.VK_X)));
	keymap.addButtonMapping(Key.C.getName(), new ButtonMapping(new Button(14,Key.C.getName()),new OutputKey("C",KeyEvent.VK_C)));
	keymap.addButtonMapping(Key.SPACE.getName(), new ButtonMapping(new Button(15,Key.SPACE.getName()),new OutputKey("SPACE",KeyEvent.VK_SPACE)));
	keymap.addButtonMapping(Key.LALT.getName(), new ButtonMapping(new Button(16,Key.LALT.getName()),new OutputKey("ALT",KeyEvent.VK_ALT)));
	// d-pad
	keymap.addButtonMapping(Key.UP.getName(), new ButtonMapping(new Button(17,Key.UP.getName()),new OutputKey("UP",KeyEvent.VK_UP)));
	keymap.addButtonMapping(Key.RIGHT.getName(), new ButtonMapping(new Button(18,Key.RIGHT.getName()),new OutputKey("RIGHT",KeyEvent.VK_RIGHT)));
	keymap.addButtonMapping(Key.DOWN.getName(), new ButtonMapping(new Button(19,Key.DOWN.getName()),new OutputKey("DOWN",KeyEvent.VK_DOWN)));
	keymap.addButtonMapping(Key.LEFT.getName(), new ButtonMapping(new Button(20,Key.LEFT.getName()),new OutputKey("LEFT",KeyEvent.VK_LEFT)));
	// wheel 
	keymap.setzUpWheelMapping(new WheelMapping(new Wheel(1),new OutputMouse("Scroll Up",-1,MouseType.MouseWheel)));
	keymap.setzDownWheelMapping(new WheelMapping(new Wheel(2),new OutputMouse("Scroll Down",1,MouseType.MouseWheel)));
	keymap.setMiddleWheelMapping(new WheelMapping(new Wheel(3),new OutputMouse("Middle-Click",InputEvent.BUTTON3_MASK,MouseType.MouseClick)));
	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public String getId(int index){
	switch(index){
	    case 1:
		return Key.TAB.getName();
	    case 2:
		return Key.Q.getName();
	    case 3:
		return Key.W.getName();
	    case 4:
		return Key.E.getName();
	    case 5:
		return Key.R.getName();
	    case 6:
		return Key.CAPITAL.getName();
	    case 7:
		return Key.A.getName();
	    case 8:
		return Key.S.getName();
	    case 9:
		return Key.D.getName();
	    case 10:
		return Key.F.getName();
	    case 11:
		return Key.LSHIFT.getName();
	    case 12:
		return Key.Z.getName();
	    case 13:
		return Key.X.getName();
	    case 14:
		return Key.C.getName();
	    case 15:
		return Key.SPACE.getName();
	    case 16:
		return Key.LALT.getName();
	    case 17:
		return Key.UP.getName();
	    case 18:
		return Key.RIGHT.getName();
	    case 19:
		return Key.DOWN.getName();
	    case 20:
		return Key.LEFT.getName();
	}
	return Key.TAB.getName();
    }
    @Override
    public int getIndex(String id){
	if(id.equals(Key.TAB.getName())){
	    return 1;
	}else if(id.equals(Key.Q.getName())){
	    return 2;
	}else if(id.equals(Key.W.getName())){
	    return 3;
	}else if(id.equals(Key.E.getName())){
	    return 4;
	}else if(id.equals(Key.R.getName())){
	    return 5;
	}else if(id.equals(Key.CAPITAL.getName())){
	    return 6;
	}else if(id.equals(Key.A.getName())){
	    return 7;
	}else if(id.equals(Key.S.getName())){
	    return 8;
	}else if(id.equals(Key.D.getName())){
	    return 9;
	}else if(id.equals(Key.F.getName())){
	    return 10;
	}else if(id.equals(Key.LSHIFT.getName())){
	    return 11;
	}else if(id.equals(Key.Z.getName())){
	    return 12;
	}else if(id.equals(Key.X.getName())){
	    return 13;
	}else if(id.equals(Key.C.getName())){
	    return 14;
	}else if(id.equals(Key.SPACE.getName())){
	    return 15;
	}else if(id.equals(Key.LALT.getName())){
	    return 16;
	}else if(id.equals(Key.UP.getName())){
	    return 17;
	}else if(id.equals(Key.RIGHT.getName())){
	    return 18;
	}else if(id.equals(Key.DOWN.getName())){
	    return 19;
	}else if(id.equals(Key.LEFT.getName())){
	    return 20;
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
