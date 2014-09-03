/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.controller.ProfileUIController;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

		// sync globalaccount
		try {
			// TODO
			// 1. Open Global Account (DB) to check revision (if it exists)
			// 2. Check if file exists on dropbox
			//    1. If it doesn't exist 
			//	     * create revision
			//		 * upload to dropbox
			//		 * done
			// 3. Get compare rev of local vs dropbox
			//    1. If local is < dropbox (or doesn't exist)
			//       * download dropbox file to local (replace)
			//    2. If local is > dropbox
			//       * upload to dropbox


			DbxEntry entry = client.getMetadata(GlobalAccount.dbFileName);

			FileOutputStream outputStream = null;
    		outputStream = new FileOutputStream("magnum-opus.txt");
			DbxEntry.File downloadedFile = client.getFile("/magnum-opus.txt", null, outputStream);
			System.out.println("Metadata: " + downloadedFile.toString());

		}catch (Exception ex) {
			Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	private void setupClient(){
		client = new DbxClient(config, accessToken);
		try {
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
		} catch (DbxException ex) {
			Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
