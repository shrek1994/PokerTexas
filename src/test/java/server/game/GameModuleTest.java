package server.game;

import messages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by maciek on 15.12.15.
 */
public class GameModuleTest {
    Server server = new Server(new PlayerFactory(), new ServerSocketFactory());
    TexasHoldRoundFactory texasHoldRoundFactory = new TexasHoldRoundFactory();
    Settings settings = new Settings();

    Socket firstPlayer;
    Socket secondPlayer;
    ReceiverMsg firstReceiver;
    ReceiverMsg secondReceiver;
    SenderMsg firstSender;
    SenderMsg secondSender;

    private final ActionMsg fold = new ActionMsg(ActionType.Fold, 0);
    private final ActionMsg bet = new ActionMsg(ActionType.Bet, 10);
    private final ActionMsg check = new ActionMsg(ActionType.Check, 0);
    private final ActionMsg allIn = new ActionMsg(ActionType.AllIn, 200);
    private final ActionMsg call = new ActionMsg(ActionType.Call, 5);
    private final ActionMsg raise = new ActionMsg(ActionType.Raise, 15);

    private Game sut;
    private Thread gameRunner;

    @Before
    public void setUp() throws Exception {
//        turnOffLogger();
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%4$s %5$s%6$s%n");

        settings.numberOfBots = 0;
        settings.numberOfPlayers = 2;

        sut = new Game(texasHoldRoundFactory, server, settings);
        gameRunner = new Thread(new GameRunner());
    }

    @After
    public void after()
    {
        try {
            gameRunner.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameRunner.interrupt();
    }

    private void turnOffLogger() {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
    }

    private void receiveRankingMsg() throws ClassNotFoundException, IOException {
        Object ranking = firstReceiver.receiveMsg();
        assertTrue(ranking instanceof RankingMsg);
        ranking = secondReceiver.receiveMsg();
        assertTrue(ranking instanceof RankingMsg);
    }

    private void playersAction(ReceiverMsg receiverMsg, SenderMsg senderMsg, ActionMsg actionMsg) throws ClassNotFoundException, IOException {
        Object notifyActionMsg = receiverMsg.receiveMsg();
        assertTrue(notifyActionMsg instanceof NotifyAboutActionMsg);
        senderMsg.sendMsg(actionMsg);
        //TODO add check if it is info about action msg
        firstReceiver.receiveMsg();
        secondReceiver.receiveMsg();
    }

    private void receiveCardMsg() throws ClassNotFoundException, IOException {
        Object cardMsg = firstReceiver.receiveMsg();
        assertTrue(cardMsg instanceof CardMsg);
        cardMsg = secondReceiver.receiveMsg();
        assertTrue(cardMsg instanceof CardMsg);
    }

    private void receiveAndReplyBlindMsg() throws ClassNotFoundException, IOException {
        Object smallBlind = secondReceiver.receiveMsg();
        assertTrue(smallBlind instanceof BlindMsg);
        assertEquals(settings.smallBlind, ((BlindMsg)smallBlind).getValue());
        secondSender.sendMsg(smallBlind);

        Object bigBlind = firstReceiver.receiveMsg();
        assertTrue(bigBlind instanceof BlindMsg);
        assertEquals(settings.bigBlind, ((BlindMsg)bigBlind).getValue());
        firstSender.sendMsg(bigBlind);

        //TODO receive infoAboutAction
        firstReceiver.receiveMsg();
        secondReceiver.receiveMsg();

        firstReceiver.receiveMsg();
        secondReceiver.receiveMsg();
    }

    private void receiveAllPlayersSettingMsg() throws ClassNotFoundException, IOException {
        Object firstSetting = firstReceiver.receiveMsg();
        Object secondSetting = secondReceiver.receiveMsg();
        assertTrue(firstSetting instanceof SettingsMsg);
        assertTrue(secondSetting instanceof SettingsMsg);
    }

    private void connectAndConfigurePlayersStreams() throws InterruptedException {
        try {
            sleep(100);

            firstPlayer = new Socket("localhost", settings.port);
            firstReceiver = new ReceiverMsg(new ObjectInputStream(firstPlayer.getInputStream()));
            firstSender = new SenderMsg(new ObjectOutputStream(firstPlayer.getOutputStream()));

            secondPlayer = new Socket("localhost", settings.port);
            secondReceiver = new ReceiverMsg(new ObjectInputStream(secondPlayer.getInputStream()));
            secondSender = new SenderMsg(new ObjectOutputStream(secondPlayer.getOutputStream()));
        } catch (IOException e) {
            fail("can't connect");
        }
    }

    private class GameRunner implements Runnable
    {
        @Override
        public void run() {
            try {
                sut.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test(timeout = 5000)
    public void shouldCorrectPlayWhenAllFoldOnFirstAuction() throws Exception
    {
        gameRunner.start();

        connectAndConfigurePlayersStreams();

        receiveAllPlayersSettingMsg();

        receiveAndReplyBlindMsg();

        receiveCardMsg();
        receiveCardMsg();

        playersAction(secondReceiver, secondSender, fold);
        playersAction(firstReceiver, firstSender, fold);

        receiveCardMsg();
        receiveCardMsg();
        receiveCardMsg();
        receiveCardMsg();
        receiveCardMsg();

        receiveRankingMsg();

        firstSender.sendMsg(new InfoAboutContinuingGameMsg(false));
        secondSender.sendMsg(new InfoAboutContinuingGameMsg(false));
    }

    @Test(timeout = 5000)
    public void shouldCorrectPlayAllAuction() throws Exception
    {
        Thread gameRunner = new Thread(new GameRunner());
        gameRunner.start();

        connectAndConfigurePlayersStreams();

        receiveAllPlayersSettingMsg();

        receiveAndReplyBlindMsg();

        receiveCardMsg();
        receiveCardMsg();

        //first Auction
        playersAction(secondReceiver, secondSender, bet);
        playersAction(firstReceiver, firstSender, raise);

        playersAction(secondReceiver, secondSender, call);
        playersAction(firstReceiver, firstSender, raise);

        playersAction(secondReceiver, secondSender, check);
        playersAction(firstReceiver, firstSender, check);

        receiveCardMsg();
        receiveCardMsg();
        receiveCardMsg();

        //second auction
        playersAction(secondReceiver, secondSender, raise);
        playersAction(firstReceiver, firstSender, raise);

        playersAction(secondReceiver, secondSender, check);
        playersAction(firstReceiver, firstSender, check);

        receiveCardMsg();

        //third Auction
        playersAction(secondReceiver, secondSender, check);
        playersAction(firstReceiver, firstSender, check);

        receiveCardMsg();

        //forth Auction
        playersAction(secondReceiver, secondSender, check);
        playersAction(firstReceiver, firstSender, check);

        receiveRankingMsg();

        firstSender.sendMsg(new InfoAboutContinuingGameMsg(false));
        secondSender.sendMsg(new InfoAboutContinuingGameMsg(false));

        gameRunner.join(1000);
        gameRunner.interrupt();
    }

}