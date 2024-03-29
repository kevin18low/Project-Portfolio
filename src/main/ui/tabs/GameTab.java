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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GameTab extends Tab implements ActionListener {

    private static final String INIT_GAME = "Welcome to Snake!";
    private JLabel gameIntro;
    private JButton newPlayer = new JButton("New Player");
    private JButton returning = new JButton("Returning Player");
    private JButton players = new JButton("Player List");
    private JButton start = new JButton("Start Game");
    private JButton home = new JButton("Return Home");
    private JButton begin = new JButton("Begin");
    private JButton newGame = new JButton("New Game");
    private JButton loadGame = new JButton("Load Game");
    private JButton submit = new JButton("Go!");

    private JTextField userInput;
    private JTextField boardWidth;
    private JTextField boardHeight;
    private JTextField snakeColor;

    private CardLayout cardLayout;
    private JPanel introPanel = createIntroPanel();
    private JPanel profilePanel = createProfilePanel();
    private JPanel gamePanel = createGamePanel();

    private DocumentListener documentListener;

    private static final int TICKS_PER_SECOND = 10;
    private Scanner input;
    private Player player;
    private String name;

    private Game game;

    private ImageIcon snakeImage;

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
    }

    private JPanel createIntroPanel() {
        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(3, 3));
        panel.setLayout(null);
        gameIntro = new JLabel(INIT_GAME, JLabel.CENTER);
//        gameIntro.setSize(WIDTH, HEIGHT / 3);
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
            // no thanks
        }
        JLabel img = new JLabel(snakeImage);
        img.setBounds(0, 0, 500, 600);
        panel.add(img);

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
        newGame.addActionListener(this);
        loadGame.addActionListener(this);
//        submit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        name = userInput.getText();
        if (e.getSource() == begin) {
            cardLayout.show(this, "profile");
        } else if (e.getSource() == newPlayer) {
            processInput(true);
        } else if (e.getSource() == returning) {
            processInput(false);
        } else if (e.getSource() == home) {
            homePressed();
        } else if (e.getSource() == start) {
            cardLayout.show(this, "game");
        } else if (e.getSource() == players) {
            getController().getTabbedPane().setSelectedIndex(SnakeUI.PLAYER_TAB_INDEX);
        } else if (e.getSource() == newGame) {
            newGamePressed();
        } else if (e.getSource() == loadGame) {
            loadGamePressed();
//        } else if (e.getSource() == submit) {
//            submitPressed();
        }
    }

    public void homePressed() {
        cardLayout.show(this, "intro");
        newPlayer.setVisible(true);
        returning.setVisible(true);
    }

    public void newGamePressed() {
        profilePanel.removeAll();
        profilePanel.revalidate();
        profilePanel.repaint();
        newGameLabels();
        newGameTextFields();
        try {
            getGameData(name, false);
        } catch (Exception exception) {
            // well
        }
    }

    public void newGameLabels() {
        Label width = new Label("Enter board width:");
        width.setBounds(10, 100, 120, 30);
        Label height = new Label("Enter board height:");
        height.setBounds(10, 130, 120, 30);
        Label color = new Label("Enter snake colour:");
        color.setBounds(10, 160, 120, 30);
        submit.setBounds(210, 200, 70, 30);
        profilePanel.add(width);
        profilePanel.add(height);
        profilePanel.add(color);
        profilePanel.add(submit);
    }

    public void newGameTextFields() {
        boardWidth = new JTextField();
        boardWidth.setBounds(130, 100, 150, 20);
        boardHeight = new JTextField();
        boardHeight.setBounds(130, 130, 150, 20);
        snakeColor = new JTextField();
        snakeColor.setBounds(130, 160, 150, 20);
        profilePanel.add(boardWidth);
        profilePanel.add(boardHeight);
        profilePanel.add(snakeColor);
        checkFieldsFilled();
        boardWidth.getDocument().addDocumentListener(documentListener);
        boardHeight.getDocument().addDocumentListener(documentListener);
        snakeColor.getDocument().addDocumentListener(documentListener);
    }

    public void checkFieldsFilled() {
        submit.setEnabled(false);
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
                // Check if both text fields are filled
                submit.setEnabled(!boardHeight.getText().isEmpty() && !boardWidth.getText().isEmpty()
                        && !snakeColor.getText().isEmpty());
            }
        };
    }

    public void bringBackProfile() {
        profilePanel.removeAll();
        this.add(createProfilePanel(), "profile");
        newPlayer.setVisible(true);
        returning.setVisible(true);
        cardLayout.show(this, "profile");
        profilePanel.revalidate();
        profilePanel.repaint();
    }

    public void loadGamePressed() {
        try {
            getGameData(name, true);
        } catch (Exception exception) {
            // well
        }
    }

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

    // EFFECTS: prints out welcome messages for new player
    public void displayNewProfile(Player p) {
        Label welcome = new Label("Welcome " + p.getName() + "!");
        welcome.setBounds(10, 100, 200, 30);
        profilePanel.add(welcome);
        Label playGames = new Label("Play some games to add to your high score list! ");
        playGames.setBounds(10, 120, 300, 30);
        profilePanel.add(playGames);
    }

//     Credit: JsonSerializationDemo
//     MODIFIES: this
//     EFFECTS: processes user input
    public void processInput(boolean isNew) {
        if (!isNew) {
            try {
                displayExistingProfile(pb.getPlayerProfile(name));
            } catch (NullPointerException e) {
                Label nullPlayer = new Label("Your player name isn't recognized.");
                nullPlayer.setBounds(10, 120,  300, 30);
                profilePanel.add(nullPlayer);
            }
//            return pb.getPlayerProfile(name);
        } else if (isNew) {
            if (pb.getPlayerProfile(name) == null) {
                addPlayer(name);
                savePlayerBase();
            } else {
                System.out.println("name in use");
                userInput.setText(null);
                return;
            }
        }
        newPlayer.setVisible(false);
        returning.setVisible(false);
        newOrLoad(isNew);
    }

    public void newOrLoad(boolean isNew) {
        JPanel buttonRow = formatButtonRow(newGame);
        if (!isNew) {
            buttonRow.add(loadGame);
        }
        buttonRow.setSize(WIDTH, HEIGHT / 6);
        buttonRow.setBounds(100, 10, 250, 60);
        profilePanel.add(buttonRow);
    }

    public void newGameLoadGame(Boolean load, JSONObject jsonPlayer) {
        if (!load) {
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonPressed = e.getActionCommand();
                    if (buttonPressed.equals("Go!")) {
                        Game g = new Game(Integer.parseInt(boardWidth.getText()),
                                Integer.parseInt(boardHeight.getText()), snakeColor.getText());
                        jsonPlayer.put("Game", g.toString());
                        pb.getPlayerProfile(name).setGame(g);
                        savePlayerBase();
                        bringBackProfile();
                    }
                }
            });
        } else if (load) {
            showGameData(name);
            bringBackProfile();
        }
    }

    public void showGameData(String name) {
        Label gameData = new Label(pb.getPlayerProfile(name).getGame().toString());
        gameData.setBounds(150,300,500,30);
        profilePanel.add(gameData);
        //buggy
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // no
        }
    }

    // MODIFIES: player
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

    // Credit: JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: prompt user for name and adds to PlayerBase
    private void addPlayer(String name) {
        pb.addPlayer(new Player(name));
        displayNewProfile(pb.getPlayerProfile(name));
    }
}