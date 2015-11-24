package cards;

import java.util.List;

/**
 * Created by maciek on 22.10.15.
 */
public interface ICollectionOfCards {
    public abstract void shuffle(int numberOfChange);
    public abstract List<Card> getCards(int number);
}
