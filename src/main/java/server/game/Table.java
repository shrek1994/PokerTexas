package server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cards.Card;

//TODO
public class Table {
	private Map<IPlayer, Double> moneys = new HashMap<IPlayer, Double>();
	private List<Card> cards = new ArrayList<Card>();

	public void addPlayer(IPlayer player)
	{
		Double money = new Double(0);
		moneys.put(player, money);
	}

	public void addCard(List<Card> card)
	{
		cards.addAll(card);
	}

	public List<Card> getCards()
	{
		return cards;
	}

	public void addToPrize(IPlayer who, double howMuch)
	{
		moneys.put(who, moneys.get(who) + howMuch);
	}

}
