/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.io;

// === java imports === //
import java.util.List;
import java.io.File;
// === db4o imports === //
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
// === kbmaster imports === //
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentActivationSupport;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import com.monkygames.kbmaster.profiles.Root;

/**
 * Manages saving and loading profiles.
 * @version 1.0
 */
public class ProfileManager{

// ============= Class variables ============== //
    private ObjectContainer db;
    //private List<Profile> profiles;
    //private AppListManager appListManager;
    private Root appsRoot;
    private Root gamesRoot;
// ============= Constructors ============== //
    /**
     * Create a new profile manager where the specified string is the location
     * of the profile database.
     * @param databaseFilename the location of the database db4o file.
     */
    public ProfileManager(String databaseFilename){
	// accessDb4o
	EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
	// turn on transparent activation
	config.common().add(new TransparentActivationSupport());

	// make sure that when a profile is updated so are all the data members
	config.common().objectClass(Root.class).cascadeOnUpdate(true);
	config.common().objectClass(App.class).cascadeOnUpdate(true);
	config.common().objectClass(Profile.class).cascadeOnUpdate(true);
	config.common().objectClass(Keymap.class).cascadeOnUpdate(true);
	config.common().objectClass(Mapping.class).cascadeOnUpdate(true);
	config.common().objectClass(ButtonMapping.class).cascadeOnUpdate(true);
	config.common().objectClass(WheelMapping.class).cascadeOnUpdate(true);
	config.common().objectClass(Button.class).cascadeOnUpdate(true);
	config.common().objectClass(Wheel.class).cascadeOnUpdate(true);
	config.common().objectClass(Output.class).cascadeOnUpdate(true);
	config.common().objectClass(AppListManager.class).cascadeOnUpdate(true);
	// make sure keymap objects are removed when a profile is deleted
	config.common().objectClass(Root.class).cascadeOnDelete(true);
	config.common().objectClass(App.class).cascadeOnDelete(true);
	config.common().objectClass(Profile.class).cascadeOnDelete(true);
	config.common().objectClass(Keymap.class).cascadeOnDelete(true);
	config.common().objectClass(Mapping.class).cascadeOnDelete(true);
	config.common().objectClass(ButtonMapping.class).cascadeOnDelete(true);
	config.common().objectClass(WheelMapping.class).cascadeOnDelete(true);
	config.common().objectClass(Button.class).cascadeOnDelete(true);
	config.common().objectClass(Wheel.class).cascadeOnDelete(true);
	config.common().objectClass(Output.class).cascadeOnDelete(true);
	config.common().objectClass(AppListManager.class).cascadeOnDelete(true);

	db = Db4oEmbedded.openFile(config, databaseFilename);

	loadRoots();
    }
// ============= Public Methods ============== //
    public void close(){
	db.close();
    }
    /**
     * Adds an app to the list if it doesn't already exist.
     * @param app the app to add to the list.
     * @return true on success and false if the name already exists.
     */
    public boolean addApp(App app){
	Root root = null;
	if(app.getAppType() == gamesRoot.getAppType()){
	    root = gamesRoot;
	}else if(app.getAppType() == appsRoot.getAppType()){
	    root = appsRoot;
	}else{
	    return false;
	}
	root.addApp(app);
	// note, the app should be implicietly stored
	// however, for sake of clarity, its explicitly stored
	try{
	    // store the app and the root
	    db.store(app);
	    db.store(root);
	}catch(Exception e){
	    return false;
	}
	return true;
    }
    /**
     * Saves the current state of the tree.
     */
    public void updateRoots(){
	try{
	    db.store(gamesRoot);
	    db.store(appsRoot);
	}catch(Exception e){}
    }
    /**
     * Returns true if the profile already exists and false otherwise.
     * @param type the type of profile.
     * @param programName the name of the program.
     * @param profileName the name of the profile.
     * @return true if profile exists and false if it does not exists.
     */
    public boolean doesProfileNameExists(App app, String profileName){
	for(Profile profile: app.getProfiles()){
	    if(profile.getApp().getAppType() == app.getAppType() && 
	       profile.getApp().getName().equals(app.getName()) && 
	       profile.getProfileName().equals(profileName)){
		return true;
	    }
	}
	return false;
    }
    /**
     * Adds the profile to the app and stores to the database.
     * @param app the app to add the profile.
     * @param profile the profile to be added.
     */
    public void addProfile(App app, Profile profile){
	Root root = null;
	app.addProfile(profile);
	if(app.getAppType() == gamesRoot.getAppType()){
	    root = gamesRoot;
	}else if(app.getAppType() == appsRoot.getAppType()){
	    root = appsRoot;
	}
	try{
	    // explicitly store the profile, app, and root.
	    db.store(profile);
	    db.store(app);
	    db.store(root);
	}catch(Exception e){}

    }
    
    /**
     * Updates the profile and saves back into the database.
     * @para profile the profile to update.
     */
    public void updateProfile(Profile profile){
	try{
	    db.store(profile);
	    //loadProfiles();
	    //addApp(profile.getApp());

	}catch(Exception e){}
    }

    /**
     * Removes the profile from the database and updates the list.
     * @param profile the profile to remove.
     */
    public void removeProfile(App app, Profile profile){
	Root root;
	if(app.getAppType() == gamesRoot.getAppType()){
	    root = gamesRoot;
	}else if(app.getAppType() == appsRoot.getAppType()){
	    root = appsRoot;
	}else{
	    return;
	}
	app.removeProfile(profile);
	profile.unlink();
	db.delete(profile);
	if(app.getProfiles().size() == 0){
	    root.removeApp(app);
	}
	// reload profiles
	loadRoots();
    }

