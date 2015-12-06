package server.game;

import cards.Card;
import messages.ActionMsg;

import java.util.List;

public interface IPlayer {
    public ActionMsg getAction();
    public int getBlind(int value);
    public void addCard(Card card);
    public int getId();
    public List<Card> getCardList();
    public void clearCards();
}
