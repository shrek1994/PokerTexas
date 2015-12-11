package client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Glowna klasa logiki aplikacji klienta. Poœredniczy miedzy polaczeniem z serwerem a GUI.
 * 
 * @author erinu
 *
 */
public class GameClient implements Observer{
	
	private ClienttoServerConnection connection;
	private boolean connectionEstablished = false;
	private GameData data;
	

	public GameClient(){
		connection = new ClienttoServerConnection();
		data = new GameData(connection.getNumberOfPlayers());
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
		return data;
	}

	/**
	 * Update GameData wedlug tego co jest na serwerze
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}


	public boolean[] getAvailableActions() {
		boolean actions[];
		actions = new boolean[6];
		for (int i=0; i<6; i++){
			switch (data.getGameType()){
			case FixedLimit:
				break;
			case NoLimit:
				break;
			case PotLimit:
				break;
			default:
				break;
			}
			actions[i] = true;
		}
		return actions;
	}


	public void setConnectionEstablished(boolean b) {
		this.connectionEstablished = b;
	}
	
	public void waitForYourMove(){
		this.connection.waitForStatusMove();
	}
	
}
