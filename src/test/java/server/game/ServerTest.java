package server.game;

import cards.Card;
import cards.Color;
import cards.Figure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.IOException;
import java.net.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by maciek on 28.11.15.
 */
public class ServerTest {
    private final int port = 1234;
    private final int numberOfPlayers = 2;

    private Socket firstSocket = mock(Socket.class);
    private Socket secondSocket = mock(Socket.class);
    private ServerSocket serverSocket = mock(ServerSocket.class);
    private IPlayer iplayer = mock(IPlayer.class);
    private Player player = mock(Player.class);
    private PlayerFactory playerFactory = mock(PlayerFactory.class);
    private ServerSocketFactory serverSocketFactory = mock(ServerSocketFactory.class);
    private Server sut;

    @Before
    public void Setup()
    {
        sut = new Server(playerFactory, serverSocketFactory);
    }

    @After
    public void After()
    {
        verifyNoMoreInteractions(firstSocket);
        verifyNoMoreInteractions(secondSocket);
        verifyNoMoreInteractions(serverSocket);
        verifyNoMoreInteractions(serverSocketFactory);
        verifyNoMoreInteractions(iplayer);
        verifyNoMoreInteractions(player);
        verifyNoMoreInteractions(playerFactory);
    }

    @Test
    public void test() throws IOException {
        when(serverSocketFactory.create(port)).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(firstSocket, secondSocket);
        when(playerFactory.create(firstSocket)).thenReturn(iplayer);
        when(playerFactory.create(secondSocket)).thenReturn(player);

        sut.runServer(port);
        sut.waitForPlayers(numberOfPlayers);

        List<IPlayer> playerList = sut.getPlayersList();

        sut.close();

        assertEquals(numberOfPlayers, playerList.size());
        assertEquals(iplayer, playerList.get(0));
        assertEquals(player, playerList.get(1));

        verify(serverSocketFactory).create(port);
        verify(playerFactory, times(2)).create(Matchers.<Socket>any());
        verify(serverSocket, times(2)).accept();
        verify(serverSocket, times(1)).close();
    }
}
