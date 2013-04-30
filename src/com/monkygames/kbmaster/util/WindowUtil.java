/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Methods for handling window specific actions.
 * @version 1.0
 */
public class WindowUtil{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
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
