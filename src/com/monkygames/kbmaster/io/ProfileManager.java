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
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.input.Profile;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import com.monkygames.kbmaster.input.WheelMapping;

/**
 * Manages saving and loading profiles.
 * @version 1.0
 */
public class ProfileManager{

// ============= Class variables ============== //
    private ObjectContainer db;
    private List<Profile> profiles;
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
	// make sure keymap objects are removed when a profile is deleted
	config.common().objectClass(Profile.class).cascadeOnDelete(true);
	config.common().objectClass(Keymap.class).cascadeOnDelete(true);
	config.common().objectClass(Mapping.class).cascadeOnDelete(true);
	config.common().objectClass(ButtonMapping.class).cascadeOnDelete(true);
	config.common().objectClass(WheelMapping.class).cascadeOnDelete(true);
	config.common().objectClass(Button.class).cascadeOnDelete(true);
	config.common().objectClass(Wheel.class).cascadeOnDelete(true);
	config.common().objectClass(Output.class).cascadeOnDelete(true);

	db = Db4oEmbedded.openFile(config, databaseFilename);

	loadProfiles();
	// if no profiles exist, create a default
	if(profiles.isEmpty()){
	    addProfile(new Profile());
	}
    }
// ============= Public Methods ============== //
    public void close(){
	db.close();
    }
    public List<Profile> getProfiles(){
	return profiles;
    }
    /**
     * Adds the profile to the database and then reloads from the database.
     * @param profile the profile to store.
     */
    public void addProfile(Profile profile){
	try{
	    db.store(profile);
	    loadProfiles();
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

	}catch(Exception e){}
    }

    /**
     * Removes the profile from the database and updates the list.
     * @param profile the profile to remove.
     */
    public void removeProfile(Profile profile){
	try{
	    db.delete(profile);
	    loadProfiles();
	}catch(Exception e){}
    }

    /**
     * Export the profile to a unique file location.
     * @param path the path to the file to save.
     * @profile the profile to save.
     * @return true on success and false otherwise.
     */
    public boolean exportProfile(String path, Profile profile){
	try{
	    String name = path+File.separator+profile.getProfileName()+".prof";
	    // check if the file exists, if so, delete it
	    File file = new File(name);
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
	    ObjectContainer exportDB = Db4oEmbedded.openFile(config, name);
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
    public boolean importProfile(String path){
	File file = new File(path);
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
	ObjectContainer importDB = Db4oEmbedded.openFile(config, path);
	try{
	    List<Profile> importProfiles = importDB.query(Profile.class);
	    // first delete all profiles
	    for(Profile importProfile: importProfiles){
		for(Profile profile: profiles){
		    if(importProfile.getProfileName().equals(profile.getProfileName())){
			db.delete(profile);
			break;
		    }
		}
	    }
	    // import the profiles
	    for(Profile importProfile: importProfiles){
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
