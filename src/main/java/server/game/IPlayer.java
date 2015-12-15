package server.game;

import cards.Card;
import messages.ActionMsg;
import messages.RankingMsg;
import messages.Settings;

import java.util.List;

public interface IPlayer {
    ActionMsg getAction();
    int getBlind(int value);
    void addCard(Card card);
    int getId();
    List<Card> getCardList();
    void clearCards();
    boolean isPlayOn();
    void updateCashIfWin(RankingMsg rankingMsg);
    void setSettings(Settings settings);
}
