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

	//TODO gather information from the server about state of the game
	
	private int money;
	private int[] cardsInHand = {-1,-1};
	private int[] cardsOnTable = {-1,-1,-1,-1,-1};
	private int pot = 0;
	private int currentBet = 0;
	private int numberOfCards = 0;
	private int id;
	private int myBet = 0;
	
	private static int nextBotId = 0;
	
	
	@Override
	public ActionMsg getAction() {
		int eagerMeter = assessSituation();
		ActionMsg msg;
		if (pot>money)
			return msg = new ActionMsg(ActionType.Fold, 0);
		if (currentBet == myBet)
			return msg = new ActionMsg(ActionType.Check, 0);
		if (eagerMeter<2 && myBet<(money/2))
			return msg = new ActionMsg(ActionType.Fold, 0);
		if (eagerMeter > 2 && myBet<currentBet  && currentBet<(money/10)*eagerMeter){
			int bet = (money/10)*eagerMeter;
			money -= bet;
			myBet = bet;
			currentBet = bet;
			pot += bet;
			return msg = new ActionMsg(ActionType.Raise,bet);
		}
		if (eagerMeter > 8){
			money = 0;
			pot += money;
			return msg = new ActionMsg(ActionType.AllIn, money);
		}
		//TODO when to bet?
		return msg = new ActionMsg(ActionType.Fold, money);
	}

	private int assessSituation() {
		int assessment = 0;
		if (cardsInHand[0]%13 == cardsInHand[1]%13)
			assessment += 3;
		if (cardsInHand[0]/13 == cardsInHand[1]/13)
			assessment++;
		if (pot/3 < money)
			assessment++;
		//TODO assessment from card hands
		return assessment;
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
