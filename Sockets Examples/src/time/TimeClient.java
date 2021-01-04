package time;
import java.net.*;
import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client.
 *
 * @author www.codejava.net
 */
public class TimeClient {
 
    public static void main(String[] args) {
 
        String hostname = "local";
        
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
            hostname = localhost.getHostAddress();
        } catch (UnknownHostException e) {
            
            e.printStackTrace();
            System.exit(0);
        }

        int port = 33_333;
 
        try (Socket socket = new Socket(hostname, port)) {
 
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            //BLOCKING call! Will wait until server sends a response
            String time = reader.readLine();
 
            System.out.println(time);
 
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}