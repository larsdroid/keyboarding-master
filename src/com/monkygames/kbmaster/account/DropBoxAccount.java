/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.monkygames.kbmaster.KeyboardingMaster;
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
	
	public DropBoxAccount(){
		// setup dropbox
		appInfo = new DbxAppInfo(DropBoxApp.APP_KEY, DropBoxApp.APP_SECRET);
		DbxRequestConfig config = new DbxRequestConfig(
            "kbmaster/"+KeyboardingMaster.VERSION, Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
	}
	public DropBoxAccount(String accessToken){
		this();
		this.accessToken = accessToken;
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
		//System.out.println("1. Go to: " + authorizeUrl);
		//System.out.println("2. Click \"Allow\" (you might have to log in first)");
		//System.out.println("3. Copy the authorization code.");
		//String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		//KeyboardingMaster.gotoWeb(authorizeUrl);
	}
	public void setAuthorizeCode(String code){
		DbxAuthFinish authFinish;
		try {
			authFinish = webAuth.finish(code);
			accessToken = authFinish.accessToken;
		} catch (DbxException ex) {
			Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public boolean syncProfiles(String directory) {
		return false;
	}
}