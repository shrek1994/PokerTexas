package server.game;

import cards.Card;
import cards.Color;
import cards.Figure;
import messages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private final Card card = new Card(Figure.Five, Color.Spades);
    private final Card secondCard = new Card(Figure.Eight, Color.Diamonds);
    private final CardMsg cardMsg = new CardMsg(card);
    private final CardMsg secondCardMsg = new CardMsg(secondCard);

    private final int blind = 123;
    private final BlindMsg blindMsg = new BlindMsg(blind);

    private class NotReceiveMsg extends ReceiverMsg
    {
        public NotReceiveMsg() throws IOException {
            super(null);
        }

        @Override
        public Object receiveMsg() throws ClassNotFoundException, IOException {
            try {
                Thread.sleep(Player.millisecondsWaitForActionPlayer + 2 * 1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }

    @Before
    public void setUp() throws Exception {
        turnOffLogger();

        sut = new Player(senderMsg, receiverMsg);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(senderMsg);
        verifyNoMoreInteractions(receiverMsg);
    }

    private void turnOffLogger() {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
    }

    /***********************************GET*ACTION*TESTS****************************************************/

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

    //TODO comment test if you're working at another class - test duration - about 35 second
    //@Test(timeout = 35*1000)
    public void shouldReturnDefaultActionWhenNothingReceive_WARNING_TEST_DURATION_35_SECOND() throws Exception {
        sut = new Player(senderMsg, new NotReceiveMsg());

        ActionMsg action = sut.getAction();

        assertEquals(defaultActionMsg, action);
        verify(senderMsg).sendMsg(notifyAboutActionMsg);
    }

    //TODO comment test if you're working at another class - test duration - about 70 second
    //@Test(timeout = 70*1000)
    public void shouldReturnTwiceDefaultActionWhenNothingReceive_WARNING_TEST_DURATION_70_SECOND() throws Exception {
        sut = new Player(senderMsg, new NotReceiveMsg());

        ActionMsg firstAction = sut.getAction();
        ActionMsg secondAction = sut.getAction();

        assertEquals(defaultActionMsg, firstAction);
        assertEquals(defaultActionMsg, secondAction);

        verify(senderMsg, times(2)).sendMsg(notifyAboutActionMsg);
    }

    /***********************************ADD*CARDS*TESTS************************************************/

    @Test
    public void shouldCorrectAddOneCard() throws Exception {
        int numberOfAddedCard = 1;

        sut.addCard(card);

        assertEquals(numberOfAddedCard, sut.getCardList().size());
        assertEquals(card, sut.getCardList().get(0));

        verify(senderMsg).sendMsg(cardMsg);
    }

    @Test
    public void shouldCorrectAddTwoCards() throws Exception {
        int numberOfAddedCard = 2;

        sut.addCard(card);
        sut.addCard(secondCard);

        assertEquals(numberOfAddedCard, sut.getCardList().size());
        assertEquals(card, sut.getCardList().get(0));
        assertEquals(secondCard, sut.getCardList().get(1));

        verify(senderMsg).sendMsg(cardMsg);
        verify(senderMsg).sendMsg(secondCardMsg);
    }

    @Test
    public void shouldDoNothingWhenSendMsgThrowIOException() throws Exception {
        int sizeOfEmptyList = 0;

        doThrow(new IOException("")).when(senderMsg).sendMsg(cardMsg);

        sut.addCard(card);

        assertEquals(sizeOfEmptyList, sut.getCardList().size());

        verify(senderMsg).sendMsg(cardMsg);
    }

    @Test
    public void shouldCorrectAddTwoCardsOneAfterClearCard() throws Exception {
        int expectedNumberOfCardInPlayer = 1;

        sut.addCard(card);
        sut.clearCards();
        sut.addCard(secondCard);

        assertEquals(expectedNumberOfCardInPlayer, sut.getCardList().size());
        assertEquals(secondCard, sut.getCardList().get(0));

        verify(senderMsg).sendMsg(cardMsg);
        verify(senderMsg).sendMsg(secondCardMsg);
    }


    /***********************************GET*BLIND*TESTS************************************************/

    @Test
    public void shouldCorrectGetBlind() throws Exception {
        when(receiverMsg.receiveMsg()).thenReturn(blindMsg);

        assertEquals(blind, sut.getBlind(blind));

        verify(senderMsg).sendMsg(blindMsg);
        verify(receiverMsg).receiveMsg();
    }

    @Test
    public void shouldReturnZeroBlindWhenCannotSendMsg() throws Exception {
        int zeroBlind = 0;

        doThrow(new IOException("")).when(senderMsg).sendMsg(blindMsg);

        assertEquals(zeroBlind, sut.getBlind(blind));

        verify(senderMsg).sendMsg(blindMsg);
    }

    @Test
    public void shouldReturnZeroBlindWhenCannotReceiveMsg() throws Exception {
        int zeroBlind = 0;

        when(receiverMsg.receiveMsg()).thenThrow(new IOException(""));

        assertEquals(zeroBlind, sut.getBlind(blind));

        verify(senderMsg).sendMsg(blindMsg);
        verify(receiverMsg).receiveMsg();
    }

    @Test
    public void shouldReturnZeroBlindWhenReceiveWrongMsg() throws Exception {
        int zeroBlind = 0;

        when(receiverMsg.receiveMsg()).thenReturn(defaultActionMsg);

        assertEquals(zeroBlind, sut.getBlind(blind));

        verify(senderMsg).sendMsg(blindMsg);
        verify(receiverMsg).receiveMsg();
    }

    /*****************************GET*ID***************************************/

    @Test
    public void shouldReturnDifferentIdsPlayers()
    {
        int maxPlayers = 8;
        List<Player> playerList = new ArrayList<Player>();
        for ( int i = 0 ; i < maxPlayers ; i++)
        {
            playerList.add(new Player(senderMsg, receiverMsg));
        }

        assertEquals(maxPlayers, playerList.size());

        int id =  playerList.get(0).getId();
        for ( int i = 0 ; i < maxPlayers ; i++ )
        {
            assertEquals(id, playerList.get(i).getId() );
            id++;
        }
    }

    /****************************UPDATE********************************/

    @Test
    public void shouldCorrectNotifyAboutUpdate() throws Exception {
        sut.update(null, defaultActionMsg);

        verify(senderMsg).sendMsg(defaultActionMsg);
    }

    @Test
    public void shouldDoNothingWhenCannotSendMsg() throws Exception {
        doThrow(new IOException("")).when(senderMsg).sendMsg(defaultActionMsg);

        sut.update(null, defaultActionMsg);

        verify(senderMsg).sendMsg(defaultActionMsg);
    }


    /*****************************IS*PLAY*ON**************************************/

    @Test
    public void shouldPlayOnWhenInMsgIsPlayOn() throws IOException, ClassNotFoundException {
        boolean isPlayOn = true;
        InfoAboutContinuingGameMsg msg = new InfoAboutContinuingGameMsg(isPlayOn);

        when(receiverMsg.receiveMsg()).thenReturn(msg);

        assertTrue(sut.isPlayOn());

        verify(receiverMsg).receiveMsg();
    }


    @Test
    public void shouldNotPlayOnWhenInMsgIsNotPlayOn() throws IOException, ClassNotFoundException {
        boolean isPlayOn = false;
        InfoAboutContinuingGameMsg msg = new InfoAboutContinuingGameMsg(isPlayOn);

        when(receiverMsg.receiveMsg()).thenReturn(msg);

        assertFalse(sut.isPlayOn());

        verify(receiverMsg).receiveMsg();
    }


    //TODO comment test if you're working at another class - test duration - about 35 second
    //@Test(timeout = 35*1000)
    public void shouldNotPlayWhenPlayerNothingReceive_WARNING_TEST_DURATION_35_SECOND() throws IOException, ClassNotFoundException {
        sut = new Player(senderMsg, new NotReceiveMsg());

        assertFalse(sut.isPlayOn());
    }
}

