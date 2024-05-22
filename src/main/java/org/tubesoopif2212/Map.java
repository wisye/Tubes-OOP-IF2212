package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;
import org.tubesoopif2212.Tile.*;
import org.tubesoopif2212.Zombies.*;

import java.util.List;

public class Map {
    private static Tile[][] tiles;

    public Map(){
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

    public static Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public void printMap() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 1; j < tiles[i].length - 1; j++) {
                synchronized(tiles[i][j]){
                    boolean isLilypad = tiles[i][j].getPlant() != null && "Lilypad".equals(tiles[i][j].getPlant().getName());
                    
                    if(isLilypad){
                        System.out.print("\u001B[32m["); // Kurung siku dengan warna hijau
                    } else if((i >= 2 && i <= 3) && (j >= 1 && j <= 9)){
                        System.out.print("\u001B[34m{"); // Kurung kurawal dengan warna biru
                    } else {
                        System.out.print("\u001B[32m["); // Kurung siku dengan warna hijau
                    }
                    
                    if(tiles[i][j].getPlant() != null){
                        System.out.print(tiles[i][j].getPlant().getName() + "-" + tiles[i][j].getPlant().getHealth());
                        if(!tiles[i][j].getZombies().isEmpty()){
                            System.out.print("_");
                            List<Zombies> zombies = (tiles[i][j].getZombies());
                            for (Zombies zombies2 : zombies) {
                                System.out.print(zombies2.getName() + "-" + zombies2.getHealth() + ", ");
                            }
                        }
                    }
                    else if(!tiles[i][j].getZombies().isEmpty()){
                        List<Zombies> zombies = (tiles[i][j].getZombies());
                        for (Zombies zombies2 : zombies) {
                            System.out.print(zombies2.getName() + "-" + zombies2.getHealth() + ", ");
                        }
                    }
                    else{
                        System.out.print(" ");
                    }
    
                    if(isLilypad){
                        System.out.print("\u001B[32m]"); // Kurung siku dengan warna hijau
                    } else if((i >= 2 && i <= 3) && (j >= 1 && j <= 9)){
                        System.out.print("\u001B[34m}"); // Kurung kurawal dengan warna biru
                    } else {
                        System.out.print("\u001B[32m]"); // Kurung siku dengan warna hijau
                    }
                    
                    System.out.print("\u001B[0m"); // Mengembalikan warna ke default
                }
            }
            System.out.println();
        }
    }
     

    public void plant(int row, int col, Plants plant) throws Exception{
        if(Sun.getAmount() < plant.getCost()){
            throw new Exception("Not enough sun");
        }
        Sun.reduceSun(plant.getCost());
        if(plant instanceof Sunflower){
            new Thread(() -> {
                while (!gameLoop.gameOver) {
                    try {
                        Thread.sleep(3000); // sleep for 3 seconds
                        Sun.addSun();
                    } 
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if(tiles[col][row].getPlant() != null && (tiles[col][row].getPlant().getName().equals(plant.getName()))){
            throw new Exception("This plant cannot be planted on this tile");
        }
        else if(tiles[col][row] instanceof Water && tiles[col][row].getPlant() instanceof Lilypad){
            plant.setHealth(plant.getHealth() + tiles[col][row].getPlant().getHealth());
            (tiles[col][row]).setPlant(plant);
        }
        else if(tiles[col][row] instanceof Water && plant instanceof Lilypad){
            (tiles[col][row]).setPlant(plant);
        }
        else if(tiles[col][row] instanceof Grass && !(plant instanceof Lilypad)){
            (tiles[col][row]).setPlant(plant);
        }
        else {
            throw new Exception("This plant cannot be planted on this tile");
        }
    }

    public void dig(int row, int col){
        (tiles[col][row]).setPlant(null);
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
