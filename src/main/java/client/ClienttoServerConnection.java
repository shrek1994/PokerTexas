package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import messages.ActionMsg;
import messages.ActionType;
import messages.BlindMsg;
import messages.CardMsg;
import messages.GameType;
import messages.InfoAboutActionMsg;
import messages.InfoAboutContinuingGameMsg;
import messages.NotifyAboutActionMsg;
import messages.RankingMsg;
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
public class ClienttoServerConnection extends Observable implements Observer{
	
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
		data.addObserver(this);
	}
	

	void getGameSettings(){
		while(data.getStatus().equals("WAITFORSETTINGS") ){
			Thread serverConnect = new Thread(){
	       	public void run() {
	                try {
	                	Object msg = null;
	                    msg = receiverMsg.receiveMsg(); 
	                    if(msg instanceof SettingsMsg){
	                    	data.setStatus("WAIT");
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
	                } catch (Exception e) {
	                	 data.setStatus("DISCONNECTED");
	                	 throw new RuntimeException(e);
	                }
	            }
	       };
    	   serverConnect.start();
           try {
               serverConnect.join(5000);
           } catch (InterruptedException e) {
        	   throw new RuntimeException(e);
           }
           serverConnect.interrupt();
       }
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
	                	 while(data.getStatus().equals("WAIT")){
		                     Object msg = receiverMsg.receiveMsg();
		                     if(msg instanceof NotifyAboutActionMsg)
		                    	 	data.setStatus("MOVE");
		                     if(msg instanceof CardMsg){
		                    	 boolean writed = false;
		                    	 data.setBetChecker(false);
		                    	 int i;
		                    	 for(i = 0; i < data.getCardsInHandANDOnTable().length && !writed; i++){
		                    		  if(data.getCardsInHandANDOnTable(i) == 0){
		                    		    	data.setCardsInHandANDOnTable(i,CardUtils.cardMsgToInt((CardMsg) msg));
		                    		    	writed = true;
		                    		    }
		                    	 }
		                    	 for (int j=0; j<data.getNumberOfPlayers(); j++){
		                    		 data.setActionOfPlayerX(j, new ActionMsg(ActionType.Check,0));
		                    	 }
		                    	 if(i>2)
		                    		 data.setNumberOfCardsOnTable(data.getNumberOfCardsOnTable()+1);
		                     }
		                     if(msg instanceof BlindMsg){
		                    	 double money[] = data.getMoneyOfPlayers();
		                    	 if(money[data.getPlayerNumber()] > ((BlindMsg) msg).getValue()){
		                    		 senderMsg.sendMsg(msg);
		                    	 }
		                    	 else{
		                    		 BlindMsg ret = new BlindMsg(0);
		                    		 senderMsg.sendMsg(ret);
		                    	 }
		                     }
		                     if(msg instanceof InfoAboutActionMsg){
		                    	 int bet = (int) ((InfoAboutActionMsg) msg).getAction().getMoney();
		                    	 double money[] = data.getMoneyOfPlayers();
		                    	 int player = ((InfoAboutActionMsg) msg).getPlayerId();
		                    	 money[player] = money[player] - bet;
		                    	 data.setActionOfPlayerX(player, ((InfoAboutActionMsg) msg).getAction());
		                    	 data.setMoneyOfPlayers(money);
		                    	 data.setPot(data.getPot()+bet);
		                    	 if (bet > data.getCurrentBet())
		                    		 data.setCurrentBet(bet);
		                    	 if (((InfoAboutActionMsg) msg).getAction().getActionType() == ActionType.Bet)
		                    		 data.setBetChecker(true);
		                     }
		                     if (msg instanceof RankingMsg){
		                    	 /*double money[] = data.getMoneyOfPlayers();
		                    	 money[((RankingMsg) msg).getPlayerIdWhoWin()] += ((RankingMsg) msg).getMoney();*/
		                    	 data.setMoneyOfPlayerX(((RankingMsg) msg).getPlayerIdWhoWin(), ((RankingMsg) msg).getMoney());
		                    	 GameData oldData = data;
		                    	 data = new GameData(oldData.getNumberOfPlayers());
		                    	 data.setStatus("WAIT");
		                   	 	 data.setGameType(oldData.getGameType());
		                   	 	 data.setBigBlind(oldData.getBigBlind());
		                   	 	 data.setNumberOfPlayers(oldData.getNumberOfPlayers());
		                   	 	 data.setSmallBlind(oldData.getSmallBlind());
		                   	 	 data.setPlayerNumber(oldData.getPlayerNumber());
		                   	 	 data.setMoneyOfPlayers(oldData.getMoneyOfPlayers());
		                   	 	 data.setAllInChecker(false);
		                   	 	 senderMsg.sendMsg(new InfoAboutContinuingGameMsg(true));
		                     }
	                	 }
	                 } catch (Exception e) {
	                    //TODO error thread
	                 }
	             }
	        };
	        waitForMove.start();
    }

	public void sendMove(Object arg1) {
		try {
            senderMsg.sendMsg(arg1);
        } catch (IOException e) {
            System.out.println("disconnect");
        }
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		setChanged();
	    notifyObservers(arg1);
	}
	
	
}
