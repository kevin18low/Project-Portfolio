package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Game;
import model.Player;
import model.PlayerBase;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.IOException;

// Represents a SnakeApp that runs a game of snake

public class SnakeAppLanterna {
    private Screen screen;

    private static final String JSON_STORE = "./data/playerbase.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Scanner input;
    private PlayerBase pb;
    private Player player;
    private String name;

    // Runs a game of snake
    public SnakeAppLanterna() throws IOException {
        input = new Scanner(System.in);
        pb = new PlayerBase();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        run();
    }

    // EFFECTS: runs program
    public void run() throws IOException {
        setup();
        screenDisplay();

    }

    // EFFECTS: set up the game by initializing the player and the game
    public void setup() throws IOException {
        System.out.println("Welcome to Snake!");
        player = initPlayer();
        System.out.println("Load game, or new game?");
        String userInput = input.nextLine();
        updateGameData(name, userInput);
    }

    // EFFECTS: set up the screen and controls for the game
    public void screenDisplay() throws IOException {
        screen = new DefaultTerminalFactory().createScreen();
        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();

        while (true) {
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                System.out.println(keyStroke);
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    System.out.println("Saving and quitting...");
                    savePlayerBase();
                    break;
                }
                player.getGame().getSnake().turn(keyStroke);
                tg.setForegroundColor(TextColor.ANSI.CYAN);
                tg.putString(1, 1, "Current direction: " + player.getGame().getSnake().getDirection());
                tg.putString(1, 2, "Current dx: " + player.getGame().getSnake().getDx());
                tg.putString(1, 3, "Current dy: " + player.getGame().getSnake().getDy());
                screen.refresh();
                screen.clear();
                System.out.println(player.getGame().getSnake().getDirection());
            }
        }
        screen.stopScreen();
    }

    // EFFECTS: gets player name and displays player profile
    private Player initPlayer() {
        System.out.println("Are you a returning player?");
        String returning = input.nextLine();
        return processInput(returning);
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
        System.out.println("Press 'escape' at any time to save and quit!");
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

    // EFFECTS: prints out names of all existing players
    public void allPlayers() {
        System.out.println("Here are all the players in the database:");
        for (Player p : pb.getPlayers()) {
            System.out.println(p.getName());
        }
    }

    // Credit: JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: processes user input
    public Player processInput(String userInput) {
        boolean inputYes = userInput.equalsIgnoreCase("yes");
        boolean inputNo = userInput.equalsIgnoreCase("no");
        if (!inputYes && !inputNo) {
            System.out.println("Please enter yes or no.");
            return initPlayer();
        }
        System.out.println("Please enter your name: ");
        name = input.nextLine();
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

    // MODIFIES: p
    // EFFECTS: add a score to given player's score list
    public void addScore(Player p, int score) {
        p.addScore(score);
        savePlayerBase();
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
