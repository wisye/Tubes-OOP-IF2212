package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;
import org.tubesoopif2212.Tile.*;
import org.tubesoopif2212.Zombies.*;

import java.util.List;

public class Map {
    private static Tile[][] tiles;
    private static Map map;

    private Map(){
        tiles = new Tile[6][11];
        for (int i = 0; i < 6; i++) {
            tiles[i][0] = new ProtectedArea();
        }
        for(int i = 0; i < 2; i++){
            for(int j = 1; j < 10; j++){
                tiles[i][j] = new Grass();
            }
        }
        for(int i = 2; i < 4; i++){
            for(int j = 1; j < 10; j++){
                tiles[i][j] = new Water();
            }
        }
        for(int i = 4; i < 6; i++){
            for(int j = 1; j < 10; j++){
                tiles[i][j] = new Grass();
            }
        }
        for (int i = 0; i < 6; i++) {
            tiles[i][10] = new SpawnArea();
        }
        tiles[2][10].setWater(true);
        tiles[3][10].setWater(true);
    }

    public static Map getInstance(){
        if(map == null){
            return new Map(); 
        }
        else{
            return map;
        }
    }

    public static Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public void printMap() {
    for (int i = 0; i < tiles.length; i++) {
        for (int j = 1; j < tiles[i].length - 1; j++) {
            synchronized(tiles[i][j]){
                boolean isPlantPresent = tiles[i][j].getPlant() != null; // Cek apakah ada tanaman
                
                // Jika ada tanaman (Lilypad atau lainnya), warna hijau
                if(isPlantPresent) {
                    System.out.print("\u001B[32m["); // Kurung siku dengan warna hijau
                } else if((i >= 2 && i <= 3) && (j >= 1 && j <= 9) && !isPlantPresent){
                    System.out.print("\u001B[34m{"); // Kurung kurawal dengan warna biru
                } else {
                    System.out.print("\u001B[32m["); // Kurung siku dengan warna hijau untuk kondisi lain
                }
                
                // Menampilkan detail tanaman atau zombie jika ada
                if(tiles[i][j].getPlant() != null){
                    System.out.print(tiles[i][j].getPlant().getName() + "-" + tiles[i][j].getPlant().getHealth());
                    if(!tiles[i][j].getZombies().isEmpty()){
                        System.out.print("_");
                        List<Zombies> zombies = tiles[i][j].getZombies();
                        for (Zombies zombie : zombies) {
                            System.out.print(zombie.getName() + "-" + zombie.getHealth() + ", ");
                        }
                    }
                } else if (!tiles[i][j].getZombies().isEmpty()){
                    List<Zombies> zombies = tiles[i][j].getZombies();
                    for (Zombies zombie : zombies) {
                        System.out.print(zombie.getName() + "-" + zombie.getHealth() + ", ");
                    }
                } else {
                    System.out.print(" "); // Print spasi jika tidak ada tanaman atau zombie
                }

                // Penutup kurung sesuai dengan kondisi tanaman
                if(isPlantPresent) {
                    System.out.print("\u001B[32m]"); // Kurung siku dengan warna hijau
                } else if((i >= 2 && i <= 3) && (j >= 1 && j <= 9) && !isPlantPresent){
                    System.out.print("\u001B[34m}"); // Kurung kurawal dengan warna biru
                } else {
                    System.out.print("\u001B[32m]"); // Kurung siku dengan warna hijau untuk kondisi lain
                }
                
                System.out.print("\u001B[0m"); // Reset warna ke default
            }
        }
        System.out.println();
    }
}
     

    public void plant(int row, int col, Plants plant) throws Exception{
        Sun.reduceSun(plant.getCost());
        if(tiles[col][row] instanceof Water && tiles[col][row].getPlant() instanceof Lilypad){
            plant.setHealth(plant.getHealth() + tiles[col][row].getPlant().getHealth());
        }
        tiles[col][row].setPlant(plant);
    }

    public void validatePlant(int row, int col, Plants plant) throws Exception {
        if(Sun.getAmount() < plant.getCost()){
            throw new Exception("Not enough sun");
        }
        if(tiles[col][row].getPlant() != null && (tiles[col][row].getPlant().getName().equals(plant.getName()))){
            throw new Exception("This plant cannot be planted on this tile");
        }
        if(!(tiles[col][row] instanceof Water && (plant instanceof Lilypad || tiles[col][row].getPlant() instanceof Lilypad)) && !(tiles[col][row] instanceof Grass && !(plant instanceof Lilypad))){
            throw new Exception("This plant cannot be planted on this tile");
        }
    }

    public void dig(int row, int col) throws Exception{
        if(tiles[col][row].getPlant() == null){
            throw new Exception("There is no plant here");
        }
        (tiles[col][row]).setPlant(null);
    }

    public static void removePlant(int row, int col){
        if(tiles[col][row] instanceof Water){
            tiles[col][row].setPlant(new Lilypad(0));
        }
        else{
            tiles[col][row].setPlant(null);
        }
    }

    public void addZombie(int row, Zombies zombie){
        synchronized(tiles[row][10]){
            tiles[row][10].addZombie(zombie);
        }
        // new Thread(() -> {
        //     while (!Main.gameOver) {
        //         Tile tile = getTile(row, zombie.getCurrentCol());
        //         if (tile.getPlant() != null) {
        //             try {
        //                 Thread.sleep(1000 * zombie.getAttack_speed()); // sleep for the attack speed duration
        //             } 
        //             catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
        //             Plants plant = tile.getPlant();
        //             if (plant != null) { // Check if plant is not null
        //                 plant.setHealth(plant.getHealth() - zombie.getAttack_damage());
        //                 // If the plant's HP is 0 or less, remove the plant
        //                 if (plant.getHealth() <= 0) {
        //                     tile.setPlant(null);
        //                 }
        //             }
        //         }
        //         // If there's no plant on the tile, move the zombie to the left
        //         else if (zombie.getCurrentCol() > 0) {
        //             try {
        //                 Thread.sleep(5000); // sleep for 5 seconds
        //             } 
        //             catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
        //             moveZombie(row, zombie);
        //         }
        //     }
        // }).start();
    }

    // public void attackZombies(int row, int col, Plants plant) {
    //     new Thread(() -> {
    //         while (!Main.gameOver) {
    //             try {
    //                 Tile tile = getTile(row, col);
    //                 synchronized(tile){
    //                     if (tile.getPlant() != null && tile.getPlant().equals(plant)) {
    //                         if (plant.getRange() == -1) {
    //                             for (int col2 = col; col2 < 11; col2++) {
    //                                 Tile tile2 = getTile(row, col2);
    //                                 if (!tile2.getZombies().isEmpty()) {
    //                                     Zombies zombie = tile2.getZombies().get(0);
    //                                     zombie.setHealth(zombie.getHealth() - plant.getAttack_damage());
    //                                     if(zombie.getHealth() <= 0){
    //                                         tile2.removeZombie(zombie);
    //                                     }
    //                                     break;
    //                                 }
    //                             }
    //                         }
    //                         else if (plant.getRange() == 1 && col < 10) {
    //                             Tile tile2 = getTile(row, col + 1);
    //                             if (!tile2.getZombies().isEmpty()) {
    //                                 Zombies zombie = tile2.getZombies().get(0);
    //                                 zombie.setHealth(zombie.getHealth() - plant.getAttack_damage());
    //                             }
    //                         }
    //                     }
    //                     else{
    //                         return;
    //                     }
    //                 }
    //                 Thread.sleep(plant.getAttack_speed() * 1000);
    //             } 
    //             catch (InterruptedException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }).start();
    // }
}
