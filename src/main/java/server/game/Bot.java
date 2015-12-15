package server.game;

import java.util.List;

import cards.Card;
import client.CardUtils;
import messages.ActionMsg;
import messages.ActionType;
import messages.RankingMsg;
import messages.ReceiverMsg;
import messages.SenderMsg;
import messages.Settings;

public class Bot implements IPlayer {

	private double money;
	private int[] cardsInHand;
	private int[] cardsOnTable;
	private double pot = 0;
	private int numberOfCards = 0;
	private int id;
	
	private static int nextBotId = 0;
	
	
	@Override
	public ActionMsg getAction() {
		ActionMsg msg;
		if (pot>money)
			return msg = new ActionMsg(ActionType.Fold, 0);
		return null;
	}

	public Bot() {
        this.id = nextBotId;
        nextBotId++;
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
		if (numberOfCards >1)
			this.cardsInHand[numberOfCards] = CardUtils.cardToInt(card);
		else{
			this.cardsOnTable[numberOfCards-2] = CardUtils.cardToInt(card);
		}
			
	}

	@Override
	public int getId() {
		return this.id;
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
