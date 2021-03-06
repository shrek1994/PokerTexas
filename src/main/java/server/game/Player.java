package server.game;

import messages.*;
import cards.Card;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Player implements IPlayer, Observer {
    private final static Logger logger = Logger.getLogger(Player.class.getName());
    final static long millisecondsWaitForActionPlayer = 30 * 1000;

    private static int nextId = 0;
    private int id;
    private SenderMsg senderMsg;
    private ReceiverMsg receiverMsg;

    private List<Card> cardList = new ArrayList<Card>();

    //TODO change for something pretty
    private Integer lock = new Integer(123);
    private ActionMsg actionMsg;
    private BlindMsg blindMsg;
    private InfoAboutContinuingGameMsg infoAboutContinuingGameMsg;

    private final ActionMsg defaultActionMsg = new ActionMsg(ActionType.Fold, 0);

    public Player(SenderMsg senderMsg, ReceiverMsg receiverMsg) {
        id = nextId;
        nextId++;
        this.senderMsg = senderMsg;
        this.receiverMsg = receiverMsg;
    }

    @Override
    public List<Card> getCardList() {
        return cardList;
    }

    @Override
    public void clearCards() {
        cardList.clear();
    }

    @Override
    public ActionMsg getAction() {
        synchronized (lock) {
            actionMsg = null;
        }

        // notify player about action
        try {
            logger.info("Player[" + id + "]: Sending NotifyAboutActionMsg");
            senderMsg.sendMsg(new NotifyAboutActionMsg());
        } catch (IOException e) {
            logger.warning("Probably player("+ id +") disconnected, " + e.getMessage());
            return defaultActionMsg;
        }

        // get action from client
        Thread receiveAction = new Thread(new ReceiveAction());
        receiveAction.start();
        try {
            receiveAction.join(millisecondsWaitForActionPlayer);
        } catch (InterruptedException e) {
            logger.warning(e.getMessage());
        }
        receiveAction.interrupt();

        synchronized (lock) {
            if (actionMsg == null)
                actionMsg = defaultActionMsg;
        }
        return actionMsg;
    }

    @Override
    public int getBlind(int value) {
        blindMsg = new BlindMsg(0);
        try {
            logger.info("Player[" + id + "]: Sending BlindMsg: " + value);
            senderMsg.sendMsg(new BlindMsg(value));

            Thread receiveBlind = new Thread(new ReceiveBlind());
            receiveBlind.start();

            receiveBlind.join(millisecondsWaitForActionPlayer);
            receiveBlind.interrupt();
        } catch (Exception e) {
            logger.warning("Probably player("+ id +") disconnected, " + e.getMessage());
        }
        return blindMsg.getValue();
    }

    @Override
    public void addCard(Card card) {
        try {
            logger.info("Player[" + id + "]: Sending CardMsg");
            senderMsg.sendMsg(new CardMsg(card));
            cardList.add(card);
        } catch (IOException e) {
            logger.warning("Probably player("+ id +") disconnected, " + e.getMessage());
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void update(Observable observable, Object msg) {
        try {
            logger.info("Player[" + id + "]: Sending " + msg.getClass().toString());
            senderMsg.sendMsg(msg);
        } catch (IOException e) {
            logger.warning("Probably player("+ id +") disconnected, " + e.getMessage());
        }
    }

    @Override
    public boolean isPlayOn()
    {
        infoAboutContinuingGameMsg = null;
        try {
            Thread receive = new Thread(new ReceiveInfoAboutContinuingGameMsg());
            receive.start();
            receive.join(millisecondsWaitForActionPlayer);
            receive.interrupt();
        } catch (Exception e) {
            logger.warning("Probably player("+ id +") disconnected, " + e.getMessage());
        }
        if( infoAboutContinuingGameMsg != null)
        {
            return infoAboutContinuingGameMsg.isPlayerContinue();
        }
        return false;
    }

    @Override
    public void updateCashIfWin(RankingMsg msg)
    {
        // TODO test!
        try {
            logger.info("Player[" + id + "]: Sending " + msg.getClass().toString());
            senderMsg.sendMsg(msg);
        } catch (IOException e) {
            logger.warning("Probably player("+ id +") disconnected, " + e.getMessage());
        }
    }

    @Override
    public void setSettings(Settings settings) {
        //TODO test!
        SettingsMsg msg = new SettingsMsg(
                settings.gameType,
                settings.moneyOnStart,
                settings.smallBlind,
                settings.bigBlind,
                this.id);
        try {
            logger.info("Player[" + id + "]: Sending " + msg.getClass().toString());
            senderMsg.sendMsg(msg);
        } catch (IOException e) {
            logger.warning("Probably player("+ id +") disconnected, " + e.getMessage());
        }
    }

    private class ReceiveAction implements Runnable
    {
        @Override
        public void run() {
            try {
                Object msg = receiverMsg.receiveMsg();
                logger.info("Player[" + id + "]: Received " + msg.getClass().toString());
                if(msg instanceof ActionMsg)
                {
                    synchronized (lock) {
                        actionMsg = (ActionMsg) msg;
                    }
                }
                else
                {
                    logger.warning("Wrong message received, isn't instance of ActionMsg.");
                }
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
    }

    private class ReceiveBlind implements Runnable{
        @Override
        public void run() {
            try {
                Object msg = receiverMsg.receiveMsg();
                logger.info("Player[" + id + "]: Received " + msg.getClass().toString());
                if(msg instanceof BlindMsg)
                {
                    blindMsg = (BlindMsg) msg;
                }
                else
                {
                    logger.warning("Wrong message received, isn't instance of BlindMsg .");
                }
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
    }

    private class ReceiveInfoAboutContinuingGameMsg implements Runnable{
        @Override
        public void run() {
            try {

                Object msg = receiverMsg.receiveMsg();
                logger.info("Player[" + id + "]: Received " + msg.getClass().toString());
                if( msg instanceof InfoAboutContinuingGameMsg)
                {
                    infoAboutContinuingGameMsg = (InfoAboutContinuingGameMsg)msg;
                }
                else
                {
                    logger.warning("Wrong message received, isn't instance of InfoAboutContinuingGameMsg .");
                }
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }
        }
    }
}
