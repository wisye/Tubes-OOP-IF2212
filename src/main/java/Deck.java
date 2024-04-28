import java.util.ArrayList;
import java.util.List;
import Plants.*;

public class Deck<T extends Plants> {
    private List<T> deck;

    public Deck(){
        this.deck = new ArrayList<>(10);
    }

    public void add(T plant) {
        if (deck.size() < 10) {
            deck.add(plant);
        } else {
            throw new IllegalStateException("Deck is full");
        }
    }

    public T get(int index) {
        return deck.get(index);
    }
}