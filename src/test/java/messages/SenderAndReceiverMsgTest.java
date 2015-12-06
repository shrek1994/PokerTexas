package messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

import static org.junit.Assert.*;

public class SenderAndReceiverMsgTest {
    private final int port = 1234;
    private ServerSocket serverSocket;
    private Socket socket;
    private SenderMsg senderMsg;
    private ReceiverMsg receiverMsg;

    private final ActionType actionType = ActionType.AllIn;
    private final double money = 123.45;
    private final ActionMsg sentMsg = new ActionMsg(actionType, money);

    private final int blindValue = 12;

    private Thread server = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Socket s = serverSocket.accept();
                senderMsg = new SenderMsg(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    @Before
    public void Setup() throws IOException, InterruptedException {
        serverSocket = new ServerSocket(port);

        server.start();
        socket = new Socket("localhost", port);
        server.join();

        receiverMsg = new ReceiverMsg(socket.getInputStream());
    }

    @After
    public void After() throws IOException {
        socket.close();
        serverSocket.close();

    }

    @Test
    public void shouldCorrectSendAndReceiveActionMsg() throws Exception {

        senderMsg.sendMsg(sentMsg);

        Object msg = receiverMsg.receiveMsg();

        assertTrue(msg instanceof ActionMsg);

        ActionMsg receivedMsg = (ActionMsg) msg;

        assertEquals(sentMsg.getAction(), receivedMsg.getAction());
        assertEquals(sentMsg.getMoney(), receivedMsg.getMoney(),0.000001);

        assertEquals(sentMsg, receivedMsg);
    }

    @Test
    public void shouldCorrectSendAndReceiveBlindMsg() throws Exception {
        BlindMsg sentMsg = new BlindMsg(blindValue);
        senderMsg.sendMsg(sentMsg);

        Object o = receiverMsg.receiveMsg();

        assertTrue(o instanceof BlindMsg);

        BlindMsg receivedMsg = (BlindMsg) o;

        assertEquals(sentMsg.getValue(), receivedMsg.getValue());

        assertEquals(sentMsg, receivedMsg);
    }
}