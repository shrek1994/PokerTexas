package server.game;

import java.util.ArrayList;
import java.util.List;

import messages.RankingMsg;
import messages.Settings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import cards.Card;
import cards.Figure;
import cards.Color;
import cards.CollectionOfCards;

public class TexasHoldRoundTest {
    private IPlayer firstPlayer = mock(IPlayer.class, "firstPlayer");
    private IPlayer secondPlayer = mock(IPlayer.class, "secondPlayer");
    private IPlayer thirdPlayer = mock(IPlayer.class, "thirdPlayer");
    private IPlayer forthPlayer = mock(IPlayer.class, "forthPlayer");
    private List<IPlayer> playersList;

    private CollectionOfCards cards = mock(CollectionOfCards.class);
    private Table table = mock(Table.class);
    private Auction auction = mock(Auction.class);
    private PlayerRanking playerRanking = mock(PlayerRanking.class);

    private Card firstCard = new Card(Figure.Two, Color.Clubs);
    private Card secondCard = new Card(Figure.Three, Color.Diamonds);
    private Card thirdCard = new Card(Figure.Five, Color.Hearts);
    private List<Card> cardListWithThreeCards;
    private List<Card> cardListWithTwoCards;
    private List<Card> cardListWithCard;
    private List<Card> cardOnTheTable;
    private Settings settings = new Settings();
    private final double cash = 12.34;
    private final int id = 7;
    private RankingMsg rankingMsg = new RankingMsg(id, cash);

    private TexasHoldRound sut;

    @Before
    public void SetUp(){
        playersList = new ArrayList<IPlayer>();
        playersList.add(firstPlayer);
        playersList.add(secondPlayer);
        playersList.add(thirdPlayer);
        playersList.add(forthPlayer);

        initCardsLists();
        cardOnTheTable = cardListWithThreeCards;

        sut = new TexasHoldRound(playersList, cards, table, auction, settings, playerRanking);

        when(playerRanking.getWinner(playersList, cardOnTheTable)).thenReturn(firstPlayer);
        when(table.getCash()).thenReturn(cash);
        when(table.getCards()).thenReturn(cardOnTheTable);
        when(firstPlayer.getId()).thenReturn(id);
    }

    @After
    public void After()
    {
        verify(table).getCards();
        verify(table).getCash();
        verify(firstPlayer).getId();
        verify(playerRanking).getWinner(anyList(),anyList());
        for(IPlayer player : playersList)
        {
            verify(player).updateCashIfWin(rankingMsg);
        }

        verifyNoMoreInteractions(firstPlayer);
        verifyNoMoreInteractions(secondPlayer);
        verifyNoMoreInteractions(thirdPlayer);
        verifyNoMoreInteractions(forthPlayer);
        verifyNoMoreInteractions(cards);
        verifyNoMoreInteractions(auction);
        verifyNoMoreInteractions(table);
        verifyNoMoreInteractions(playerRanking);
    }

    private void initCardsLists() {
        cardListWithCard = new ArrayList<Card>();
        cardListWithTwoCards = new ArrayList<Card>();
        cardListWithThreeCards = new ArrayList<Card>();

        cardListWithCard.add(firstCard);

        cardListWithTwoCards.add(firstCard);
        cardListWithTwoCards.add(secondCard);

        cardListWithThreeCards.add(firstCard);
        cardListWithThreeCards.add(secondCard);
        cardListWithThreeCards.add(thirdCard);
    }

    @Test
    public void shouldCorrectPlayRound()
    {
        when(secondPlayer.getBlind(settings.smallBlind)).thenReturn(settings.smallBlind);
        when(thirdPlayer.getBlind(settings.bigBlind)).thenReturn(settings.bigBlind);

        when(cards.getCards(3)).thenReturn(cardListWithThreeCards);
        when(cards.getCards(2)).thenReturn(cardListWithTwoCards);
        when(cards.getCards(1)).thenReturn(cardListWithCard);

        sut.runRound();

        verifyAddTwoCardsToPlayer(firstPlayer);
        verifyAddTwoCardsToPlayer(secondPlayer);
        verifyAddTwoCardsToPlayer(thirdPlayer);
        verifyAddTwoCardsToPlayer(forthPlayer);

        verifyGetBlindAndAddToTable(secondPlayer, settings.smallBlind);
        verifyGetBlindAndAddToTable(thirdPlayer, settings.bigBlind);

        verifyCreateShuffleAndGetCards(playersList.size());

        verify(auction).start(forthPlayer);
        verify(auction, times(3)).start(secondPlayer);

        verify(table).addCard(cardListWithThreeCards);
        verify(table, times(2)).addCard(cardListWithCard);
    }

