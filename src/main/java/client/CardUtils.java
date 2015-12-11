package client;

import messages.CardMsg;

public class CardUtils {
	static String CardValue(int n){
		String value = "";
		switch (n%13){
			case 0: value = "A";
			break;
			case 10: value = "J";
			break;
			case 11: value = "Q";
			break;
			case 12: value = "K";
			break;
			default: value = Integer.toString(n%13);
			break;
		}
		switch (n/13){
			case 0: value += "}";
			break;
			case 1: value += "}";
			break;
			case 2: value += "]";
			break;
			case 3: value += "[";
			break;
		}
		return value;
	}
	
	static int cardMsgToInt(CardMsg c){
		return 1;
	}
	
}
