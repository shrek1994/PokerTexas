package messages;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by maciek on 06.12.15.
 */
public class SenderMsg {
    private ObjectOutputStream outputStream;

    public SenderMsg(ObjectOutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
    }

    public void sendMsg(Object msg) throws IOException {
        outputStream.writeObject(msg);
    }
}
