package cards;

/**
 * Created by maciek on 09.12.15.
 */
public enum SetOfCard {
    HighCard(0), //wysoka karta
    OnePair(1), // para
    TwoPair(2), //dwie pary
    ThreeOfKind(3), //trojka
    Straight(4), // strit
    Flush(5), //kolor
    FullHouse(6), // full
    FourOfKind(7), //kareta
    StraightFlush(8), //poker
    RoyalFlush(9); // poker krolewski

    private final int value;
    private SetOfCard(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
