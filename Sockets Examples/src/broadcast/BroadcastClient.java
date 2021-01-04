
package broadcast;

import java.net.*;
import java.io.*;

public class BroadcastClient {

    public static final int DEFAULT_PORT = 33_333;

    private final String myHostname;
    private final int myPort;
    
    private Socket mySocket;
    private OutputStream myOutput;
    private PrintWriter myWriter;

    public BroadcastClient() {
        this(getLocalHost(), DEFAULT_PORT);
    }

    public BroadcastClient(final String theIpAddress, final int thePort) {
        myHostname = theIpAddress;
        myPort = thePort;
    }

    public boolean connect() {
        boolean isConnected = true;
        /*
         * We do NOT use try with resources syntax here. A try with resources will close the
         * resource that was opened when the try block is exited. We need to keep the socket
         * open.
         */
        try { //
            mySocket = new Socket(myHostname, myPort);

            new Thread(this::recieve).start();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());
            isConnected = false;

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
            isConnected = false;
        }
        return isConnected;
    }

    public static String getLocalHost() {
        String localHostName = "local";

        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
            localHostName = localhost.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Client could NOT find localhost ip");
            System.exit(0);
        }
        return localHostName;
    }

    public boolean isClosed() {
        return mySocket.isClosed();
    }

    public void disconnect() {
        try {
            mySocket.close();
            myWriter.close();
            myOutput.close();
        } catch (IOException e) {
            System.err.println("Error closing socket");
        }
    }

    public void sendMessage(final String theMessage) {
        /*
         * We do NOT use try with resources syntax here. A try with resources will close the
         * resource that was opened when the try block is exited. We need to keep the socket
         * open.
         */
        try {
            if (myOutput == null) {
                myOutput = mySocket.getOutputStream();
            }
            if (myWriter == null) {
                myWriter = new PrintWriter(myOutput, true);
            }
            myWriter.println(theMessage);
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    public void recieve() {
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
                    System.out.println(message);
                }
                while (!mySocket.isClosed() && message != null);
                System.out.println("Server Diconnected. Type [enter/return] to exit.");
            }
        } catch (IOException e) {
            System.out.println("Server Diconnected. Type [enter/return] to exit.");
        }
    }
}
