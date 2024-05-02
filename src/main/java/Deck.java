import java.util.ArrayList;
import java.util.List;
import Plants.*;

public class Deck<T extends Plants> {
    private List<PlantFactory<? extends Plants>> deck;

    public interface PlantFactory<T extends Plants> {
        T create(int timeCreated);
    }

    public Deck(){
        this.deck = new ArrayList<>(6);
    }

    public void add(PlantFactory<? extends Plants> factory) {
        if (deck.size() < 6) {
            deck.add(factory);
        } else {
            throw new IllegalStateException("Deck is full");
        }
    }

    public PlantFactory<? extends Plants> get(int index) {
        return deck.get(index);
    }

    public Plants create(int index, int timeCreated){
        return deck.get(index).create(timeCreated);
    }
    
    public static class PeashooterFactory implements PlantFactory<Peashooter> {
        public Peashooter create(int timeCreated) {
            return new Peashooter(timeCreated);
        }
    }
    
    public static class SunflowerFactory implements PlantFactory<Sunflower> {
        public Sunflower create(int timeCreated) {
            return new Sunflower(timeCreated);
        }
    }
    
    public static class LilypadFactory implements PlantFactory<Lilypad> {
        public Lilypad create(int timeCreated) {
            return new Lilypad(timeCreated);
        }
    }
    
    public static class WallnutFactory implements PlantFactory<Wallnut> {
        public Wallnut create(int timeCreated) {
            return new Wallnut(timeCreated);
        }
    }

    public void remove(PlantFactory<? extends Plants> factory) {
        deck.remove(factory);
    }
}
