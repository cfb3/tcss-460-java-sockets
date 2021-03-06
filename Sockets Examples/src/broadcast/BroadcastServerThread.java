
package broadcast;

import java.io.*;
import java.net.*;

/**
 * This thread is responsible to handle client connection.
 *
 */
public class BroadcastServerThread extends Thread {

    private final Socket mySocket;
    private final BroadcastSocketManager myManager;

    public BroadcastServerThread(final BroadcastSocketManager theManager,
                                 final Socket socket) {
        super();
        mySocket = socket;
        myManager = theManager;
    }

    public void run() {
        try {
            // This NOPMD is OK because there is a PMD Bug. We DO close the resources.
            final InputStream input = mySocket.getInputStream(); // NOPMD
            final BufferedReader reader = // NOPMD
                            new BufferedReader( // NOPMD
                                               new InputStreamReader(input)); // NOPMD

            String text;

            final String name = reader.readLine();
            myManager.broadcastMessage(name + " has entered the chat.");
            System.out.println(name + " has entered the chat.");

            do {
                text = reader.readLine();
                if (text != null) {
                    myManager.broadcastMessage("Broadcast from " + name + ": " + text);
                    System.out.println("Client " + name + ": " + text);
                }

            }
            while (!"bye".equals(text) && text != null);

            myManager.broadcastMessage(name + " is leaving the chat.");
            System.out.println(name + " is leaving the chat.");
            myManager.removeSocket(mySocket);

            mySocket.close();
            reader.close();
            input.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
