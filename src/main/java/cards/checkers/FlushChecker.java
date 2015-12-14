package cards.checkers;

import cards.Card;
import cards.SetOfCard;
import messages.ConfigurationCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by maciek on 14.12.15.
 */
public class FlushChecker implements ICardChecker {
    private ICardChecker cardChecker;
    public FlushChecker(ICardChecker cardChecker) {
        this.cardChecker = cardChecker;
    }

    @Override
    public ConfigurationCard check(List<Card> cardList) {
        Collections.sort(cardList);

        int hearts = 0, diamonds = 0, clubs = 0, spades = 0;
        for (Card card : cardList) {
            switch (card.getColor())
            {
                case Clubs:
                    clubs++;
                    break;
                case Diamonds:
                    diamonds++;
                    break;
                case Hearts:
                    hearts++;
                    break;
                case Spades:
                    spades++;
                    break;
            }
        }

        if ( hearts > 4 || diamonds > 4 || clubs > 4 || spades > 4)
        {
            return new ConfigurationCard(SetOfCard.Flush, cardList.get(cardList.size() - 1));
        }

        return cardChecker.check(cardList);
    }
}
