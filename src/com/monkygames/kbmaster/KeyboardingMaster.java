/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster;

import com.monkygames.kbmaster.controller.login.LoginUIController;
import com.monkygames.kbmaster.util.WindowUtil;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main class for loading the Keyboard Master GUI.
 */
public class KeyboardingMaster extends Application {

    private LoginUIController controller;
    public static final String VERSION = "0.1.10";
	private static KeyboardingMaster _instance;

	/**
	 * Opens the default web browser.
	 * @url the url to open.
	 */
	public static void gotoWeb(String url){
		_instance.getHostServices().showDocument(url);
	}
    
    @Override
    public void start(Stage stage) {
	Parent root;
	KeyboardingMaster._instance = this;
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/login/LoginUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    root = (Parent)fxmlLoader.load(location.openStream());
	    //root = fxmlLoader.load(getClass().getResource("/com/monkygames/kbmaster/fxml/LoginUI.fxml"));
	    controller = (LoginUIController) fxmlLoader.getController();
	    controller.setStage(stage);
	    AnchorPane pane = (AnchorPane)root;
	    WindowUtil.configureStage(pane.prefWidthProperty().doubleValue(), 
				      pane.prefHeightProperty().doubleValue(), 
				      root, stage);

	    stage.show();
	    //undecorator.setCenter();
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
