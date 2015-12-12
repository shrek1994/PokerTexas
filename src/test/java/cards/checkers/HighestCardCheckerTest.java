package cards.checkers;

import cards.Card;
import cards.Color;
import cards.Figure;
import cards.SetOfCard;
import messages.ConfigurationCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HighestCardCheckerTest {
    private Card highestCard = new Card(Figure.King, Color.Diamonds);
    private Card firstCard = new Card(Figure.Eight, Color.Clubs);
    private Card secondCard = new Card(Figure.Jack, Color.Spades);
    private Card thirdCard = new Card(Figure.Queen, Color.Hearts);
    private Card fourthCard = new Card(Figure.Six, Color.Hearts);
    private Card fifthCard = new Card(Figure.Ten, Color.Clubs);
    private Card sixthCard = new Card(Figure.Two, Color.Diamonds);
    private List<Card> cardList;

    private ICardChecker sut = new HighestCardChecker();

    @Before
    public void Setup()
    {
        cardList = new ArrayList<Card>();
        cardList.add(firstCard);
        cardList.add(secondCard);
        cardList.add(highestCard);
        cardList.add(thirdCard);
        cardList.add(fourthCard);
        cardList.add(fifthCard);
        cardList.add(sixthCard);
    }

    @Test
    public void shouldCorrectFindHighestCard()
    {
        ConfigurationCard expected = new ConfigurationCard(SetOfCard.HighCard, highestCard);
        ConfigurationCard actual = sut.check(cardList);

        assertEquals(expected.getSetOfCard(), actual.getSetOfCard());
        assertEquals(expected.getHighestCard(), actual.getHighestCard());
    }

}