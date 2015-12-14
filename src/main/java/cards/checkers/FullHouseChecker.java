package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 14.12.15.
 */
public class FullHouseChecker implements ICardChecker {
    private ICardChecker cardChecker;
    public FullHouseChecker(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        Collections.sort(cardList);
        int threeOfKind = 0;
        int pair = 0;
        for (int i = 0; i < cardList.size() - 2; i++) {
            if (cardList.get(i).getFigure() == cardList.get(i + 1).getFigure() ) {
                if (cardList.get(i + 1).getFigure() == cardList.get(i + 2).getFigure())
                    threeOfKind++;
                else
                    pair++;
            }
        }

        if ( threeOfKind > 0 && pair > 0)
            return new ConfigurationCard(SetOfCard.FullHouse, cardList.get(cardList.size() - 1));
        return cardChecker.check(cardList);
    }
}
