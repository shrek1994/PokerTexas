package cards;

import messages.ConfigurationCard;

import java.util.List;

/**
 * Created by maciek on 10.12.15.
 */
public interface ICardChecker {
    public ConfigurationCard check(List<Card> cardList);
}
