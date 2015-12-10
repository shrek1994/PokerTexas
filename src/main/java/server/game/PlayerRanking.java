package server.game;

import cards.ICardChecker;

import java.util.List;

/**
 * Created by maciek on 10.12.15.
 */
public class PlayerRanking {
    private List<IPlayer> playerList;
    private ICardChecker cardChecker;

    public IPlayer getWinner()
    {
        // TODO getWinner
        return null;
    }

    public PlayerRanking(List<IPlayer> playerList, ICardChecker cardChecker) {
        this.playerList = playerList;
        this.cardChecker = cardChecker;
    }
}
