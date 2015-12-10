package messages;

import cards.Card;
import cards.SetOfCard;

/**
 * Created by maciek on 10.12.15.
 */
public class ConfigurationCard {
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
}
