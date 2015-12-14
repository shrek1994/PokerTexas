package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import messages.ActionMsg;
import messages.BlindMsg;
import messages.CardMsg;
import messages.GameType;
import messages.InfoAboutActionMsg;
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
		data = new GameData(12);
	}
	

	void getGameSettings(){
		System.out.println("probuje odbieranie ustwaien");
		Thread serverConnect = new Thread(){
       	public void run() {
                try {
                	while(true){
                	System.out.println("zaczynam odbieranie ustwaien");
                    Object msg = receiverMsg.receiveMsg(); 
                    System.out.println(msg.toString());
                    System.out.println(((SettingsMsg) msg).getValueOfBigBlind());
                    if(msg instanceof SettingsMsg){
                    	System.out.println("odbieranie ustwaien");
                   	 	data.setGameType(((SettingsMsg) msg).getType());
                   	 	data.setBigBlind(((SettingsMsg) msg).getValueOfBigBlind());
                   	 	data.setNumberOfPlayers(((SettingsMsg) msg).getNumberOfPlayers());
                   	 	data.setSmallBlind(((SettingsMsg) msg).getValueOfSmallBlind());
                   	 	data.setPlayerNumber(((SettingsMsg) msg).getPlayerId());
                   	 	double[] money = new double[data.getNumberOfPlayers()];
                   	 	for (int i=0; i<data.getNumberOfPlayers(); i++)
                   	 		money[i] = ((SettingsMsg) msg).getMoneyOnStart();
                   	 	data.setMoneyOfPlayers(money);
                    }
                    }
                } catch (Exception e) {
                   //TODO error thread
                }
            }
       };
       serverConnect.start();
       try {
           serverConnect.join(999);
       } catch (InterruptedException e) {
           //TODO server disconnect
       }
       serverConnect.interrupt();
	}

	public boolean connectTo(String address2, String port2) throws IOException {
		port = port2;
		address = address2;
		try{
			socket = new Socket(address2, Integer.parseInt(port2));
		}
		catch(Exception e){
			return false;
		}
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        senderMsg = new SenderMsg(objectOutputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        receiverMsg = new ReceiverMsg(objectInputStream);
		return true;
	}
	
	public void waitForMsg() {
        Thread waitForMove = new Thread(){
        	 public void run() {
                 try {
                     Object msg = receiverMsg.receiveMsg();
                     System.out.println(msg.toString());
                     if(msg instanceof NotifyAboutActionMsg)
                    	 	data.setStatus("MOVE");
                     if(msg instanceof CardMsg){
                    	 boolean writed = false;
                    	 int i;
                    	 for(i = 0; i < data.getCardsInHandANDOnTable().length && !writed; i++){
                    		    if(data.getCardsInHandANDOnTable(i) == 0){
                    		    	data.setCardsInHandANDOnTable(i,CardUtils.cardMsgToInt((CardMsg) msg));
                    		    	writed = true;
                    		    }
                    	 }
                    	 if(i>2)
                    		 data.setNumberOfCardsOnTable(data.getNumberOfCardsOnTable()+1);
                     }
                     if(msg instanceof BlindMsg){
                    	 System.out.println("wysylanie ciemnej");
                    	 double money[] = data.getMoneyOfPlayers();
                    	 if(money[data.getPlayerNumber()] > ((BlindMsg) msg).getValue()){
                    		 senderMsg.sendMsg(msg);
                    		 money[data.getPlayerNumber()] = money[data.getPlayerNumber()] - ((BlindMsg) msg).getValue();
                    		 data.setMoneyOfPlayers(money);
                    	 }
                     }
                     if(msg instanceof InfoAboutActionMsg){
                    	 System.out.println("odebranie akcji");
                    	 double money[] = data.getMoneyOfPlayers();
                    	 money[data.getPlayerNumber()] = money[data.getPlayerNumber()] - ((InfoAboutActionMsg) msg).getAction().getMoney();
                    	 data.setActionOfPlayerX(((InfoAboutActionMsg) msg).getPlayerId(), ((InfoAboutActionMsg) msg).getAction());
                    	 data.setMoneyOfPlayers(money);
                     }
                 } catch (Exception e) {
                    //TODO error thread
                 }
             }
        };
        waitForMove.start();
        try {
            waitForMove.join(99);
        } catch (InterruptedException e) {
            //TODO server disconnect
        }
        waitForMove.interrupt();
    }

	public void sendMove(Object arg1) {
		try {
            senderMsg.sendMsg(arg1);
        } catch (IOException e) {
            System.out.println("disconnect");
        }
	}
	
	
}
