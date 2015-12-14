package server.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
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
        server = serverSocketFactory.create(port);
    }

    public void waitForPlayers(int numberOfPlayers) throws IOException
    {
        while ( playersList.size() < numberOfPlayers )
        {
            IPlayer player = playerFactory.create(server.accept());
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
