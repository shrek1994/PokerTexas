package cards;


import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
    private Color color;
    private Figure figure;

    public Card(Figure figure, Color color)
    {
        this.color = color;
        this.figure = figure;
    }

    public Figure getFigure() {
        return figure;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Card)
        {
            Card card = (Card) obj;
            return color == card.color && figure == card.figure;
        }
        return false;
    }

    @Override
    public int compareTo(Card card) {
        if (this.figure.getValue() == card.figure.getValue())
        {
            return this.color.getValue() - card.color.getValue();
        }
        return this.figure.getValue() - card.figure.getValue();
    }

    @Override
    public String toString() {
        return this.figure.toString() + " " +this.color.toString();
    }
}
