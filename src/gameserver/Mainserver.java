package gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.stream.events.StartDocument;

public class Mainserver {

	public static void main(String[] args) {
		// Erstelle und starte den Thread für den ClientHandler auf Port 8080
		ServerSocketThread clientHandlerThread = new ServerSocketThread(8080, ClientHandler.class);
		clientHandlerThread.start();

		// Erstelle und starte den Thread für den RegisterHandler auf Port 8081
		ServerSocketThread registerHandlerThread = new ServerSocketThread(8081, RegisterHandler.class);
		registerHandlerThread.start();
    }

}
