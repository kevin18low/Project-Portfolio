package ui;

import model.*;
import ui.tabs.*;

import javax.swing.*;
import java.awt.*;

// Credit: SmartHome
// Represents a SnakeUI class that runs the snake program
public class SnakeUI extends JFrame {
    public static final int GAME_TAB_INDEX = 0;
    public static final int PLAYER_TAB_INDEX = 1;
    public static final int SCORE_TAB_INDEX = 2;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JTabbedPane sidebar;
    private Game game;

    // EFFECTS: create a new instance of the snake game
    public SnakeUI() {
        super("Snake Game Console");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds side tabs to the main panel
    private void loadTabs() {
        JPanel gameTab = new GameTab(this);
        JPanel playerTab = new PlayerTab(this);
        JPanel scoreTab = new ScoreTab(this);

        sidebar.add(gameTab, GAME_TAB_INDEX);
        sidebar.setTitleAt(GAME_TAB_INDEX, "Game");
        sidebar.add(playerTab, PLAYER_TAB_INDEX);
        sidebar.setTitleAt(PLAYER_TAB_INDEX, "Players");
        sidebar.add(scoreTab, SCORE_TAB_INDEX);
        sidebar.setTitleAt(SCORE_TAB_INDEX, "Top scores");
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    public Game getGame() {
        return this.game;
    }
}
