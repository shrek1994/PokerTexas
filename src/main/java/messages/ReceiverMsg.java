package messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by maciek on 06.12.15.
 */
public class ReceiverMsg {
    private ObjectInputStream inputStream;

    public ReceiverMsg(InputStream inputStream) throws IOException {
        this.inputStream = new ObjectInputStream(inputStream);
    }

    public Object receiveMsg() throws ClassNotFoundException, IOException {
        return inputStream.readObject();
    }
}
