package client;

import cards.Card;
import cards.Color;
import cards.Figure;
import messages.CardMsg;

public class CardUtils {
	public static String CardValue(int n){
		String value = "";
		switch ((n-1)/13){
			case 0: value = "}";
			break;
			case 1: value = "{";
			break;
			case 2: value = "]";
			break;
			case 3: value = "[";
			break;
		}
		switch ((n-1)%13){
			case -1: value = "";
			break;
			case 0: value += "A";
			break;
			case 10: value += "J";
			break;
			case 11: value += "Q";
			break;
			case 12: value += "K";
			break;
			default: value += Integer.toString(n%13);
			break;
		}
		return value;
	}
	
	public static int cardToInt(Card c){
		int col = c.getColor().ordinal();
		int fig = c.getFigure().ordinal();
		int card = -1;
		switch (fig){
			case 12: card = 0;
			break;
			case 9: card = 10;
			break;
			case 10: card = 11;
			break;
			case 11: card = 12;
			break;
			default: card = ((fig+2)%13);
			break;
		}
		switch (col){
			case 0: card += 13;
			break;
			case 1: card += 39;
			break;
			case 2: card += 26;
			break;
			case 3: card += 0;
			break;
		}
		return card;
	}
	
	public static int cardMsgToInt(CardMsg c){
		return cardToInt(c.getCard());
	}
	
}
