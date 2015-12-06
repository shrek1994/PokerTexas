package client;

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
	boolean connectionEstablished = false;
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
	 */
	boolean setUpConnection(String address, String port){
		return true;
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

}
