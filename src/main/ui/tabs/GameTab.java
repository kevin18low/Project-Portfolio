package ui.tabs;

import model.*;
import model.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

// Represents the game tab that holds all info related to the game and game setup
public class GameTab extends Tab implements ActionListener, WindowListener {

    private static final String INIT_GAME = "Welcome to Snake!";
    private JButton newPlayer = new JButton("New Player");
    private JButton returning = new JButton("Returning Player");
    private JButton players = new JButton("Player List");
    private JButton start = new JButton("Start Game");
    private JButton home = new JButton("Return Home");
    private JButton begin = new JButton("Begin");
    private JButton newGame = new JButton("New Game");
    private JButton loadGame = new JButton("Load Game");
    private JButton submit = new JButton("Go!");

    private JTextField userName;
    private JTextField boardSize;
    private JTextField snakeColor;

    private CardLayout cardLayout;
    private JPanel introPanel = createIntroPanel();
    private JPanel profilePanel = createProfilePanel();

    private DocumentListener documentListener;
    private String name;

    private ImageIcon snakeImage;

    private Player player;
    private Game playerGame;

    // Credit: SmartHome
    //EFFECTS: constructs a game tab for console
    public GameTab(SnakeUI controller) {
        super(controller);
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        initButtonListeners();

        this.add(introPanel, "intro");
        this.add(profilePanel, "profile");

        cardLayout.show(this, "intro");

        controller.add(this);
    }

    // EFFECTS: creates the screen shown when the program runs
    private JPanel createIntroPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel gameIntro = new JLabel(INIT_GAME, JLabel.CENTER);
        gameIntro.setBounds(150, 0, 200, 100);
        panel.add(gameIntro);
        JPanel buttonRow = formatButtonRow(begin);
        buttonRow.setBounds(200, 70, 100, 40);
        panel.add(buttonRow);

        JPanel buttonRow2 = formatButtonRow(players);
        buttonRow2.setBounds(200, 110, 100, 40);
        panel.add(buttonRow2);

        try {
            snakeImage = new ImageIcon(new URL(
                    "https://preview.redd.it/q6tds1behfm21.jpg?auto=webp&s=85478b618609bb1657e8f60a053c8051e5c1ebcd"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JLabel img = new JLabel(snakeImage);
        img.setBounds(0, 0, 500, 600);
        panel.add(img);

        return panel;
    }

    // EFFECTS: creates screen that shows the player's info
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel profile = new JLabel("Profile", JLabel.LEFT);
        profile.setSize(WIDTH, HEIGHT / 2);
        profile.setBounds(10, 10, 100, 40);
        panel.add(profile);

        start.setBounds(390, 10, 100, 20);
        panel.add(start);
        start.setEnabled(false);

        Label enterName = new Label("Enter your player name, then select if you're a new or returning player:");
        enterName.setBounds(10, 50, 400, 20);
        panel.add(enterName);

        userName = new JTextField();
        userName.setBounds(10, 80, 120, 20);
        panel.add(userName);
        placeIntroButtons(panel);
        return panel;
    }

    //EFFECTS: creates New and Returning buttons for players
    private void placeIntroButtons(JPanel buttons) {
        JPanel buttonRow = formatButtonRow(newPlayer);
        buttonRow.add(returning);
        buttonRow.setSize(WIDTH, HEIGHT / 6);
        buttonRow.setBounds(150, 70, 250, 60);
        buttons.add(buttonRow);
        checkNameFilled();
        userName.getDocument().addDocumentListener(documentListener);
    }

    // EFFECTS: initializes action listeners for all buttons
    private void initButtonListeners() {
        newPlayer.addActionListener(this);
        returning.addActionListener(this);
        home.addActionListener(this);
        start.addActionListener(this);
        players.addActionListener(this);
        begin.addActionListener(this);
        newGame.addActionListener(this);
        loadGame.addActionListener(this);
    }

    // EFFECTS: describes behaviour of each button when pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        name = userName.getText();
        if (e.getSource() == begin) {
            cardLayout.show(this, "profile");
        } else if (e.getSource() == newPlayer) {
            processInput(true);
        } else if (e.getSource() == returning) {
            processInput(false);
        } else if (e.getSource() == home) {
            homePressed();
        } else if (e.getSource() == start) {
            startPressed();
        } else if (e.getSource() == players) {
            getController().getTabbedPane().setSelectedIndex(SnakeUI.PLAYER_TAB_INDEX);
        } else if (e.getSource() == newGame) {
            newGamePressed();
        } else if (e.getSource() == loadGame) {
            loadGamePressed();
        }
    }

