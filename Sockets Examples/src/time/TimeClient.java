package time;

import static util.NetworkUtilities.getLocalHost;
import java.net.*;
import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client.
 *
 * @author www.codejava.net
 * @author Charles Bryan
 * @version Slight Edits from original
 */
public class TimeClient {
 
    public static void main(final String[] args) {
         try (Socket socket = new Socket(getLocalHost(), TimeServer.DEFAULT_PORT)) {
             try (InputStream input = socket.getInputStream()) {
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
     
                    //BLOCKING call! Will wait until server sends a response
                    System.out.println(reader.readLine());
                    
                }
            }
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}