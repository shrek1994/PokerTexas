package cards.checkers;

import cards.Card;
import cards.Color;
import cards.Figure;
import cards.SetOfCard;
import messages.ConfigurationCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

/**
 * Created by maciek on 15.12.15.
 */
public class FourOfKindCheckerTest {
    //TODO powinno zwracac czworke jako najwyzsza karta
    private Card highestCard = new Card(Figure.King, Color.Diamonds);
    private Card firstCard = new Card(Figure.Ten, Color.Clubs);
    private Card secondCard = new Card(Figure.Jack, Color.Spades);
    private Card thirdCard = new Card(Figure.Ten, Color.Hearts);
    private Card fourthCard = new Card(Figure.Ten, Color.Hearts);
    private Card fifthCard = new Card(Figure.Ten, Color.Clubs);
    private Card sixthCard = new Card(Figure.Two, Color.Diamonds);
    private List<Card> cardList;

    ICardChecker cardChecker = mock(ICardChecker.class);

    ICardChecker sut;

    @Before
    public void setUp() throws Exception {
        cardList = new ArrayList<Card>();
        cardList.add(firstCard);
        cardList.add(secondCard);
        cardList.add(highestCard);
        cardList.add(thirdCard);
        cardList.add(fourthCard);
        cardList.add(fifthCard);
        cardList.add(sixthCard);

        sut = new FourOfKindChecker(cardChecker);
    }

    @After
    public void After()
    {
        verifyNoMoreInteractions(cardChecker);
    }

    @Test
    public void shouldCorrectFindThreeOfKindAndHighestCard()
    {
        ConfigurationCard expected = new ConfigurationCard(SetOfCard.FourOfKind, highestCard);
        ConfigurationCard actual = sut.check(cardList);

        assertEquals(expected.getSetOfCard(), actual.getSetOfCard());
        assertEquals(expected.getHighestCard(), actual.getHighestCard());
    }

    @Test
    public void shouldNotFindThreeOfKindAndCallNextChecker()
    {
        ConfigurationCard expected = new ConfigurationCard(SetOfCard.HighCard, firstCard);
        when(cardChecker.check(anyList())).thenReturn(expected);

        cardList.remove(fifthCard);
        ConfigurationCard actual = sut.check(cardList);

        verify(cardChecker).check(anyList());

        assertEquals(highestCard, cardList.get(cardList.size() - 1));
        assertEquals(expected.getSetOfCard(), actual.getSetOfCard());
        assertEquals(expected.getHighestCard(), actual.getHighestCard());
    }

}