package org.tubesoopif2212;

public class Print {
    public static void printMenu() {
        // ANSI escape codes for colors
        String[] greenGradient = generateGradient(144, 238, 144, 34, 139, 34, 7);
        String[] redGradient = generateGradient(255, 182, 193, 139, 0, 0, 7);
        String[] blueGradient = generateGradient(173, 216, 230, 0, 0, 139, 7);

        // // ASCII art lines
        String[] art = {
            " ___      _______  ___      _______  _______  _______  __    _    __   __  _______    __   __  ___   _______  __   __  _______  _______  ___     ",
            "|   |    |   _   ||   |    |   _   ||       ||   _   ||  |  | |  |  | |  ||       |  |  |_|  ||   | |       ||  | |  ||   _   ||       ||   |    ",
            "|   |    |  |_|  ||   |    |  |_|  ||    _  ||  |_|  ||   |_| |  |  |_|  ||  _____|  |       ||   | |       ||  |_|  ||  |_|  ||    ___||   |    ",
            "|   |    |       ||   |    |       ||   |_| ||       ||       |  |       || |_____   |       ||   | |       ||       ||       ||   |___ |   |    ",
            "|   |___ |       ||   |___ |       ||    ___||       ||  _    |  |       ||_____  |  |       ||   | |      _||       ||       ||    ___||   |___ ",
            "|       ||   _   ||       ||   _   ||   |    |   _   || | |   |   |     |  _____| |  | ||_|| ||   | |     |_ |   _   ||   _   ||   |___ |       |",
            "|_______||__| |__||_______||__| |__||___|    |__| |__||_|  |__|    |___|  |_______|  |_|   |_||___| |_______||__| |__||__| |__||_______||_______|"
        };

        // Print the ASCII art with color gradient sections
        for (int i = 0; i < art.length; i++) {
            String line = art[i];

            // First section (Green gradient)
            for (int j = 0; j < 65; j++) {
                System.out.print(greenGradient[i % greenGradient.length] + line.charAt(j));
            }

            // Second section (Red gradient)
            for (int j = 65; j < 85; j++) {
                System.out.print(redGradient[i % redGradient.length] + line.charAt(j));
            }

            // Third section (Blue gradient)
            for (int j = 85; j < line.length(); j++) {
                System.out.print(blueGradient[i % blueGradient.length] + line.charAt(j));
            }

            // Reset color and move to the next line
            System.out.print("\u001B[0m\n");
        }

        // dalam sebuah tile ada lebih dari 1 zombie
    }
    
    public static void printLose(){
        // ANSI escape codes for colors
        String[] colors = {
            "\u001B[38;2;255;255;0m",   // Kuning
            "\u001B[38;2;255;204;0m",
            "\u001B[38;2;255;153;0m",
            "\u001B[38;2;255;102;0m",
            "\u001B[38;2;255;51;0m",
            "\u001B[38;2;255;0;0m"    // Merah gelap
        };

        // ASCII art lines
        String[] art = {
            "██    ██  ██████  ██    ██     ██       ██████  ███████ ███████ ██ ",
            " ██  ██  ██    ██ ██    ██     ██      ██    ██ ██      ██      ██ ",
            "  ████   ██    ██ ██    ██     ██      ██    ██ ███████ █████   ██ ",
            "   ██    ██    ██ ██    ██     ██      ██    ██      ██ ██         ",
            "   ██     ██████   ██████      ███████  ██████  ███████ ███████ ██ ",
            "                                                                    ",
            "                                                                    "
        };

        // Print the ASCII art with color gradient
        int terminalWidth = 80;
        String horizontalLine = "=".repeat(terminalWidth);

        // Print the top horizontal line
        System.out.println(horizontalLine);

        // Print the ASCII art with color gradient
        System.out.println();
        for (int i = 0; i < art.length; i++) {
            int padding = (terminalWidth - art[i].length()) / 2;
            System.out.print(" ".repeat(padding));
            System.out.println(colors[i % colors.length] + art[i] + "\u001B[0m");
        }
        System.out.println(horizontalLine);
    }

    public static void printWin(){
        String[] colors = {
            "\u001B[38;2;255;255;0m",   // Kuning
            "\u001B[38;2;204;255;0m",
            "\u001B[38;2;153;255;0m",
            "\u001B[38;2;102;255;0m",
            "\u001B[38;2;51;255;0m",
            "\u001B[38;2;0;255;0m",    // Hijau terang
            "\u001B[38;2;0;204;0m",    // Hijau sedang
            "\u001B[38;2;0;153;0m",    // Hijau tua
        };

        // ASCII art lines
        String[] art = {
            "██    ██  ██████  ██    ██     ██     ██ ██ ███    ██ ██ ",
            " ██  ██  ██    ██ ██    ██     ██     ██ ██ ████   ██ ██ ",
            "  ████   ██    ██ ██    ██     ██  █  ██ ██ ██ ██  ██ ██ ",
            "   ██    ██    ██ ██    ██     ██ ███ ██ ██ ██  ██ ██    ",
            "   ██     ██████   ██████       ███ ███  ██ ██   ████ ██ ",
            "                                                         ",
            "                                                         "
        };

        // Print the ASCII art with color gradient
        for (int i = 0; i < art.length; i++) {
            System.out.println(colors[i % colors.length] + art[i] + "\u001B[0m");
        }
    }

    public static void printHelp(){
        String[] art = {
            "  _  _ ___ _    ___ ",
            " | || | __| |  | _ \\",
            " | __ | _|| |__|  _/",
            " |_||_|___|____|_|  ",
            "                    "
        };
        
        int terminalWidth = 80;
        String horizontalLine = "=".repeat(terminalWidth);

        // Print the ASCII art without centering
        for (String line : art) {
            System.out.println(line);
        }

        // Print the bottom horizontal line
        System.out.println(horizontalLine);
        
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("| 1. Pilih tanaman apa yang ingin kalian tanam, pastikan ada sunflower  |");
        System.out.println("| 2. Letakkan tanaman di koordinat yang diinginkan                      |");
        System.out.println("| 3. Lindungi rumah dari serangan zombie                                |");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("");
    }

    private static void printInventory(){

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
}