    /**
     * Export the profile to a unique file location.
     * @param path the path to the file to save.
     * @profile the profile to save.
     * @return true on success and false otherwise.
     */
    public boolean exportProfile(File file, Profile profile){
	try{
	    if(file.exists()){
		file.delete();
	    }

	    EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
	    config.common().objectClass(Profile.class).cascadeOnUpdate(true);
	    config.common().objectClass(Keymap.class).cascadeOnUpdate(true);
	    config.common().objectClass(Mapping.class).cascadeOnUpdate(true);
	    config.common().objectClass(ButtonMapping.class).cascadeOnUpdate(true);
	    config.common().objectClass(WheelMapping.class).cascadeOnUpdate(true);
	    config.common().objectClass(Button.class).cascadeOnUpdate(true);
	    config.common().objectClass(Wheel.class).cascadeOnUpdate(true);
	    config.common().objectClass(Output.class).cascadeOnUpdate(true);
	    ObjectContainer exportDB = Db4oEmbedded.openFile(config, file.getPath());
	    //ObjectContainer exportDB = Db4oEmbedded.openFile(config, name);
	    exportDB.store(profile);
	    exportDB.store(profile.getApp());
	    exportDB.close();
	}catch(Exception e){
	    return false;
	}
	return true;
    }

    /**
     * Imports the profile into the project.
     * @return false if error and true on success.
     */
    public boolean importProfile(File file){
	if(!file.exists()){
	    return false;
	}
	EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
	config.common().objectClass(Profile.class).cascadeOnUpdate(true);
	config.common().objectClass(Keymap.class).cascadeOnUpdate(true);
	config.common().objectClass(Mapping.class).cascadeOnUpdate(true);
	config.common().objectClass(ButtonMapping.class).cascadeOnUpdate(true);
	config.common().objectClass(WheelMapping.class).cascadeOnUpdate(true);
	config.common().objectClass(Button.class).cascadeOnUpdate(true);
	config.common().objectClass(Wheel.class).cascadeOnUpdate(true);
	config.common().objectClass(Output.class).cascadeOnUpdate(true);
	ObjectContainer importDB = Db4oEmbedded.openFile(config, file.getAbsolutePath());
	try{
	    List<Profile> importProfiles = importDB.query(Profile.class);
	    // first delete all profiles that match the profile to be imported
	    for(Profile importProfile: importProfiles){
		// find the root
		Root root;
		if(importProfile.getApp().getAppType() == gamesRoot.getAppType()){
		    root = gamesRoot;
		}else if(importProfile.getApp().getAppType() == appsRoot.getAppType()){
		    root = appsRoot;
		}else {
		    return false;
		}

		// find the app
		App existingApp = null;
		for(App app: root.getList()){
		    if(app.equals(importProfile.getApp())){
			existingApp = app;

		    }
		}
		if(existingApp == null){
		    // add since the app doesn't exist in the user's db
		    existingApp = importProfile.getApp();
		    root.addApp(existingApp);
		}else{
		    // search for the prof, if found, delete it
		    for(Profile prof: existingApp.getProfiles()){
			if(prof.equals(importProfile)){
			    existingApp.removeProfile(prof);
			    prof.unlink();
			    db.delete(prof);
			}
		    }
		    // set the app and add the profile to this app
		    importProfile.setApp(existingApp);
		    existingApp.addProfile(importProfile);
		}
		// save the profile
		db.store(root);
	    }
	    importDB.close();
	    // load profiles
	    this.loadRoots();
	}catch(Exception e){ 
	    return false;
	}
	return true;
    }
    public Root getAppsRoot(){
	return getRoot(AppType.APPLICATION);
    }
    public Root getGamesRoot(){
	return getRoot(AppType.GAME);
    }
    /**
     * Returns the root based on the app type.
     * @return the apps or games type, and defaults to games.
     */
    public Root getRoot(AppType type){
	loadRoots();
	if(type == appsRoot.getAppType()){
	    return appsRoot;
	}else if(type == gamesRoot.getAppType()){
	    return gamesRoot;
	}
	return gamesRoot;
    }

    /**
     * Prints the profiles out in a human readable way.
     */
    public void printProfilesFormatted(){
	System.out.println("=== Game Profiles ===");
	for(App app: gamesRoot.getList()){
	    for(Profile profile: app.getProfiles()){
		profile.printString();
		System.out.println("== End of "+profile.getProfileName()+" ==");
	    }
	}
	System.out.println("=== Appp Profiles ===");
	for(App app: appsRoot.getList()){
	    for(Profile profile: app.getProfiles()){
		profile.printString();
		System.out.println("== End of "+profile.getProfileName()+" ==");
	    }
	}
	System.out.println("== ======== ==");
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void loadRoots(){
	try{
	    List<Root> rootList = db.query(Root.class);
	    if(!rootList.isEmpty()){
		for(Root root: rootList){
		    if(root.getAppType() == AppType.APPLICATION){
			appsRoot = root;
		    }else if(root.getAppType() == AppType.GAME){
			gamesRoot = root;
		    }
		}
	    }else{
		appsRoot = new Root("Application",AppType.APPLICATION);
		gamesRoot = new Root("Game",AppType.GAME);
		db.store(appsRoot);
		db.store(gamesRoot);
	    }
	}catch(Exception e){}
    }
    /**
     * Returns a list of applications that have profiles.
     */
    private List<App> getAppTypeNames(AppType type){
	if(type == gamesRoot.getAppType()){
	    return gamesRoot.getList();
	}else{
	    return appsRoot.getList();
	}
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
