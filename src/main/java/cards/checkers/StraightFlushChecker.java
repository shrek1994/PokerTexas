package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 09.12.15.
 */
public class StraightFlushChecker implements ICardChecker{
    private ICardChecker nextChecker;

    public StraightFlushChecker(ICardChecker nextChecker) {
        this.nextChecker = nextChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList)
    {
        ConfigurationCard configurationCard = getRoyalFlushConfiguration(cardList);
        if ( configurationCard != null)
        {
            return configurationCard;
        }
        return nextChecker.check(cardList);
    }

    private ConfigurationCard getRoyalFlushConfiguration(List<Card> cardList) {
        Collections.sort(cardList);
        for (int i = 0; i < cardList.size() - 4; i++) {
            if(cardList.get(i).getFigure().getValue() + 1 == cardList.get(i + 1).getFigure().getValue() &&
                    cardList.get(i + 1).getFigure().getValue() + 1 == cardList.get(i + 2).getFigure().getValue() &&
                    cardList.get(i + 2).getFigure().getValue() + 1 == cardList.get(i + 3).getFigure().getValue() &&
                    cardList.get(i + 3).getFigure().getValue() + 1 == cardList.get(i + 4).getFigure().getValue()
                    &&
                    cardList.get(i).getColor() == cardList.get(i + 1).getColor() &&
                    cardList.get(i + 1).getColor() == cardList.get(i + 2).getColor() &&
                    cardList.get(i + 2).getColor() == cardList.get(i + 3).getColor() &&
                    cardList.get(i + 3).getColor() == cardList.get(i + 4).getColor()
                    )
                return new ConfigurationCard(SetOfCard.StraightFlush, cardList.get(i + 4));
        }
        return null;
    }

}
