/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.kbmaster;

import com.monkygames.kbmaster.controller.LoginUIController;
import com.monkygames.kbmaster.util.WindowUtil;
import insidefx.undecorator.Undecorator;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The main class for loading the Keyboard Master GUI.
 */
public class KeyboardingMaster extends Application {

    private LoginUIController controller;
    
    @Override
    public void start(Stage stage) {
	Parent root;
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/LoginUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    root = (Parent)fxmlLoader.load(location.openStream());
	    //root = fxmlLoader.load(getClass().getResource("/com/monkygames/kbmaster/fxml/LoginUI.fxml"));
	    controller = (LoginUIController) fxmlLoader.getController();
	    controller.setStage(stage);


	    //root = FXMLLoader.load(getClass().getResource("/com/monkygames/kbmaster/fxml/LoginUI.fxml"));
	    //Scene scene = new Scene(root);
	    Undecorator undecorator = new Undecorator(stage, (Region) root, 
						      null, StageStyle.UNDECORATED);
	    // Default theme
	    undecorator.getStylesheets().add("/com/monkygames/kbmaster/skin/undecorator.css");
	    Scene scene = new Scene(undecorator);

	    // Transparent scene and stage
	    scene.setFill(Color.TRANSPARENT);
	    stage.initStyle(StageStyle.TRANSPARENT);
	    // accomodate undecorator style
	    double width = 592+25*2;
	    double height = 307+25*2;	
	    stage.setMinWidth(width);
	    stage.setMinHeight(height);
	    stage.setMaxWidth(width);
	    stage.setMaxHeight(height);
	    stage.setResizable(false);
	    
	    stage.setScene(scene);
	    stage.show();
	    //WindowUtil.centerStage(stage);
	    undecorator.setCenter();
	} catch (IOException ex) {
	    Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }
    @Override
    public void stop(){
	//controller.
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
