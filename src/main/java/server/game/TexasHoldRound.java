package server.game;

import java.util.List;

import cards.Card;
import cards.CollectionOfCards;

public class TexasHoldRound {
    private final int numberOfCardForPlayer = 2;
    private int indexOfPlayerWihDealerButton = 0;
    final int smallBlind;
    final int bigBlind;
    static final int numberOfShuffle = 6969;

    private Table table;
    private List<IPlayer> players;
    private CollectionOfCards cards;
    private Auction auction;

    public TexasHoldRound(List<IPlayer> players, CollectionOfCards cards, Table table, Auction auction)
    {
        this.players = players;
        this.cards = cards;
        this.table = table;
        this.auction = auction;
        this.smallBlind = 5;
        this.bigBlind = 10;
    }

    public void runGame()
    {
        //placenie ciemnych
        IPlayer playerShouldPaySmallBlind = players.get(getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind());
        IPlayer playerShouldPayBigBlind = players.get(getIndexOfPlayerTwoOnTheLeftOfDealerButton_bigBlind());

        int smallBlind = playerShouldPaySmallBlind.getBlind(this.smallBlind);
        int bigBlind = playerShouldPayBigBlind.getBlind(this.bigBlind);
        //TODO if blind == 0 ask other player and this remove
        table.addMoney(playerShouldPaySmallBlind, smallBlind);
        table.addMoney(playerShouldPayBigBlind, bigBlind);

        //potasowanie kart
        cards.createNewDeckCard();
        cards.shuffle(numberOfShuffle);

        //rozdanie kart graczom
        for( IPlayer player : players)
        {
            List<Card> cardsList = this.cards.getCards(numberOfCardForPlayer);
            for ( Card card : cardsList)
            {
                player.addCard(card);
            }
        }

        //pierwsza licytacja
        int indexOfPlayerWhoStart = getIndexOfTheLeft(getIndexOfPlayerTwoOnTheLeftOfDealerButton_bigBlind());
        auction.start(players.get(indexOfPlayerWhoStart));

        //wylozenie na stol 3 karty
        table.addCard(cards.getCards(3));

        //druga licytacja
        indexOfPlayerWhoStart = getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind();
        auction.start(players.get(indexOfPlayerWhoStart));

        //wylozenie na stol 1 karty
        table.addCard(cards.getCards(1));

        //trzecia licytacja
        indexOfPlayerWhoStart = getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind();
        auction.start(players.get(indexOfPlayerWhoStart));

        //wylozenie na stol 1 karty
        table.addCard(cards.getCards(1));

        //czwarta licytacja
        indexOfPlayerWhoStart = getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind();
        auction.start(players.get(indexOfPlayerWhoStart));

        //przesuniecie
        indexOfPlayerWihDealerButton++;
        if(indexOfPlayerWihDealerButton >= players.size())
        {
            indexOfPlayerWihDealerButton = 0;
        }
    }

    private int getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind() {
        return indexOfPlayerWihDealerButton + 1 >= players.size() ?
                0 : indexOfPlayerWihDealerButton + 1;
    }

    private int getIndexOfPlayerTwoOnTheLeftOfDealerButton_bigBlind() {
        return getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind() + 1 >= players.size() ?
                0 : getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind() + 1;
    }

    private int getIndexOfTheLeft(int index)
    {
        return index + 1 >= players.size() ?
                0 : index + 1;
    }
}
