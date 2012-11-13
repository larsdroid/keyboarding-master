/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.kbmaster;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class for loading the Keyboard Master GUI.
 */
public class KeyboardingMaster extends Application {
    
    @Override
    public void start(Stage stage) {
	Parent root;
	try {
	    root = FXMLLoader.load(getClass().getResource("/com/monkygames/kbmaster/fxml/LoginUI.fxml"));
	    Scene scene = new Scene(root);
	    
	    stage.setScene(scene);
	    stage.show();
	} catch (IOException ex) {
	    Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }
}
