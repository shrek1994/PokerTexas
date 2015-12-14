package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 14.12.15.
 */
public class StraightChecker implements ICardChecker {
    private ICardChecker cardChecker;
    public StraightChecker(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        Collections.sort(cardList);

        for (int i = 0; i < cardList.size() - 4; i++) {
            if(cardList.get(i).getFigure().getValue() + 1 == cardList.get(i + 1).getFigure().getValue() &&
                    cardList.get(i + 1).getFigure().getValue() + 1 == cardList.get(i + 2).getFigure().getValue() &&
                    cardList.get(i + 2).getFigure().getValue() + 1 == cardList.get(i + 3).getFigure().getValue() &&
                    cardList.get(i + 3).getFigure().getValue() + 1 == cardList.get(i + 4).getFigure().getValue())
                return new ConfigurationCard(SetOfCard.Straight, cardList.get(i + 4));
        }

        return cardChecker.check(cardList);
    }
}
