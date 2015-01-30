package com.monkygames.kbmaster.io;

import com.monkygames.kbmaster.account.DeviceList;
import com.monkygames.kbmaster.account.DevicePackage;
import com.monkygames.kbmaster.account.UserSettings;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceInformation;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Hardware;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputKeymapSwitch;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.Root;
import com.monkygames.kbmaster.profiles.RootManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages files using XStream to read and write.
 */
public class XStreamManager {

    /**
     * Singleton.
     */
    private static XStreamManager xStreamManager;

    /**
     * For saving the user settings.
     */
    private XStream userSettingsStream;

    /**
     * For saving the Global Account.
     */
    private XStream globalAccountStream;

    /**
     * For saving roots for entire profiles.
     */
    private XStream rootStream;

    /**
     * The user settings file.
     */
    private File settingsFile;

    /**
     * The global account file.
     */
    private File globalAccountFile;

    public static final String settingsFileName = "settings.xml";
    public static final String globalAccountFileName = "global_account.xml";

    public XStreamManager(){
        // user settings
        userSettingsStream = new XStream(new DomDriver());
        userSettingsStream.alias("UserSettings", UserSettings.class);
        settingsFile = new File(settingsFileName);

        // global account
        globalAccountStream = new XStream(new DomDriver());
        globalAccountStream.alias("RootManager",RootManager.class);
        globalAccountStream.alias("Root",Root.class);
        globalAccountStream.alias("AppType",AppType.class);
        globalAccountStream.alias("ArrayList",ArrayList.class);
        globalAccountStream.alias("Profile",Profile.class);
        globalAccountStream.alias("App",App.class);
        globalAccountStream.alias("Keymap",Keymap.class);
        globalAccountStream.alias("HashMap",HashMap.class);
        globalAccountStream.alias("ButtonMapping",ButtonMapping.class);
        globalAccountStream.alias("Button",Button.class);
        globalAccountStream.alias("Output",Output.class);
        globalAccountStream.alias("WheelMapping",WheelMapping.class);
        globalAccountStream.alias("Wheel",Wheel.class);
        globalAccountStream.alias("Mapping",Mapping.class);
        globalAccountStream.alias("Hardware",Hardware.class);
        globalAccountStream.alias("OutputDisabled",OutputDisabled.class);
        globalAccountStream.alias("OutputKey",OutputKey.class);
        globalAccountStream.alias("OutputKeymapSwitch",OutputKeymapSwitch.class);
        globalAccountStream.alias("OutputMouse",OutputMouse.class);
        globalAccountStream.alias("DeviceList",DeviceList.class);
        globalAccountStream.alias("DevicePackage",DevicePackage.class);
        globalAccountStream.alias("Device",Device.class);
        globalAccountStream.alias("DeviceInformation",DeviceInformation.class);
        globalAccountFile = new File(globalAccountFileName);

        // root stream
        rootStream = new XStream(new DomDriver());
        rootStream.alias("RootManager",RootManager.class);
        rootStream.alias("Root",Root.class);
        rootStream.alias("AppType",AppType.class);
        rootStream.alias("ArrayList",ArrayList.class);
        rootStream.alias("Profile",Profile.class);
        rootStream.alias("App",App.class);
        rootStream.alias("Keymap",Keymap.class);
        rootStream.alias("HashMap",HashMap.class);
        rootStream.alias("ButtonMapping",ButtonMapping.class);
        rootStream.alias("Button",Button.class);
        rootStream.alias("Output",Output.class);
        rootStream.alias("WheelMapping",WheelMapping.class);
        rootStream.alias("Wheel",Wheel.class);
        rootStream.alias("Mapping",Mapping.class);
        rootStream.alias("Hardware",Hardware.class);
        rootStream.alias("OutputDisabled",OutputDisabled.class);
        rootStream.alias("OutputKey",OutputKey.class);
        rootStream.alias("OutputKeymapSwitch",OutputKeymapSwitch.class);
        rootStream.alias("OutputMouse",OutputMouse.class);
    }

    // === Public Methods === //

    /**
     * Writes the user settings out to the file.
     * @param userSettings the user settings to write.
     */
    public void writeUserSettings(UserSettings userSettings){
        write(userSettingsStream,settingsFile.getAbsolutePath(),userSettings);
    }

    /**
     * Reads the user settings from file.
     * If the file doesn't exist, than create a user setting and returns.
     * @return the user settings and null on error.
     */
    public UserSettings readUserSettings(){
        UserSettings userSettings = (UserSettings)read(userSettingsStream,settingsFile.getAbsolutePath());
        if(userSettings == null){
            return new UserSettings();
        }
        return userSettings;
    }

    /**
     * Writes the specified root manager to file.
     * @return true on success and false otherwise.
     */
    public boolean writeRootManager(String filename, RootManager rootManager){
        return write(rootStream,filename,rootManager);
    }

    /**
     * Reads the root manager from file and returns.
     * If no file exist, it creats a new Root Manager.
     * @param filename the file to read from.
     * @return the root manager or null on error.
     */
    public RootManager readRootManager(String filename){
        RootManager rootManager = (RootManager)read(rootStream,filename);
        if(rootManager == null){
            return new RootManager();
        }
        return rootManager;
    }

    /**
     * Writes the profile to file.
     * @return true on success and false otherwise.
     */
    public boolean writeProfile(String filename, Profile profile){
        return write(rootStream,filename,profile);
    }

    /**
     * Reads the Profile from file.
     * If no file exists, return null.
     * @param filename the file to read from.
     * @return the Profile or null on error.
     */
    public Profile readProfile(String filename){
        return (Profile)read(rootStream,filename);
    }

    /**
     * Writes the specified root manager to file.
     * @return true on success and false otherwise.
     */
    public boolean writeGlobalAccount(DeviceList deviceList){
        return write(globalAccountStream,globalAccountFile.getAbsolutePath(),deviceList);
    }

    /**
     * Reads the root manager from file and returns.
     * If no file exist, it creates a new Root Manager.
     * @return the root manager or null on error.
     */
    public DeviceList readGlobalAccount(){
        DeviceList deviceList = (DeviceList)read(this.globalAccountStream,globalAccountFile.getAbsolutePath());
        if(deviceList == null){
            return new DeviceList();
        }
        return deviceList;
    }

    // === Private Methods === //
    /**
     * Writes the specified object to the stream.
     * @return true on success and false otherwise.
     */
    private boolean write(XStream stream, String filename, Object obj){
        File file = new File(filename);
        String xml = stream.toXML(obj);
        try{
            Files.write(file.toPath(),xml.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Reads the object from file and returns the object.
     * If no file exist, null is returned.
     * @param filename the file to read from.
     * @return the unsearlized object and null if error.
     */
    private Object read(XStream stream, String filename){
        File file = new File(filename);
        if(!file.exists()){
            return null;
        }
        try{
            String xml = new String(Files.readAllBytes(file.toPath()));
            return stream.fromXML(xml);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    // === Static Methods === //
    /**
     * Singleton for returning the manager.
     * @return A singleton that manages the XStreams.
     */
    public static XStreamManager getStreamManager(){
        if(xStreamManager == null){
            xStreamManager = new XStreamManager();
        }
        return xStreamManager;
    }
}