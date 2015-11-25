package server.game;

import cards.Card;
import action.Action;

public interface IPlayer {
	public Action getAction();
	public int getBlind(int value);
	public void addCard(Card card);
}
