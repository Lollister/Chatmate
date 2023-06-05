package gameserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketThread extends Thread {
	
	private int port;
    private Class<? extends Thread> handlerClass;

    public ServerSocketThread(int port, Class<? extends Thread> handlerClass) {
        this.port = port;
        this.handlerClass = handlerClass;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server gestartet auf Port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neue Verbindung hergestellt auf Port: " + port);

                // Erzeuge eine Instanz des entsprechenden Handlers und starte ihn in einem Thread
                Thread handlerThread = handlerClass.getDeclaredConstructor(Socket.class).newInstance(clientSocket); 
                handlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
