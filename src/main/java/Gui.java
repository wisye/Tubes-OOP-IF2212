import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import Plants.Plants;

public class Gui extends JFrame {


    public Gui() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            Scanner scanner = new Scanner(System.in);
            Main.start(scanner, this);
        });

        JButton helpButton = new JButton("Help");
        // helpButton.addActionListener(e -> help());

        JButton plantsListButton = new JButton("Plants List");
        // plantsListButton.addActionListener(e -> plantsList());

        JButton zombiesListButton = new JButton("Zombies List");
        // zombiesListButton.addActionListener(e -> zombiesList());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(helpButton);
        add(plantsListButton);
        add(zombiesListButton);
        add(exitButton);

        setTitle("Guardians of Flora Versus the Legions of Necrosis!");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void startMenu(Deck<Plants> deck, Inventory inventory) {
        getContentPane().removeAll();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel deckPanel = new JPanel();
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.X_AXIS));
        for (int i = 0; i < 6; i++) {
            JButton deckSlot = new JButton(deck.size() > i ? deck.get(i).getClass().getName() : "Empty Slot");
            int j = i;
            deckSlot.addActionListener(e -> {
                if (deck.size() > j) {
                    Plants plant = deck.remove(j);
                    inventory.add(plant);
                    startMenu(deck, inventory);
                }
            });
            deckPanel.add(deckSlot);
        }

        JPanel inventoryPanel = new JPanel(new GridLayout(4, 5));
        for (int i = 0; i < inventory.size(); i++) {
            JButton inventorySlot = new JButton(inventory.get(i).getName());
            int finalI = i;
            inventorySlot.addActionListener(e -> {
                if (deck.size() < 6) {
                    inventory.choosePlant(null, deck);
                    startMenu(deck, inventory);
                }
            });
            inventoryPanel.add(inventorySlot);
        }

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            // Code to start the game goes here
        });

        add(deckPanel);
        add(inventoryPanel);
        add(startButton);

        revalidate();
        repaint();
    }
}