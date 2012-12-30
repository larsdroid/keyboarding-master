/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

// === jnostromo imports === //
//import com.monkygames.jnostromo.io.Mapper;

/**
 * Contains the configuration for all the keymaps.
 * @version 1.0
 */
public class Profile{

// ============= Class variables ============== //
    /**
     * The keymaps for this profile.
     */
    private Keymap[] keymaps;
    /**
     * The name of the profile.
     */
    private String name;
// ============= Constructors ============== //
    public Profile(){
	this("Default");
    }
    public Profile(String name){
	this.name = name;
	keymaps = new Keymap[8];
	for(int i = 0; i < keymaps.length; i++){
	    //keymaps[i] = Mapper.generateDefaultKeymap(i+1);
	}
    }
// ============= Public Methods ============== //

    /**
     * Sets this profile to the contents of the profile specified.
     */
    public void setProfile(Profile profile){
	for(int i = 0; i < 8; i++){
	    this.setKeymap(i, profile.getKeymap(i));
	}
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    /**
     * Returns the keymap at the specified index.
     * Note, valid index is 0 - 7 inclusive.
     */
    public Keymap getKeymap(int index){
	return keymaps[index];
    }
    public void setKeymap(int index, Keymap keymap){
	keymaps[index] = keymap;
    }

    /**
     * Clones this profile and returns the clone with the specified name.
     * Note, this is a deep clone.
     * @param newName the new name of the clone.
     * @return the cloned profile.
     */
    public Profile cloneProfile(String newName){
	Profile newProfile = new Profile(newName);
	for(int i = 0; i < 8; i++){
	    newProfile.setKeymap(i, (Keymap)keymaps[i].clone());
	}
	return newProfile;
    }

    public void printString(){
	String out = "Profile "+name+"[\n";
	for(int i = 0; i < 8; i++){
	    out += keymaps[i].toStringFormatted()+"\n";
	}
	System.out.println(out+"]");
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