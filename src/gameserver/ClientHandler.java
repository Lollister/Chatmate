package gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientHandler extends Thread{
	
	public static List<ClientHandler> clients = new ArrayList<>();
	
	 private Socket clientSocket;
	 private PrintWriter writer;
	 private BufferedReader reader;

	 public ClientHandler(Socket socket) {
		 this.clientSocket = socket;
	 }
	 
	 public void run() {
		 try {
			 // Eingabestream vom Client erhalten
			 reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 
			 // Ausgabestream zum Client erstellen
			 writer = new PrintWriter(clientSocket.getOutputStream(), true);
			 
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
				 
				 System.out.println(params);
				 
				 // Anmeldeinformationen überprüfen
				 String username = (String) params.get("username");
				 String password = (String) params.get("password");
				 
				 if (isValidCredentials(username, password)) {
					 // Anmeldeinformationen sind gültig					 										 					 
					 String action = (String) params.get("action");
					 if (action.equals("login")) {
						 ClientHandler.clients.add(this);
						 JSONObject serverResponse = returnUserInfo(username, password);
						 writer.println(serverResponse.toJSONString());
						 System.out.println("Antwort an den Client gesendet: " + serverResponse);
					 }
				 } else {
					 // Anmeldeinformationen sind ungültig
					 String serverResponse = "Anmeldung fehlgeschlagen";
					 writer.println(serverResponse);
					 System.out.println("Antwort an den Client gesendet: " + serverResponse);
				 }			 
				 
				 String action = (String) params.get("action");
				 if (action.equals("sendMessage")) {
					 
					 LocalTime time = LocalTime.now();
					 String Chatmessage = time.withNano(0) + " " + (String)params.get("username") + ": " + (String)params.get("message");
					 broadcastMessage(Chatmessage);
					 System.out.println("broadcast successful");
				 }
			 }	
			 		 
			 // Ressourcen freigeben
			 reader.close();
			 writer.close();
			 clientSocket.close();
			 System.out.println("Verbindung geschlossen.");
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 private void broadcastMessage(String message) {
		 for (ClientHandler client : clients) {
			 client.sendMessage(message);
         }
     }

	 private void sendMessage(String message) {
		 writer.println(message);
	 }

	private JSONObject returnUserInfo(String ip_username, String ip_password) {
		
		JSONObject params = new JSONObject();
		 
	 	String url = "jdbc:mysql://localhost:3306/SchachGPT";
        String username = "root";
        String password = "lorenz";

        try {
            // Treiber registrieren
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verbindung herstellen
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String query = "SELECT * FROM user where Username = '" + ip_username + "' and Password = '" + ip_password + "'";
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // SELECT-Abfrage ausführen
            ResultSet resultSet = preparedStatement.executeQuery();  
                 
            if (resultSet.next()) {
            	params.put("access", "granted");
                params.put("username", resultSet.getString("Username"));
                params.put("siege", resultSet.getString("siege"));
                params.put("unentschieden", resultSet.getString("unentschieden"));
                params.put("niederlagen", resultSet.getString("niederlagen"));
            } else {
            	params.put("access", "denied");
            }
            
            // Verbindung schließen
            connection.close();
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return params;
		
	}

	private boolean isValidCredentials(String ip_username, String ip_password) {
		boolean success = false;
		 
	 	String url = "jdbc:mysql://localhost:3306/SchachGPT";
        String username = "root";
        String password = "lorenz";

        try {
            // Treiber registrieren
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verbindung herstellen
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String query = "SELECT * FROM user where Username = '" + ip_username + "' and Password = '" + ip_password + "'";
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // SELECT-Abfrage ausführen
            ResultSet resultSet = preparedStatement.executeQuery();  
            
            if (resultSet.next()) {
                success = true;
            } else {
                success = false;
            }          
            
            // Verbindung schließen
            connection.close();
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
	}
	 

}
