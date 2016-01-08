package server.game;


import messages.ActionMsg;
import messages.ActionType;
import messages.InfoAboutActionMsg;

import java.util.*;

public class Auction {
    private final int onePlayer = 1;
    private Map<IPlayer, ActionMsg> lastActions = new HashMap<IPlayer, ActionMsg>();
    private Table table;
    private List<IPlayer> playerList;

    public Auction(Table table, List<IPlayer> playerList) {
        this.table = table;
        this.playerList = playerList;
    }

    public void clearMap()
    {
        lastActions = new HashMap<IPlayer, ActionMsg>();
    }

    public void start(IPlayer playerWhoStart){
        //TODO dodac max ilosc zagran raise (fixed-limit)
        //Ustawienie kolejnosci licytacji
        List<IPlayer> players = new ArrayList<IPlayer>();

        int position = playerList.indexOf(playerWhoStart);
        for (int i = position; i < playerList.size(); i++) {
            IPlayer player = playerList.get(i);
            if(lastActions.get(player) == null
                || (lastActions.get(player).getActionType() != ActionType.Fold
                    && lastActions.get(player).getActionType() != ActionType.AllIn) ) {
                players.add(player);
            }
        }
        for (int i = 0; i < position; i++) {
            IPlayer player = playerList.get(i);
            if(lastActions.get(player) == null
                    || (lastActions.get(player).getActionType() != ActionType.Fold
                        && lastActions.get(player).getActionType() != ActionType.AllIn)) {
                players.add(player);
            }
        }

        //Licytacja
        do {
            for(int i = 0; i < players.size(); i++)
            {
                IPlayer player = players.get(i);
                ActionMsg action = player.getAction();
                saveLastAction(player, action);
                notifyAllAboutAction(action, player.getId());

                switch (action.getActionType())
                {
                    case Check:
                        break;
                    case AllIn:
                        players.remove(player);
                        i--;
                        //specifically the lack of break;
                    case Bet:
                    case Raise:
                    case Call:
                        table.addMoney(player, action.getMoney());
                        break;
                    case Fold:
                        players.remove(player);
                        i--;
                        break;
                }
            }
        } while( ! table.haveAllPlayersTheSameMoney() &&
                players.size() > onePlayer &&
                ! allPlayersFoldOrCheckOrCall() );
    }

    private void notifyAllAboutAction(ActionMsg action, int playerId) {
        for (IPlayer player: playerList)
        {
            if ( player instanceof Observer)
                ((Observer)player).update(null, new InfoAboutActionMsg(playerId, action));
        }
    }

    private boolean allPlayersFoldOrCheckOrCall() {
        int numberOfRaiseAndBet = 0;
        for (Map.Entry<IPlayer, ActionMsg> pair : lastActions.entrySet())
        {
            //TODO change
            if (pair.getValue().getActionType() == ActionType.Raise ||
                    pair.getValue().getActionType() == ActionType.Bet)
                numberOfRaiseAndBet++;

            if ( pair.getValue().getActionType() != ActionType.Check &&
                    pair.getValue().getActionType() != ActionType.Call &&
                    pair.getValue().getActionType() != ActionType.Fold &&
                    numberOfRaiseAndBet > 1)
            {
                return false;
            }
        }
        return true;
    }

    private void saveLastAction(IPlayer player, ActionMsg action) {
        lastActions.put(player, action);
    }

}
