package cards;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maciek on 19.10.15.
 */
public enum Figure {
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Ten(10),
    Jack(11), // walet
    Queen(12), // dama
    King(13), // król
    Ace(14); //as

    final private int value;

    private Figure(int value)
    {
        this.value = value;
    }


    private static final Map<Integer, Figure> _map = new HashMap<Integer, Figure>();
    static
    {
        for (Figure figure : Figure.values())
            _map.put(figure.value, figure);
    }

    public static Figure get(int value)
    {
        return _map.get(value);
    }
}
