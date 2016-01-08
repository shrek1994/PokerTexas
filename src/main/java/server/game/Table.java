package server.game;

import java.util.*;

import cards.Card;
import messages.CardMsg;

public class Table extends Observable {
    private Map<IPlayer, Double> moneys = new HashMap<IPlayer, Double>();
    private List<Card> cards = new ArrayList<Card>();

    public void addPlayer(IPlayer player)
    {
        Double money = new Double(0);
        moneys.put(player, money);
        if ( player instanceof Observer)
        {
            this.addObserver((Observer)player);
        }
    }

    public void addCard(List<Card> cardList)
    {
        for(Card card : cardList)
        {
            notifyAllPlayers(new CardMsg(card));
        }
        cards.addAll(cardList);
    }

    public List<Card> getCards()
    {
        return cards;
    }

    public void addMoney(IPlayer who, double howMuch)
    {
        if (moneys.get(who) != null)
            moneys.put(who, moneys.get(who) + howMuch);
        else
            moneys.put(who, howMuch);
    }

    public boolean haveAllPlayersTheSameMoney() {
        Double money = null;
        for (Map.Entry<IPlayer, Double> playerMoney : moneys.entrySet())
        {
            if ( money != null && playerMoney.getValue().compareTo(money) != 0 ) {
                return false;
            }
            money = playerMoney.getValue();
        }
        return true;
    }


    public void clearCard() {
        cards.clear();
    }

    public void notifyAllPlayers(Object message)
    {
        setChanged();
        notifyObservers(message);
    }

    public double getCash() {
        double money = 0;
        for (Map.Entry<IPlayer, Double> playerMoney : moneys.entrySet())
        {
            money += playerMoney.getValue().doubleValue();
        }
        moneys = new HashMap<IPlayer, Double>();
        return money;
    }
}
