/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.marauder;

// === java imports === //
import com.monkygames.kbmaster.driver.razer.marauder.*;
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
public class Marauder extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Marauder(){
	super("Razer","Maurader","Razer Marauder", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/razer/marauder/resources/icon.png",
		// === Description === //
		"Designed exclusively for StarCraft II: Wings of Liberty, the Razer Marauder StarCraft II gaming keyboard is a full-featured, tournament ready keyboard with an extremely compact design." +
		"* Full keyboard layout with integrated number pad keys\n" +
		"* APM-Lighting System\n" +
		"* Optimized key travel & spacing\n" +
		"* Ultrapolling™ (1000Hz Polling / 1ms Response)\n" +
		"* Up to 200 inches per second and 50g of acceleration\n" +
		"* Seven-foot lightweight, braided fiber cable",
		// === =========== === //
		"1.0",
		"com.monkygames.kbmaster.driver.razer.marauder.Marauder",
		"/com/monkygames/kbmaster/driver/razer/marauder/Marauder.fxml",
		"/com/monkygames/kbmaster/driver/razer/marauder/resources/printable.png");
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
	keymap.addButtonMapping(Key.ESCAPE.getName(), new ButtonMapping(new Button(1,Key.ESCAPE.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_ESCAPE),KeyEvent.VK_ESCAPE,0)));
	keymap.addButtonMapping(Key.F1.getName(), new ButtonMapping(new Button(2,Key.F1.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F1),KeyEvent.VK_F1,0)));
	keymap.addButtonMapping(Key.F2.getName(), new ButtonMapping(new Button(3,Key.F2.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F2),KeyEvent.VK_F2,0)));
	keymap.addButtonMapping(Key.F3.getName(), new ButtonMapping(new Button(4,Key.F3.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F3),KeyEvent.VK_F3,0)));
	keymap.addButtonMapping(Key.F4.getName(), new ButtonMapping(new Button(5,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F5.getName(), new ButtonMapping(new Button(6,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F6.getName(), new ButtonMapping(new Button(7,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F7.getName(), new ButtonMapping(new Button(8,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F8.getName(), new ButtonMapping(new Button(9,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F9.getName(), new ButtonMapping(new Button(10,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F10.getName(), new ButtonMapping(new Button(11,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F11.getName(), new ButtonMapping(new Button(12,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	keymap.addButtonMapping(Key.F12.getName(), new ButtonMapping(new Button(13,Key.F4.getName()),new OutputKey(KeyEvent.getKeyText(KeyEvent.VK_F4),KeyEvent.VK_F4,0)));
	//keymap.addButtonMapping(Key.Q.getName(), new ButtonMapping(new Button(2,Key.Q.getName()),new OutputKey("Q",KeyEvent.VK_Q,0)));
	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public Mapping getMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
	    /*
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
	    */

    }
    @Override
    public String getId(int index){
	switch(index){
	    case 1:
		return Key.ESCAPE.getName();
	    case 2:
		return Key.F1.getName();
	    case 3:
		return Key.F2.getName();
	    case 4:
		return Key.F3.getName();
	    case 5:
		return Key.F4.getName();
	    case 6:
		return Key.F5.getName();
	    case 7:
		return Key.F6.getName();
	    case 8:
		return Key.F7.getName();
	    case 9:
		return Key.F8.getName();
	    case 10:
		return Key.F9.getName();
	    case 11:
		return Key.F10.getName();
	    case 12:
		return Key.F11.getName();
	    case 13:
		return Key.F12.getName();
	}
	return Key.TAB.getName();
    }
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
