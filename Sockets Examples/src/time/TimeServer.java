package time;
import java.io.*;
import java.net.*;
import java.util.Date;
 
/**
 * This program demonstrates a simple TCP/IP socket server.
 *
 * @author www.codejava.net
 * @author Charles Bryan
 * @version Slight Edits from original
 */
public class TimeServer {
 
    public static final int DEFAULT_PORT = 33_333;
    
    public static void main(final String[] args) {
 
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
 
            System.out.println("Server is listening on port " + DEFAULT_PORT);
 
            while (!serverSocket.isClosed()) {
                
                /*
                 * OK to use try with resources here. We do not need to keep the socket open
                 * once the connection has been made and time is sent. 
                 */
                //BLOCKING call! Will wait until a client connects. 
                try (final Socket socket = serverSocket.accept()) {
                    System.out.println("New client connected");
                    try (final OutputStream output = socket.getOutputStream()) {
                        try (final PrintWriter writer = new PrintWriter(output, true)) {
                            writer.println(new Date().toString());
                        }
                    }
                }

            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}