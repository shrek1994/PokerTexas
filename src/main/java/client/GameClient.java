package client;


/**
 * Glowna klasa logiki aplikacji klienta. Pośredniczy miedzy polaczeniem z serwerem a GUI.
 * 
 * @author erinu
 *
 */
public class GameClient {
	private GameData data = new GameData();
	private ClienttoServerConnection connection;
	boolean connectionEstablished = false;
	
	

	public GameClient(){
		connection = new ClienttoServerConnection();
	}
	
	public void updateGUI(){
		
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

}
