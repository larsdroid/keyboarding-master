/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.io;

// === jnostromo imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.input.WheelMapping;
// === java imports === //
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

/**
 * Generates an image that shows the keybindings.
 * @version 1.0
 */
public class GenerateBindingsImage{

// ============= Class variables ============== //
    private URL templateURL;
    private URL templatePrintableURL;
    private Color textColor;
    private Color textColor2;
    private BufferedImage image;
    private Device device;
// ============= Constructors ============== //
    public GenerateBindingsImage(Device device){
	this.device = device;
	try{
	    templateURL = getClass().getResource(device.getDeviceInformation().getImageBindingsTemplate());
	    textColor = Color.BLACK;
	    textColor2 = Color.BLACK;

	}catch(Exception e){
	    e.printStackTrace();
	}
	//120, 75
    }
// ============= Public Methods ============== //
    public Image[] generateImages(Profile profile){
	return generateImages(profile,false);
    }
    public Image[] generateImages(Profile profile, boolean isPrintable){
	URL url = templateURL;
	Image[] images = new Image[8];
	try{
	    for(int j = 0; j < 8; j++){
		image = ImageIO.read(url);
		Keymap keymap = profile.getKeymap(j);
		this.writeTitle(keymap);
		for(int i = 1; i <= 20; i++){
		    writeKeyBinding(keymap,i);
		}
		writeWheelBinding(keymap.getzUpWheelMapping(),1);
		writeWheelBinding(keymap.getMiddleWheelMapping(),2);
		writeWheelBinding(keymap.getzDownWheelMapping(),3);
		images[j] = Toolkit.getDefaultToolkit().createImage(image.getSource());
	    }
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
	return images;
    }
    /**
     * Generates a single image from the specified keymap.
     * @param keymap the image is based on this keymap.
     * @param isPrintable true if printable and false otherwise.
     * @return the image of the descriptions and null on failure.
     */
    public Image generateImage(Keymap keymap, boolean isPrintable){
	try{
	    URL url = templateURL;
	    image = ImageIO.read(url);
	    this.writeTitle(keymap);
	    for(int i = 1; i <= 20; i++){
		writeKeyBinding(keymap,i);
	    }
	    writeWheelBinding(keymap.getzUpWheelMapping(),1);
	    writeWheelBinding(keymap.getMiddleWheelMapping(),2);
	    writeWheelBinding(keymap.getzDownWheelMapping(),3);
	    return Toolkit.getDefaultToolkit().createImage(image.getSource());
	}catch(Exception e){
	    e.printStackTrace();
	    return null;
	}
    }
    public boolean generateAndSaveImage(Profile profile, String imagePath){
	try{
	    image = ImageIO.read(templateURL);
	    Keymap keymap = profile.getKeymap(0);
	    for(int i = 1; i <= 20; i++){
		writeKeyBinding(keymap,i);
	    }
	    writeWheelBinding(keymap.getzUpWheelMapping(),1);
	    writeWheelBinding(keymap.getMiddleWheelMapping(),2);
	    writeWheelBinding(keymap.getzDownWheelMapping(),3);
	    saveImage("test_image.png");
	}catch(Exception e){
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void writeTitle(Keymap keymap){
	Graphics2D g2 = image.createGraphics();
	Font font = new Font("Dialog",Font.BOLD,18);
	g2.setFont(font);
	g2.setColor(textColor);
	g2.drawString("keymap "+keymap.getID(), 15,15);
	if(!keymap.getDescription().equals("keymap description")){
	    font = new Font("Dialog",Font.PLAIN,15);
	    g2.setFont(font);
	    g2.setColor(textColor2);
	    g2.drawString(keymap.getDescription(), 15,30);

	}
    }
    private void writeKeyBinding(Keymap keymap,int buttonID){
	Output output = device.getButtonMapping(buttonID, keymap).getOutput();
	Graphics2D g2 = image.createGraphics();
	g2.setColor(textColor);
	int[] pos = getBindingPosition(buttonID);
	g2.drawString(output.getName(), pos[0],pos[1]);
	writeDescription(g2, output, pos[0] - 8, pos[1] + 15);
    }
    private void writeWheelBinding(WheelMapping mapping,int index){
	Output output = mapping.getOutput();
	Graphics2D g2 = image.createGraphics();
	g2.setColor(textColor);
	//g2.setFont(font);
	int[] pos = getBindingPosition(index + 20);
	g2.drawString(output.getName(), pos[0],pos[1]);
	writeDescription(g2, output, pos[0] - 8, pos[1] + 15);
    }
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
		pos[1] = 340;
		break;
	    case 23:
		pos[0] = 530;
		pos[1] = 390;
		break;
	}
	return pos;
    }
    /**
     * Writes the description of the output.
     */
    private void writeDescription(Graphics2D g2, Output output, int posx, int posy){
	if(!output.getName().equals(output.getDescription())){
	    // write description
	    Font font = new Font("Dialog",Font.ITALIC,10);
	    g2.setFont(font);
	    g2.setColor(textColor2);
	    g2.drawString(output.getDescription(), posx,posy);
	}
    }
    private boolean saveImage(String path){
	File outputfile = new File(path);
	try {
	    ImageIO.write(image, "png", outputfile);
	} catch (IOException ex) {
	    return false;
	}
	return true;
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
