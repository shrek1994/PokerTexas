package server.game;

import cards.Card;
import cards.checkers.ICardChecker;
import messages.ConfigurationCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciek on 10.12.15.
 */
public class PlayerRanking {
    private ICardChecker cardChecker;

    public IPlayer getWinner(List<IPlayer> playerList, List<Card> cardOnTable)
    {
        ConfigurationCard winnerConfiguration = null;
        IPlayer winner = null;
        for (IPlayer player : playerList)
        {
            List<Card> cardList = new ArrayList<Card>();
            cardList.addAll(cardOnTable);
            cardList.addAll(player.getCardList());
            ConfigurationCard configuration = cardChecker.check(cardList);
            if (winnerConfiguration == null || configuration.compareTo(winnerConfiguration) > 0)
            {
                winnerConfiguration = configuration;
                winner = player;
            }
        }
        return winner;
    }

    public PlayerRanking(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }
}
