package server.game;

import java.util.List;

import cards.Card;
import cards.CollectionOfCards;
import messages.RankingMsg;
import messages.Settings;

public class TexasHoldRound {
    private final int numberOfCardForPlayer = 2;
    private int indexOfPlayerWihDealerButton = 0;
    private final int smallBlind;
    private final int bigBlind;
    static final int numberOfShuffle = 6969;

    private Table table;
    private List<IPlayer> players;
    private CollectionOfCards cards;
    private Auction auction;
    private PlayerRanking playerRanking;

    public TexasHoldRound(List<IPlayer> players,
                          CollectionOfCards cards,
                          Table table,
                          Auction auction,
                          Settings settings,
                          PlayerRanking playerRanking)
    {
        this.players = players;
        this.cards = cards;
        this.table = table;
        this.auction = auction;
        this.smallBlind = settings.smallBlind;
        this.bigBlind = settings.bigBlind;
        this.playerRanking = playerRanking;
    }

    public void runRound()
    {
        //placenie ciemnych
        payingBlinds();

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

        IPlayer winner = playerRanking.getWinner(players, table.getCards());
        RankingMsg rankingMsg = new RankingMsg(winner.getId(), table.getCash());
        for(IPlayer player : players)
        {
            player.updateCashIfWin(rankingMsg);
        }

        //przesuniecie dealer button-a
        indexOfPlayerWihDealerButton++;
        if(indexOfPlayerWihDealerButton >= players.size())
        {
            indexOfPlayerWihDealerButton = 0;
        }
    }

    private void payingBlinds() {
        IPlayer playerShouldPaySmallBlind = players.get(getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind());
        int smallBlind = playerShouldPaySmallBlind.getBlind(this.smallBlind);
        while ( smallBlind == 0 && players.size() > 1)
        {
            players.remove(playerShouldPaySmallBlind);
            playerShouldPaySmallBlind = players.get(getIndexOfPlayerOnTheLeftOfDealerButton_smallBlind());
            smallBlind = playerShouldPaySmallBlind.getBlind(this.smallBlind);
        }

        IPlayer playerShouldPayBigBlind = players.get(getIndexOfPlayerTwoOnTheLeftOfDealerButton_bigBlind());
        int bigBlind = playerShouldPayBigBlind.getBlind(this.bigBlind);
        while ( bigBlind == 0 && players.size() > 1)
        {
            players.remove(playerShouldPayBigBlind);
            playerShouldPayBigBlind = players.get(getIndexOfPlayerTwoOnTheLeftOfDealerButton_bigBlind());
            bigBlind = playerShouldPayBigBlind.getBlind(this.bigBlind);
        }

        table.addMoney(playerShouldPaySmallBlind, smallBlind);
        table.addMoney(playerShouldPayBigBlind, bigBlind);
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
