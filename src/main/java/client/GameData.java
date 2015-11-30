package client;
import java.util.Observable;
import messages.ActionMsg;
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
	private int cardsInHand[];
	private int cardsOnTable[];
	private int smallBlind;
	private int bigBlind;
	private RankingMsg ranking;

	private ActionMsg actions[];
	
	void setNumberOfPlayers(int n){
		numberOfPlayers = n;
		setChanged();
	    notifyObservers();
	}
	
	void setCardsInHand(int[] n){
		cardsInHand = n;
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
	
	int getNumberOfPlayer(){
		return numberOfPlayers;
	}
	
	int[] getCardsInHand(){
		return cardsInHand;
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
	
	int[] getCardsOnTable(){
		return cardsOnTable;
	}
	
	ActionMsg getActionOfPlayerX(int x){
		return actions[x];
	}

}
