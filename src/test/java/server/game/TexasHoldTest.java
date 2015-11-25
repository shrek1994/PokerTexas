package server.game;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import cards.Card;
import cards.Figure;
import cards.Color;
import cards.CollectionOfCards;

public class TexasHoldTest {
	private IPlayer firstPlayer = mock(IPlayer.class);
	private IPlayer secondPlayer = mock(IPlayer.class);
	private List<IPlayer> playersList;

	private CollectionOfCards cards = mock(CollectionOfCards.class);
	private Table table = mock(Table.class);
	private Auction auction = mock(Auction.class);

	private Card firstCard = new Card(Figure.Two, Color.Clubs);
	private Card secondCard = new Card(Figure.Three, Color.Diamonds);
	private Card thirdCard = new Card(Figure.Five, Color.Hearts);
	private List<Card> cardListWithThreeCards;
	private List<Card> cardListWithTwoCards;
	private List<Card> cardListWithCard;

	private TexasHold sut;

	@Before
	public void SetUp(){
		playersList = new ArrayList<IPlayer>();
		playersList.add(firstPlayer);
		playersList.add(secondPlayer);

		initCardsLists();

		sut = new TexasHold(playersList, cards, table, auction);
	}

	@After
	public void After()
	{
		verifyNoMoreInteractions(firstPlayer);
		verifyNoMoreInteractions(secondPlayer);
		verifyNoMoreInteractions(cards);
		verifyNoMoreInteractions(auction);
		verifyNoMoreInteractions(table);
	}

	private void initCardsLists() {
		cardListWithCard = new ArrayList<Card>();
		cardListWithTwoCards = new ArrayList<Card>();
		cardListWithThreeCards = new ArrayList<Card>();

		cardListWithCard.add(firstCard);

		cardListWithTwoCards.add(firstCard);
		cardListWithTwoCards.add(secondCard);

		cardListWithThreeCards.add(firstCard);
		cardListWithThreeCards.add(secondCard);
		cardListWithThreeCards.add(thirdCard);
	}

	@Test
	public void shouldCorrectPlayGame()
	{
		when(secondPlayer.getBlind(TexasHold.smallBlind)).thenReturn(TexasHold.smallBlind);
		when(firstPlayer.getBlind(TexasHold.bigBlind)).thenReturn(TexasHold.bigBlind);

		when(cards.getCards(3)).thenReturn(cardListWithThreeCards);
		when(cards.getCards(2)).thenReturn(cardListWithTwoCards);
		when(cards.getCards(1)).thenReturn(cardListWithCard);

		sut.runGame();

		verify(firstPlayer).addCard(cardListWithTwoCards.get(0));
		verify(firstPlayer).addCard(cardListWithTwoCards.get(1));
		verify(firstPlayer).getBlind(TexasHold.bigBlind);

		verify(secondPlayer).addCard(cardListWithTwoCards.get(0));
		verify(secondPlayer).addCard(cardListWithTwoCards.get(1));
		verify(secondPlayer).getBlind(TexasHold.smallBlind);

		verify(cards).shuffle(TexasHold.numberOfShuffle);
		verify(cards, times(1)).getCards(3);
		verify(cards, times(playersList.size())).getCards(2);
		verify(cards, times(2)).getCards(1);

		verify(auction, times(4)).start();

		verify(table).addCard(cardListWithThreeCards);
		verify(table, times(2)).addCard(cardListWithCard);

	}
}
