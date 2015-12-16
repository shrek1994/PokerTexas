package messages;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by maciek on 06.12.15.
 */
public class ReceiverMsg {
    private ObjectInputStream inputStream;

    public ReceiverMsg(ObjectInputStream inputStream) throws IOException {
        this.inputStream = inputStream;
    }

    public Object receiveMsg() throws ClassNotFoundException, IOException {
        Object msg = inputStream.readObject();
        return msg;
    }
}
