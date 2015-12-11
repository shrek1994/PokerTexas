package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import messages.ActionMsg;
import messages.GameType;
import messages.NotifyAboutActionMsg;
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

	ClienttoServerConnection(){
		//TODO get from server number of players;
		data = new GameData(12);
	}
	
	public int getNumberOfPlayers() {
		return data.getNumberOfPlayers();
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
		return true;
		//TODO wait for server
		/*
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
		return true;*/
	}
	
	public void waitForStatusMove() {
        Thread waitForMove = new Thread(){
        	 public void run() {
                 try {
                     Object msg = receiverMsg.receiveMsg();
                     if(msg instanceof NotifyAboutActionMsg)
                             data.setStatus("MOVE");
                 } catch (Exception e) {
                    //TODO error thread
                 }
             }
        };
        waitForMove.start();
        try {
            waitForMove.join(999999999);
        } catch (InterruptedException e) {
            //TODO server disconnect
        }
        waitForMove.interrupt();
    }
	
	
}
