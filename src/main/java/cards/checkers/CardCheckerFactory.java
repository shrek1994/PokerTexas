package cards.checkers;

/**
 * Created by maciek on 11.12.15.
 */
public class CardCheckerFactory {
    public ICardChecker create() {
        ICardChecker highestCardChecker = new HighestCardChecker();
        ICardChecker onePairChecker = new OnePairChecker(highestCardChecker);
        ICardChecker twoPairChecker = new TwoPairChecker(onePairChecker);
        ICardChecker threeOfKindChecker = new ThreeOfKindChecker(twoPairChecker);
        ICardChecker straightChecker = new StraightChecker(threeOfKindChecker);
        ICardChecker flushChecker = new FlushChecker(straightChecker);
        ICardChecker fullHouseChecker = new FullHouseChecker(flushChecker);
        ICardChecker fourOfKindChecker = new FourOfKindChecker(fullHouseChecker);
        ICardChecker straightFlushChecker = new StraightFlushChecker(fourOfKindChecker);
        return straightFlushChecker;
    }
}
