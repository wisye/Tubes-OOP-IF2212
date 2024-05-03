package Tile;
import Plants.*;
import Zombies.*;

// import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tile {
    private Boolean protectedArea;
    private Boolean grass;
    private Boolean water;
    private Boolean spawnArea;
    private Plants plant;
    private List<Zombies> zombies;

    public Tile(Boolean protectedArea, Boolean grass, Boolean water, Boolean spawnArea){
        this.protectedArea = protectedArea;
        this.grass = grass;
        this.water = water;
        this.spawnArea = spawnArea;
        this.zombies = new CopyOnWriteArrayList<>();
    }

    public Boolean getProtectedArea() {
        return protectedArea;
    }

    public Boolean getGrass() {
        return grass;
    }

    public Boolean getWater() {
        return water;
    }

    public Boolean getSpawnArea() {
        return spawnArea;
    }

    public void setProtectedArea(Boolean protectedArea) {
        this.protectedArea = protectedArea;
    }

    public void setGrass(Boolean grass) {
        this.grass = grass;
    }

    public void setWater(Boolean water) {
        this.water = water;
    }

    public void setSpawnArea(Boolean spawnArea) {
        this.spawnArea = spawnArea;
    }

    public void setPlant(Plants plant){
        this.plant = plant;
    }

    public Plants getPlant() {
        return plant;
    }

    public List<Zombies> getZombies() {
        return zombies;
    }
    public void addZombie(Zombies zombie) {
        zombies.add(zombie);
    }

    public void removeZombie(Zombies zombie) {
        zombies.remove(zombie);
    }
}
