package client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import messages.ActionMsg;
import messages.ActionType;

/**
 * Glowna klasa logiki aplikacji klienta. Posredniczy miedzy polaczeniem z serwerem a GUI.
 * 
 * @author erinu
 *
 */
public class GameClient implements Observer{
	
	private ClienttoServerConnection connection;
	public ClienttoServerConnection getConnection() {
		return connection;
	}


	public void setConnection(ClienttoServerConnection connection) {
		this.connection = connection;
	}


	private boolean connectionEstablished = false;

	public GameClient(){
		connection = new ClienttoServerConnection();
		connection.addObserver(this);
	} 
	
	
	/**
	 * Metoda dla GUI do utworzenia polaczenia z serwerem w aplikacji klienta
	 * @param address adres serwera
	 * @param port port serwera
	 * @return bolean true dla udanego polaczenia
	 * @throws IOException 
	 */
	boolean setUpConnection(String address, String port) throws IOException{
		return connection.connectTo(address, port);
	}
	
	GameData getGameData(){
		return connection.getData();
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		/*if(arg1 instanceof ActionMsg){
			connection.sendMove(arg1);
		}*/
	}
	
	public boolean[] getAvailableActions() {
		int id = connection.getData().getPlayerNumber();
		boolean checkerActions[] = new boolean[6];
		ActionMsg actions[] = connection.getData().getActions();
		double money = connection.getData().getMoneyOfPlayerX(id);
		double pot = connection.getData().getPot();
		double currentBet = connection.getData().getCurrentBet();
		boolean betChecker = connection.getData().isBetChecker();
		for (int i=0; i<6; i++){
			checkerActions[i] = false;
		}
		if (currentBet == actions[id].getMoney())
			checkerActions[0] = true; 					//Check
		if (betChecker == false && money > currentBet)
			checkerActions[1] = true; 					//Bet
		if (betChecker && money > currentBet)
			checkerActions[2] = true; 					//Raise
		if (money >= currentBet && betChecker)
			checkerActions[3] = true;					//Call
		checkerActions[4] = true;						//Fold
		if (checkerActions[1] == false && checkerActions[2] == false && checkerActions[3] == false)
			checkerActions[5] = true;					//Allin
		if (connection.getData().isAllInChecker()){
			checkerActions[4] = false;
			checkerActions[5] = true;
		}
		return checkerActions;
	}


	public void setConnectionEstablished(boolean b) {
		this.connectionEstablished = b;
	}
	
	public void waitForServer(){
		this.connection.waitForMsg();
	}


	public void setSettingsFromServer() {
		connection.getGameSettings();
	}


	public void setAction(ActionType type, double bet) {
		int id = connection.getData().getPlayerNumber();
		double money = connection.getData().getMoneyOfPlayerX(id);
		if (type == ActionType.AllIn){
			bet = money;
			connection.getData().setAllInChecker(true);
		}
		if (type == ActionType.Fold || type == ActionType.Check)
			bet = 0;
		if (type == ActionType.Call)
			bet = connection.getData().getCurrentBet();
		if (type == ActionType.Bet || type == ActionType.Raise)
			if (bet <= connection.getData().getCurrentBet())
				bet = connection.getData().getCurrentBet() +1;
		if (type == ActionType.Bet)
			connection.getData().setBetChecker(true);
		connection.sendMove(new ActionMsg(type,bet));
	}
	
	
	
}
