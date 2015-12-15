package cards;

/**
 * Created by maciek on 19.10.15.
 */
public enum Color {
    Hearts(0), // kier (serce)
    Diamonds(1), //karo (poduszka)
    Clubs(3), //trefl (zołądz)
    Spades(4); //pik (czarne serce)

    final private int value;

    Color(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }
}
