package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.control.TextArea;

public class ConnectionHandler {
	
	private PrintWriter writer;
	
	private BufferedReader reader;
	
	private Socket socket;
	
	public ConnectionHandler() {
		openConnection();	
	}
	
	public void finalize() {
		try {
			writer.close();
	        reader.close();
	        socket.close();
		} catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}
		
	public void openConnection() {
		
		try {
			// Verbindung zum Server herstellen
	        this.socket = new Socket("localhost", 8080);
			
			// Ausgabestream zum Server erstellen
	        this.writer = new PrintWriter(socket.getOutputStream(), true);
	
	        // Eingabestream vom Server erhalten
	        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}
	
	public JSONObject loginToServer(JSONObject params) {
		JSONParser parser = new JSONParser();
        JSONObject serverResponseJSON = null;
				
		try {	
	        // Nachricht an den Server senden
            writer.println(params.toJSONString());
	
	        // Antwort vom Server empfangen und anzeigen
            String serverResponse = reader.readLine();
            System.out.println("Antwort vom Server: " + serverResponse);
            
            
            try {
            	serverResponseJSON = (JSONObject) parser.parse(serverResponse);
            } catch (ParseException e) {
            	e.printStackTrace();
            }
                   
	        
	        System.out.println("Verbindung geschlossen.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return serverResponseJSON;		
	}
	
	public void startChatListener(TextArea taLobbyChat) {
		new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                	taLobbyChat.appendText(message + "\n");
                	System.out.println("Listener hat nachricht empfangen:" + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
	}
	
	public void broadcastMessage(JSONObject message) {
		JSONParser parser = new JSONParser();
        JSONObject serverResponseJSON = null;
        
        // Nachricht an den Server senden
        writer.println(message.toJSONString());
        System.out.println("Anfrage abgeschickt");
		
	}
}
