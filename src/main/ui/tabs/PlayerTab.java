package ui.tabs;

import ui.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.*;

import java.util.*;

// Represents the tab that shows the list of all existing players
public class PlayerTab extends Tab {
    private static final String PLAYER_LIST = "List of all existing players:";

    private Border border;
    private GridLayout rows;
    private JLabel title = new JLabel(PLAYER_LIST);

    private static final int LEFT = 70;
    private static final int BOTTOM = 5;
    private static final int HEIGHT = 50;

    // Credit: SmartHome
    //REQUIRES: SnakeUI controller that holds this tab
    //EFFECTS: creates player tab with buttons
    public PlayerTab(SnakeUI controller) {
        super(controller);
        initialize();
        for (Player p : pb.getPlayers()) {
            add(displayPlayer(p));
        }
    }

    // MODIFIES: this
    // EFFECTS: displays screen with list of players
    public void initialize() {
        border = BorderFactory.createEmptyBorder(0, LEFT, BOTTOM, 0);
        rows = new GridLayout(1, 2);
        JButton sortByName = new JButton("Sort alphabetically");
        this.add(title);
        this.add(sortByName);

        sortByName.addActionListener(e -> {
            pb.sortAlphabetically(pb.getPlayers());
            updatePlayers(pb.getPlayers());
        });

        JPanel headers = new JPanel(rows);
        headers.setPreferredSize(new Dimension(SnakeUI.WIDTH - LEFT, HEIGHT));
        headers.add(new JLabel("Name", SwingConstants.CENTER));
        headers.add(new JLabel("Scores", SwingConstants.CENTER));
        this.add(headers);
    }

    // MODIFIES: this
    // EFFECTS: refreshes the screen to add any new players
    public void updatePlayers(ArrayList<Player> players) {
        removeAll();
        initialize();
        for (Player p : players) {
            add(displayPlayer(p));
        }
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: displays given player on screen
    public JPanel displayPlayer(Player p) {
        JPanel row = new JPanel(rows);
        row.setPreferredSize(new Dimension(SnakeUI.WIDTH, HEIGHT));
        row.setBorder(border);

        JLabel name = new JLabel(p.getClass().getSimpleName());
        name.setVerticalAlignment(SwingConstants.TOP);


        JLabel playerName = new JLabel(p.getName());
        JLabel scores = new JLabel(p.scoresToString());

        JButton remove = new JButton("Remove Player");
        remove.setMaximumSize(new Dimension(100, 20));
        remove.addActionListener(e -> {
            pb.removePlayer(p);
            updatePlayers(pb.getPlayers());
        });


        row.add(playerName);
        row.add(scores);
        row.add(remove);
        return row;
    }
}
