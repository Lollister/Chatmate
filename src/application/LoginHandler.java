package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

import javafx.scene.control.TextArea;

public class LoginHandler {
	
	private String Username;
	
	private String Password;
	
	private ConnectionHandler connectionHandler;
	
	public LoginHandler(String Username, String Password) {
		
		this.Password = Password;
		this.Username = Username;
		
		hashPassword();
		
	}
	
	public boolean register() {
		
		String serverResponse = "";
		
		JSONObject params = new JSONObject();
        params.put("action", "register");
        params.put("username", Username);
        params.put("password", Password);
        
        try {
            // Verbindung zum Server herstellen
            Socket socket = new Socket("localhost", 8081);
            System.out.println("Verbindung zum Server hergestellt.");

            // Ausgabestream zum Server erstellen
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Eingabestream vom Server erhalten
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Nachricht an den Server senden
            writer.println(params.toJSONString());
            System.out.println("Nachricht an den Server gesendet: " + params.toJSONString());

            // Antwort vom Server empfangen und anzeigen
            serverResponse = reader.readLine();
            System.out.println("Antwort vom Server: " + serverResponse);

            // Ressourcen freigeben
            writer.close();
            reader.close();
            socket.close();
            System.out.println("Verbindung geschlossen.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (serverResponse.equals("true")) {
        	return true;
        }
        else {
        	return false;
        }
		
	}
	
	public User login() {
		
		connectionHandler = new ConnectionHandler();
		
		JSONObject params = new JSONObject();
        params.put("username", Username);
        params.put("password", Password);
        params.put("action", "login");
        
        JSONObject user = connectionHandler.loginToServer(params);  
		
		return createUser(user);
	}
	
	private void hashPassword() {
		
	}
	
	private User createUser(JSONObject params) {
		
		User user = new User((String)params.get("username"),Integer.parseInt((String)params.get("siege")),Integer.parseInt((String)params.get("niederlagen")),Integer.parseInt((String)params.get("unentschieden")));
		
		return user;
	}
	
	public void startChatListener(TextArea taLobbyChat) {
		connectionHandler.startChatListener(taLobbyChat);
		
	}

	public void broadcastMessage(String message) {
		
		JSONObject params = new JSONObject();
        params.put("username", Username);
        params.put("password", Password);
        params.put("message", message);
        params.put("action", "sendMessage");
		
		connectionHandler.broadcastMessage(params);
	}
	


}
