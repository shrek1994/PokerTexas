package client;

import static org.junit.Assert.*;

import org.junit.Test;

import cards.Card;
import cards.Color;
import cards.Figure;
import messages.CardMsg;

public class CardUtilsTest {

	@Test
	public void testCardValue() {
		assertEquals("{5",CardUtils.CardValue(18));
		assertEquals("}4",CardUtils.CardValue(4));
		assertEquals("]6",CardUtils.CardValue(32));
		assertEquals("[J",CardUtils.CardValue(50));
		assertEquals("{K",CardUtils.CardValue(26));
		assertEquals("",CardUtils.CardValue(0));
	}

	@Test
	public void testCardToInt() {
		Card c= new Card(Figure.Five, Color.Hearts);
		assertEquals(18,CardUtils.cardToInt(c));
		c= new Card(Figure.Queen, Color.Diamonds);
		assertEquals(50,CardUtils.cardToInt(c));
		c= new Card(Figure.Ten, Color.Spades);
		assertEquals(10,CardUtils.cardToInt(c));
		c= new Card(Figure.Ace, Color.Clubs);
		assertEquals(26,CardUtils.cardToInt(c));
	}

	@Test
	public void testCardMsgToInt() {
		Card c= new Card(Figure.Five, Color.Hearts);
		CardMsg card= new CardMsg(c);
		assertEquals(18,CardUtils.cardMsgToInt(card));
	}

}
