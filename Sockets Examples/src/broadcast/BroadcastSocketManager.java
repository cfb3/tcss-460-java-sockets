package broadcast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadcastSocketManager {

    private final List<Socket> mySockets;
    private final Map<Socket, PrintWriter> myWriters;
    
    public BroadcastSocketManager() {
        super();
        mySockets = new ArrayList<>();
        myWriters = new HashMap<>();
    }
    
    public synchronized void addSocket(final Socket theSocket) throws IOException {
        if (mySockets.contains(theSocket)) {
            throw new IllegalArgumentException("I already manage this Socket");
        }
        mySockets.add(theSocket);
        myWriters.put(theSocket, new PrintWriter(theSocket.getOutputStream(), true));
    }
    
    public synchronized void removeSocket(final Socket theSocket) {
        mySockets.remove(theSocket);
        myWriters.remove(theSocket);
    }
    
    public synchronized void broadcastMessage(final String theMessage ) {
        myWriters.entrySet().stream()
            .filter(s -> !s.getKey().isClosed())
            .forEach(set -> set.getValue().println(theMessage));
    }
}
