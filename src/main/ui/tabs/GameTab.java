package ui.tabs;

import model.Game;
import model.Player;
import model.PlayerBase;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameTab extends Tab implements ActionListener {

    private static final String INIT_GAME = "Welcome to Snake!";
    private JLabel gameIntro;
    private JButton newPlayer = new JButton("New player");
    private JButton returning = new JButton("Returning player");
    private JButton players = new JButton("Player list");
    private JButton start = new JButton("Start game");
    private JButton home = new JButton("Return home");
    private JButton begin = new JButton("Begin");
    private JTextField userInput;
    private CardLayout cardLayout;
    private JPanel introPanel = createIntroPanel();
    private JPanel profilePanel = createProfilePanel();
    private JPanel gamePanel = createGamePanel();

    private static final int TICKS_PER_SECOND = 10;
    private static final String JSON_STORE = "./data/playerbase.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Scanner input;
    private PlayerBase pb;
    private Player player;
    private String name;

    private Game game;

    //EFFECTS: constructs a home tab for console with buttons and a greeting
    public GameTab(SnakeUI controller) {
        super(controller);
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        initButtonListeners();

        this.add(introPanel, "intro");
        this.add(gamePanel, "game");
        this.add(profilePanel, "profile");

        cardLayout.show(this, "intro");

        controller.add(this);

        input = new Scanner(System.in);
        pb = new PlayerBase();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private JPanel createIntroPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        gameIntro = new JLabel(INIT_GAME, JLabel.CENTER);
        gameIntro.setSize(WIDTH, HEIGHT / 3);
        panel.add(gameIntro);
        JPanel buttonRow = formatButtonRow(begin);
        panel.add(buttonRow);

        JPanel buttonRow2 = formatButtonRow(players);
        buttonRow2.setSize(WIDTH, HEIGHT / 6);
        panel.add(buttonRow2);

        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel profile = new JLabel("Profile", JLabel.LEFT);
        profile.setSize(WIDTH, HEIGHT / 2);
        profile.setBounds(10, 10, 100, 40);
        panel.add(profile);

        start.setBounds(390, 10, 100, 20);
        panel.add(start);

        Label enterName = new Label("Enter your player name, then select if you're a new or returning player:");
        enterName.setBounds(10, 50, 400, 20);
        panel.add(enterName);

        userInput = new JTextField();
        userInput.setBounds(10, 80, 120, 20);
        panel.add(userInput);
        placeIntroButtons(panel);
        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel game = new JLabel("Game", JLabel.LEFT);
        game.setSize(WIDTH, HEIGHT / 2);
        game.setBounds(10, 10, 100, 20);
        panel.add(game);

        home.setBounds(10, 30, 120, 20);
        panel.add(home);
        return panel;
    }

    //EFFECTS: creates Arrive and Leave buttons that change greeting message when clicked
    private void placeIntroButtons(JPanel buttons) {
        JPanel buttonRow = formatButtonRow(newPlayer);
        buttonRow.add(returning);
        buttonRow.setSize(WIDTH, HEIGHT / 6);
        buttonRow.setBounds(150, 70, 250, 60);
        buttons.add(buttonRow);
    }

    private void initButtonListeners() {
        newPlayer.addActionListener(this);
        returning.addActionListener(this);
        home.addActionListener(this);
        start.addActionListener(this);
        players.addActionListener(this);
        begin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        name = userInput.getText();
        if (e.getSource() == begin) {
            cardLayout.show(this, "profile");
        }
        if (e.getSource() == newPlayer) {
            processInput("no");
        }
        if (e.getSource() == returning) {
            processInput("yes");
        }
        if (e.getSource() == home) {
            cardLayout.show(this, "intro");
        }
        if (e.getSource() == start) {
            cardLayout.show(this, "game");
        }
        if (e.getSource() == players) {
            getController().getTabbedPane().setSelectedIndex(SnakeUI.PLAYER_TAB_INDEX);
        }
    }

//    // EFFECTS: set up the game by initializing the player and the game
    public void setup(Player p) throws IOException, InterruptedException {
        player = p;
        System.out.println("Load game, or new game?");
//        String userInput = input.nextLine();
//        updateGameData(name, userInput);
        System.out.println("Press 'esc' to save and quit, or close the game.");
    }
//
//    // EFFECTS: gets player name and displays player profile
//    private Player initPlayer() {
//        return processInput(returning);
//    }

    // EFFECTS: returns game with given snake colour and board dimensions
    public Game initGame() {
        System.out.println("Pick your snake colour: ");
        String color = input.nextLine();
        System.out.println("How wide do you want the board to be?");
        int width = input.nextInt();
        System.out.println("How tall do you want the board to be?");
        int height = input.nextInt();
        Game game = new Game(width, height, color);
        System.out.println("Starting game with " + width + " x " + height + " board!");
        return game;
    }

    // EFFECTS: prints out player name and their past high scores for an existing player
    public void displayExistingProfile(Player p) {
        System.out.println("Welcome back " + p.getName() + "!");
        System.out.println("Here are your high scores: ");
        for (int s : p.getScores()) {
            System.out.println(s);
        }
    }

    // EFFECTS: prints out welcome messages for new player
    public void displayNewProfile(Player p) {
        System.out.println("Welcome " + p.getName() + "!");
        System.out.println("Play some games to add to your high score list! ");
    }

//     Credit: JsonSerializationDemo
//     MODIFIES: this
//     EFFECTS: processes user input
    public Player processInput(String userInput) {
        boolean inputYes = userInput.equalsIgnoreCase("yes");
        boolean inputNo = userInput.equalsIgnoreCase("no");
//        if (!inputYes && !inputNo) {
//            System.out.println("Please enter yes or no.");
//            return initPlayer();
//        }
        loadPlayerBase();
        if (inputYes) {
            displayExistingProfile(pb.getPlayerProfile(name));
            return pb.getPlayerProfile(name);
        } else if (inputNo) {
            addPlayer(name);
            savePlayerBase();
            return pb.getPlayerProfile(name);
        }
        // should not reach here
        return null;
    }

//    public Player processNewPlayer() {
//        addPlayer(name);
//        savePlayerBase();
//        return pb.getPlayerProfile(name);
//    }
//
//    public Player processReturningPlayer() {
//        loadPlayerBase();
//        return pb.getPlayerProfile(name);
//    }

    // MODIFIES: player
    // EFFECTS: start a new game, or load game from player's profile
    public void updateGameData(String name, String userInput) throws IOException {
        JSONObject jsonObject = new JSONObject(jsonReader.readFile(JSON_STORE));
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonPlayer = jsonArray.getJSONObject(i);
            if (userInput.equalsIgnoreCase("new")) {
                if (jsonPlayer.get("Name").equals(name)) {
                    Game g = initGame();
                    jsonPlayer.put("Game", g.toString());
                    player.setGame(g);
                    break;
                }
            } else if (userInput.equalsIgnoreCase("load")) {
                System.out.println("Loading game... get ready!");
                System.out.println(player.getGame().toString());
                break;
            }
        }
    }

    // Credit: JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads PlayerBase from file
    private void loadPlayerBase()  {
        try {
            pb = jsonReader.read();
            System.out.println("Loaded PlayerBase from " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Credit: JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: prompt user for name and adds to PlayerBase
    private void addPlayer(String name) {
        pb.addPlayer(new Player(name));
        displayNewProfile(pb.getPlayerProfile(name));
    }

    // Credit: JsonSerializationDemo
    // EFFECTS: saves the PlayerBase to file
    private void savePlayerBase() {
        try {
            jsonWriter.open();
            jsonWriter.write(pb);
            jsonWriter.close();
            System.out.println("Saved PlayerBase to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}