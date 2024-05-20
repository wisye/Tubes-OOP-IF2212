package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;
import org.tubesoopif2212.Tile.*;
import org.tubesoopif2212.Zombies.*;

import java.util.Random;
import java.util.Scanner;

public class gameLoop {
    public static Boolean gameOver = false;
    public static int seconds = 0;

    public static void main(String[] args) {
        int choice = 0;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            // Thread gameThread = null;
            try {
                menu();
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    startGame(scanner);
                } else if (choice == 2) {
                    // help();
                } else if (choice == 3) {
                    // plantsList(scanner);
                } else if (choice == 4) {
                    // zombiesList(scanner);
                } else if (choice == 5) {
                    scanner.close();
                    System.out.println("Byee");
                    break;
                } else {
                    throw new IllegalArgumentException("Choice is invalid");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void menu() {
        System.out.println("1. Start\n" +
                "2. Help\n" +
                "3. Plants List\n" +
                "4. Zombies List\n" +
                "5. Exit");
    }

    public static void startGame(Scanner scanner) {
        gameOver = false;
        seconds = 0;
        Sun.getInstance();
        Sun.setSun(10000);
        Map map = new Map();
        Random random = new Random();
        Actions action = new Actions();
        Deck<Plants> deck = new Deck<Plants>();
        Inventory inventory = new Inventory();

        pickPlant(scanner, deck, inventory);

        // map.plant(9, 1, deck.create(3, seconds));
        // map.plant(8, 5, deck.create(1, seconds));
        // map.plant(8, 4, deck.create(3, seconds));
        // map.plant(7, 0, deck.create(1, seconds));
        // map.plant(1, 1, deck.create(0, seconds));
        // map.plant(1, 0, deck.create(4, seconds));
        // map.plant(2, 0, deck.create(4, seconds));
        // map.plant(2, 1, deck.create(0, seconds));
        // map.plant(2, 4, deck.create(0, seconds));
        // map.plant(2, 5, deck.create(0, seconds));
        // map.plant(8, 1, deck.create(1, seconds));

        // Thread for game loop
        Thread gameThread = new Thread(() -> {
            int lastSunUpdate = 0;
            // try{
            // map.plant(5, 5, deck.create(0, seconds));
            // } catch (Exception e){System.out.println(e.getMessage());}
            // map.addZombie(0, new EntireZom100Cast(seconds));
            while (!gameOver && seconds < 200) {
                try {
                    if(seconds == 10){
                        map.addZombie(5, new ShrekButZombie(seconds));
                    }
                    if (seconds <= 100) {
                        if (seconds - lastSunUpdate >= (random.nextInt(6) + 5)) {
                            Sun.addSun();
                            lastSunUpdate = seconds;
                        }
                    }

                    if (seconds >= 55 && seconds <= 58) { // BONUS YANG FLAG
                        for (int i = 0; i < 6; i++) {
                            if (random.nextFloat() < 0.3) {
                                Normal zombie = new Normal(seconds);
                                map.addZombie(i, zombie);
                            }
                        }
                    }

                    if (seconds >= 20 && seconds <= 160) {
                        for (int i = 0; i < 6; i++) {
                            Tile tile = Map.getTile(i, 10);
                            if (random.nextFloat() < 0.3) {
                                action.spawnRandomZombies(tile);
                            }
                        }
                    }

                    for (int row = 0; row < 5; row++) {
                        if (!Map.getTile(row, 0).getZombies().isEmpty()) {
                            gameOver = true;
                            break;
                        }
                    }
                    Thread.sleep(1000); // sleep for 1 second
                    seconds++;
                    // System.out.println(Sun.getAmount());
                    // System.out.println(Sun.getAmount());
                    // map.printMap();
                    // System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (gameOver) {
                System.out.println("You lose!");
            }

            if (seconds >= 200) {
                System.out.println("You win!");
                gameOver = true;
            }

        });
        gameThread.start();

        // // Thread for user input
        new Thread(() -> {
            map.printMap();
            while (!gameOver) {
                System.out.println(
                    "\n" +
                    "Time: " + seconds + "\n" +
                    "<1 x y plants(index)> Plant tanaman di koordinat map\n" +
                    "<2 x y> Dig tanaman di koordinat map\n" +
                    "<3> Print map\n" +
                    "Sun: " + Sun.getAmount() + "\n" +
                    deck.toString() + "\n"
                );
                int input = scanner.nextInt();
                try{
                    if(input == 1){
                        map.plant(scanner.nextInt(), scanner.nextInt() - 1, deck.create(scanner.nextInt() - 1, seconds));
                    }
                    else if(input == 2){
                        map.dig(scanner.nextInt(), scanner.nextInt() - 1);
                    }
                    else if(input == 3){
                    }
                    else{
                        throw new Exception("Pilihan tidak tersedia!");
                    }
                } catch(Exception e) {
                    System.out.println("Input tidak sesuai! Perhatikan format input");
                } finally {
                    map.printMap();
                }
            }
        }).start();

        // Thread for plants and zombies
        // new Thread(() -> {
        while (!gameOver) {
            try {
                for (int i = 0; i < 11; i++) {
                    for (int j = 0; j < 6; j++) {
                        Tile tile = Map.getTile(j, i);
                        synchronized (tile) {
                            if (!(tile.getPlant() == null)) {
                                action.attackPlant(i, j, tile.getPlant());
                            }
                            if (!tile.getZombies().isEmpty()) {
                                if (tile.getPlant() == null) {
                                    action.moveZombie(j, tile.getZombies());
                                } else {
                                    action.attackZombie(tile, map, i, j);
                                }
                            }

                        }
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // }).start();

        // return gameThread;
    }

    public static void pickPlant(Scanner scanner, Deck<Plants> deck, Inventory inventory) {
        while (deck.size() < 6) {
            System.out.println("Inventory: ");
            System.out.println(inventory.toString());
            System.out.println();
            System.out.println("Deck: ");
            System.out.println(deck.toString());
            System.out.println(
                "<1 x>\t Pilih tanaman untuk dimasukkan ke deck\n" +
                "<2 x>\t Pilih tanaman untuk dikeluarkan dari deck\n" +
                "<3 x y>\t Pilih tanaman untuk ditukar di deck\n"
            );
            int choice = scanner.nextInt();
            try {
                if (choice == 1) {
                    int x = scanner.nextInt() - 1;
                    try{
                        inventory.choosePlant(inventory.get(x), deck);
                    } catch (Exception e){
                        System.out.println("Indeks tersebut tidak ada di Inventory!");
                    }
                } else if (choice == 2) {
                    int x = scanner.nextInt() - 1;
                    try{
                        inventory.removePlant(x, deck);
                    } catch(Exception e){
                        System.out.println("Indeks tersebut tidak ada di Deck!");
                    }
                } else if (choice == 3) {
                    int x = scanner.nextInt() - 1;
                    int y = scanner.nextInt() - 1;
                    try{
                        inventory.swapPlant(x, y, deck);
                    } catch(Exception e){
                        System.out.println("Indeks tersebut tidak ada di Deck!");
                    }
                } else {
                    scanner.nextLine();
                    throw new Exception("Pilihan " + choice+ " tidak tersedia!");
                }
            } catch (Exception e) {
                System.out.println("Perhatikan format input!");
            }
        }
    }
}