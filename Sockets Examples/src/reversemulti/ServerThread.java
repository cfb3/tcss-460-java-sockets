package reversemulti;

import java.io.*;
import java.net.*;
 
/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */
public class ServerThread extends Thread {
    private static int id_count = 0;
    
    private Socket mySocket;
    private int myId;
 
    public ServerThread(Socket socket) {
        mySocket = socket;
        myId = id_count++;
    }
 
    public void run() {
        try {
            InputStream input = mySocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = mySocket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
 
            String text;
 
            do {
                text = reader.readLine();
                String reverseText = new StringBuilder(text).reverse().toString();
                writer.println("Server: " + reverseText);
                System.out.println("Server " + myId +": " + reverseText);
 
            } while (!text.equals("bye"));
 
            mySocket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
