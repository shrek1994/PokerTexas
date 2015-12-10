package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import messages.GameType;
import messages.ReceiverMsg;
import messages.SenderMsg;
import messages.SettingsMsg;

/**
 * Tymczasowa klasa dla polaczenia z serwerem.
 * Pozniej moze byc zastapiona jakims interface
 * 
 * @author erinu
 *
 */
public class ClienttoServerConnection extends Observable{
	
	private String address;
	private String port;
	private boolean connection;
	private Socket socket;
	private GameData data;
	private SenderMsg senderMsg;
	private ReceiverMsg receiverMsg;
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
	
	GameType getGameType(){
		return data.getGameType();
	}

	public boolean connectTo(String address2, String port2) throws IOException {
		port = port2;
		address = address2;
		try{
			socket = new Socket(address2, Integer.parseInt(port2));
		}
		catch(Exception e){
			//TODO connection failed
			return false;
		}
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        senderMsg = new SenderMsg(objectOutputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        receiverMsg = new ReceiverMsg(objectInputStream);
		return true;
	}
}
