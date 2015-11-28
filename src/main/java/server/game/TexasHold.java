package server.game;

import java.util.List;

import cards.Card;
import cards.CollectionOfCards;

public class TexasHold {
	private final int numberOfCardForPlayer = 2;
	private int indexOfPlayerWihDealerButton = 0;
	static final int smallBlind = 5;
	static final int bigBlind = 10;
	static final int numberOfShuffle = 6969;

	private Table table;
	private List<IPlayer> players;
	private CollectionOfCards cards;
	private Auction auction;

	public TexasHold(List<IPlayer> players, CollectionOfCards cards, Table table, Auction auction)
	{
		this.players = players;
		this.cards = cards;
		this.table = table;
		this.auction = auction;
	}

	public void runGame()
	{
		//placenie ciemnych
		IPlayer playerShouldPaySmallBlind = players.get(getIndexOfPlayerOnTheLeftOfDealerButton());
		IPlayer playerShouldPayBigBlind = players.get(getIndexOfPlayerTwoOnTheLeftOfDealerButton());

		playerShouldPaySmallBlind.getBlind(smallBlind);
		playerShouldPayBigBlind.getBlind(bigBlind);

		//potasowanie kart
		cards.createNewDeckCard();
		cards.shuffle(numberOfShuffle);

		//rozdanie kart graczom
		for( IPlayer player : players)
		{
			List<Card> cardsList = this.cards.getCards(numberOfCardForPlayer);
			for ( Card card : cardsList)
			{
				player.addCard(card);
			}
		}

		//pierwsza licytacja
		auction.start();

		//wylozenie na stol 3 karty
		table.addCard(cards.getCards(3));

		//druga licytacja
		auction.start();

		//wylozenie na stol 1 karty
		table.addCard(cards.getCards(1));

		//trzecia licytacja
		auction.start();

		//wylozenie na stol 1 karty
		table.addCard(cards.getCards(1));

		//czwarta licytacja
		auction.start();

		//przesuniecie
		indexOfPlayerWihDealerButton++;
		if(indexOfPlayerWihDealerButton >= players.size())
		{
			indexOfPlayerWihDealerButton = 0;
		}
	}

	private int getIndexOfPlayerOnTheLeftOfDealerButton() {
		return indexOfPlayerWihDealerButton + 1 >= players.size() ?
				0 : indexOfPlayerWihDealerButton + 1;
	}

	private int getIndexOfPlayerTwoOnTheLeftOfDealerButton() {
		return getIndexOfPlayerOnTheLeftOfDealerButton() + 1 >= players.size() ?
				0 : getIndexOfPlayerOnTheLeftOfDealerButton() + 1;
	}
}
