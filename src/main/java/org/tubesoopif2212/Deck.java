package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;

import java.util.ArrayList;
import java.util.List;

public class Deck<T> {
    private List<PlantFactory<? extends Plants>> deck;

    public Deck() {
        this.deck = new ArrayList<>(6);
    }

    public void add(Plants plant) {
        try {
            if (deck.size() < 6) {
                switch (plant.getName()) {
                    case "Peashooter":
                        deck.add(new PeashooterFactory());
                        break;
                    case "Sunflower":
                        deck.add(new SunflowerFactory());
                        break;
                    case "Lilypad":
                        deck.add(new LilypadFactory());
                        break;
                    case "Wall nut":
                        deck.add(new WallnutFactory());
                        break;
                    case "Squash":
                        deck.add(new SquashFactory());
                        break;
                    case "Snow pea":
                        deck.add(new SnowPeaFactory());
                        break;
                    case "Nahida":
                        deck.add(new NahidaFactory());
                        break;
                    case "Planterra":
                        deck.add(new PlanterraFactory());
                        break;
                    case "Cannabis":
                        deck.add(new CannabisFactory());
                        break;
                    case "Ceres Fauna":
                        deck.add(new CeresFaunaFactory());
                        break;
                    default:
                        break;
                }
            } else {
                throw new IllegalStateException("Deck is full");
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public PlantFactory<? extends Plants> get(int index) {
        return deck.get(index);
    }

    public Plants create(int index, int timeCreated) {
        return deck.get(index).create(timeCreated);
    }

    public int size() {
        return deck.size();
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

    public static class SquashFactory implements PlantFactory<Squash> {
        public Squash create(int timeCreated) {
            return new Squash(timeCreated);
        }
    }

    public static class SnowPeaFactory implements PlantFactory<Snowpea> {
        public Snowpea create(int timeCreated) {
            return new Snowpea(timeCreated);
        }
    }

    public static class NahidaFactory implements PlantFactory<Nahida> {
        public Nahida create(int timeCreated) {
            return new Nahida(timeCreated);
        }
    }

    public static class PlanterraFactory implements PlantFactory<Planterra> {
        public Planterra create(int timeCreated) {
            return new Planterra(timeCreated);
        }
    }

    public static class CannabisFactory implements PlantFactory<Cannabis> {
        public Cannabis create(int timeCreated) {
            return new Cannabis(timeCreated);
        }
    }

    public static class CeresFaunaFactory implements PlantFactory<CeresFauna> {
        public CeresFauna create(int timeCreated) {
            return new CeresFauna(timeCreated);
        }
    }

    public void remove(PlantFactory<? extends Plants> factory) {
        deck.remove(factory);
    }

    public void set(int index, PlantFactory<? extends Plants> factory) {
        deck.set(index, factory);
    }

    public void swap(int index1, int index2) {
        PlantFactory<? extends Plants> temp = deck.get(index1);
        deck.set(index1, deck.get(index2));
        deck.set(index2, temp);
    }

    public String toString() {
        String ret = new String();
        int i = 1;
        for (PlantFactory<? extends Plants> p : deck) {
            ret += i + ". " + (p.create(0).getName()) + ("\n");
            i++;
        }
        return ret.toString();
    }
}
