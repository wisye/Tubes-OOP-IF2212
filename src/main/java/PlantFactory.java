import Plants.Plants;

public interface PlantFactory<T extends Plants> {
        T create(int timeCreated);
    }