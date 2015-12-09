package server.game;

import messages.ActionMsg;
import messages.ActionType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AuctionTest {
    private IPlayer firstPlayer = mock(IPlayer.class, "firstPlayer");
    private IPlayer secondPlayer = mock(IPlayer.class, "secondPlayer");
    private IPlayer thirdPlayer = mock(IPlayer.class, "thirdPlayer");
    private IPlayer fourthPlayer = mock(IPlayer.class, "fourthPlayer");
    private IPlayer fifthPlayer = mock(IPlayer.class, "fifthPlayer");
    private List<IPlayer> playerList = new ArrayList<IPlayer>();

    private Table table = mock(Table.class);

    private final ActionMsg foldAction = new ActionMsg(ActionType.Fold, 0);
    private final ActionMsg allInAction = new ActionMsg(ActionType.AllIn, 123);
    private final ActionMsg raiseAction = new ActionMsg(ActionType.Raise, 234);

    private Auction sut;

    @Before
    public void Setup(){
        playerList.add(firstPlayer);
        playerList.add(secondPlayer);
        playerList.add(thirdPlayer);
        playerList.add(fourthPlayer);
        playerList.add(fifthPlayer);

        sut = new Auction(table, playerList);
    }

    @After
    public void after()
    {
        verifyNoMoreInteractions(firstPlayer);
        verifyNoMoreInteractions(secondPlayer);
        verifyNoMoreInteractions(thirdPlayer);
        verifyNoMoreInteractions(fourthPlayer);
        verifyNoMoreInteractions(fifthPlayer);
        verifyNoMoreInteractions(table);
    }

    @Test(timeout=3000)
    public void shouldTwoTimesGetActionInOrderFromPlayers() throws Exception {
        when(firstPlayer.getAction()).thenReturn(foldAction);
        when(secondPlayer.getAction()).thenReturn(raiseAction, raiseAction);
        when(thirdPlayer.getAction()).thenReturn(allInAction);
        when(fourthPlayer.getAction()).thenReturn(raiseAction, foldAction);
        when(fifthPlayer.getAction()).thenReturn(foldAction);

        when(table.haveAllPlayersTheSameMoney()).thenReturn(false, false);

        sut.start(thirdPlayer);

        InOrder order = inOrder(firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer);
        order.verify(thirdPlayer).getAction();
        order.verify(fourthPlayer).getAction();
        order.verify(fifthPlayer).getAction();
        order.verify(firstPlayer).getAction();
        order.verify(secondPlayer).getAction();
        order.verify(fourthPlayer).getAction();
        order.verify(secondPlayer).getAction();

        verify(table, times(2)).haveAllPlayersTheSameMoney();
        verify(table, times(2)).addMoney(secondPlayer, raiseAction.getMoney());
        verify(table).addMoney(thirdPlayer, allInAction.getMoney());
        verify(table).addMoney(fourthPlayer, raiseAction.getMoney());
    }

    @Test(timeout=3000)
    public void shouldGetActionInOrderFromAllPlayers() throws Exception {
        when(firstPlayer.getAction()).thenReturn(foldAction);
        when(secondPlayer.getAction()).thenReturn(foldAction);
        when(thirdPlayer.getAction()).thenReturn(foldAction);
        when(fourthPlayer.getAction()).thenReturn(foldAction);
        when(fifthPlayer.getAction()).thenReturn(foldAction);

        when(table.haveAllPlayersTheSameMoney()).thenReturn(false);

        sut.start(thirdPlayer);

        InOrder order = inOrder(firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer);
        order.verify(thirdPlayer).getAction();
        order.verify(fourthPlayer).getAction();
        order.verify(fifthPlayer).getAction();
        order.verify(firstPlayer).getAction();
        order.verify(secondPlayer).getAction();

        verify(table).haveAllPlayersTheSameMoney();
    }

    @Test(timeout=3000)
    public void shouldStartsFromOtherPlayers() throws Exception {
        when(firstPlayer.getAction()).thenReturn(raiseAction, foldAction);
        when(secondPlayer.getAction()).thenReturn(raiseAction, raiseAction, raiseAction, raiseAction);
        when(thirdPlayer.getAction()).thenReturn(raiseAction, raiseAction, raiseAction, allInAction);
        when(fourthPlayer.getAction()).thenReturn(raiseAction, raiseAction, foldAction);
        when(fifthPlayer.getAction()).thenReturn(foldAction);

        when(table.haveAllPlayersTheSameMoney()).thenReturn(true, true, true, false);

        sut.start(thirdPlayer);
        sut.start(firstPlayer);
        sut.start(fourthPlayer);
        sut.start(secondPlayer);

        InOrder order = inOrder(firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer);
        order.verify(thirdPlayer).getAction();
        order.verify(fourthPlayer).getAction();
        order.verify(fifthPlayer).getAction();
        order.verify(firstPlayer).getAction();
        order.verify(secondPlayer).getAction();

        order.verify(firstPlayer).getAction();
        order.verify(secondPlayer).getAction();
        order.verify(thirdPlayer).getAction();

        order.verify(fourthPlayer, times(2)).getAction();

        order.verify(secondPlayer).getAction();
        order.verify(thirdPlayer).getAction();

        order.verify(secondPlayer).getAction();
        order.verify(thirdPlayer).getAction();

        verify(table, times(4)).haveAllPlayersTheSameMoney();
        verify(table).addMoney(firstPlayer, raiseAction.getMoney());
        verify(table, times(4)).addMoney(secondPlayer, raiseAction.getMoney());
        verify(table, times(3)).addMoney(thirdPlayer, raiseAction.getMoney());
        verify(table).addMoney(thirdPlayer, allInAction.getMoney());
        verify(table, times(2)).addMoney(fourthPlayer, raiseAction.getMoney());
    }
}