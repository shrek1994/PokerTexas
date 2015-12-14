package messages;

/**
 * Created by maciek on 10.12.15.
 */
public class Settings {
    public int port = 8080;
    public int numberOfPlayers = 3;
    public int numberOfBots = 0;
    public int smallBlind = 5;
    public int bigBlind = 10;
    public GameType gameType = GameType.NoLimit;
    public int moneyOnStart = 200;
}
