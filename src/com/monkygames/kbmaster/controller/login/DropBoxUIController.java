/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.login;

import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.account.DropBoxAccount;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLHtmlElement;

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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		// setup dropbox
		cloudAccount = new DropBoxAccount();
		web.getEngine().load(cloudAccount.getAuthorizeURL());
		// open for testing html to parse it
		KeyboardingMaster.gotoWeb(cloudAccount.getAuthorizeURL());
		// so that stages can hide without being terminated.
		//Platform.setImplicitExit(false);
    }
	@FXML
	public void cancelEventFire(ActionEvent evt){
		loginController.showStage();
	}

    @FXML
    public void acceptEventFired(ActionEvent evt){
		NodeList nodeList = web.getEngine().getDocument().getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++){
			//System.out.println(nodeList.item(i));
			NodeList nodeList2 = nodeList.item(i).getChildNodes();
			for(int j = 0; j < nodeList2.getLength(); j++){
				//System.out.println("\t"+nodeList2.item(j));
				NodeList nodeList3 = nodeList2.item(j).getChildNodes();
				for(int k = 0; k < nodeList3.getLength(); k++){
					Node node3 = nodeList3.item(k);
					NamedNodeMap map = node3.getAttributes();
					if(map != null){
						Node node4 = map.getNamedItem("id");
						if(node4 != null){
							String id = node4.getTextContent();
							if(id != null && id.equals("auth")){
								//System.out.println("node4 = "+node4);
								//System.out.println("node4.getTextContent() = "+node4.getTextContent());
								//System.out.println("\t\t"+nodeList3.item(k)+" "+node3.getTextContent());
								String content = node3.getTextContent();
								// parse content
								int index = content.indexOf("process.");
								String code = content.substring(index+8);
								System.out.println(code);
								cloudAccount.setAuthorizeCode(code);
								loginController.showDeviceMenuFromLogin(cloudAccount, true);
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
}
