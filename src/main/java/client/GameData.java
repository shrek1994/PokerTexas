package client;
import java.util.Observable;
import messages.ActionMsg;

/**
 * Fabryka dla aktualnego stanu gry.
 * Pobiera dane z serwera i uklada je w polach.
 * TODO
 * @author erinu
 *
 */
public class GameData extends Observable{

	int numberOfPlayers;
	int cardsInHand[];
	int cardsOnTable[];

	ActionMsg actions[];
	
	void writeValues(int numberOfPlayers, int[] cardsInHand, int[] cardsOnTable){
		this.numberOfPlayers = numberOfPlayers;
		this.cardsInHand = cardsInHand;
		this.cardsOnTable = cardsOnTable;
		setChanged();
	    notifyObservers();
	}

}
