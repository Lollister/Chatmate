package application;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {
	
	private ConnectionHandler connectionHandler;
	private LoginHandler loginHandler;
	private User user;
	
	@FXML
	private VBox vLobby;
	
	@FXML
	private VBox vLoginButton;
	
	@FXML
	private VBox vLoginWin;
	
	@FXML
	private VBox vPwConfirm;
	
	@FXML
	private VBox vUserInfo;
	
	@FXML
	private TextField tfNutzername;
	
	@FXML
	private TextField tfPasswort;
	
	@FXML
	private TextField tfLobbyChat;
	
	@FXML
	private TextField tfPasswordConfirm;
	
	@FXML
	private TextArea taLobbyChat;
	
	@FXML
	private Label laLoginError;
	
	@FXML
	private Label laUsername;
	
	@FXML
	private Label laSiege;
	
	@FXML
	private Label laUnentschieden;
	
	@FXML
	private Label laNiederlagen;
	
	void initialize() {}
	
	public void loginButtonClicked() {	
			
		vLoginWin.setVisible(true);
		vLoginWin.toFront();
		
		System.out.println("a");
		
	}
	
	public void closeLoginWindow() {
		
		vLoginWin.setVisible(false);
		vLoginWin.toBack();
		vPwConfirm.setVisible(false);
		tfNutzername.clear();
		tfPasswort.clear();		
	}
	
	public void sendMessage() {
		
		loginHandler.broadcastMessage(tfLobbyChat.getText());
		tfLobbyChat.clear();
	}
	
	public void register() {
		
		if (tfNutzername.getText().equals("")) {
			laLoginError.setText("Fill in Username");
		}
		else if (tfPasswort.getText().equals("")) {
			laLoginError.setText("Fill in Password");
		}
		else {
			
			if (checkLoginInput(tfNutzername.getText())== true) {
				laLoginError.setText("Username contains illegal Character");
			}
			else if (checkLoginInput(tfPasswort.getText())== true) {
				laLoginError.setText("Password contains illegal Character");
			}
			else {
				vPwConfirm.setVisible(true);
				vPwConfirm.toFront();			
			}			
		}			
	}
	
	public boolean checkLoginInput(String input) {
		
		Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\" \\_^]");
	    Matcher matcher = pattern.matcher(input);
	    return matcher.find();	
	}
	
	public void loginConfirm() {
		if (tfPasswort.getText().equals(tfPasswordConfirm.getText())) {
			LoginHandler loginHandler = new LoginHandler(tfNutzername.getText(),tfPasswort.getText());
			if (loginHandler.register()) {
				vPwConfirm.setVisible(false);
				tfPasswordConfirm.clear();
			}
			else {
				vPwConfirm.setVisible(false);
				tfPasswordConfirm.clear();
				laLoginError.setText("User couldn`t be registered");
			}
		}
		else {
			vPwConfirm.setVisible(false);
			tfPasswordConfirm.clear();
			laLoginError.setText("Password doesn`t match");
		}
	}
	
	public void login() {
		
		if (tfNutzername.getText().equals("")) {
			laLoginError.setText("Fill in Username");
		}
		else if (tfPasswort.getText().equals("")) {
			laLoginError.setText("Fill in Password");
		}
		else {
			
			if (checkLoginInput(tfNutzername.getText())== true) {
				laLoginError.setText("Username contains illegal Character");
			}
			else if (checkLoginInput(tfPasswort.getText())== true) {
				laLoginError.setText("Password contains illegal Character");
			}
			else {
				loginHandler = new LoginHandler(tfNutzername.getText(),tfPasswort.getText());	
				user = loginHandler.login();
				if (user != null) {
					vPwConfirm.setVisible(false);
					vLoginWin.setVisible(false);
					vLoginButton.setVisible(false);
					tfPasswordConfirm.clear();
					tfNutzername.clear();
					tfPasswort.clear();
					ladeNutzer();
				}
							
			}			
		}			
		
	}

	private void ladeNutzer() {
		vUserInfo.setVisible(true);	
		laUsername.setText(user.username);
		laSiege.setText(Integer.toString(user.siege));
		laUnentschieden.setText(Integer.toString(user.unentschieden));
		laNiederlagen.setText(Integer.toString(user.niederlagen));	
		taLobbyChat.setPromptText("Type something to chat!");
		chatListener();
	}
	
	public void chatListener() {
		loginHandler.startChatListener(taLobbyChat);
	}

}
