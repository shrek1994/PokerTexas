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
        SenderMsg senderMsg = new SenderMsg(socket.getOutputStream());
        ReceiverMsg receiverMsg = new ReceiverMsg(socket.getInputStream());
        return new Player(senderMsg, receiverMsg);
    }
}
