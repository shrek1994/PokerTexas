package cards;

import java.util.Objects;

public class Card {
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
}
