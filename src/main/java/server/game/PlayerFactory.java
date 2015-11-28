package server.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by maciek on 28.11.15.
 */
public class PlayerFactory {
    public IPlayer create(Socket socket) throws IOException {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        return new Player(inputStream, outputStream);
    }
}
