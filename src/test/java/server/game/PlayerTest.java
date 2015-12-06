package server.game;

import messages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerTest {
    private SenderMsg senderMsg = mock(SenderMsg.class);
    private ReceiverMsg receiverMsg = mock(ReceiverMsg.class);
    private Player sut;

    private final ActionMsg correctAction = new ActionMsg(ActionType.Bet, 1234);
    private final ActionMsg defaultActionMsg = new ActionMsg(ActionType.Fold, 0);
    private final NotifyAboutActionMsg notifyAboutActionMsg = new NotifyAboutActionMsg();

    @Before
    public void setUp() throws Exception {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);

        sut = new Player(senderMsg, receiverMsg);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(senderMsg);
        verifyNoMoreInteractions(receiverMsg);
    }


    @Test
    public void shouldCorrectReturnActionWhenCorrectGetResponse() throws Exception {
        when(receiverMsg.receiveMsg()).thenReturn(correctAction);

        ActionMsg action = sut.getAction();

        assertEquals(correctAction, action);

        verify(senderMsg).sendMsg(notifyAboutActionMsg);
        verify(receiverMsg).receiveMsg();
    }

    @Test
    public void shouldReturnDefaultActionWhenSendMsgThrowIOException() throws Exception {
        doThrow(new IOException("")).when(senderMsg).sendMsg(notifyAboutActionMsg);

        ActionMsg action = sut.getAction();

        assertEquals(defaultActionMsg, action);
        verify(senderMsg).sendMsg(notifyAboutActionMsg);
    }

    @Test
    public void shouldReturnDefaultActionWhenReceiveMsgThrowIOException() throws Exception {
        when(receiverMsg.receiveMsg()).thenThrow(new IOException(""));

        ActionMsg action = sut.getAction();

        assertEquals(defaultActionMsg, action);
        verify(senderMsg).sendMsg(notifyAboutActionMsg);
        verify(receiverMsg).receiveMsg();
    }

    @Test
    public void shouldReturnDefaultActionWhenReceiveWrongMsg() throws Exception {
        when(receiverMsg.receiveMsg()).thenReturn(new Object());

        ActionMsg action = sut.getAction();

        assertEquals(defaultActionMsg, action);
        verify(senderMsg).sendMsg(notifyAboutActionMsg);
        verify(receiverMsg).receiveMsg();
    }

    //TODO comment if working at another class - test duration - about 35 second
    @Test
    public void shouldReturnDefaultActionWhenNothingReceive_WARNING_TEST_DURATION_35_SECOND() throws Exception {
        sut = new Player(senderMsg, new NotReceiveMsg());

        ActionMsg action = sut.getAction();

        assertEquals(defaultActionMsg, action);
        verify(senderMsg).sendMsg(notifyAboutActionMsg);
    }

    //TODO comment if working at another class - test duration - about 70 second
    @Test
    public void shouldReturnTwiceDefaultActionWhenNothingReceive_WARNING_TEST_DURATION_70_SECOND() throws Exception {
        sut = new Player(senderMsg, new NotReceiveMsg());

        ActionMsg firstAction = sut.getAction();
        ActionMsg secondAction = sut.getAction();

        assertEquals(defaultActionMsg, firstAction);
        assertEquals(defaultActionMsg, secondAction);

        verify(senderMsg, times(2)).sendMsg(notifyAboutActionMsg);
    }

    private class NotReceiveMsg extends ReceiverMsg
    {
        public NotReceiveMsg() throws IOException {
            super(null);
        }

        @Override
        public Object receiveMsg() throws ClassNotFoundException, IOException {
            try {
                Thread.sleep(Player.milisecondWaitForActionPlayer + 2 * 1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }
}