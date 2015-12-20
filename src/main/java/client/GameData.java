package client;
import java.util.Observable;
import messages.ActionMsg;
import messages.ActionType;
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
	private int pot=0;
	private int currentBet=0;
	private boolean betChecker = false;
	private boolean allInChecker = false;
	
	GameData(int players){
		this.actions = new ActionMsg[players];
		this.numberOfPlayers = players;
		this.cardsInHandANDOnTable = new int[7];
		this.moneyOfPlayers = new double[players];
	}
	
	void setNumberOfPlayers(int n){
		this.numberOfPlayers = n;
		this.moneyOfPlayers = new double[n];
		this.actions = new ActionMsg[n];
		for (int i=0; i<n; i++)
			actions[i] = new ActionMsg(ActionType.Check,0);
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
	
	ActionMsg[] getActions(){
		return this.actions;
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
	
	public double getMoneyOfPlayerX(int i){
		return moneyOfPlayers[i];
	}

	public void setMoneyOfPlayers(double moneyOfPlayers[]) {
		this.moneyOfPlayers = moneyOfPlayers;
		setChanged();
	    notifyObservers();
	}
	
	public void setMoneyOfPlayerX(int numberOfPlayer, double money) {
		this.moneyOfPlayers[numberOfPlayer] = money;
		setChanged();
	    notifyObservers();
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
		setChanged();
	    notifyObservers();
	}

	public int getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
		setChanged();
	    notifyObservers();
	}

	public boolean isBetChecker() {
		return betChecker;
	}

	public void setBetChecker(boolean betChecker) {
		this.betChecker = betChecker;
	}

	public boolean isAllInChecker() {
		return allInChecker;
	}

	public void setAllInChecker(boolean allInChecker) {
		this.allInChecker = allInChecker;
	}

}
