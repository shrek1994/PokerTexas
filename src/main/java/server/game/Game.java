package server.game;

import messages.Settings;

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

    public Game(TexasHoldRoundFactory texasHoldRoundFactory, Server server, Settings settings) {
        this.texasHoldRoundFactory = texasHoldRoundFactory;
        this.server = server;
        this.settings = settings;
    }

    public void run() throws IOException {
        server.runServer(settings.port);
        server.waitForPlayers(settings.numberOfPlayers);
        playerList = server.getPlayersList();
        for (IPlayer player : playerList)
        {
            player.setSettings(settings);
        }
        TexasHoldRound texasHoldRound = texasHoldRoundFactory.create(playerList, settings);

        while ( playerList.size() > settings.numberOfBots ) {

            texasHoldRound.runRound();

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
