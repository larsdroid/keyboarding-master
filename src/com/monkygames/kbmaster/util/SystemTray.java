/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.jappindicator.AppIndicatorEvent;
import com.monkygames.jappindicator.AppIndicatorEventListener;
import com.monkygames.jappindicator.JAppIndicator;
import com.monkygames.kbmaster.controller.DeviceMenuUIController;
import java.io.File;

/**
 * Adds a system tray icon and uses unity's App Indicator.
 * @version 1.0
 */
public class SystemTray implements AppIndicatorEventListener{

// ============= Class variables ============== //
    private JAppIndicator appIndicator;
    //private static final String path = "/com/monkygames/kbmaster/fxml/resources";
    //private static final String icon = "systray";
    private static final String path = "/home/mspeth/Projects/tools/jappindicator/dist";
    private static final String icon = "test_app_icon";
    private DeviceMenuUIController controller;
// ============= Constructors ============== //
    public SystemTray(DeviceMenuUIController controller){
	this.controller = controller;
	appIndicator = new JAppIndicator("libs/native");
	appIndicator.registerAppIndicatorEventListener(this);

	System.out.println("Has App-Indictor library: " + appIndicator.hasAppIndicatorLibrary());

	String[] menuItems = {"show", "|", "quit"};
	File pathF = new File(path);
	System.out.println("pathF.exists "+pathF.exists());
	boolean ret = appIndicator.startAppIndicator(icon, pathF.getAbsolutePath(), menuItems, appIndicator);
	if (ret) {
	    System.out.println("Started App Indicator: Success");
	} else {
	    System.out.println("Started App Indicator: Failure");
	}
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void registerEvent(AppIndicatorEvent event) {
        if(event.getMenuItemSelected().equals("quit")){
            appIndicator.quit();
            //System.exit(0);
        }else if(event.getMenuItemSelected().equals("show")){
        }
        System.out.println("Event registered: "+event.getMenuItemSelected());
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
