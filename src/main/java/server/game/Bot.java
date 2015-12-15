package server.game;

import java.util.List;

import cards.Card;
import messages.ActionMsg;
import messages.RankingMsg;
import messages.Settings;

public class Bot implements IPlayer {

	private double money;
	private int[] cardsInHand;
	private int[] cardsOnTable;
	private double pot = 0;
	private int numberOfCards = 0;;
	
	
	@Override
	public ActionMsg getAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBlind(int value) {
		if (money > value){
			money -= value;
			return value;
		}
		else{
			return 0;
		}
	}

	@Override
	public void addCard(Card card) {
	
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Card> getCardList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearCards() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPlayOn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateCashIfWin(RankingMsg rankingMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSettings(Settings settings) {
		this.money = settings.moneyOnStart;
	}

}
