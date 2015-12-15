package client;
import java.util.Observable;
import messages.ActionMsg;
import messages.GameType;
import messages.RankingMsg;

/**
 * Fabryka dla aktualnego stanu gry.
 * Pobiera dane z serwera i uklada je w polach.
 * TODO
 * @author erinu
 *
 */
public class GameData extends Observable{

	private int numberOfPlayers = 12;
	private int cardsInHandANDOnTable[];
	private int smallBlind;
	private int bigBlind;
	private RankingMsg ranking;
	private int playerNumber = 5;
	private ActionMsg actions[];
	private String status = "WAITFORSETTINGS";
	private GameType gameType = GameType.NoLimit;
	private int numberOfCardsOnTable = 0;
	private double moneyOfPlayers[];
	
	GameData(int players){
		actions = new ActionMsg[players];
		this.numberOfPlayers = players;
		this.cardsInHandANDOnTable = new int[7];
	}
	
	void setNumberOfPlayers(int n){
		numberOfPlayers = n;
		setChanged();
	    notifyObservers();
	}
	
	void setSmallBlind(int n){
		smallBlind = n;
		setChanged();
	    notifyObservers();
	}
	
	void setBigBlind(int n){
		bigBlind = n;
		setChanged();
	    notifyObservers();
	}
	
	void setRanking(RankingMsg n){
		ranking = n;
		setChanged();
	    notifyObservers();
	}
	
	int getNumberOfPlayers(){
		return numberOfPlayers;
	}
	
	int getSmallBlind(){
		return smallBlind;
	}
	
	int getBigBlind(){
		return bigBlind;
	}
	
	RankingMsg getRankingMsg(){
		return ranking;
	}
	

	ActionMsg getActionOfPlayerX(int x){
		return actions[x];
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
		setChanged();
	    notifyObservers();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		setChanged();
	    notifyObservers();
	    System.out.println(status);
	}

	public void setActionOfPlayerX(int numberOfPlayer, ActionMsg msg) {
		this.actions[numberOfPlayer] = msg;
		setChanged();
	    notifyObservers(msg);
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
		setChanged();
	    notifyObservers();
	}

	public int getNumberOfCardsOnTable() {
		return numberOfCardsOnTable;
	}

	public void setNumberOfCardsOnTable(int numberOfCardsOnTable) {
		this.numberOfCardsOnTable = numberOfCardsOnTable;
		setChanged();
	    notifyObservers();
	}

	public int[] getCardsInHandANDOnTable() {
		return cardsInHandANDOnTable;
	}

	public int getCardsInHandANDOnTable(int n) {
		return cardsInHandANDOnTable[n];
	}
		
	public void setCardsInHandANDOnTable(int cardsInHandANDOnTable[]) {
		this.cardsInHandANDOnTable = cardsInHandANDOnTable;
		setChanged();
	    notifyObservers();
	}
	
	public void setCardsInHandANDOnTable(int pos, int n) {
		this.cardsInHandANDOnTable[pos] = n;
		setChanged();
	    notifyObservers();
	}

	public double[] getMoneyOfPlayers() {
		return moneyOfPlayers;
	}

	public void setMoneyOfPlayers(double moneyOfPlayers[]) {
		this.moneyOfPlayers = moneyOfPlayers;
	}

}
