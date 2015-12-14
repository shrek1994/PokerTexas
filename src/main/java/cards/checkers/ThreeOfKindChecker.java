package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 14.12.15.
 */
public class ThreeOfKindChecker implements ICardChecker {
    private ICardChecker cardChecker;
    public ThreeOfKindChecker(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        Collections.sort(cardList);

        for (int i = 0; i < cardList.size() - 2 ; i++) {
            if( cardList.get(i).getFigure() == cardList.get(i+1).getFigure()
                    && cardList.get(i+1).getFigure() == cardList.get(i+2).getFigure() ) {
                return new ConfigurationCard(SetOfCard.ThreeOfKind, cardList.get(cardList.size() - 1));
            }
        }

        return cardChecker.check(cardList);
    }
}
