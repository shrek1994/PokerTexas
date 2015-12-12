package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 12.12.15.
 */
public class HighestCardChecker implements ICardChecker {

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        int indexOfLastCard = cardList.size() - 1;
        Collections.sort(cardList);
        ConfigurationCard configurationCard = new ConfigurationCard(SetOfCard.HighCard, cardList.get(indexOfLastCard));
        return configurationCard;
    }
}
