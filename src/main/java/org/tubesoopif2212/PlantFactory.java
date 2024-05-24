package org.tubesoopif2212;

import org.tubesoopif2212.Plants.Plants;

public interface PlantFactory<T extends Plants> {
    T create(int timeCreated) throws Exception;

    public int getLastPlantedTime();

    public int getCooldown();
}