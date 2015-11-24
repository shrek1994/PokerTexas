package cards;

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
}
