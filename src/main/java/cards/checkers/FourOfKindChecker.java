package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 15.12.15.
 */
public class FourOfKindChecker implements ICardChecker {
    private ICardChecker cardChecker;

    public FourOfKindChecker(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        Collections.sort(cardList);

        for (int i = 0; i < cardList.size() - 3; i++) {
            if( cardList.get(i).getFigure() == cardList.get(i+1).getFigure()
                    && cardList.get(i+1).getFigure() == cardList.get(i+2).getFigure()
                    && cardList.get(i+2).getFigure() == cardList.get(i+3).getFigure()) {
                return new ConfigurationCard(SetOfCard.FourOfKind, cardList.get(cardList.size() - 1));
            }
        }

        return cardChecker.check(cardList);
    }
}
