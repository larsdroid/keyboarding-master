package com.monkygames.kbmaster.util;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentActivationSupport;
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.io.AppListManager;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.profiles.*;
import java.util.List;

/**
 * Converts profiles to xml using new XStream.
 */
public class ConvertToXML {

    public ConvertToXML(){}
    public boolean Convert(String db4oFile, String xmlFile){
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
        EmbeddedObjectContainer db = Db4oEmbedded.openFile(config, db4oFile);
        //EmbeddedObjectContainer db = Db4oEmbedded.openFile(db4oFile);
        // todo add cascade on read
        Root appsRoot = null;
        Root gamesRoot = null;
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
                System.out.println("Not valid db file");
                return false;
	    }
	}catch(Exception e){
            return false;
        }

        if(gamesRoot != null){
            System.out.println("Games Root: "+gamesRoot.getName());
            for(App app: gamesRoot.getList()){
                app.printString();
                for(Profile profile: app.getProfiles()){
                    profile.printString();
                }
            }
        }

        RootManager rootManager = new RootManager(appsRoot, gamesRoot);
        return XStreamManager.getStreamManager().writeRootManager(xmlFile, rootManager);
    }

    /**
     * Writes the old db4o to xml format.
     * Args[0] the src db4o file.
     * Args[1] the output xml file.
     */
    public static void main (String []args){
        ConvertToXML converter = new ConvertToXML();
        converter.Convert(args[0], args[1]);
    }
}
