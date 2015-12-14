package cards.checkers;

/**
 * Created by maciek on 11.12.15.
 */
public class CardCheckerFactory {
    public ICardChecker create() {
        //TODO create cardCheckers
        ICardChecker highestCardChecker = new HighestCardChecker();
        ICardChecker onePairChecker = new OnePairChecker(highestCardChecker);
        ICardChecker twoPairChecker = new TwoPairChecker(onePairChecker);
        ICardChecker threeOfKindChecker = new ThreeOfKindChecker(twoPairChecker);
        ICardChecker straightChecker = new StraightChecker(threeOfKindChecker);
        ICardChecker flushChecker = new FlushChecker(straightChecker);

        ICardChecker royalFlushChecker = new RoyalFlushChecker(onePairChecker);
        return royalFlushChecker;
    }
}
