package messages;

import cards.Card;
import cards.SetOfCard;

import java.util.Comparator;

/**
 * Created by maciek on 10.12.15.
 */
public class ConfigurationCard implements Comparable<ConfigurationCard> {
    private SetOfCard setOfCard;
    private Card highestCard;

    public ConfigurationCard(SetOfCard setOfCard, Card highestCard) {
        this.setOfCard = setOfCard;
        this.highestCard = highestCard;
    }

    public SetOfCard getSetOfCard() {
        return setOfCard;
    }

    public Card getHighestCard() {
        return highestCard;
    }

    @Override
    public int compareTo(ConfigurationCard card) {
        if( this.setOfCard.getValue() == card.setOfCard.getValue())
        {
            return this.highestCard.getFigure().getValue() - card.highestCard.getFigure().getValue();
        }
        return this.setOfCard.getValue() - card.setOfCard.getValue();
    }
}
