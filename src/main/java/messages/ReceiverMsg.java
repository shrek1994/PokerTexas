package messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

/**
 * Created by maciek on 06.12.15.
 */
public class ReceiverMsg {
    private final static Logger logger = Logger.getLogger(ReceiverMsg.class.getName());
    private ObjectInputStream inputStream;

    public ReceiverMsg(ObjectInputStream inputStream) throws IOException {
        this.inputStream = inputStream;
    }

    public Object receiveMsg() throws ClassNotFoundException, IOException {
        Object msg = inputStream.readObject();
        logger.info("Received msg: " + msg.getClass().getName());
        return msg;
    }
}
