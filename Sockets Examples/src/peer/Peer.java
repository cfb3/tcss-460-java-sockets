package peer;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Peer {
    
    private Socket mySocket;
    
    public Peer(final Socket theSocket) {
        mySocket = theSocket;
    }
    
    public void start() {
        new Thread(this::recieve).start();
        new Thread(this::send).start();
    }
    
    private void send() {
        try (PrintWriter writer = new PrintWriter(mySocket.getOutputStream(), true)) {
            final Console console = System.console();
            String text;
            
            System.out.println("Enter text to chat");
            do {
                text = console.readLine();
                writer.println(text);
            } while (!"bye".equals(text) && !mySocket.isClosed());
            mySocket.close();
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
    
    private void recieve() {
        /*
         * OK to use try with resources here. We will not leave the try block until it is
         * OK to close the resources. 
         */
        try (final InputStream input = mySocket.getInputStream()) {
            try (final BufferedReader reader =
                            new BufferedReader(new InputStreamReader(input))) {
                String message;
                do {
                    message = reader.readLine();
                    System.out.println("message recieved: " + message);
                }
                while (!mySocket.isClosed() && message != null);
                System.out.println("Peer Diconnected. Type [enter/return] to exit.");
            }
        } catch (IOException e) {
            System.out.println("Socket closed. Exiting application.");
        }
    }
}
