package reverse;
import java.io.*;
import java.net.*;
 
/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is single-threaded.
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
                try (Socket socket = serverSocket.accept()) {
                    //BLOCKING call
                    handleSocketConnection(socket);
                    socket.close();
                }
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private static void handleSocketConnection(final Socket theSocket) {
        System.out.println("New client connected");
        
        /*
         * OK to use try with resources here. Its ok to close all open streams when the user
         * exits the loop. 
         */
        try (InputStream input = theSocket.getInputStream()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                try (OutputStream output = theSocket.getOutputStream()) {
                    try (PrintWriter writer = new PrintWriter(output, true)) {
                        String text;
                        do {
                            //blocking call, waiting for client to send me someting to revers
                            
                            text = reader.readLine();
                            if (text != null) {
                                final String reverseText = 
                                                new StringBuilder(text)
                                                    .reverse()
                                                    .toString();
                                writer.println("Server: " + reverseText);
                            }
                
                        } while (!"bye".equals(text));
                    }
                }
            }
        } catch (IOException theException) {
            theException.printStackTrace();
        }
    }
}