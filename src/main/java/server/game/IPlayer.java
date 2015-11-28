package server.game;

import cards.Card;
import messages.ActionMsg;

public interface IPlayer {
	public ActionMsg getAction();
	public int getBlind(int value);
	public void addCard(Card card);
}
