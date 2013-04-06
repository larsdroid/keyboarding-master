/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Contains information about an App.
 * App's are used for sorting profiles.
 * All profiles are children of an App.
 * @version 1.0
 */
public class App{

// ============= Class variables ============== //
    /**
     * The information about the app.
     */
    private String info;
    /**
     * The path to this application's logo.
     */
    private String appLogoPath;
    /**
     * The path to the logo for the developer of this app.
     */
    private String devLogoPath;
    /**
     * The name of this app.
     */
    private String name;
    /**
     * The type of App.
     */
    private AppType appType;
	    
    
// ============= Constructors ============== //
    public App(String info, String appLogoPath, String devLogoPath, String name, AppType appType) {
	this.info = info;
	this.appLogoPath = appLogoPath;
	this.devLogoPath = devLogoPath;
	this.name = name;
	this.appType = appType;
    }
// ============= Public Methods ============== //


    public String getInfo() {
	return info;
    }

    public void setInfo(String info) {
	this.info = info;
    }

    public String getAppLogoPath() {
	return appLogoPath;
    }

    public void setAppLogoPath(String appLogoPath) {
	this.appLogoPath = appLogoPath;
    }

    public String getDevLogoPath() {
	return devLogoPath;
    }

    public void setDevLogoPath(String devLogoPath) {
	this.devLogoPath = devLogoPath;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public AppType getAppType() {
	return appType;
    }

    public void setAppType(AppType appType) {
	this.appType = appType;
    }
    /**
     * True if the apps match and false otherwise.
     */
    @Override
    public boolean equals(Object app){
	if(app instanceof App){
	    App testApp = (App)app;
	    if(testApp.getName().equals(name) &&
	       testApp.getAppType() == appType){
		return true;
	    }
	}
	return false;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public String toString(){
	return name;
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
