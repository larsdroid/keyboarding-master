package com.monkygames.kbmaster.util;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Root;
import com.monkygames.kbmaster.profiles.RootManager;
import java.util.List;

/**
 * Converts profiles to xml using new XStream.
 */
public class ConvertToXML {

    public ConvertToXML(){}
    public boolean Convert(String db4oFile, String xmlFile){
        EmbeddedObjectContainer db = Db4oEmbedded.openFile(db4oFile);
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
