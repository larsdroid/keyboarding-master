/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.jappindicator;

import java.io.File;

/**
 * Tests the functionality of JAppIndicator.
 * @version 1.0
 */
public class TestJAppIndicator implements AppIndicatorEventListener{

// ============= Class variables ============== //
    private JAppIndicator appIndicator;

// ============= Constructors ============== //
    public TestJAppIndicator(String icon, String path){
	appIndicator = new JAppIndicator();
	appIndicator.registerAppIndicatorEventListener(this);

	System.out.println("Has App-Indictor library: "+appIndicator.hasAppIndicatorLibrary());

	String[] menuItems = {"test","|","quit"};
	File pathF = new File(path);
	boolean ret = appIndicator.startAppIndicator(icon, pathF.getAbsolutePath(), menuItems, appIndicator);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void registerEvent(AppIndicatorEvent event) {
	if(event.getMenuItemSelected().equals("quit")){
	    appIndicator.quit();
	    System.exit(0);
	}
	System.out.println("Event registered: "+event.getMenuItemSelected());
    }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
    public static void main(String []args){
	new TestJAppIndicator("test_app_icon.png",".");
    }


}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */