package messages;

import cards.Card;
import cards.Color;
import cards.Figure;
import cards.SetOfCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationCardTest {
    private ConfigurationCard sut = new ConfigurationCard(SetOfCard.Straight, new Card(Figure.Ten, Color.Diamonds));

    private ConfigurationCard lowerConfiguration = new ConfigurationCard(SetOfCard.TwoPair, sut.getHighestCard());
    private ConfigurationCard higherConfiguration = new ConfigurationCard(SetOfCard.FourOfKind, sut.getHighestCard());
    private ConfigurationCard sameSetHigherCard = new ConfigurationCard(sut.getSetOfCard(), new Card(Figure.Ace, Color.Diamonds));
    private ConfigurationCard sameSetLowerCard = new ConfigurationCard(sut.getSetOfCard(), new Card(Figure.Two, Color.Diamonds));

    @Test
    public void shouldReturnZeroWhenConfigurationIsTheSame() {
        int zero = 0;
        assertEquals(zero, sut.compareTo(sut));
    }

    @Test
    public void shouldReturnNegativeValueWhenCompareConfigurationIsHigher() {
        int negativeValue =  sut.getSetOfCard().getValue() - higherConfiguration.getSetOfCard().getValue();
        assertTrue(negativeValue < 0);
        assertEquals(negativeValue, sut.compareTo(higherConfiguration));
    }

    @Test
    public void shouldReturnPositiveValueWhenConfigurationIsHigher() {
        int positiveValue = sut.getSetOfCard().getValue() - lowerConfiguration.getSetOfCard().getValue();
        assertTrue(positiveValue > 0);
        assertEquals(positiveValue, sut.compareTo(lowerConfiguration));
    }

    @Test
    public void shouldReturnNegativeValueWhenCompareConfigurationIsSameAndHighestCard() {
        int negativeValue =  sut.getHighestCard().getFigure().getValue()
                - sameSetHigherCard.getHighestCard().getFigure().getValue();
        assertTrue(negativeValue < 0);
        assertEquals(negativeValue, sut.compareTo(sameSetHigherCard));
    }

    @Test
    public void shouldReturnPositiveValueWhenConfigurationIsSameAndLowerCard() {
        int positiveValue =  sut.getHighestCard().getFigure().getValue()
                - sameSetLowerCard.getHighestCard().getFigure().getValue();
        assertTrue(positiveValue > 0);
        assertEquals(positiveValue, sut.compareTo(sameSetLowerCard));
    }
}