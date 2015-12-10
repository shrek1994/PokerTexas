package server.game;

import cards.Card;
import cards.Color;
import cards.Figure;
import messages.CardMsg;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by maciek on 28.11.15.
 */
public class TableTest {
    private IPlayer bot = mock(IPlayer.class, "bot");
    private Player firstPlayer = mock(Player.class, "firstPlayer");
    private Player secondPlayer = mock(Player.class, "secondPlayer");

    private Card firstCard = new Card(Figure.Four, Color.Clubs);
    private Card secondCard = new Card(Figure.Eight, Color.Diamonds);
    private Card thirdCard = new Card(Figure.Eight, Color.Diamonds);
    private Card fourthCard = new Card(Figure.Eight, Color.Diamonds);
    private List<Card> firstCardList;
    private List<Card> secondCardList;
    private double money = 1.23;

    private Table sut = new Table();

    @Before
    public void Setup()
    {
        sut = new Table();
        sut.addPlayer(firstPlayer);
        sut.addPlayer(bot);
        sut.addPlayer(secondPlayer);

        firstCardList = new ArrayList<Card>();
        firstCardList.add(firstCard);
        firstCardList.add(secondCard);

        secondCardList = new ArrayList<Card>();
        secondCardList.add(thirdCard);
        secondCardList.add(fourthCard);
    }

    @After
    public void after(){
        verifyNoMoreInteractions(bot);
        verifyNoMoreInteractions(firstPlayer);
        verifyNoMoreInteractions(secondPlayer);
    }

    @Test
    public void shouldNotifyWhenAddCards()
    {
        CardMsg firstCardMsg = new CardMsg(firstCard);
        CardMsg secondCardMsg = new CardMsg(secondCard);

        sut.addCard(firstCardList);

        verify(firstPlayer).update(sut, firstCardMsg);
        verify(firstPlayer).update(sut, secondCardMsg);
        verify(secondPlayer).update(sut, firstCardMsg);
        verify(secondPlayer).update(sut, secondCardMsg);
    }

    @Test
    public void shouldHaveOnlyCardsWhichInsertAfterClearTable()
    {
        sut.addCard(firstCardList);
        sut.clearCard();
        sut.addCard(secondCardList);

        assertArrayEquals(secondCardList.toArray(), sut.getCards().toArray());

        verify(firstPlayer, times(4)).update(eq(sut), anyObject());
        verify(secondPlayer, times(4)).update(eq(sut), anyObject());
    }

    @Test
    public void shouldNotAllPlayersHaveTheSameValueOfMoney()
    {
        sut.addMoney(firstPlayer, money);

        assertFalse(sut.haveAllPlayersTheSameMoney());
    }

    @Test
    public void shouldAllPlayersHaveTheSameValueOfMoney()
    {
        sut.addMoney(firstPlayer, money);
        sut.addMoney(secondPlayer, money);
        sut.addMoney(bot, money);

        assertTrue(sut.haveAllPlayersTheSameMoney());
    }
}
