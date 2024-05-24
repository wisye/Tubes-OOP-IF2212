package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;

import java.util.ArrayList;
import java.util.List;

public class Deck<T> {
    private List<PlantFactory<? extends Plants>> deck;

    public Deck() {
        this.deck = new ArrayList<>(6);
    }

    private static Deck lastSelectedDeck;

    public static Deck getLastSelectedDeck(){
        return lastSelectedDeck;
    }

    public static void setLastSelectedDeck(Deck selectedDeck) {
        lastSelectedDeck = selectedDeck;
    }

    public List<PlantFactory<? extends Plants>> getPlants() {
        return new ArrayList<>(deck);
    }

    public void add(Plants plant) {
        System.out.println("Before adding: " + deck.size() + " plants in the deck.");

        if (deck.size() >= 6) {
            throw new IllegalStateException("Deck is full");
        }

        try {
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
                case "Wallnut":
                    deck.add(new WallnutFactory());
                    break;
                case "Squash":
                    deck.add(new SquashFactory());
                    break;
                case "Snowpea":
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
                case "CeresFauna":
                    deck.add(new CeresFaunaFactory());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Failed to add plant: " + e.getMessage());
            return; // Skip the rest of the method if adding the plant failed
        }

        System.out.println("After adding: " + deck.size() + " plants in the deck.");
        System.out.println("Added plant: " + plant.getName());
    }

    public PlantFactory<? extends Plants> get(int index) {
        return deck.get(index);
    }

    public Plants create(int index, int timeCreated) throws Exception{
        return deck.get(index).create(timeCreated);
    }

    public int size() {
        return deck.size();
    }

    public static class PeashooterFactory implements PlantFactory<Peashooter> {
        private int cooldown = new Peashooter(0).getCooldown();
        private int lastPlantedTime = -200;

        public Peashooter create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Peashooter(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }
        public String getName(){
            return "Peashooter";
        }
    }

    public static class SunflowerFactory implements PlantFactory<Sunflower> {
        private int cooldown = new Sunflower(0).getCooldown();
        private int lastPlantedTime = -200;

        public Sunflower create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Sunflower(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }
        public String getName(){
            return "Sunflower";
        }
    }

    public static class LilypadFactory implements PlantFactory<Lilypad> {
        private int cooldown = new Lilypad(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public Lilypad create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Lilypad(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "Lilypad";
        }
    }
    
    public static class WallnutFactory implements PlantFactory<Wallnut> {
        private int cooldown = new Wallnut(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public Wallnut create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Wallnut(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "Wallnut";
        }
    }
    
    public static class SquashFactory implements PlantFactory<Squash> {
        private int cooldown = new Squash(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public Squash create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Squash(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "Squash";
        }
    }
    
    public static class SnowPeaFactory implements PlantFactory<Snowpea> {
        private int cooldown = new Snowpea(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public Snowpea create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Snowpea(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "Snowpea";
        }
    }
    
    public static class NahidaFactory implements PlantFactory<Nahida> {
        private int cooldown = new Nahida(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public Nahida create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Nahida(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "Nahida";
        }
    }
    
    public static class PlanterraFactory implements PlantFactory<Planterra> {
        private int cooldown = new Planterra(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public Planterra create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Planterra(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "Planterra";
        }
    }
    
    public static class CannabisFactory implements PlantFactory<Cannabis> {
        private int cooldown = new Cannabis(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public Cannabis create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new Cannabis(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "Cannabis";
        }
    }
    
    public static class CeresFaunaFactory implements PlantFactory<CeresFauna> {
        private int cooldown = new CeresFauna(0).getCooldown();
        private int lastPlantedTime = -200;
    
        public CeresFauna create(int timeCreated) throws Exception {
            if (timeCreated - lastPlantedTime < cooldown) {
                throw new Exception("Cannot plant yet, cooldown has not passed");
            }
            lastPlantedTime = timeCreated;
            return new CeresFauna(timeCreated);
        }

        public int getLastPlantedTime(){
            return lastPlantedTime;
        }

        public int getCooldown(){
            return cooldown;
        }

        public String getName(){
            return "CeresFauna";
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
            ret += i + ". " + (p.getClass().getSimpleName().replace("Factory", "")) + ("\n");
            i++;
        }
        return ret.toString();
    }

    public String toStringWithCD(){
        String ret = new String();
        int i = 1;
        for (PlantFactory<? extends Plants> p : deck) {
            int cooldown = ((p.getCooldown() - (gameLoop.seconds - p.getLastPlantedTime()) <= 0) ? p.getCooldown() : (gameLoop.seconds - p.getLastPlantedTime()));
            ret += i + ". " + (p.getClass().getSimpleName().replace("Factory", "")) + " - " + cooldown + "/" + p.getCooldown() +("\n");
            i++;
        }
        return ret.toString();
    }

    public void removeByName(String plantName) {
        if (plantName == null || plantName.isEmpty()) {
            throw new IllegalArgumentException("Plant name cannot be null or empty");
        }
        for (PlantFactory<? extends Plants> factory : deck) {
            if (factory.getName().equals(plantName)) {
                deck.remove(factory);
                return;
            }
        }
        throw new IllegalArgumentException("Plant not found: " + plantName);
    }
}
