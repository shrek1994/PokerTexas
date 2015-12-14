package server.game;

import cards.*;
import cards.checkers.ICardChecker;
import messages.ConfigurationCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerRankingTest {
    private ICardChecker cardChecker = mock(ICardChecker.class);
    private IPlayer firstPlayer = mock(IPlayer.class, "firstPlayer");
    private IPlayer secondPlayer = mock(IPlayer.class, "secondPlayer");
    private IPlayer thirdPlayer = mock(IPlayer.class, "thirdPlayer");
    private List<IPlayer> playerList;

    private final Card card = new Card(Figure.King, Color.Spades);
    private List<Card> cardOnTable;
    private List<Card> playersCard;
    private List<Card> cardToCheck;

    private final ConfigurationCard firstConfiguration = new ConfigurationCard(SetOfCard.ThreeOfKind, card);
    private final ConfigurationCard secondConfiguration = new ConfigurationCard(SetOfCard.FourOfKind, card);
    private final ConfigurationCard thirdConfiguration = new ConfigurationCard(SetOfCard.TwoPair, card);

    private PlayerRanking sut;

    @Before
    public void setUp() {
        playerList = new ArrayList<IPlayer>();
        playerList.add(firstPlayer);
        playerList.add(secondPlayer);
        playerList.add(thirdPlayer);

        cardOnTable = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            cardOnTable.add(card);
        }

        playersCard = new ArrayList<Card>();
        for (int i = 0; i < 2; i++) {
            playersCard.add(card);
        }

        cardToCheck = new ArrayList<Card>();
        cardToCheck.addAll(cardOnTable);
        cardToCheck.addAll(playersCard);

        sut = new PlayerRanking(cardChecker);
    }

    @After
    public void After()
    {
        verifyNoMoreInteractions(cardChecker);
        verifyNoMoreInteractions(firstPlayer);
        verifyNoMoreInteractions(secondPlayer);
        verifyNoMoreInteractions(thirdPlayer);
    }

    @Test
    public void shouldWinSecondPlayerWithTheHighestConfiguration() {
        when(cardChecker.check(anyList())).thenReturn(firstConfiguration, secondConfiguration, thirdConfiguration);
        when(firstPlayer.getCardList()).thenReturn(playersCard);
        when(secondPlayer.getCardList()).thenReturn(playersCard);
        when(thirdPlayer.getCardList()).thenReturn(playersCard);

        assertEquals(secondPlayer, sut.getWinner(playerList, cardOnTable));

        verify(cardChecker, times(3)).check(cardToCheck);
        verify(firstPlayer).getCardList();
        verify(secondPlayer).getCardList();
        verify(thirdPlayer).getCardList();
    }
}