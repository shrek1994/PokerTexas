package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 14.12.15.
 */
public class OnePairChecker implements ICardChecker {
    private ICardChecker cardChecker;
    public OnePairChecker(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        Collections.sort(cardList);

        Card lastCard = null;
        for( Card card : cardList)
        {
            if( lastCard != null && card.getFigure() == lastCard.getFigure())
                return new ConfigurationCard(SetOfCard.OnePair, cardList.get(cardList.size() - 1));
            lastCard = card;
        }

        return cardChecker.check(cardList);
    }
}
