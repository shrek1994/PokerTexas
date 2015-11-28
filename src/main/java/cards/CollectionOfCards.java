package cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by maciek on 19.10.15.
 */
public class CollectionOfCards {
    private List<Card> cards;

    public CollectionOfCards()
    {
        createNewDeckCard();
    }

    public void createNewDeckCard() {
        cards = new ArrayList<Card>();
        for (Color color : Color.values())
        {
            for (final Figure figure : Figure.values())
            {
                cards.add(new Card(figure, color));
            }
        }
	}

    public void shuffle(int numberOfChange)
    {
        Random generator = new Random();
        for (int i = 0; i < numberOfChange * cards.size(); i++)
        {
            int first = generator.nextInt(cards.size());
            int second = generator.nextInt(cards.size());
            Card temp = cards.get(first);
            cards.set(first, cards.get(second));
            cards.set(second, temp);
        }
    }


    public List<Card> getCards(int number)
    {
    	List<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < number; i++)
        {
            if(this.cards.size() < 1)
            {
                throw new EmptyCollection("Empty Collection of cards, size: " + Integer.toString(cards.size()));
            }
            cards.add(this.cards.remove(this.cards.size() - 1));
        }
        return cards;
    }

	public class EmptyCollection extends RuntimeException {
		EmptyCollection(String msg){
            super(msg);
        }
    }
}
