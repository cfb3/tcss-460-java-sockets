package peer;

import java.io.*;
import java.net.*;

import util.NetworkUtilities;
 
/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is multi-threaded.
 *
 * @author www.codejava.net
 */
public class PeerToPeerChat {
    
    private static final int PORT = 33_333;
    
 
    public static void main(String[] args) {
        System.out.println("Choose wisely:");
        System.out.println("1 to start a connection:");
        System.out.println("2 to make a connection:");
        
        String response = System.console().readLine();
        if ("1".equals(response)) {
            waitForPeer();
        } else if ("2".equals(response)) {
            connectToPeer();
        } else {
            System.out.println("Try again later.");
        }

    }
    
    public static void waitForPeer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            
            System.out.println("Waiting for peer on port: " + PORT);
 
            Socket socket = serverSocket.accept();
            System.out.println("Peer connected");
            Peer peer = new Peer(socket);
            peer.start();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void connectToPeer() {
        
        try {
            
            Peer peer = new Peer(new Socket(NetworkUtilities.getLocalHost(), PORT));
            peer.start();
            
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}