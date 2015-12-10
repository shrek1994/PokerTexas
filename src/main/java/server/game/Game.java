package server.game;

import messages.Settings;
import messages.SettingsMsg;

import java.io.IOException;
import java.util.List;

/**
 * Created by maciek on 09.12.15.
 */
public class Game {
    private TexasHoldRoundFactory texasHoldRoundFactory;
    private Server server;
    private Settings settings;
    private List<IPlayer> playerList;

    public void run() throws IOException {
        server.runServer(settings.port);
        server.waitForPlayers(settings.numberOfPlayers);
        playerList = server.getPlayersList();

        while ( playerList.size() > settings.numberOfBots ) {
            TexasHoldRound texasHoldRound = texasHoldRoundFactory.create(playerList);

            texasHoldRound.runGame();

            for (int i = 0 ; i < playerList.size(); i++) {
                IPlayer player = playerList.get(i);
                if(! player.isPlayOn())
                {
                    playerList.remove(player);
                    --i;
                }
            }
        }
    }
}
