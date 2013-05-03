/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import insidefx.undecorator.Undecorator;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Methods for handling window specific actions.
 * @version 1.0
 */
public class WindowUtil{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Creates a stage with special decorations.
     * Note, uses the preferred width and height of the root.
     * @param root contains the ui to add to the stage.
     * @return the configured stage.
     */
    public static Stage createStage(Parent root){
	AnchorPane pane = (AnchorPane)root;
	double width = pane.prefWidthProperty().doubleValue();
	double height = pane.prefHeightProperty().doubleValue();
	return configureStage(width,height,root,new Stage());
    }
    /**
     * Creates a stage with special decorations.
     * Note, creates a new stage.
     * @param width the width of the stage.
     * @param height the hight of the stage.
     * @param root contains the ui to add to the stage.
     * @return the configured stage.
     */
    public static Stage createStage(double width, double height, Parent root){
	return configureStage(width,height,root,new Stage());
    }
    /**
     * Configures a stage and sets the width and height to the preferred
     * of the parent.
     * @param root to be added to the stage.
     * @param stage receives the root.
     * @return the updated stages.
     */
    public static Stage configureStage(Parent root, Stage stage){
	AnchorPane pane = (AnchorPane)root;
	double width = pane.prefWidthProperty().doubleValue();
	double height = pane.prefHeightProperty().doubleValue();
	return configureStage(width,height,root,stage);
    }
    /**
     * Configures a stage with special decorations.
     * @param width the width of the stage.
     * @param height the hight of the stage.
     * @param root contains the ui to add to the stage.
     * @return the configured stage.
     */
    public static Stage configureStage(double width, double height, Parent root, Stage stage){
	Undecorator undecorator = new Undecorator(stage, (Region) root, 
						  null, StageStyle.UNDECORATED);
	Scene scene = new Scene(undecorator);
	// Transparent scene and configureDeviceStage
	scene.setFill(Color.TRANSPARENT);
	stage.initStyle(StageStyle.TRANSPARENT);
	// accomodate undecorator style
	width += 25*2;
	height += 25*2;	
	stage.setMinWidth(width);
	stage.setMinHeight(height);
	stage.setMaxWidth(width);
	stage.setMaxHeight(height);
	stage.setResizable(false);
	undecorator.setCenter();
	stage.setScene(scene);
	return stage;
    }
    /**
     * Centers the stage to the center of the screen.
     * @param stage the stage to center.
     */
    public static void centerStage(Stage stage){
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	double width = stage.getMinWidth();
	double height = stage.getMinHeight();

	// calculate x
	double x = bounds.getWidth()/2 - width/2;
	double y = bounds.getHeight()/2 - height/2;
	System.out.println("bounds = "+bounds);
	System.out.println("x = "+x);
	System.out.println("y = "+y);
	System.out.println("stage.width = "+width);
	System.out.println("stage.height = "+height);
	stage.setX(x);
	stage.setY(y);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
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