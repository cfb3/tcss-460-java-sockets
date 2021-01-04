package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtilities {

    
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
    
}
