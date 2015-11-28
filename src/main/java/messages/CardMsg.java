package messages;

import cards.Card;

/**
 * Created by maciek on 28.11.15.
 */
public class CardMsg {
    private Card card;

    public Card getCard() {
        return card;
    }

    public CardMsg(Card card) {

        this.card = card;
    }
}
