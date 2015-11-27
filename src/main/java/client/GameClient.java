package client;


/**
 * Glowna klasa logiki aplikacji klienta. Pośredniczy między połączeniem z serwerem a GUI.
 * 
 * @author erinu
 *
 */
public class GameClient {
	private GameData data;
	private ClienttoServerConnection connection;
	boolean connectionEstablished = false;
	
	/**
	 * Getter danych o stanie gry dla GUI itp.
	 * @return GameData dane o aktualnym stanie gry
	 */
	GameData getData(){
		return null;
	}

	public GameClient(){
		connection = new ClienttoServerConnection();
	}
	
	/**
	 * Metoda dla GUI do utworzenia połączenia z serwerem w aplikacji klienta
	 * @param address adres serwera
	 * @param port port serwera
	 * @return bolean true dla udanego połączenia
	 */
	boolean setUpConnection(String address, String port){
		return false;
	}
	

}
