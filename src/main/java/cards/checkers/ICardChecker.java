package cards.checkers;

import cards.Card;
import messages.ConfigurationCard;

import java.util.List;

/**
 * Created by maciek on 10.12.15.
 */
public interface ICardChecker {
    ConfigurationCard check(List<Card> cardList);
}
