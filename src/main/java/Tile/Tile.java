package Tile;

public abstract class Tile {
    private Boolean protectedArea;
    private Boolean grass;
    private Boolean water;
    private Boolean spawnArea;

    public Tile(Boolean protectedArea, Boolean grass, Boolean water, Boolean spawnArea){
        this.protectedArea = protectedArea;
        this.grass = grass;
        this.water = water;
        this.spawnArea = spawnArea;
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
}
