package client;

import java.net.Socket;
import java.util.Observable;

import messages.SettingsMsg;

/**
 * Tymczasowa klasa dla polaczenia z serwerem.
 * Pozniej moze byc zastapiona jakims interface
 * 
 * @author erinu
 *
 */
public class ClienttoServerConnection extends Observable{
	
	String address;
	String port;
	boolean connection;
	Socket socket;
	private GameData data;

	/**
	 * Getter danych o stanie gry dla GUI itp.
	 * @return GameData dane o aktualnym stanie gry
	 */
	GameData getData(){
		return data;
	}

	public int getNumberOfPlayers() {
		// TODO
		return 12;
	}
	
	
	/**
	 * Pobiera liczbe graczy z serwera i powiadamia clienta wysylajac dane
	 */
	void setSettingsFromServer(){
		//get from server
		SettingsMsg msg = null;
		notifyObservers(msg);
	}
}
