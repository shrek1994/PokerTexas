package messages;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by maciek on 06.12.15.
 */
public class SenderMsg {
    private ObjectOutputStream outputStream;

    public SenderMsg(OutputStream outputStream) throws IOException {
        this.outputStream = new ObjectOutputStream(outputStream);
    }

    public void sendMsg(Object msg) throws IOException {
        outputStream.writeObject(msg);
    }
}
