import Plants.*;
import Zombies.*;
import Tile.*;

import java.util.Random;
import java.util.Scanner;


public class Main {
    public static Boolean gameOver = false;
    public static int seconds = 0;
    public static void main(String[] args) {
        int choice = 0;
        while(true){
            Scanner scanner = new Scanner(System.in);
            // Thread gameThread = null;
            try{
                choice = menu(scanner);
                if(choice == 1){
                    start(scanner);
                }
                else if(choice == 2){
                    // help();
                }
                else if(choice == 3){
                    // plantsList(scanner);
                }
                else if(choice == 4){
                    // zombiesList(scanner);
                }
                else if(choice == 5){
                    System.out.println("Byee");
                    break;
                }
                else{
                    throw new IllegalArgumentException("Choice is invalid");
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            // if (gameThread != null) {
            //     try {
            //         gameThread.join();
            //     } catch (InterruptedException e) {
            //         e.printStackTrace();
            //     }
            // }
        }
    }

    public static int menu(Scanner scanner){
        System.out.println( "1. Start\n" +
                            "2. Help\n" +
                            "3. Plants List\n" +
                            "4. Zombies List\n" +
                            "5. Exit");
        return Integer.parseInt(scanner.nextLine());
    }

    public static void start(Scanner scanner){
        gameOver = false;
        seconds = 0;
        Map map = new Map();
        Random random = new Random();
        Deck<Plants> deck = new Deck<Plants>();
        deck.add(new Deck.PeashooterFactory());
        deck.add(new Deck.SunflowerFactory());
        deck.add(new Deck.LilypadFactory());
        deck.add(new Deck.WallnutFactory());

//        map.plant(2, 1, deck.get(1));
//        map.plant(3, 1, deck.get(1));
//        map.plant(4, 1, deck.get(1));
//        map.plant(4, 1, deck.get(1));
        map.plant(9, 1, deck.create(3, seconds));
        map.plant(8, 5, deck.create(1, seconds));
        map.plant(8, 4, deck.create(3, seconds));
        map.plant(7, 0, deck.create(1, seconds));
        map.plant(1, 1, deck.create(0, seconds));
        map.plant(1, 0, deck.create(0, seconds));
        map.plant(2, 0, deck.create(0, seconds));
        map.plant(2, 1, deck.create(0, seconds));
        map.plant(2, 4, deck.create(0, seconds));
        map.plant(2, 5, deck.create(0, seconds));
        map.plant(8, 1, deck.create(1, seconds));

        // Thread for game loop
        Thread gameThread = new Thread(() -> {
            int lastSunUpdate = 0;
            
            while (!gameOver && seconds < 200) {
                try {


                    if (seconds <= 100) {
                        if (seconds - lastSunUpdate >= (random.nextInt(6) + 5)) {
                            Sun.addSun();
                            lastSunUpdate = seconds;
                        }
                    }

                    if (seconds >= 20 && seconds <= 160) {
                        if(random.nextFloat() < 0.3){
                            Normal zombie = new Normal(seconds); // Jadiin per tile
                            map.addZombie(random.nextInt(5), zombie);
                        }
                    }

                    for (int row = 0; row < 5; row++) {
                        if (!map.getTile(row, 0).getZombies().isEmpty()) {
                            gameOver = true;
                            break;
                        }
                    }
                    Thread.sleep(1000); // sleep for 1 second
                    seconds++;
//                    System.out.println(Sun.getAmount());
                    map.printMap();
                    System.out.println();
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(gameOver){
                System.out.println("You lose!");
            }

            if (seconds >= 200) {
                System.out.println("You win!");
                gameOver = true;
            }


        });
        gameThread.start();

        // // Thread for user input
        // new Thread(() -> {
        //     while (!gameOver) {
        //     }
        // }).start();

        // Thread for plants and zombies actions
        // new Thread(() -> {
        while (!gameOver) {
            try{
                for(int i = 0; i < 11; i++){
                    for(int j = 0; j < 6; j++){
                        Tile tile = map.getTile(j, i);
                        synchronized(tile){
                            if(!(tile.getPlant() == null)){
                                Plants plant = tile.getPlant();
                                if (plant.getRange() == -1) {
                                    if((seconds - plant.getTimeCreated()) % plant.getAttack_speed() == 0){
                                        for (int col2 = i; col2 < 11; col2++) {
                                            Tile tile2 = map.getTile(j, col2);
                                            if (!tile2.getZombies().isEmpty()) {
                                                Zombies zombie = tile2.getZombies().get(0);
                                                zombie.setHealth(zombie.getHealth() - plant.getAttack_damage());
                                                if(zombie.getHealth() <= 0){
                                                    tile2.removeZombie(zombie);
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if(!tile.getZombies().isEmpty()){
                                if(tile.getPlant() == null){
                                    for (Zombies zombie : tile.getZombies()){
                                        if(seconds > zombie.getTimeCreated() && (seconds - zombie.getTimeCreated()) % 5 == 0){
                                            map.moveZombie(j, zombie);
                                        }
                                    }
                                }
                                else{
                                    for (Zombies zombie : tile.getZombies()) {
                                        if(seconds > zombie.getTimeCreated() && (seconds - zombie.getTimeCreated()) % zombie.getAttack_speed() == 0){
                                            tile.getPlant().setHealth(tile.getPlant().getHealth() - zombie.getAttack_damage());
                                        }
                                        if(tile.getPlant().getHealth() <= 0){
                                            map.dig(i, j);
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                Thread.sleep(1000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        // }).start();

        // return gameThread;
    }

    public static void pickPlant(Scanner scanner){

    }
}