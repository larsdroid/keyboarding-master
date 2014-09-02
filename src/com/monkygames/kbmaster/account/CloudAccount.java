/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

/**
 *
 * A ClientAccount manages saving and loading profiles.
 * Profiles can be uploaded to the cloud or saved locally.
 * @author spethm
 */
public interface CloudAccount {

	/**
	 * Syncs the specified directory with the cloud.
	 */
	public boolean syncProfiles(String directory);

	/**
	 * Returns the access token for the web account.
	 */
	public String getAccessToken();
}
