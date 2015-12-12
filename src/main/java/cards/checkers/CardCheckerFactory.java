package cards.checkers;

/**
 * Created by maciek on 11.12.15.
 */
public class CardCheckerFactory {
    public ICardChecker create() {
        //TODO create cardCheckers
        ICardChecker highestCardChecker = new HighestCardChecker();

        ICardChecker royalFlushChecker = new RoyalFlushChecker(highestCardChecker);
        return royalFlushChecker;
    }
}
