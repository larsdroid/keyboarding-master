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
import com.monkygames.kbmaster.input.App;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.input.AppType;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import java.util.ArrayList;

/**
 * Manages saving and loading profiles.
 * @version 1.0
 */
public class ProfileManager{

// ============= Class variables ============== //
    private ObjectContainer db;
    private List<Profile> profiles;
    private AppListManager appListManager;
// ============= Constructors ============== //
    /**
     * Create a new profile manager where the specified string is the location
     * of the profile database.
     * @param databaseFilename the location of the database db4o file.
     */
    public ProfileManager(String databaseFilename){
	// accessDb4o
	EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
	// make sure that when a profile is updated so are all the data members
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

	loadProfiles();
	loadProgramListManager();
	/*
	// if no profiles exist, create a default
	if(profiles.isEmpty()){
	    addProfile(new Profile());
	}
	*/
    }
// ============= Public Methods ============== //
    public void close(){
	db.close();
    }
    public List<Profile> getProfiles(){
	return profiles;
    }
    /**
     * Returns the profiles that have the specified type and program name.
     * @param type the type of profile.
     * @param programName the program name associated with this profile.
     * @return the list of profiles with the specified type and program name.
     */
    public List<Profile> getProfile(App app){
	List<Profile> sortedList = new ArrayList<>();
	for(Profile profile: profiles){
	    if(profile.getApp().getAppType() == app.getAppType() &&
		profile.getApp().getName().equals(app.getName())){
		sortedList.add(profile);
	    }
	}
	return sortedList;
    }
    /**
     * Returns a list of apps that are available to use with profiles.
     * Note, this list can contain app names that are not associated with
     * profiles.
     * @param type the type of app.
     */
    public List<App> getAvailableApps(AppType type){
	return appListManager.getAvailableAppList(type);
    }
    /**
     * Adds an app to the list if it doesn't already exist.
     * @param app the app to add to the list.
     * @return true on success and false if the name already exists.
     */
    public boolean addApp(App app){
	if(appListManager.addApp(app)){
	    // store the list
	    db.store(appListManager);
	    return true;
	}
	return false;
    }
    /**
     * Returns a list of applications that have profiles.
     */
    public List<App> getApplications(){
	return getAppTypeNames(AppType.APPLICATION);
    }
    /**
     * Returns a list of games that have profiles.
     */
    public List<App> getGames(){
	return getAppTypeNames(AppType.GAME);
    }
    /**
     * Returns true if the profile already exists and false otherwise.
     * @param type the type of profile.
     * @param programName the name of the program.
     * @param profileName the name of the profile.
     * @return true if profile exists and false if it does not exists.
     */
    public boolean doesProfileNameExists(App app, String profileName){
	for(Profile profile: profiles){
	    if(profile.getApp().getAppType() == app.getAppType() && 
	       profile.getApp().getName().equals(app.getName()) && 
	       profile.getProfileName().equals(profileName)){
		return true;
	    }
	}
	return false;
    }
    /**
     * Adds the profile to the database and then reloads from the database.
     * @param profile the profile to store.
     */
    public void addProfile(Profile profile){
	try{
	    db.store(profile);
	    loadProfiles();
	    // add the program to the list if its not already added
	    addApp(profile.getApp());
	}catch(Exception e){}
    }
    
    /**
     * Updates the profile and saves back into the database.
     * @para profile the profile to update.
     */
    public void updateProfile(Profile profile){
	try{
	    db.store(profile);
	    loadProfiles();
	    addApp(profile.getApp());

	}catch(Exception e){}
    }

    /**
     * Removes the profile from the database and updates the list.
     * @param profile the profile to remove.
     */
    public void removeProfile(Profile profile){
	try{
	    System.out.println("=== before delete");
	    for(Profile prof: profiles){
		System.out.println(prof.getProfileName());
	    }

	    for(Profile prof: profiles){
		if(prof.getApp().getAppType() == profile.getApp().getAppType() && 
		    profile.getApp().getName().equals(profile.getApp().getName()) &&
		    profile.getProfileName().equals(profile.getProfileName())){
		    System.out.println("profile found and is deleted");
		    db.delete(prof);
		    break;
		}
	    }

	    db.delete(profile);
	    loadProfiles();
	    System.out.println("=== after delete");
	    for(Profile prof: profiles){
		System.out.println(prof.getProfileName());
	    }
	}catch(Exception e){}
    }

    /**
     * Export the profile to a unique file location.
     * @param path the path to the file to save.
     * @profile the profile to save.
     * @return true on success and false otherwise.
     */
    //public boolean exportProfile(String path, Profile profile){
    public boolean exportProfile(File file, Profile profile){
	try{
	    /*
	    String name = path+File.separator+profile.getProfileName()+".prof";
	    // check if the file exists, if so, delete it
	    File file = new File(name);
	    */
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
	System.out.println("importProfile file = "+file);
	//File file = new File(path);
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
	//ObjectContainer importDB = Db4oEmbedded.openFile(config, file.getPath());
	ObjectContainer importDB = Db4oEmbedded.openFile(config, file.getAbsolutePath());
	try{
	    List<Profile> importProfiles = importDB.query(Profile.class);
	    System.out.println("imprtProfiles.size = "+importProfiles.size());
	    // first delete all profiles that match the profile to be imported
	    for(Profile importProfile: importProfiles){
		for(Profile profile: profiles){
		    if(importProfile.getApp().getAppType() == profile.getApp().getAppType() && 
			importProfile.getApp().getName().equals(profile.getApp().getName()) &&
			importProfile.getProfileName().equals(profile.getProfileName())){
			db.delete(profile);
			break;
		    }
		}
	    }
	    // import the profiles
	    for(Profile importProfile: importProfiles){
		System.out.println("importing profile: "+importProfile);
		db.store(importProfile);
	    }
	    // load profiles
	    loadProfiles();
	}catch(Exception e){ 
	    return false;
	}
	return true;
    }


    /**
     * Prints the profiles out in a human readable way.
     */
    public void printProfilesFormatted(){
	System.out.println("== Profiles ==");
	for(Profile profile: profiles){
	    profile.printString();
	    System.out.println("== End of "+profile.getProfileName()+" ==");
	}
	System.out.println("== ======== ==");
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void loadProfiles(){
	try{
	    profiles = db.query(Profile.class);
	}catch(Exception e){}
    }
    private void loadProgramListManager(){
	try{
	    List<AppListManager> appListManagerList = db.query(AppListManager.class);
	    if(!appListManagerList.isEmpty()){
		appListManager = appListManagerList.get(0);
	    }
	    if(appListManager == null){
		appListManager = new AppListManager();
		db.store(appListManager);
	    }
	}catch(Exception e){}
    }
    /**
     * Returns a list of applications that have profiles.
     */
    private List<App> getAppTypeNames(AppType type){
	ArrayList<App> list = new ArrayList<>();
	for(Profile profile: profiles){
	    if(profile.getApp().getAppType() == type){
		if(!list.contains(profile.getApp())){
		    list.add(profile.getApp());
		}
	    }
	}
	return list;
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
