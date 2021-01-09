package broadcast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class BroadcastSocketManager {

    private final Map<Socket, PrintWriter> myWriters;
    
    public BroadcastSocketManager() {
        super();
        myWriters = new HashMap<>();
    }
    
    public synchronized void addSocket(final Socket theSocket) throws IOException {
        if (myWriters.keySet().contains(theSocket)) {
            throw new IllegalArgumentException("I already manage this Socket");
        }
        myWriters.put(theSocket, new PrintWriter(theSocket.getOutputStream(), true));
    }
    
    public synchronized void removeSocket(final Socket theSocket) {
        myWriters.remove(theSocket);
    }
    
    public synchronized void broadcastMessage(final String theMessage ) {
        myWriters.entrySet().stream()
            .filter(s -> !s.getKey().isClosed())
            .forEach(set -> set.getValue().println(theMessage));
    }
    
    public synchronized void sendMessage(final String theMessage, final Socket theDestination) {
        myWriters.get(theDestination).println(theMessage);
    }
}
