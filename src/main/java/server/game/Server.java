package server.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Server {
    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private ServerSocket server;
    private PlayerFactory playerFactory;
    private List<IPlayer> playersList;
    private ServerSocketFactory serverSocketFactory;

    public Server(PlayerFactory playerFactory, ServerSocketFactory serverSocketFactory) {
        this.playerFactory = playerFactory;
        this.playersList = new ArrayList<IPlayer>();
        this.serverSocketFactory = serverSocketFactory;
    }

    public void runServer(int port) throws IOException
    {
        logger.info("Running server at port: " + port);
        server = serverSocketFactory.create(port);
    }

    public void waitForPlayers(int numberOfPlayers) throws IOException
    {
        while ( playersList.size() < numberOfPlayers )
        {
            IPlayer player = playerFactory.create(server.accept());
            logger.info("Player[" + player.getId() + "] connected");
            playersList.add(player);
            //TODO sprawdzanie czy player sie nie rozlaczyl
        }
    }
    
    public List<IPlayer> getPlayersList()
    {
        return playersList;
    }
    
    public void close() throws IOException
    {
        server.close();
    }
}
