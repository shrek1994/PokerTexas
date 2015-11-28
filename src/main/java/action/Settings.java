package action;

public class Settings {
    private GameType type;
    private int moneyOnStart;
    private int valueOfSmallBlind;
    private int valueOfBigBlind;

    public Settings(GameType type, int moneyOnStart, int valueOfSmallBlind, int valueOfBigBlind) {
        this.type = type;
        this.moneyOnStart = moneyOnStart;
        this.valueOfSmallBlind = valueOfSmallBlind;
        this.valueOfBigBlind = valueOfBigBlind;
    }

    public GameType getType() {
        return type;
    }

    public int getMoneyOnStart() {
        return moneyOnStart;
    }

    public int getValueOfSmallBlind() {
        return valueOfSmallBlind;
    }

    public int getValueOfBigBlind() {
        return valueOfBigBlind;
    }
}
