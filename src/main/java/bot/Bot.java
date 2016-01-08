package bot;

import cards.Card;
import messages.ActionMsg;
import messages.RankingMsg;
import messages.Settings;
import server.game.IPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Created by maciek on 06.01.16.
 */
public class Bot implements IPlayer, Observer {
    private final static Logger logger = Logger.getLogger(Bot.class.getName());
    private int id;
    private int money;
    private List<Card> cardList;
    private Settings settings;

    //TODO cala klasa!
    public Bot()
    {

    }

    @Override
    public ActionMsg getAction() {
        //TODO
        return null;
    }

    @Override
    public int getBlind(int value) {
        if ( money > value)
        {
            money -= value;
            return value;
        }
        return 0;
    }

    @Override
    public void addCard(Card card) {
        cardList.add(card);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<Card> getCardList() {
        assert (cardList.size() >= 2);
        List<Card> botsCards = new ArrayList<Card>();
        botsCards.add(cardList.get(0));
        botsCards.add(cardList.get(1));
        return botsCards;
    }

    @Override
    public void clearCards() {
        cardList.clear();
    }

    @Override
    public boolean isPlayOn() {
        return true;
    }

    @Override
    public void updateCashIfWin(RankingMsg rankingMsg) {
        if( rankingMsg.getPlayerIdWhoWin() == id)
        {
            money += rankingMsg.getMoney();
        }
    }

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;

    }

    @Override
    public void update(Observable observable, Object o) {
        //TODO
    }
}
