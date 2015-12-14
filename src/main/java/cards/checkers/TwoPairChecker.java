package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 14.12.15.
 */
public class TwoPairChecker implements ICardChecker {
    private ICardChecker cardChecker;
    public TwoPairChecker(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        Collections.sort(cardList);
        int pair = 0;
        Card lastCard = null;
        for( Card card : cardList)
        {
            if( lastCard != null && card.getFigure() == lastCard.getFigure()) {
                pair++;
                lastCard = null;
            }
            lastCard = card;
        }
        if ( pair == 2)
            return new ConfigurationCard(SetOfCard.TwoPair, cardList.get(cardList.size() - 1));
        return cardChecker.check(cardList);
    }
}
