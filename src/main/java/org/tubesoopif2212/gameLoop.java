package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;
import org.tubesoopif2212.Tile.*;
import org.tubesoopif2212.Zombies.*;

import java.util.Random;
import java.util.Scanner;

public class gameLoop {
    public static Boolean gameOver = false;
    public static int seconds = 0;
    public static Inventory inventory = new Inventory();  // Inisialisasi inventory di awal

    public static void main(String[] args) {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                menu(scanner);
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    startGame(scanner);
                } else if (choice == 2) {
                    help();
                } else if (choice == 3) {
                    plantLists(scanner);
                } else if (choice == 4) {
                    zombieLists(scanner);
                } else if (choice == 5) {
                    System.out.println("Byee");
                    break;
                } else {
                    throw new IllegalArgumentException("Choice is invalid");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    public static void menu(Scanner scanner) {
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
        inventory = new Inventory();  // Pastikan inventory diinisialisasi

        pickPlant(scanner, deck);

        // Thread for game loop
        Thread gameThread = new Thread(() -> {
            int lastSunUpdate = 0;
            try {
                map.plant(5, 5, deck.create(0, seconds));
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }

            while (!gameOver && seconds < 200) {
                try {
                    if (seconds == 10) {
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
                    map.printMap();
                    System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan: " + e.getMessage());
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

        // Thread for user input
        new Thread(() -> {
            map.printMap();
            while (!gameOver) {
                try {
                    System.out.println(
                        "\n" +
                        "<1 x y plants(index)> Plant tanaman di koordinat map\n" +
                        "<2 x y> Dig tanaman di koordinat map\n" +
                        Sun.getAmount() + "\n" +
                        deck.toString() + "\n"
                    );
                    int input = scanner.nextInt();
                    if (input == 1) {
                        map.plant(scanner.nextInt(), scanner.nextInt() - 1, deck.create(scanner.nextInt() - 1, seconds));
                        map.printMap();
                    } else if (input == 2) {
                        map.dig(scanner.nextInt(), scanner.nextInt() - 1);
                        map.printMap();
                    } else {
                        throw new IllegalArgumentException("Invalid input");
                    }
                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan: " + e.getMessage());
                }
            }
        }).start();

        // Thread for plants and zombies
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
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    public static void pickPlant(Scanner scanner, Deck<Plants> deck) {
        while (deck.size() < 6) {
            try {
                System.out.println("Inventory: ");
                System.out.println(inventory.toString());
                System.out.println();
                System.out.println("Deck: ");
                System.out.println(deck.toString());
                System.out.println(
                    "<1 x> Pilih tanaman untuk dimasukkan ke deck\n" +
                    "<2 x> Pilih tanaman untuk dikeluarkan dari deck\n" +
                    "<3 x y> Pilih tanaman untuk ditukar di deck\n"
                );
                int choice = scanner.nextInt();
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
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    public static void help() {
        System.out.println("Berikut adalah langkah dalam melakukan permainan:");
        System.out.println("1. Pilih tanaman apa yang ingin kalian tanam, pastikan ada sunflower");
    }

    public static void plantLists(Scanner scanner) {
        int choice = -1;
        while (choice != 0){
            System.out.println("Tanaman: ");
            System.out.println(inventory.toString());
            System.out.println();
            try {
                System.out.print("Pilih indeks tanaman (0 untuk kembali ke menu utama): ");
                choice = scanner.nextInt();
                Plants tans = inventory.get(choice-1);
                System.out.println(inventory.toString(tans));
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    public static void zombieLists(Scanner scanner){
        int choice = -1;
        Zombies.addZombie();
        while (choice != 0){
            System.out.println("Zombie: ");
            int i = 1;
            for (Zombies zombies : Zombies.zoms){
                System.out.println(i + ". " + zombies.getName());
                i++;
            }

            try{
                System.out.print("Pilih indeks zombie (0 untuk kembali ke menu utama): ");
                choice = scanner.nextInt();
                Zombies zom = Zombies.zoms.get(choice-1);
                System.out.println(Zombies.toString(zom));
            } catch(Exception e){
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }


    }
}
