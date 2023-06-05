package gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RegisterHandler extends Thread {
	
	private Socket registerSocket;
	private PrintWriter writer;
	private BufferedReader reader;

	public RegisterHandler(Socket socket) {
		this.registerSocket = socket;
	}
	
	public void run() {
		try {
			// Eingabestream vom Client erhalten
			reader = new BufferedReader(new InputStreamReader(registerSocket.getInputStream()));
			
			// Ausgabestream zum Client erstellen
			writer = new PrintWriter(registerSocket.getOutputStream(), true);
			
			// Nachricht vom Client empfangen und anzeigen
			String clientMessage;
			while ((clientMessage = reader.readLine()) != null) {
				
				JSONParser parser = new JSONParser();
				JSONObject params = null;
				try {
					params = (JSONObject) parser.parse(clientMessage);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				writer.println(readJSON(params));
			}	
			 		 
			 // Ressourcen freigeben
			 reader.close();
			 writer.close();
			 registerSocket.close();
			 System.out.println("Verbindung geschlossen.");
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 private boolean readJSON(JSONObject params) {
		 
		 if (params.get("action").equals("register")) {
			 return register((String)params.get("username"), (String)params.get("password"));
		 }	
		 
		 return false;
	 }
	 
	 private boolean register(String ip_username, String ip_password) {
		 
		 	boolean success = false;
		 
		 	String url = "jdbc:mysql://localhost:3306/Chatmate";
	        String username = "";
	        String password = "";

	        try {
	            // Treiber registrieren
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            
	            // Verbindung herstellen
	            Connection connection = DriverManager.getConnection(url, username, password);
	            
	            String createTableQuery = "INSERT INTO User (Username, Password) VALUES ('" + ip_username + "','" + ip_password + "')";
	            
	            // Statement erstellen
	            Statement statement = connection.createStatement();
	            
	            // Datenbank erstellen
	            statement.executeUpdate(createTableQuery); 
	            System.out.println("User erfolgreich erstellt.");
	            success = true;
	            
	            
	            // Verbindung schlieﬂen
	            connection.close();
	            
	            
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return success;
	 }

}