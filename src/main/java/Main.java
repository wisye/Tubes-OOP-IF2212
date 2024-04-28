import Plants.*;
import Zombies.*;

import java.util.Random;
import java.util.Scanner;


public class Main {
    public static Boolean gameOver = false;

    public static void main(String[] args) {
        int choice = 0;
        while(true){
            Scanner scanner = new Scanner(System.in);
            Thread gameThread = null;
            try{
                choice = menu(scanner);
                if(choice == 1){
                    gameThread = start(scanner);
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

            if (gameThread != null) {
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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

    public static Thread start(Scanner scanner){
        gameOver = false;
        Map map = new Map();
        Random random = new Random();
        Deck<Plants> deck = new Deck<Plants>();
        deck.add(new Peashooter());
        deck.add(new Sunflower());
        deck.add(new Lilypad());
        deck.add(new Wallnut());

//        map.plant(2, 1, deck.get(1));
//        map.plant(3, 1, deck.get(1));
//        map.plant(4, 1, deck.get(1));
//        map.plant(4, 1, deck.get(1));
        map.plant(9, 1, deck.get(3));
        map.plant(8, 5, deck.get(1));
        map.plant(8, 4, deck.get(3));
        map.plant(7, 0, deck.get(1));
        map.plant(1, 1, deck.get(0));
        map.plant(2, 0, deck.get(0));
        map.plant(2, 1, deck.get(0));
        map.plant(2, 4, deck.get(0));
        map.plant(2, 5, deck.get(0));
        map.plant(8, 1, deck.get(1));

        // Thread for game loop
        Thread gameThread = new Thread(() -> {
            int seconds = 0;
            int lastSunUpdate = 0;
            
            while (!gameOver && seconds < 200) {
                try {
                    Thread.sleep(1000); // sleep for 1 second
                    seconds++;

                    if (seconds <= 100) {
                        if (seconds - lastSunUpdate >= (random.nextInt(6) + 5)) {
                            Sun.addSun();
                            lastSunUpdate = seconds;
                        }
                    }

                    if (seconds >= 20 && seconds <= 160) {
                        if(random.nextFloat() < 0.3){
                            Normal zombie = new Normal();
                            map.addZombie(random.nextInt(5), zombie);
                        }
                    }

                    for (int row = 0; row < 5; row++) {
                        if (!map.getTile(row, 0).getZombies().isEmpty()) {
                            gameOver = true;
                            break;
                        }
                    }
//                    System.out.println(Sun.getAmount());
                    map.printMap();
                    System.out.println();
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (seconds >= 200) {
                System.out.println("You win!");
            }

            if(gameOver){
                System.out.println("You lose!");
            }
        });
        gameThread.start();

        // // Thread for user input
        // new Thread(() -> {
        //     while (!gameOver) {
        //     }
        // }).start();

        // // Thread for plant actions
        // new Thread(() -> {
        //     while (!gameOver) {
        //     }
        // }).start();

        // // Thread for zombie actions
        // new Thread(() -> {
        //     while (!gameOver) {
        //     }
        // }).start();
        return gameThread;
    }

    public static void pickPlant(Scanner scanner){

    }
}