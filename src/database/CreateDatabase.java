package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {

	public static void main(String[] args) {
			
		createDB();
		createTables();
		createSampleUser();

	}
	
	private static void createSampleUser() {
		String url = "jdbc:mysql://localhost:3306/Chatmate";
        String username = "";
        String password = "";

        try {
            // Treiber registrieren
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verbindung herstellen
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String createTableQuery = "INSERT INTO User (Username, Password, Siege, Unentschieden, Niederlagen) VALUES ('max','1234',12,200,2)";
            
            // Statement erstellen
            Statement statement = connection.createStatement();
            
            // Datenbank erstellen
            statement.executeUpdate(createTableQuery); 
            System.out.println("User erfolgreich erstellt.");
            
            
            // Verbindung schlieﬂen
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	private static void createDB() {
		
		String url = "jdbc:mysql://localhost:3306/";
        String username = "";
        String password = "";

        try {
            // Treiber registrieren
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verbindung herstellen
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String createDatabaseQuery = "CREATE DATABASE Chatmate";
            
            // Statement erstellen
            Statement statement = connection.createStatement();
            
            // Datenbank erstellen
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Datenbank erfolgreich erstellt.");
            
            
            // Verbindung schlieﬂen
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}
	
	private static void createTables() {
		
		String url = "jdbc:mysql://localhost:3306/Chatmate";
        String username = "";
        String password = "";

        try {
            // Treiber registrieren
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verbindung herstellen
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String createTableQuery = "CREATE TABLE User ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "Username VARCHAR(100) NOT NULL,"
                    + "Password VARCHAR(100) NOT NULL,"
                    + "Siege int default 0,"
                    + "Niederlagen int default 0,"
                    + "Unentschieden int default 0)";
            
            // Statement erstellen
            Statement statement = connection.createStatement();
            
            // Datenbank erstellen
            statement.executeUpdate(createTableQuery); 
            System.out.println("Tabellen erfolgreich erstellt.");
            
            
            // Verbindung schlieﬂen
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

}
