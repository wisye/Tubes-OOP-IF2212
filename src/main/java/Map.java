import Tile.*;
import Plants.*;
import Zombies.*;

//import java.util.ArrayList;
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
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public void printMap() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                synchronized(tiles[i][j]){
                    System.out.print("[");
                    if(tiles[i][j].getPlant() != null){
                        System.out.print(tiles[i][j].getPlant().getName() + "-" + tiles[i][j].getPlant().getHealth());
                        if(!tiles[i][j].getZombies().isEmpty()){
                            System.out.print("_");
                            List<Zombies> zombies = (tiles[i][j].getZombies());
                            for (Zombies zombies2 : zombies) {
                                System.out.print(zombies2.getName() + "-" +zombies2.getHealth() + ", ");
                            }
                        }
                    }
                    else if(!tiles[i][j].getZombies().isEmpty()){
                        List<Zombies> zombies = (tiles[i][j].getZombies());
                        for (Zombies zombies2 : zombies) {
                            System.out.print(zombies2.getName() + "-" +zombies2.getHealth() + ", ");
                        }
                    }
                    else{
                        System.out.print(" ");
                    }
                    System.out.print("]");
                }
            }
            System.out.println();
        }
    }

    public void plant(int row, int col, Plants plant){
        if(plant instanceof Sunflower){
            new Thread(() -> {
                while (!Main.gameOver) {
                    try {
                        Thread.sleep(10000); // sleep for 10 seconds
                        Sun.addSun();
                    } 
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        if(tiles[col][row] instanceof Water && plant instanceof Lilypad){
            (tiles[col][row]).setPlant(plant);
            ((Water)tiles[col][row]).setLilypadHere();
        }
        else if(tiles[col][row] instanceof Grass || ((Water)tiles[col][row]).getLilypadHere()){
            (tiles[col][row]).setPlant(plant);
        }
        else {
            throw new IllegalArgumentException("This plant cannot be planted on this tile");
        }
        // if(plant.getAttack_damage() != 0){
        //     attackZombies(col, row, plant);
        // }
    }

    public void dig(int row, int col){
        if(tiles[col][row] instanceof Grass){
            (tiles[col][row]).setPlant(null);
        }
        else if(tiles[col][row] instanceof Water){
            if((tiles[col][row]).getPlant().getName() != "Lilypad"){
                (tiles[col][row]).setPlant(new Lilypad(0));
            }
            else{
                (tiles[col][row]).setPlant(null);
            }
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

    public synchronized void moveZombie(int row, Zombies zombie) {
        for (int col = 0; col < 11; col++) {
            Tile tile = getTile(row, col);
            if (tile.getZombies().contains(zombie)) {
                // Move the zombie to the left
                if (col > 0) {
                    Tile leftTile = getTile(row, col - 1);
                    leftTile.addZombie(zombie);
                    tile.removeZombie(zombie);
                    zombie.moveLeft();
                }
                break;
            }
        }
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
