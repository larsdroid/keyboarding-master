/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

/**
 * Manages the DropBox Synchronization.
 * @author spethm
 */
public class DropBoxAccount implements CloudAccount{

	@Override
	public boolean syncProfiles(String directory) {
		return false;
	}
}
