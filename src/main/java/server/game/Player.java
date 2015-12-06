package server.game;

import messages.*;
import cards.Card;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class Player implements IPlayer, Observer {
    private final static Logger logger = Logger.getLogger(Player.class.getName());
    final static long millisecondsWaitForActionPlayer = 30 * 1000;

    private static int nextId = 0;
    private int Id;
    private SenderMsg senderMsg;
    private ReceiverMsg receiverMsg;

    private List<Card> cardList = new ArrayList<Card>();

    //TODO change for something pretty
    private Integer lock = new Integer(123);
    private ActionMsg actionMsg;
    private BlindMsg blindMsg;

    private final ActionMsg defaultActionMsg = new ActionMsg(ActionType.Fold, 0);

    public Player(SenderMsg senderMsg, ReceiverMsg receiverMsg) {
        Id = nextId;
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
            senderMsg.sendMsg(new NotifyAboutActionMsg());
        } catch (IOException e) {
            logger.warning("Probably player disconnected, " + e.getMessage());
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
        //TODO check that isn't interrupt dangerous?
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
            senderMsg.sendMsg(new BlindMsg(value));

            Thread receiveBlind = new Thread(new ReceiveBlind());
            receiveBlind.start();

            receiveBlind.join(millisecondsWaitForActionPlayer);
            //TODO check that isn't interrupt dangerous?
            receiveBlind.interrupt();
        } catch (Exception e) {
            logger.warning("Probably player disconnected, " + e.getMessage());
        }
        return blindMsg.getValue();
    }

    @Override
    public void addCard(Card card) {
        try {
            senderMsg.sendMsg(new CardMsg(card));
            cardList.add(card);
        } catch (IOException e) {
            logger.warning("Probably player disconnected, " + e.getMessage());
        }
    }

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public void update(Observable observable, Object o) {
        //TODO
    }

    private class ReceiveAction implements Runnable
    {
        @Override
        public void run() {
            try {
                Object msg = receiverMsg.receiveMsg();
                if(msg instanceof ActionMsg)
                {
                    synchronized (lock) {
                        actionMsg = (ActionMsg) msg;
                    }
                }
                else
                {
                    logger.warning("Wrong message received, isn't instance of ActionMsg .");
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
}
