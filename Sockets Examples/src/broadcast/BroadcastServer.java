package broadcast;

import java.io.*;
import java.net.*;
 
/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is multi-threaded.
 */
public class BroadcastServer {
    
    public static final int DEFAULT_PORT = 33_333;
 
    public static void main(final String[] args) {
        
        final BroadcastSocketManager manager = new BroadcastSocketManager();
 
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
 
            System.out.println("Server is listening on port " + DEFAULT_PORT);
 
            while (!serverSocket.isClosed()) {
                //This NOPMD is OK since the Socket is closed somewhere else
                final Socket socket = serverSocket.accept(); //NOPMD
                System.out.println("New client connected");
 
                try {
                    manager.addSocket(socket);
                    new BroadcastServerThread(manager, socket).start();
                } catch (Exception e) {
                    System.err.println("Error addign socket to SocketManager.");
                    socket.close();
                }
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}