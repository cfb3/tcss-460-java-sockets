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
    
    private final Socket mySocket;
    private final int myId;
 
    public ServerThread(final Socket socket) {
        super();
        mySocket = socket;
        myId = id_count++;
    }
 
    public void run() {
        /*
         * OK to use try with resources here. Its OK to close all open streams when the user
         * exits the loop. 
         */
        try (InputStream input = mySocket.getInputStream()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                try (OutputStream output = mySocket.getOutputStream()) {
                    try (PrintWriter writer = new PrintWriter(output, true)) {
                        String text;
 
                        do {
                            text = reader.readLine();
                            if (text != null) {
                                final String reverseText = 
                                                new StringBuilder(text)
                                                    .reverse()
                                                    .toString();
                                writer.println("Server: " + reverseText);
                                System.out.println("Server " + myId +": " + reverseText);

                            }
                                  
                        } while (!"bye".equals(text));
                        mySocket.close();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
