package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropDatabase {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/";
        String username = "";
        String password = "";

        try {
            // Treiber registrieren
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verbindung herstellen
            Connection connection = DriverManager.getConnection(url, username, password);
            
            String createDatabaseQuery = "DROP DATABASE Chatmate";
            
            // Statement erstellen
            Statement statement = connection.createStatement();
            
            // Datenbank erstellen
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Datenbank erfolgreich gelöscht.");
            
            
            // Verbindung schließen
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
