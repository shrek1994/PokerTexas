package server.game;

import cards.checkers.CardCheckerFactory;
import cards.CollectionOfCards;
import cards.checkers.ICardChecker;
import messages.Settings;

import java.util.List;

/**
 * Created by maciek on 10.12.15.
 */
public class TexasHoldRoundFactory {
    public TexasHoldRound create(List<IPlayer> playerList, Settings settings, Table table) {
        CollectionOfCards collectionOfCards = new CollectionOfCards();
        Auction auction = new Auction(table, playerList);
        CardCheckerFactory cardCheckerFactory = new CardCheckerFactory();
        ICardChecker cardChecker = cardCheckerFactory.create();
        PlayerRanking playerRanking = new PlayerRanking(cardChecker);
        TexasHoldRound texasHoldRound =
                new TexasHoldRound(playerList, collectionOfCards, table, auction, settings, playerRanking);
        return texasHoldRound;
    }
}
