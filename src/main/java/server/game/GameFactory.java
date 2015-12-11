package server.game;

import messages.Settings;

/**
 * Created by maciek on 10.12.15.
 */
public class GameFactory {
    public Game create(Settings settings) {
        PlayerFactory playerFactory = new PlayerFactory();
        ServerSocketFactory serverSocketFactory = new ServerSocketFactory();
        Server server = new Server(playerFactory, serverSocketFactory);
        TexasHoldRoundFactory texasHoldRoundFactory = new TexasHoldRoundFactory();
        Game game = new Game(texasHoldRoundFactory, server, settings);
        return game;
    }
}
