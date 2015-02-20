package com.monkygames.kbmaster.driver;

/**
 *
 * Used for setting the input mapping in order to detect input for the device.
 */
public class InputMap {

    /**
     * The id of the 
     */
    private final int id;
    private final String name;
    public InputMap(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
