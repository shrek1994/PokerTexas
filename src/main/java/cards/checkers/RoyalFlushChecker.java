package cards.checkers;

import cards.Card;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 09.12.15.
 */
public class RoyalFlushChecker implements ICardChecker{
    private ICardChecker nextChecker;

    public RoyalFlushChecker(ICardChecker nextChecker) {
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
        //TODO implement
        return null;
    }

}
