package server.game;

import messages.ActionMsg;
import messages.ActionType;
import cards.Card;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;

public class Player implements IPlayer, Observer {
    private InputStream inputStream;
    private OutputStream outputStream;

    public Player(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public ActionMsg getAction() {
        ActionMsg action = new ActionMsg(ActionType.Fold,0);

        return action;
    }

    @Override
    public int getBlind(int value) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addCard(Card card) {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
