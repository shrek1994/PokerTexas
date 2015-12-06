package server.game;

import messages.ReceiverMsg;
import messages.SenderMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by maciek on 28.11.15.
 */
public class PlayerFactory {
    public IPlayer create(Socket socket) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        SenderMsg senderMsg = new SenderMsg(objectOutputStream);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ReceiverMsg receiverMsg = new ReceiverMsg(objectInputStream);

        return new Player(senderMsg, receiverMsg);
    }
}
