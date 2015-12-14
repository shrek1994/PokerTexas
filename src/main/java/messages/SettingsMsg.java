package messages;

import java.io.Serializable;

public class SettingsMsg implements Serializable {
    private GameType type;
    private int moneyOnStart;
    private int valueOfSmallBlind;
    private int valueOfBigBlind;
    private int playerId;
    private int raiseLimitWithFixedLimit = 5; //TODO
    private int numberOfPlayers = 3;//TODO

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

	public int getRaiseLimitWithFixedLimit() {
		return raiseLimitWithFixedLimit;
	}
	
    @Override
    public boolean equals(Object o)
    {
        // using in tests
        if ( o instanceof SettingsMsg)
        {
            SettingsMsg msg = (SettingsMsg)o;
            return msg.type == type &&
                    msg.moneyOnStart == moneyOnStart &&
                    msg.valueOfSmallBlind == valueOfSmallBlind &&
                    msg.valueOfBigBlind == valueOfBigBlind &&
                    msg.playerId == playerId;
        }
        return false;
    }

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

}
