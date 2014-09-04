/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.login;

import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.account.DropBoxAccount;
import com.monkygames.kbmaster.util.thread.DropboxSyncTask;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * FXML Controller class
 */
public class DropBoxUIController implements Initializable {

    // === variables === //
    @FXML
    public Button acceptB;
    @FXML
    public Button cancelB;
	@FXML
	public WebView web;

	/**
	 * Network enabled accounts.
	 */
	private DropBoxAccount cloudAccount;

	/**
	 * The login controller.
	 */
	private LoginUIController loginController;

	/**
	 * The stage for this controller.
	 */
	private Stage stage;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		// setup dropbox
		cloudAccount = new DropBoxAccount();
	   // hide webview scrollbars whenever they appear.
		final WebView webb = web;
		webb.getChildrenUnmodifiable().addListener(new ListChangeListener<javafx.scene.Node>() {
		  @Override public void onChanged(Change<? extends javafx.scene.Node> change) {
			Set<javafx.scene.Node> deadSeaScrolls = webb.lookupAll(".scroll-bar");
			for (javafx.scene.Node scroll : deadSeaScrolls) {
			  scroll.setVisible(false);
			}
		  }
		});

		web.getEngine().load(cloudAccount.getAuthorizeURL());
		// open for testing html to parse it
		//KeyboardingMaster.gotoWeb(cloudAccount.getAuthorizeURL());
		// so that stages can hide without being terminated.
		//Platform.setImplicitExit(false);
    }
	@FXML
	public void cancelEventFired(ActionEvent evt){
		stage.hide();
		loginController.showStage();
	}

    @FXML
    public void acceptEventFired(ActionEvent evt){
		NodeList nodeList = web.getEngine().getDocument().getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++){
			NodeList nodeList2 = nodeList.item(i).getChildNodes();
			for(int j = 0; j < nodeList2.getLength(); j++){
				NodeList nodeList3 = nodeList2.item(j).getChildNodes();
				for(int k = 0; k < nodeList3.getLength(); k++){
					Node node3 = nodeList3.item(k);
					NamedNodeMap map = node3.getAttributes();
					if(map != null){
						Node node4 = map.getNamedItem("id");
						if(node4 != null){
							String id = node4.getTextContent();
							if(id != null && id.equals("auth")){
								String content = node3.getTextContent();
								// parse content
								int index = content.indexOf("process.");
								String code = content.substring(index+8);
								cloudAccount.setAuthorizeCode(code);
								stage.hide();
								// create a task
								KeyboardingMaster kbmaster = KeyboardingMaster.getInstance();
								kbmaster.startDropboxSync(cloudAccount,true);
								//loginController.showDeviceMenuFromLogin(cloudAccount, true);

								// TODO if not found, show pop error
							}
						}
					}
				}
			}
		}
	}

	public void setLoginController(LoginUIController loginController){
		this.loginController = loginController;
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}
}
