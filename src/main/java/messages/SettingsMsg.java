package messages;

public class SettingsMsg {
    private GameType type;
    private int moneyOnStart;
    private int valueOfSmallBlind;
    private int valueOfBigBlind;
    private int playerId;

    public SettingsMsg(GameType type, int moneyOnStart, int valueOfSmallBlind, int valueOfBigBlind, int playerId) {
        this.type = type;
        this.moneyOnStart = moneyOnStart;
        this.valueOfSmallBlind = valueOfSmallBlind;
        this.valueOfBigBlind = valueOfBigBlind;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
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
