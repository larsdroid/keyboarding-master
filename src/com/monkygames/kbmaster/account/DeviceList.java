package com.monkygames.kbmaster.account;

import java.util.ArrayList;


/**
 * Contains a list of DevicesPackages.
 */
public class DeviceList {

    /**
     * The list of devices used locally.
     */
    private ArrayList<DevicePackage> devices;

    public DeviceList(){
        devices = new ArrayList<>();
    }

    public ArrayList<DevicePackage> getList(){
        return devices;
    }
    
}
