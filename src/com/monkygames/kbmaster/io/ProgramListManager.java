/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.io;

import com.monkygames.kbmaster.input.ProfileType;
import java.util.ArrayList;

/**
 * Handles managing the lists of programs.
 * @version 1.0
 */
public class ProgramListManager{

// ============= Class variables ============== //
    /** 
     * A list of game names that can be used with profiles.
     */
    private ArrayList <String> gameNames;
    /**
     * A list of application names that can be used with profiles.
     */
    private ArrayList <String> applicationNames;
// ============= Constructors ============== //
    public ProgramListManager(){
	gameNames = new ArrayList<>();
	applicationNames = new ArrayList<>();
    }
// ============= Public Methods ============== //
    /**
     * Adds a program to the list specified by type.
     * @param program the name of the program to be added.
     * @param type the type of program to be added.
     * @return if the program already exists, false is returned and true on success.
     */
    public boolean addProgram(String program, ProfileType type){
	ArrayList<String> programs;
	switch(type){
	    case APPLICATION:
		programs = applicationNames;
		break;
	    case GAME:
	    default:
		programs = gameNames;

	}
	if(programs.contains(program)){
	    return false;
	}
	programs.add(program);
	return true;
    }
    /**
     * Returns the available programs that can be used with profiles.
     * @param type the type of program names to get.
     */
    public ArrayList<String> getAvailableProgramList(ProfileType type){
	if(type == ProfileType.APPLICATION){
	    return applicationNames;
	}
	return gameNames;
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
