package server.game;

import java.util.*;

import cards.Card;
import messages.CardMsg;

//TODO
public class Table extends Observable {
    private Map<IPlayer, Double> moneys = new HashMap<IPlayer, Double>();
    private List<Card> cards = new ArrayList<Card>();

    public void addPlayer(IPlayer player)
    {
        Double money = new Double(0);
        moneys.put(player, money);
        if ( player instanceof Player)
        {
            this.addObserver((Player)player);
        }
    }

    public void addCard(List<Card> cardList)
    {
        cards.addAll(cardList);
        for(Card card : cardList)
        {
            notifyObservers(new CardMsg(card));
        }
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
