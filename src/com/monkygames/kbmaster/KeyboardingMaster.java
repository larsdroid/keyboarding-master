/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.monkygames.kbmaster.account.DropBoxAccount;
import com.monkygames.kbmaster.account.UserSettings;
import com.monkygames.kbmaster.controller.login.LoginUIController;
import com.monkygames.kbmaster.util.WindowUtil;
import com.monkygames.kbmaster.util.thread.DropboxSyncTask;
import com.monkygames.kbmaster.util.thread.SyncEventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main class for loading the Keyboard Master GUI.
 */
public class KeyboardingMaster extends Application {

    private LoginUIController controller;
    public static final String VERSION = "0.1.10";

	/**
	 * Reference to this object.
	 */
	private static KeyboardingMaster _instance;

	/**
	 * Manages the user settings database.
	 */
    private ObjectContainer db;

	/**
	 * The user's settings.
	 */
	private UserSettings userSettings;

	/**
	 * The stage for the dropbox sync UI.
	 */
	private Stage dropboxSyncStage;

	/**
	 * Opens the default web browser.
	 * @url the url to open.
	 */
	public static void gotoWeb(String url){
		_instance.getHostServices().showDocument(url);
	}

	public static UserSettings getUserSettings(){
		return _instance.userSettings;
	}

	/**
	 * Save the user settings.
	 */
	public static void saveUserSettings(){
		// saves the user settings
		_instance.saveDB();
	}

	/**
	 * Save the database.
	 */
	public void saveDB(){
		db.store(userSettings);
		db.commit();
	}

    /**
     * Sets up the db4o database for the user's settings.
     */
    private void initDatabase(){
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		// make sure that when a profile is updated so are all the data members
		config.common().objectClass(UserSettings.class).cascadeOnUpdate(true);
		db = Db4oEmbedded.openFile(config, "settings.db4o");

		// retrive settings from db
		List <UserSettings> settings = db.query(UserSettings.class);
		if(settings.isEmpty()){
			// initialize the db
			userSettings = new UserSettings();
			db.store(userSettings);
			db.commit();
		}else{
			userSettings = settings.get(0);
		}
    }
    
    @Override
    public void start(Stage stage) {
		Parent root;
		KeyboardingMaster._instance = this;

		initDatabase();

		// initialize the login ui
		try {
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/login/LoginUI.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent)fxmlLoader.load(location.openStream());
			controller = (LoginUIController) fxmlLoader.getController();
			controller.setStage(stage);
			AnchorPane pane = (AnchorPane)root;
			WindowUtil.configureStage(pane.prefWidthProperty().doubleValue(), 
						  pane.prefHeightProperty().doubleValue(), 
						  root, stage);
		} catch (IOException ex) {
			Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
		}
		// initialize the dropbox sync ui
		try {
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/DropboxSync.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent)fxmlLoader.load(location.openStream());
			dropboxSyncStage = WindowUtil.createStage(root);
		} catch (IOException ex) {
			Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
		}

		// test user settings
		if(userSettings.isRemember){
			switch(userSettings.loginMethod){
				case LoginUIController.LOGIN_LOCAL:
					controller.showDeviceMenuFromLogin(null,false);
					break;
				case LoginUIController.LOGIN_DROPBOX:
					// get accesstoken
					if(userSettings.accessToken != null && !userSettings.accessToken.equals("")){
						startDropboxSync(new DropBoxAccount(userSettings.accessToken),false);
					}else{
						controller.showDeviceMenuFromLogin(null,false);
					}
					break;
			}
		}else{
			controller.showStage();
		}
    }

	/**
	 * Opens the dropbox sync UI and created a thread to start syncing.
	 * @param dropBoxAccount the dropbox account to sync.
	 * @param checkRemember true if the settings should be saved and false otherwise.
	 */
	public void startDropboxSync(DropBoxAccount dropBoxAccount, boolean checkRemember){
		// show the sync 
		dropboxSyncStage.show();
		SyncEventHandler handler = new SyncEventHandler (dropBoxAccount, checkRemember, controller, dropboxSyncStage);
		DropboxSyncTask syncTask = new DropboxSyncTask(dropBoxAccount);
		syncTask.setOnSucceeded(handler);
		syncTask.setOnFailed(handler);
		new Thread(syncTask).start();
	}

	public static KeyboardingMaster getInstance(){
		return _instance;
	}

    @Override
    public void stop(){
		db.close();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		launch(args);
    }
}
