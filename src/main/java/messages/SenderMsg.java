package messages;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Created by maciek on 06.12.15.
 */
public class SenderMsg {
    private final static Logger logger = Logger.getLogger(SenderMsg.class.getName());
    private ObjectOutputStream outputStream;

    public SenderMsg(ObjectOutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
    }

    public void sendMsg(Object msg) throws IOException {
        logger.info("Sended msg: " + msg.getClass().getName());
        outputStream.writeObject(msg);
    }
}
