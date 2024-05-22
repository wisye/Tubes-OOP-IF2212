package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;
import org.tubesoopif2212.Tile.*;
import org.tubesoopif2212.Zombies.*;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

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
                choice = scanner.nextInt();
                if (choice == 1) {
                    startGame(scanner);
                    continue;
                } else if (choice == 2) {
                    help(scanner);
                    continue;
                } else if (choice == 3) {
                    plantLists(scanner);
                    continue;
                } else if (choice == 4) {
                    zombieLists(scanner);
                    continue;
                } else if (choice == 5) {
                    System.out.println("Byee");
                    break;
                } else {
                    throw new IllegalArgumentException("Choice is invalid");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
            } catch (Exception e) {
                System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
            }
        }
    }

    public static void menu(Scanner scanner) {
        // Teks ASCII art tanpa pewarnaan untuk debugging
        Print.printMenu();
        String boldStart = "\033[1m";
        String boldEnd = "\033[0m";
        
        // Printing the menu with bold text for the title
        System.out.println("------------------------------");
        System.out.println("|" + boldStart + "          MENU UTAMA        " + boldEnd + "|");
        System.out.println("------------------------------");
        System.out.println("| 1. START                   |");
        System.out.println("| 2. HELP                    |");
        System.out.println("| 3. PLANT LIST              |");
        System.out.println("| 4. ZOMBIE LIST             |");
        System.out.println("| 5. EXIT                    |");
        System.out.println("------------------------------");
        System.out.print("Pilihan Anda: ");
    }

    public static void startGame(Scanner scanner) {
        gameOver = false;
        seconds = 0;
        Sun.getInstance();
        Sun.setSun(50);
        Map map = new Map();
        Random random = new Random();
        Actions action = new Actions();
        Deck<Plants> deck = new Deck<Plants>();
        ZombieFactory zf = new ZombieFactory();
        // Inventory inventory = new Inventory();  // Pastikan inventory diinisialisasi

        pickPlant(scanner, deck);

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

                    if ((seconds >= 75 && seconds <= 78) ||
                        (seconds >= 135 && seconds <= 138)) { // BONUS YANG FLAG
                        Zombies.maxAmount = 25;
                        zf.flag();
                    }

                    if ((seconds >= 20 && seconds <= 74) ||
                        (seconds >= 79 && seconds <= 134) ||
                        (seconds >= 139 && seconds <= 160)) {
                        Zombies.maxAmount = 10;
                        zf.spawnZombies();
                    }

                    for (int row = 0; row < 5; row++) {
                        if (!Map.getTile(row, 0).getZombies().isEmpty()) {
                            gameOver = true;

                            break;
                        }
                    }
                    Thread.sleep(1000); // sleep for 1 second
                    seconds++;
                    // map.printMap();
                    // System.out.println();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
                }
            }

            if (gameOver) {
                Print.printLose();
            }

            if (seconds >= 200) {
                Print.printWin();
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
                        "\n" + 
                        "Jumlah sun : " + Sun.getAmount() + "\n" +
                        "Waktu : " + seconds + "\n" +
                        "Max zombie amount : " + Zombies.amount + "\n" + 
                        "\n" +
                        deck.toString() + "\n"
                    );
                    int input = scanner.nextInt();
                    if (input == 1) {
                        map.plant(scanner.nextInt(), scanner.nextInt() - 1, deck.create(scanner.nextInt() - 1, seconds));
                    } else if (input == 2) {
                        map.dig(scanner.nextInt(), scanner.nextInt() - 1);
                    } else {
                        throw new IllegalArgumentException("Error : Invalid input");
                    }
                } catch (InputMismatchException e){
                    scanner.nextLine();
                    System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
                } catch (Exception e) {
                    System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
                } finally {
                    map.printMap();
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
                System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
            }
        }
        try{
            gameThread.join();
        } catch(Exception e){}
    }

    public static void pickPlant(Scanner scanner, Deck<Plants> deck) {
        while (deck.size() < 6) {
            try {
                // System.out.println("\nInventory: ");
                // System.out.println(inventory.toString());
                // System.out.println();
                // System.out.println("Deck: ");
                // System.out.println(deck.toString());

                String[] inventoryLines = inventory.toString().split("\n");
                String[] deckLines = deck.toString().split("\n");

                int maxLength = Math.max(inventoryLines.length, deckLines.length);

                String boldStart = "\033[1m";
                String boldEnd = "\033[0m";
                
                // Printing the menu with bold text for the title
                System.out.println();
                System.out.println("--------------------------------------");
                System.out.println(boldStart + "Inventory:\t\t\tDeck:" + boldEnd);
                System.out.println("--------------------------------------");
                for (int i = 0; i < maxLength; i++) {
                    String inventoryLine = i < inventoryLines.length ? inventoryLines[i] : "";
                    String deckLine = i < deckLines.length ? deckLines[i] : "";

                    System.out.printf("%-20s\t\t%s\n", inventoryLine, deckLine);
                }
                System.out.println(
                    "\n<1 x> Pilih tanaman untuk dimasukkan ke deck\n" +
                    "<2 x> Pilih tanaman untuk dikeluarkan dari deck\n" +
                    "<3 x y> Pilih tanaman untuk ditukar di deck\n"
                );
                int choice = scanner.nextInt();
                if (choice == 1) {
                    try{
                        int x = scanner.nextInt() - 1;
                        inventory.choosePlant(x, deck);
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
                    } catch (Exception e){
                        System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
                    }
                } else if (choice == 2) {
                    try{
                        int x = scanner.nextInt() - 1;
                        inventory.removePlant(x, deck);
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
                    } catch (Exception e){
                        System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
                    }
                } else if (choice == 3) {
                    try{
                        int x = scanner.nextInt() - 1;
                        int y = scanner.nextInt() - 1;
                        inventory.swapPlant(x, y, deck);
                    } catch (InputMismatchException e){
                        scanner.nextLine();
                        System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
                    } catch (Exception e){
                        System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
                    }
                } else {
                    scanner.nextLine();
                    throw new Exception(choice + " does not exists");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
            } catch (Exception e){
                System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
            }
        }
    }

    public static void help(Scanner scanner) {
        int choice = -1;
        while(choice != 0){
            Print.printHelp();
            System.out.print("Masukkan 0 jika sudah paham: ");
            choice = scanner.nextInt();
        }
        
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
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
            } catch (Exception e) {
                System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
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
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("\u001B[91m" + "Error : Invalid input" + "\u001B[0m");
            } catch(Exception e){
                System.out.println("\u001B[91m" + "Error : " + e.getMessage() + "\u001B[0m");
            }
        }
    }

    // warna main
    private static String[] generateGradient(int r1, int g1, int b1, int r2, int g2, int b2, int steps) {
        String[] gradient = new String[steps];
        for (int i = 0; i < steps; i++) {
            int r = r1 + (r2 - r1) * i / (steps - 1);
            int g = g1 + (g2 - g1) * i / (steps - 1);
            int b = b1 + (b2 - b1) * i / (steps - 1);
            gradient[i] = String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
        }
        return gradient;
    }

    private static String printa(){
        return ("1. Start\n" +
                "2. Help\n" +
                "3. Plants List\n" +
                "4. Zombies List\n" +
                "5. Exit");
    }
}
