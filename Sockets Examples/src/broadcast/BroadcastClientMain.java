package broadcast;

import java.io.*;
 

public class BroadcastClientMain {
 
    public static void main(final String args[]) {
        
        final BroadcastClient client = new BroadcastClient();
        if (client.connect()) {
            
            final Console console = System.console();
            String text;
            
            text = console.readLine("Enter your name: ");
            client.sendMessage(text);
            
            System.out.println("Enter text to chat");
            do {
                text = console.readLine();
                client.sendMessage(text);
            } while (!"bye".equals(text) && !client.isClosed());
            
            client.disconnect();
        } else {
            System.out.println("Error connecting to the server. Contact your SysAdmin.");
        }
    }
}