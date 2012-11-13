/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.kbmaster;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main class for loading the Keyboard Master GUI.
 */
public class KeyboardingMaster extends Application {
    
    @Override
    //public void start(Stage primaryStage) {
    public void start(Stage stage) {
	/*
	Button btn = new Button();
	btn.setText("Say 'Hello World'");
	btn.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		System.out.println("Hello World!");
	    }
	});
	
	StackPane root = new StackPane();
	root.getChildren().add(btn);
	
	Scene scene = new Scene(root, 300, 250);
	
	primaryStage.setTitle("Hello World!");
	primaryStage.setScene(scene);
	primaryStage.show();
	*/
	/*
	System.out.println("Sslslkajsdfs****'asdfasfd");
        Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, "Whiat the 3103");
        try {
	    File file = new File("com"+File.separator+"monkygames"+File.separator+"kbmaster"+File.separator+"fxml"+File.separator+"LoginUI.fxml");
	    System.out.println(file+" exists: "+file.exists());
            AnchorPane page = (AnchorPane) FXMLLoader.load(KeyboardingMaster.class.getResource(file.getAbsolutePath()));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            //primaryStage.setTitle("Issue Tracking Lite Sample");
            primaryStage.show();
        } catch (Exception ex) {
            //Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
	    System.exit(0);
        }
	*/

	Parent root;
	try {
	    //File file = new File("fxml"+File.separator+"LoginUI.fxml");
	    //File file = new File("com/monkygames/kbmaster/fxml/LoginUI.fxml");
	    //System.out.println(file.exists());
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