    // EFFECTS: starts a snake game when start is pressed
    public void startPressed() {
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(playerGame.getBoardWidth(), playerGame.getBoardHeight());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton save = new JButton("Save game");
        save.setBounds(50, 10, 100, 20);
        frame.add(save);
        SnakeGame snakeGame = new SnakeGame(playerGame, player, this);
        frame.add(snakeGame);
        frame.addWindowListener(this);
        frame.pack();

        snakeGame.requestFocus();
    }

    // MODIFIES: this
    // EFFECTS: shows the home card
    public void homePressed() {
        cardLayout.show(this, "intro");
        newPlayer.setVisible(true);
        returning.setVisible(true);
    }

    // EFFECTS: loads in game with player's saved data
    public void loadGamePressed() {
        start.setEnabled(true);
        try {
            getGameData(name, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        playerGame = pb.getPlayerProfile(name).getGame();
    }

    // MODIFIES: this
    // EFFECTS: sets up screen to prompt for new game dimensions
    public void newGamePressed() {
        profilePanel.removeAll();
        profilePanel.revalidate();
        profilePanel.repaint();
        newGameLabels();
        newGameTextFields();
        try {
            getGameData(name, false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // MODIFIES: profilePanel
    // EFFECTS: sets up all labels for new game prompts
    public void newGameLabels() {
        Label width = new Label("Enter tile size:");
        width.setBounds(10, 100, 120, 30);
        Label color = new Label("Enter snake colour:");
        color.setBounds(10, 130, 120, 30);
        submit.setBounds(210, 170, 70, 30);
        profilePanel.add(width);
        profilePanel.add(color);
        profilePanel.add(submit);
    }

    // MODIFIES: profilePanel
    // EFFECTS: sets up all text fields for new game prompts
    public void newGameTextFields() {
        boardSize = new JTextField();
        boardSize.setBounds(130, 100, 150, 20);
        snakeColor = new JTextField();
        snakeColor.setBounds(130, 130, 150, 20);
        profilePanel.add(boardSize);
        profilePanel.add(snakeColor);
        checkFieldsFilled();
        boardSize.getDocument().addDocumentListener(documentListener);
        snakeColor.getDocument().addDocumentListener(documentListener);
    }

    // EFFECTS: turns submit button off until all fields are filled
    public void checkFieldsFilled() {
        submit.setEnabled(false);
        newPlayer.setEnabled(false);
        returning.setEnabled(false);
        documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonState();
            }

            private void updateButtonState() {
                submit.setEnabled(!boardSize.getText().isEmpty()
                        && !snakeColor.getText().isEmpty());
            }
        };
    }

    // EFFECTS: makes sure the user enters a player name
    public void checkNameFilled() {
        newPlayer.setEnabled(false);
        returning.setEnabled(false);
        documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonState();
            }

            private void updateButtonState() {
                newPlayer.setEnabled(!userName.getText().isEmpty());
                returning.setEnabled(!userName.getText().isEmpty());
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: re-displays the main profile screen
    public void bringBackProfile() {
        profilePanel.removeAll();
        this.add(createProfilePanel(), "profile");
        newPlayer.setVisible(true);
        returning.setVisible(true);
        cardLayout.show(this, "profile");
        profilePanel.revalidate();
        profilePanel.repaint();
        start.setEnabled(true);
    }

    // MODIFIES: profilePanel
    // EFFECTS: prints out player name and their past high scores for an existing player
    public void displayExistingProfile(Player p) throws NullPointerException {
        Label welcome = new Label("Welcome back " + p.getName() + "!");
        welcome.setBounds(10, 100, 200, 30);
        profilePanel.add(welcome);
        Label scores = new Label("Here are your high scores: ");
        scores.setBounds(10, 120, 300, 30);
        profilePanel.add(scores);
        int start = 120;
        int counter = 0;
        for (int s : p.getScores()) {
            Label score = new Label(String.valueOf(s));
            score.setBounds(10, start + counter, 50, 30);
            profilePanel.add(score);
            counter += 20;
        }
    }

    // MODIFIES: profilePanel
    // EFFECTS: prints out welcome messages for new player
    public void displayNewProfile(Player p) {
        Label welcome = new Label("Welcome " + p.getName() + "!");
        welcome.setBounds(10, 100, 200, 30);
        profilePanel.add(welcome);
        Label playGames = new Label("Play some games to add to your high score list! ");
        playGames.setBounds(10, 120, 300, 30);
        profilePanel.add(playGames);
    }

    // MODIFIES: profilePanel, pb
    // EFFECTS: processes user input
    public void processInput(boolean isNew) {
        if (!isNew) {
            try {
                displayExistingProfile(pb.getPlayerProfile(name));
            } catch (NullPointerException e) {
                Label nullPlayer = new Label("Your player name isn't recognized.");
                nullPlayer.setBounds(10, 120,  300, 30);
                profilePanel.add(nullPlayer);
            }
        } else if (isNew) {
            if (pb.getPlayerProfile(name) == null) {
                addPlayer(name);
                savePlayerBase();
            } else {
                System.out.println("name in use");
                userName.setText(null);
                return;
            }
        }
        player = pb.getPlayerProfile(name);
        newPlayer.setVisible(false);
        returning.setVisible(false);
        newOrLoad(isNew);
    }

    // MODIFIES: profilePanel
    // EFFECTS: new players can only play new games. Returning players can play a new game or load an old game.
    public void newOrLoad(boolean isNew) {
        JPanel buttonRow = formatButtonRow(newGame);
        if (!isNew) {
            buttonRow.add(loadGame);
        }
        buttonRow.setSize(WIDTH, HEIGHT / 6);
        buttonRow.setBounds(100, 10, 250, 60);
        profilePanel.add(buttonRow);
    }

    // EFFECTS: start a new game, or load game from player's profile
    public void getGameData(String name, Boolean load) throws IOException {
        JSONObject jsonObject = new JSONObject(jsonReader.readFile(JSON_STORE));
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonPlayer = jsonArray.getJSONObject(i);
            if (jsonPlayer.get("Name").equals(name)) {
                newGameLoadGame(load, jsonPlayer);
                break;
            }
        }
    }

    // MODIFIES: pb
    // EFFECTS: starts old game if load is pressed, prompts new game if new is pressed
    public void newGameLoadGame(Boolean load, JSONObject jsonPlayer) {
        if (!load) {
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonPressed = e.getActionCommand();
                    if (buttonPressed.equals("Go!")) {
                        Game g = new Game(snakeColor.getText(), Integer.parseInt(boardSize.getText()));
                        jsonPlayer.put("Game", g.toString());
                        pb.getPlayerProfile(name).setGame(g);
                        playerGame = new Game(snakeColor.getText(), Integer.parseInt(boardSize.getText()));
                        savePlayerBase();
                        bringBackProfile();
                    }
                }
            });
        } else if (load) {
            showGameData(name);
        }
    }

    // EFFECTS: shows the stats of the game being loaded
    public void showGameData(String name) {
        Label gameData = new Label(pb.getPlayerProfile(name).getGame().toString());
        gameData.setBounds(160,360,500,30);
        profilePanel.add(gameData);
        profilePanel.repaint();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: pb
    // EFFECTS: adds player with given name to PlayerBase
    private void addPlayer(String name) {
        pb.addPlayer(new Player(name));
        displayNewProfile(pb.getPlayerProfile(name));
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