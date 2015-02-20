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
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.OutputMouse.MouseType;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import java.awt.Rectangle;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class RazerNostromoDevice extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public RazerNostromoDevice(){
	super("Razer","Nostromo","Razer Razer Nostromo", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/razer/nostromo/resources/RazerNostromoIcon.png",
		// === Description === //
		"* Ergonomic form factor and tournament-grade layout\n"
		+ "* 16 fully programmable Hyperesponse keys\n"
		+ "* Programmable 8-way directional thumb pad and scroll wheel\n"
		+ "* Instantaneous switching between 8 key maps\n"
		+ "* Adjustable soft-touch wrist pad for exceptional comfort\n"
		+ "* Backlit keypad and scroll wheel for total control even in dark conditions\n"
		+ "* Approximate Size : 183 mm (Width) x 159 mm (Height) x 59 mm (Depth)\n"
		+ "* Approximate Weight: 297 g\n",
		// === =========== === //
		"1.0",
		"com.monkygames.kbmaster.driver.razer.nostromo.RazerNostromoDevice",
		"/com/monkygames/kbmaster/driver/razer/nostromo/RazerNostromo.fxml",
		"/com/monkygames/kbmaster/driver/razer/nostromo/resources/nostromo_bindings_template_printable.png");

	// setup input bindings
	int i = 1;
	inputMaps.put(i,new InputMap(i++,Key.TAB.getName()));
	inputMaps.put(i,new InputMap(i++,Key.Q.getName()));
	inputMaps.put(i,new InputMap(i++,Key.W.getName()));
	inputMaps.put(i,new InputMap(i++,Key.E.getName()));
	inputMaps.put(i,new InputMap(i++,Key.R.getName()));
	inputMaps.put(i,new InputMap(i++,Key.CAPITAL.getName()));
	inputMaps.put(i,new InputMap(i++,Key.A.getName()));
	inputMaps.put(i,new InputMap(i++,Key.S.getName()));
	inputMaps.put(i,new InputMap(i++,Key.D.getName()));
	inputMaps.put(i,new InputMap(i++,Key.F.getName()));
	inputMaps.put(i,new InputMap(i++,Key.LSHIFT.getName()));
	inputMaps.put(i,new InputMap(i++,Key.Z.getName()));
	inputMaps.put(i,new InputMap(i++,Key.X.getName()));
	inputMaps.put(i,new InputMap(i++,Key.C.getName()));
	inputMaps.put(i,new InputMap(i++,Key.SPACE.getName()));
	inputMaps.put(i,new InputMap(i++,Key.LALT.getName()));
	inputMaps.put(i,new InputMap(i++,Key.UP.getName()));
	inputMaps.put(i,new InputMap(i++,Key.RIGHT.getName()));
	inputMaps.put(i,new InputMap(i++,Key.DOWN.getName()));
	inputMaps.put(i,new InputMap(i++,Key.LEFT.getName()));
	inputMaps.put(23,new InputMap(23,net.java.games.input.Component.Identifier.Button.MIDDLE.getName()));
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private int[] getBindingPosition(int buttonID){
	int pos[] = new int[2];
	switch(buttonID){
	    case 1:
		pos[0] = 85;
		pos[1] = 120;
		break;
	    case 2:
		pos[0] = 190;
		pos[1] = 90;
		break;
	    case 3:
		pos[0] = 300;
		pos[1] = 90;
		break;
	    case 4:
		pos[0] = 410;
		pos[1] = 90;
		break;
	    case 5:
		pos[0] = 520;
		pos[1] = 90;
		break;
	    case 6:
		pos[0] = 85;
		pos[1] = 230;
		break;
	    case 7:
		pos[0] = 190;
		pos[1] = 200;
		break;
	    case 8:
		pos[0] = 300;
		pos[1] = 200;
		break;
	    case 9:
		pos[0] = 410;
		pos[1] = 200;
		break;
	    case 10:
		pos[0] = 520;
		pos[1] = 200;
		break;
	    case 11:
		pos[0] = 85;
		pos[1] = 336;
		break;
	    case 12:
		pos[0] = 190;
		pos[1] = 305;
		break;
	    case 13:
		pos[0] = 300;
		pos[1] = 305;
		break;
	    case 14:
		pos[0] = 410;
		pos[1] = 305;
		break;
	    case 15:
		pos[0] = 515;
		pos[1] = 480;
		break;
	    case 16:
		pos[0] = 655;
		pos[1] = 235;
		break;
	    case 17:
		pos[0] = 665;
		pos[1] = 310;
		break;
	    case 18:
		pos[0] = 695;
		pos[1] = 350;
		break;
	    case 19:
		pos[0] = 665;
		pos[1] = 400;
		break;
	    case 20:
		pos[0] = 625;
		pos[1] = 350;
		break;
	    case 21:
		pos[0] = 530;
		pos[1] = 285;
		break;
	    case 22:
		pos[0] = 530;
		pos[1] = 390;
		break;
	    case 23:
		pos[0] = 530;
		pos[1] = 340;
		break;
	}
	return pos;
    }
// ============= Implemented Methods ============== //

    @Override
    public Keymap generateDefaultKeymap(int id){
	Keymap keymap = new Keymap(id+1);
	int i = 1;
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_TAB);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_Q);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_W);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_E);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_R);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_CAPS_LOCK);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_A);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_S);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_D);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_F);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_SHIFT);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_Z);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_X);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_C);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_SPACE);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_ALT);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_UP);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_RIGHT);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_DOWN);
	addButtonMapping(keymap, inputMaps.get(i++), KeyEvent.VK_LEFT);

	// wheel 
	net.java.games.input.Component.Identifier.Button jinputB;
	jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(23,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_MASK,MouseType.MouseClick)));
	keymap.setzUpWheelMapping(new WheelMapping(new Wheel(21),new OutputMouse("Scroll Up",-1,MouseType.MouseWheel)));
	keymap.setzDownWheelMapping(new WheelMapping(new Wheel(22),new OutputMouse("Scroll Down",1,MouseType.MouseWheel)));

	/*
	// add 01 - 20
	keymap.addButtonMapping(Key.TAB.getName(), new ButtonMapping(new Button(1,Key.TAB.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_TAB),KeyEvent.VK_TAB,0)));
	keymap.addButtonMapping(Key.Q.getName(), new ButtonMapping(new Button(2,Key.Q.getName()),new OutputKey("Q",KeyEvent.VK_Q,0)));
	keymap.addButtonMapping(Key.W.getName(), new ButtonMapping(new Button(3,Key.W.getName()),new OutputKey("W",KeyEvent.VK_W,0)));
	keymap.addButtonMapping(Key.E.getName(), new ButtonMapping(new Button(4,Key.E.getName()),new OutputKey("E",KeyEvent.VK_E,0)));
	keymap.addButtonMapping(Key.R.getName(), new ButtonMapping(new Button(5,Key.R.getName()),new OutputKey("R",KeyEvent.VK_R,0)));
	keymap.addButtonMapping(Key.CAPITAL.getName(), new ButtonMapping(new Button(6,Key.CAPITAL.getName()),new OutputKey("CAPS_LOCK",KeyEvent.VK_CAPS_LOCK,0)));
	keymap.addButtonMapping(Key.A.getName(), new ButtonMapping(new Button(7,Key.A.getName()),new OutputKey("A",KeyEvent.VK_A,0)));
	keymap.addButtonMapping(Key.S.getName(), new ButtonMapping(new Button(8,Key.S.getName()),new OutputKey("S",KeyEvent.VK_S,0)));
	keymap.addButtonMapping(Key.D.getName(), new ButtonMapping(new Button(9,Key.D.getName()),new OutputKey("D",KeyEvent.VK_D,0)));
	keymap.addButtonMapping(Key.F.getName(), new ButtonMapping(new Button(10,Key.F.getName()),new OutputKey("F",KeyEvent.VK_F,0)));
	keymap.addButtonMapping(Key.LSHIFT.getName(), new ButtonMapping(new Button(11,Key.LSHIFT.getName()),new OutputKey("SHIFT",KeyEvent.VK_SHIFT,0)));
	keymap.addButtonMapping(Key.Z.getName(), new ButtonMapping(new Button(12,Key.Z.getName()),new OutputKey("Z",KeyEvent.VK_Z,0)));
	keymap.addButtonMapping(Key.X.getName(), new ButtonMapping(new Button(13,Key.X.getName()),new OutputKey("X",KeyEvent.VK_X,0)));
	keymap.addButtonMapping(Key.C.getName(), new ButtonMapping(new Button(14,Key.C.getName()),new OutputKey("C",KeyEvent.VK_C,0)));
	keymap.addButtonMapping(Key.SPACE.getName(), new ButtonMapping(new Button(15,Key.SPACE.getName()),new OutputKey("SPACE",KeyEvent.VK_SPACE,0)));
	keymap.addButtonMapping(Key.LALT.getName(), new ButtonMapping(new Button(16,Key.LALT.getName()),new OutputKey("ALT",KeyEvent.VK_ALT,0)));
	// d-pad
	keymap.addButtonMapping(Key.UP.getName(), new ButtonMapping(new Button(17,Key.UP.getName()),new OutputKey("UP",KeyEvent.VK_UP,0)));
	keymap.addButtonMapping(Key.RIGHT.getName(), new ButtonMapping(new Button(18,Key.RIGHT.getName()),new OutputKey("RIGHT",KeyEvent.VK_RIGHT,0)));
	keymap.addButtonMapping(Key.DOWN.getName(), new ButtonMapping(new Button(19,Key.DOWN.getName()),new OutputKey("DOWN",KeyEvent.VK_DOWN,0)));
	keymap.addButtonMapping(Key.LEFT.getName(), new ButtonMapping(new Button(20,Key.LEFT.getName()),new OutputKey("LEFT",KeyEvent.VK_LEFT,0)));
	// wheel 
	net.java.games.input.Component.Identifier.Button jinputB;
	jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(23,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_MASK,MouseType.MouseClick)));
	keymap.setzUpWheelMapping(new WheelMapping(new Wheel(21),new OutputMouse("Scroll Up",-1,MouseType.MouseWheel)));
	keymap.setzDownWheelMapping(new WheelMapping(new Wheel(22),new OutputMouse("Scroll Down",1,MouseType.MouseWheel)));
	//keymap.setMiddleWheelMapping(new WheelMapping(new Wheel(23),new OutputMouse("Middle-Click",InputEvent.BUTTON2_MASK,MouseType.MouseClick)));
	*/
	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public Mapping getMapping(int index, Keymap keymap){
	switch(index){
	    case 21:
		return keymap.getzUpWheelMapping();
	    case 22:
		return keymap.getzDownWheelMapping();
	    //case 23:
		//return keymap.getMiddleWheelMapping();
	    default:
		return keymap.getButtonMapping(getId(index));
	}

    }
    /*
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
	    case 23:
		return net.java.games.input.Component.Identifier.Button.MIDDLE.getName();
	}
	return Key.TAB.getName();
    }
    */
// ============= Extended Methods ============== //
    @Override
    public Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping) {
	Rectangle rect = new Rectangle();
	int[] pos = getBindingPosition(mapping.getInputHardware().getID());
	rect.x = pos[0];
	rect.y = pos[1];
	rect.width = pos[0] - 8;
	rect.height = pos[1] + 15;

	return rect;
    }
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
