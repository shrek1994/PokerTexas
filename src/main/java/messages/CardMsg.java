package messages;

import cards.Card;

import java.io.Serializable;

/**
 * Created by maciek on 28.11.15.
 */
public class CardMsg implements Serializable {
    private Card card;

    public Card getCard() {
        return card;
    }

    public CardMsg(Card card) {

        this.card = card;
    }

    @Override
    public boolean equals(Object o)
    {
        // using in tests
        if ( o instanceof CardMsg)
        {
            CardMsg msg = (CardMsg)o;
            return msg.card.getColor().equals(card.getColor()) &&
                    msg.card.getFigure().equals(card.getFigure());
        }
        return false;
    }
}
