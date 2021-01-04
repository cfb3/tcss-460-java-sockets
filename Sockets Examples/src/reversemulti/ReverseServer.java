package reversemulti;

import java.io.*;
import java.net.*;
 
/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is multi-threaded.
 *
 * @author www.codejava.net
 * @author Charles Bryan
 * @version Edited from original
 */
public class ReverseServer {
 
    public static final int DEFAULT_PORT = 33_333;
    
    public static void main(final String[] args) {
        /*
         * OK to use try with resources here. We do not need to keep the server socket open
         * once the connection has been made.  
         */
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
 
            System.out.println("Server is listening on port " + DEFAULT_PORT);
 
            while (!serverSocket.isClosed()) {
                //This NOPMD is OK since the Socket is closed somewhere else
                final Socket socket = serverSocket.accept(); //NOPMD
                System.out.println("New client connected");
 
                new ServerThread(socket).start();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}