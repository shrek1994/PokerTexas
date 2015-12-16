package messages;

/**
 * Created by maciek on 28.11.15.
 */
public enum GameType {
    NoLimit(0),
    PotLimit(1),
    FixedLimit(2);


    final private int value;

    GameType(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    static public GameType getGameType(int value)
    {
        for ( GameType gameType : GameType.values())
        {
            if ( gameType.value == value)
                return gameType;
        }
        return null;
    }
}
