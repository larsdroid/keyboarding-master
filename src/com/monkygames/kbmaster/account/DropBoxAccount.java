/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;
import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.account.dropbox.MetaData;
import com.monkygames.kbmaster.controller.ProfileUIController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the DropBox Synchronization.
 * @author spethm
 */
public class DropBoxAccount implements CloudAccount{

	private DbxAppInfo appInfo;
	private DbxWebAuthNoRedirect webAuth;
	private String accessToken;
	private DbxClient client;
	private DbxRequestConfig config;
	
	public DropBoxAccount(){
		// setup dropbox
		appInfo = new DbxAppInfo(DropBoxApp.APP_KEY, DropBoxApp.APP_SECRET);
		config = new DbxRequestConfig(
            "kbmaster/"+KeyboardingMaster.VERSION, Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
	}

	public DropBoxAccount(String accessToken){
		this();
		this.accessToken = accessToken;
		setupClient();
	}

	@Override
	public String getAccessToken(){
		return accessToken;
	}

	/**
	 * Returns the authorization URL.
	 */
	public String getAuthorizeURL(){
		// Have the user sign in and authorize your app.
		return webAuth.start();
	}

	/**
	 * Set the authorize code to get access token.
	 * @param code the authorize code to get the access token.
	 */
	public void setAuthorizeCode(String code){
		DbxAuthFinish authFinish;
		try {
			authFinish = webAuth.finish(code);
			accessToken = authFinish.accessToken;
			setupClient();
		} catch (DbxException ex) {
			Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public boolean sync() {
		System.out.println("[DropBoxAccount:sync]");

		// sync globalaccount
		if(syncFile(GlobalAccount.dbFileName)){
			System.out.println("[DropBoxAccount:sync] "+GlobalAccount.dbFileName+" sync success");
		}else{
			System.out.println("[DropBoxAcount:sync] "+GlobalAccount.dbFileName+" sync failure");
			return false;
		}

		// make sure local profiles dir exists
		String profileDir = ProfileUIController.profileDirS;
		File localProfileDir = new File(profileDir);
		if(!localProfileDir.exists()){
			localProfileDir.mkdir();
		}

		try {

			// sync profiles
			DbxEntry.WithChildren listing = client.getMetadataWithChildren("/"+profileDir);
			if(listing == null){
				// need to create the directory
				DbxEntry entry =  client.createFolder("/"+profileDir);
				if(entry == null){
					System.out.println("can't create directory on dropbox");
					return false;
				}
				// upload all files from profiles directory
				for(File file: localProfileDir.listFiles()){
					if(!syncFile(profileDir+"/"+file.getName())){
						System.out.println("["+file.getName()+"] sync failure");
						return false;
					}
				}
			}else {
				// need to iterate through all and compare individual files
				for (DbxEntry child : listing.children) {
					// first investigate files stored on the cloud
					if(!syncFile(profileDir+"/"+child.name)){
						System.out.println("["+child.name+"] sync failure");
						return false;
					}
					System.out.println("	" + child.name + ": " + child.toString());
				}
				// now iterate through all children
				for(File file: localProfileDir.listFiles()){
					if(!syncFile(profileDir+"/"+file.getName())){
						System.out.println("["+file.getName()+"] sync failure");
						return false;
					}
				}
			}

		} catch (DbxException ex) {
			Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}

		return true;
	}

	private void setupClient(){
		System.out.println("[DropBoxAccount:setupClient]");
		client = new DbxClient(config, accessToken);
		try {
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
		} catch (DbxException ex) {
			Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private boolean syncFile(String filename){
		System.out.println("[DropBoxAccount:syncFile]");
		MetaData localMetaData = getLocalDropboxMetaData(filename);
		MetaData cloudMetaData = getCloudDropboxMetaData(filename);
		System.out.println("[DropBoxAccount:syncFile] "+localMetaData+" "+cloudMetaData);

		// note, the conditionals below could be reduced;
		// however, for code readabilty, i have elected to
		// break it out

		// both local and cloud doesn't exist
		// OR local only exists
		if( (localMetaData == null && cloudMetaData == null) ||
			(localMetaData != null && cloudMetaData == null) ){
			System.out.println("[DropBoxAccount:syncFile] State-None");
			return uploadAndUpdateLocalFile(filename);
		}

		// cloud only exists
		if(localMetaData == null && cloudMetaData != null){
			System.out.println("[DropBoxAccount:syncFile] State-cloud_only");
			return downloadAndUpdateLocalFile(filename);
		}

		// both metadata exist
		if(localMetaData.rev.equals(cloudMetaData.rev)){
			System.out.println("[DropBoxAccount:syncFile] State-synced");
			return true;
		}else{
			// different, select which one
			if(localMetaData.lastModified > cloudMetaData.lastModified){
			System.out.println("[DropBoxAccount:syncFile] State-local_newer");
				// local data is newer
				return uploadAndUpdateLocalFile(filename);
			}else{
				// the cloud is newer
			System.out.println("[DropBoxAccount:syncFile] State-cloud_newer");
				return downloadAndUpdateLocalFile(filename);
			}
		}
	}

	/**
	 * Return the drobox metadata from the cloud.
	 * @param filename the name of the file to get the rev number
	 * @return the metadata and -1 if it doesn't exist
	 */
	private MetaData getCloudDropboxMetaData(String filename){
		System.out.println("[DropBoxAccount:getCloudDropboxMetaData] "+filename);
		DbxEntry.File entry;
		try {

			System.out.println("[DropBoxAccount:getCloudDropboxMetaData] before entry");
			entry = (DbxEntry.File)client.getMetadata("/"+filename);
			System.out.println("[DropBoxAccount:getCloudDropboxMetaData] "+entry);
			if(entry != null){
				MetaData metaData = new MetaData(entry.rev, entry.lastModified.getTime());
				System.out.println("[DropBoxAccount:getCloudDropboxMetaData] "+metaData);
				return metaData;
			}else{
				// doens't exist
				System.out.println("[DropBoxAccount:getCloudDropboxMetaData] doesn't exist");
				return null;
			}

		} catch (DbxException ex) {
			Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	 * Return the dropbox meta data
	 * @param the filename on the local filesystem.
	 * @return the meta data and null if it doesn't exist.
	 */
	private MetaData getLocalDropboxMetaData(String filename){
		System.out.println("[DropBoxAccount:getLocalDropboxMetaData] "+filename);
		try{
			MetaData retVal = null;
			EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
			ObjectContainer db = Db4oEmbedded.openFile(config, filename);

			List<MetaData> revList = db.query(MetaData.class);
			if(revList.isEmpty()){
				retVal = null;
			}else{
				retVal = revList.get(0);
			}
			db.close();
			System.out.println("[DropBoxAccount:getLocalDropboxMetaData] "+retVal);
			return retVal;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error in opening: "+filename);
			return null;
		}
	}

	/**
	 * Uploads a file to the cloud.
	 * @param filename the file to upload to the cloud.
	 * @return the metadata for the uploaded file (on success) 
	 * and null on failure.
	 */ 
	private MetaData uploadFile(String filename){
		FileInputStream inputStream = null;
		try {
			File inputFile = new File(filename);
			inputStream = new FileInputStream(inputFile);

    		DbxEntry.File uploadedFile = client.uploadFile("/"+filename,
        	DbxWriteMode.add(), inputFile.length(), inputStream);

	    	System.out.println("Uploaded: " + uploadedFile.toString());

			MetaData metaData = new MetaData(uploadedFile.rev,uploadedFile.lastModified.getTime());

			return metaData;
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException ex) {
					Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return null;
	}

	/**
	 * Download the file from the cloud.
	 * @return the metadata for the downloaded file (on success) 
	 * and null on failure.
	 */
	private MetaData downloadFile(String filename){
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filename);
    		DbxEntry.File downloadedFile = client.getFile("/"+filename, null, outputStream);
    		System.out.println("Metadata: " + downloadedFile.toString());
			MetaData metaData = new MetaData(downloadedFile.rev, downloadedFile.lastModified.getTime());
			return metaData;
		}catch (Exception e){
			
		} finally {
			if(outputStream != null){
				try {
						outputStream.close();
				} catch (IOException ex) {
					Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return null;
	}

	private boolean updateLocalFileMetaData(String filename, MetaData metaData){
		try{
			EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
			ObjectContainer db = Db4oEmbedded.openFile(config, filename);

			List<MetaData> revList = db.query(MetaData.class);
			if(revList.isEmpty()){
				db.store(metaData);
				db.commit();
			}else{
				MetaData storedMetaData = revList.get(0);
				storedMetaData.update(metaData);
				db.store(storedMetaData);
				db.commit();
			}
			db.close();
			return true;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Error in updating metadata: "+filename);
			return false;
		}
	}

	/**
	 * Uploads the file to the cloud and updates the local file's meta data.
	 * @param filename the filename to upload and update.
	 * @return true on success and false otherwise.
	 */
	private boolean uploadAndUpdateLocalFile(String filename){
		MetaData metaData = uploadFile(filename);
		if(metaData != null){
			// update local file's metadata
			return updateLocalFileMetaData(filename, metaData);
		}
		return false;
	}

	/**
	 * Downloads the file to the cloud and updates the local file's meta data.
	 * @param filename the filename to download and update.
	 * @return true on success and false otherwise.
	 */
	private boolean downloadAndUpdateLocalFile(String filename){
		// download file
		MetaData metaData = downloadFile(filename);
		if(metaData != null){
			// update local file's metadata
			return updateLocalFileMetaData(filename, metaData);
		}
		return false;
	}
}