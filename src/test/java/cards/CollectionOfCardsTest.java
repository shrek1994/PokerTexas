package cards;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionOfCardsTest {
    private ICollectionOfCards sut = new CollectionOfCards();

	@Test
	public void shouldReturnEmptyVectorWhenGetZeroCard() {
        List<Card> emptyVector = new ArrayList<Card>();

        List<Card> result = sut.getCards(0);


        assertEquals(emptyVector, result);
	}

    @Test
    public void shouldReturnFourElementVectorWhenGetFourCards() {
        int numberOfCard = 4;
        List<Card> result = sut.getCards(numberOfCard);

        assertEquals(numberOfCard, result.size());
    }

    @Test
    public void shouldReturnEachOtherCards() {
    	List<List<Card>> result = new ArrayList<List<Card>>();

        while(true){
            try {
                result.add(sut.getCards(1));
            }
            catch (CollectionOfCards.EmptyCollection e){
                break;
            }
        }

        assertNotEqualsAtList(result);
    }

    @Test
    public void shouldReturnEachOtherCardsAfterShuffle() {
        int numberOfCard = Color.values().length * Figure.values().length;
        List<List<Card>> result = new ArrayList<List<Card>>();

        sut.shuffle(numberOfCard);

        while(true){
            try {
                result.add(sut.getCards(1));
            }
            catch (CollectionOfCards.EmptyCollection e){
                break;
            }
        }

        assertNotEqualsAtList(result);
    }

    private void assertNotEqualsAtList(List<List<Card>> result) {
        for(int i = 0; i < result.size(); i++)
        {
            for (int j = i+1; j < result.size(); j++)
            {
                assertNotSame("Number of card: i=" + Integer.toString(i) + ", j=" + Integer.toString(j) + "\n",
                                result.get(i).get(0),
                                result.get(j).get(0));
            }
        }
    }
}
