package ui.tabs;

import ui.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
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

    private JScrollPane scrollPane;
    private JPanel topPanel;

    // Credit: SmartHome
    //REQUIRES: SnakeUI controller that holds this tab
    //EFFECTS: creates player tab with buttons
    public PlayerTab(SnakeUI controller) {
        super(controller);
        initialize();
        for (Player p : pb.getPlayers()) {
            addPlayerPanel(displayPlayer(p));
        }
    }

    // MODIFIES: this
    // EFFECTS: displays screen with list of players
    // MODIFIES: this
    public void initialize() {
        border = BorderFactory.createEmptyBorder(0, LEFT, BOTTOM, 0);
        rows = new GridLayout(0, 4);
        setLayout(new BorderLayout());

        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(title);

        addSortButton();
        addRefreshButton();
        addSaveButton();

        add(topPanel, BorderLayout.NORTH); // Add topPanel to the top of the BorderLayout

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel headers = new JPanel(rows);
        headers.setPreferredSize(new Dimension(SnakeUI.WIDTH - LEFT, HEIGHT));
        headers.add(new JLabel("Name", SwingConstants.CENTER));
        headers.add(new JLabel("Scores", SwingConstants.CENTER));
        headers.add(new JLabel("Actions", SwingConstants.CENTER));

        mainPanel.add(headers);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: topPanel
    // EFFECTS: adds sort button to top panel
    public void addSortButton() {
        JButton sortByName = new JButton("Sort alphabetically");
        topPanel.add(sortByName);

        sortByName.addActionListener(e -> {
            pb.sortAlphabetically(pb.getPlayers());
            updatePlayers(pb.getPlayers());
        });
    }

    // MODIFIES: topPanel
    // EFFECTS: adds the refresh button to top panel
    public void addRefreshButton() {
        JButton refresh = new JButton("Refresh");
        topPanel.add(refresh);

        refresh.addActionListener(e -> {
            loadPlayerBase();
            updatePlayers(pb.getPlayers());
        });
    }

    // MODIFIES: topPanel
    // EFFECTS: adds save button to top panel
    public void addSaveButton() {
        JButton save = new JButton("Save");
        topPanel.add(save);

        save.addActionListener(e -> {
            savePlayerBase();
        });
    }

    // MODIFIES: this
    // EFFECTS: refreshes the screen to add any new players
    public void updatePlayers(ArrayList<Player> players) {
        removeAll();
        initialize();
        for (Player p : players) {
            addPlayerPanel(displayPlayer(p));
        }
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds a player panel to the mainPanel
    private void addPlayerPanel(JPanel panel) {
        JPanel mainPanel = (JPanel) ((JViewport) scrollPane.getComponent(0)).getView();
        mainPanel.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for displaying given player
    public JPanel displayPlayer(Player p) {
        JPanel row = new JPanel(rows);
        row.setPreferredSize(new Dimension(SnakeUI.WIDTH, HEIGHT));
        row.setBorder(border);

        JLabel playerName = new JLabel(p.getName());
        JLabel scores = new JLabel(p.scoresToString());

        JButton remove = new JButton("Remove Player");
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
