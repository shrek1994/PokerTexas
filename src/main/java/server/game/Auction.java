package server.game;


import messages.ActionMsg;
import messages.ActionType;

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
                ! allPlayersFoldOrCheck() );
    }

    private boolean allPlayersFoldOrCheck() {
        for (Map.Entry<IPlayer, ActionMsg> pair : lastActions.entrySet())
        {
            if ( pair.getValue().getActionType() != ActionType.Check &&
                    pair.getValue().getActionType() != ActionType.Fold)
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
