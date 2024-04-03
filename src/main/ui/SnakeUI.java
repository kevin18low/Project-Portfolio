package ui;

import model.*;
import model.Event;
import ui.tabs.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Credit: SmartHome
// Represents a SnakeUI class that runs the snake program
public class SnakeUI extends JFrame implements WindowListener {
    public static final int GAME_TAB_INDEX = 0;
    public static final int PLAYER_TAB_INDEX = 1;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JTabbedPane sidebar;

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
        addWindowListener(this);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds side tabs to the main panel
    private void loadTabs() {
        JPanel gameTab = new GameTab(this);
        JPanel playerTab = new PlayerTab(this);

        sidebar.add(gameTab, GAME_TAB_INDEX);
        sidebar.setTitleAt(GAME_TAB_INDEX, "Game");
        sidebar.add(playerTab, PLAYER_TAB_INDEX);
        sidebar.setTitleAt(PLAYER_TAB_INDEX, "Players");
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return sidebar;
    }

    public void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        printLog(EventLog.getInstance());
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
