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
		boolean actions[];
		actions = new boolean[6];
		for (int i=0; i<6; i++){
			actions[i] = true;
			switch (connection.getData().getGameType()){
			case FixedLimit:
				break;
			case NoLimit:
				break;
			case PotLimit:
				break;
			default:
				break;
			}
		}
		for (int i=0; i<connection.getData().getNumberOfPlayers();i++){
			if (i != connection.getData().getPlayerNumber())
				if (connection.getData().getActionOfPlayerX(i).getActionType() != ActionType.Check){
					actions[0]=false;
				}
		}
		if (connection.getData().getCurrentBet()>connection.getData().getMoneyOfPlayerX(id)){
			actions[1] = false;
			actions[2] = false;
			actions[3] = false;
		}
		if (connection.getData().getActionOfPlayerX(id) != null){
			if (connection.getData().getCurrentBet() == connection.getData().getActionOfPlayerX(id).getMoney())
				actions[2] = false;
		}
		return actions;
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
		if (type == ActionType.AllIn)
			bet = money;
		if (type == ActionType.Fold)
			bet = 0;
		if (type == ActionType.Call)
			bet = connection.getData().getCurrentBet();
		connection.sendMove(new ActionMsg(type,bet));
	}
	
	
	
}