    @Test
    public void shouldPlayerWhoDoNotPaySmallBlindEndPlayingAndNextPlayerPaySmallBlind()
    {
        int numberOfPlayers = playersList.size();
        when(secondPlayer.getBlind(settings.smallBlind)).thenReturn(0);
        when(thirdPlayer.getBlind(settings.smallBlind)).thenReturn(settings.smallBlind);
        when(forthPlayer.getBlind(settings.bigBlind)).thenReturn(settings.bigBlind);

        when(cards.getCards(3)).thenReturn(cardListWithThreeCards);
        when(cards.getCards(2)).thenReturn(cardListWithTwoCards);
        when(cards.getCards(1)).thenReturn(cardListWithCard);

        sut.runRound();

        verifyAddTwoCardsToPlayer(firstPlayer);
        verifyAddTwoCardsToPlayer(thirdPlayer);
        verifyAddTwoCardsToPlayer(forthPlayer);

        verify(secondPlayer).getBlind(settings.smallBlind);
        verifyGetBlindAndAddToTable(thirdPlayer, settings.smallBlind);
        verifyGetBlindAndAddToTable(forthPlayer, settings.bigBlind);

        verifyCreateShuffleAndGetCards(numberOfPlayers - 1);

        verify(auction).start(firstPlayer);
        verify(auction, times(3)).start(thirdPlayer);

        verify(table).addCard(cardListWithThreeCards);
        verify(table, times(2)).addCard(cardListWithCard);
    }

    @Test
    public void shouldPlayerWhoDoNotPayBigBlindEndPlayingAndNextPlayerPayBigBlind()
    {
        int numberOfPlayers = playersList.size();
        when(secondPlayer.getBlind(settings.smallBlind)).thenReturn(settings.smallBlind);
        when(thirdPlayer.getBlind(settings.bigBlind)).thenReturn(0);
        when(forthPlayer.getBlind(settings.bigBlind)).thenReturn(settings.bigBlind);

        when(cards.getCards(3)).thenReturn(cardListWithThreeCards);
        when(cards.getCards(2)).thenReturn(cardListWithTwoCards);
        when(cards.getCards(1)).thenReturn(cardListWithCard);

        sut.runRound();

        verifyAddTwoCardsToPlayer(firstPlayer);
        verifyAddTwoCardsToPlayer(secondPlayer);
        verifyAddTwoCardsToPlayer(forthPlayer);

        verifyGetBlindAndAddToTable(secondPlayer, settings.smallBlind);
        verify(thirdPlayer).getBlind(settings.bigBlind);
        verifyGetBlindAndAddToTable(forthPlayer, settings.bigBlind);

        verifyCreateShuffleAndGetCards(numberOfPlayers - 1);

        verify(auction).start(firstPlayer);
        verify(auction, times(3)).start(secondPlayer);

        verify(table).addCard(cardListWithThreeCards);
        verify(table, times(2)).addCard(cardListWithCard);
    }

    private void verifyCreateShuffleAndGetCards(int numberOfPlayers) {
        verify(cards).createNewDeckCard();
        verify(cards).shuffle(TexasHoldRound.numberOfShuffle);
        verify(cards, times(1)).getCards(3);
        verify(cards, times(numberOfPlayers)).getCards(2);
        verify(cards, times(2)).getCards(1);
    }

    private void verifyGetBlindAndAddToTable(IPlayer player, int blind) {
        verify(player).getBlind(blind);
        verify(table).addMoney(player, blind);
    }

    private void verifyAddTwoCardsToPlayer(IPlayer player) {
        verify(player).addCard(cardListWithTwoCards.get(0));
        verify(player).addCard(cardListWithTwoCards.get(1));
    }
}
