package client;


/**
 * Glowna klasa logiki aplikacji klienta. Poœredniczy miêdzy po³¹czeniem z serwerem a GUI.
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
	 * Metoda dla GUI do utworzenia po³¹czenia z serwerem w aplikacji klienta
	 * @param address adres serwera
	 * @param port port serwera
	 * @return bolean true dla udanego po³¹czenia
	 */
	boolean setUpConnection(String address, String port){
		return false;
	}
	

}
