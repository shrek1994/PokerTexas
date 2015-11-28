package server.game;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by maciek on 28.11.15.
 */
public class ServerSocketFactory {
    public ServerSocket create(int port) throws IOException {
        return new ServerSocket(port);
    }
}
