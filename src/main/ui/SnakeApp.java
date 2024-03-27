//package ui;
//
////import com.googlecode.lanterna.TerminalPosition;
////import com.googlecode.lanterna.TerminalSize;
////import com.googlecode.lanterna.TextColor;
////import com.googlecode.lanterna.graphics.TextGraphics;
////import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
////import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
////import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
////import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
////import com.googlecode.lanterna.input.KeyStroke;
////import com.googlecode.lanterna.input.KeyType;
////import com.googlecode.lanterna.screen.Screen;
////import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
//import model.*;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import javax.swing.*;
//import java.util.Scanner;
//
//import java.io.IOException;
//import java.io.FileNotFoundException;
//
//
//
//
//public class SnakeApp extends JFrame {
//
//    public static final int WIDTH = 600;
//    public static final int HEIGHT = 400;
//
//    private static final int TICKS_PER_SECOND = 10;
//    private static final String JSON_STORE = "./data/playerbase.json";
//    private JsonReader jsonReader;
//    private JsonWriter jsonWriter;
//    private Scanner input;
//    private PlayerBase pb;
//    private Player player;
//    private String name;
//
//    private Game game;
//
//    // Runs a game of snake
//    public SnakeApp() throws IOException, InterruptedException {
//        super("Snake Game Console");
//        setSize(WIDTH, HEIGHT);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        input = new Scanner(System.in);
//        pb = new PlayerBase();
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        run();
//    }
//
//    // EFFECTS: runs program
//    public void run() throws IOException, InterruptedException {
//        setup();
//        screenDisplay();
//    }
//
//    // EFFECTS: set up the game by initializing the player and the game
//    public void setup() throws IOException, InterruptedException {
//        System.out.println("Welcome to Snake!");
//        player = initPlayer();
//        System.out.println("Load game, or new game?");
//        String userInput = input.nextLine();
//        updateGameData(name, userInput);
//        System.out.println("Press 'esc' to save and quit, or close the game.");
//    }
//
//    // EFFECTS: gets player name and displays player profile
//    private Player initPlayer() {
//        System.out.println("Are you a returning player?");
//        String returning = input.nextLine();
//        return processInput(returning);
//    }
//
//    // EFFECTS: returns game with given snake colour and board dimensions
//    public Game initGame() {
//        System.out.println("Pick your snake colour: ");
//        String color = input.nextLine();
//        System.out.println("How wide do you want the board to be?");
//        int width = input.nextInt();
//        System.out.println("How tall do you want the board to be?");
//        int height = input.nextInt();
//        Game game = new Game(width, height, color);
//        System.out.println("Starting game with " + width + " x " + height + " board!");
//        return game;
//    }
//
//    // EFFECTS: prints out player name and their past high scores for an existing player
//    public void displayExistingProfile(Player p) {
//        System.out.println("Welcome back " + p.getName() + "!");
//        System.out.println("Here are your high scores: ");
//        for (int s : p.getScores()) {
//            System.out.println(s);
//        }
//    }
//
//    // EFFECTS: prints out welcome messages for new player
//    public void displayNewProfile(Player p) {
//        System.out.println("Welcome " + p.getName() + "!");
//        System.out.println("Play some games to add to your high score list! ");
//    }
//
//    // EFFECTS: prints out names of all existing players
//    public void allPlayers() {
//        System.out.println("Here are all the players in the database:");
//        for (Player p : pb.getPlayers()) {
//            System.out.println(p.getName());
//        }
//    }
//
//    // Credit: JsonSerializationDemo
//    // MODIFIES: this
//    // EFFECTS: processes user input
//    public Player processInput(String userInput) {
//        boolean inputYes = userInput.equalsIgnoreCase("yes");
//        boolean inputNo = userInput.equalsIgnoreCase("no");
//        if (!inputYes && !inputNo) {
//            System.out.println("Please enter yes or no.");
//            return initPlayer();
//        }
//        System.out.println("Please enter your name: ");
//        name = input.nextLine();
//        loadPlayerBase();
//        if (inputYes) {
//            displayExistingProfile(pb.getPlayerProfile(name));
//            return pb.getPlayerProfile(name);
//        } else if (inputNo) {
//            addPlayer(name);
//            savePlayerBase();
//            return pb.getPlayerProfile(name);
//        }
//        // should not reach here
//        return null;
//    }
//
//    // MODIFIES: player
//    // EFFECTS: start a new game, or load game from player's profile
//    public void updateGameData(String name, String userInput) throws IOException {
//        JSONObject jsonObject = new JSONObject(jsonReader.readFile(JSON_STORE));
//        JSONArray jsonArray = jsonObject.getJSONArray("Players");
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonPlayer = jsonArray.getJSONObject(i);
//            if (userInput.equalsIgnoreCase("new")) {
//                if (jsonPlayer.get("Name").equals(name)) {
//                    Game g = initGame();
//                    jsonPlayer.put("Game", g.toString());
//                    player.setGame(g);
//                    break;
//                }
//            } else if (userInput.equalsIgnoreCase("load")) {
//                System.out.println("Loading game... get ready!");
//                System.out.println(player.getGame().toString());
//                break;
//            }
//        }
//    }
//
//    // MODIFIES: p
//    // EFFECTS: add a score to given player's score list
//    public void addScore(Player p, int score) {
//        p.addScore(score);
//        savePlayerBase();
//    }
//
//    // Credit: JsonSerializationDemo
//    // MODIFIES: this
//    // EFFECTS: loads PlayerBase from file
//    private void loadPlayerBase()  {
//        try {
//            pb = jsonReader.read();
//            System.out.println("Loaded PlayerBase from " + JSON_STORE + "\n");
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//
//    // Credit: JsonSerializationDemo
//    // MODIFIES: this
//    // EFFECTS: prompt user for name and adds to PlayerBase
//    private void addPlayer(String name) {
//        pb.addPlayer(new Player(name));
//        displayNewProfile(pb.getPlayerProfile(name));
//    }
//
//    // Credit: JsonSerializationDemo
//    // EFFECTS: saves the PlayerBase to file
//    private void savePlayerBase() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(pb);
//            jsonWriter.close();
//            System.out.println("Saved PlayerBase to " + JSON_STORE + "\n");
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//
//
//
//    public void screenDisplay() throws IOException, InterruptedException {
//        screen = new DefaultTerminalFactory().createScreen();
//        screen.startScreen();
//
//        TerminalSize terminalSize = screen.getTerminalSize();
//
//        game = player.getGame();
//
//        beginTicks();
//    }
//
//    /**
//     * Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until
//     * game has ended and the endGui has been exited.
//     //     */
//    private void beginTicks() throws IOException, InterruptedException {
//        while (!game.isGameOver() || endGui.getActiveWindow() != null) {
//            KeyStroke keyStroke = screen.pollInput();
//            if (keyStroke != null) {
//                System.out.println(keyStroke);
//                if (keyStroke.getKeyType() == KeyType.Escape) {
//                    System.out.println("Saving and quitting...");
//                    savePlayerBase();
//                    break;
//                }
//                player.getGame().getSnake().turn(keyStroke);
//                System.out.println(player.getGame().getSnake().getDirection());
//            }
//            tick();
//            Thread.sleep(1000L / TICKS_PER_SECOND);
//        }
//
//        System.exit(0);
//    }
//
//    /**
//     * Handles one cycle in the game by taking user input,
//     * ticking the game internally, and rendering the effects
//     */
//    private void tick() throws IOException {
//        handleUserInput();
//
//        game.tick();
//
//        screen.setCursorPosition(new TerminalPosition(0, 0));
//        screen.clear();
//        render();
//        screen.refresh();
//
//        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
//    }
//
//    /**
//     * Sets the snake's direction corresponding to the
//     * user's keystroke
//     */
//    private void handleUserInput() throws IOException {
//        KeyStroke stroke = screen.pollInput();
//
//        if (stroke == null) {
//            return;
//        }
//
//        if (stroke.getCharacter() != null) {
//            return;
//        }
//        game.getSnake().keyDirection(stroke);
//    }
//
//    /**
//     * Renders the current screen.
//     * Draws the end screen if the game has ended, otherwise
//     * draws the score, snake, and food.
//     */
//    private void render() {
//        if (game.isGameOver()) {
//            if (endGui == null) {
//                drawEndScreen();
//            }
//
//            return;
//        }
//
//        drawScore();
//        drawDirection();
//        drawSnake();
//        drawFood();
//    }
//
//    private void drawEndScreen() {
//        endGui = new MultiWindowTextGUI(screen);
//
//        new MessageDialogBuilder()
//                .setTitle("Game over!")
//                .setText("You finished with a score of " + game.getScore() + "!")
//                .addButton(MessageDialogButton.Close)
//                .build()
//                .showDialog(endGui);
//    }
//
//    private void drawScore() {
//        text = screen.newTextGraphics();
//        text.setForegroundColor(TextColor.ANSI.WHITE);
//        text.putString(1, 0, "Score: ");
//
//        text = screen.newTextGraphics();
//        text.setForegroundColor(TextColor.ANSI.WHITE);
//        text.putString(8, 0, String.valueOf(game.getScore()));
//    }
//
//    private void drawDirection() {
//        text = screen.newTextGraphics();
//        text.setForegroundColor(TextColor.ANSI.WHITE);
//        text.putString(50, 1, "Current direction: " + player.getGame().getSnake().getDirection());
//        text.putString(50, 2, "Current dx: " + player.getGame().getSnake().getDx());
//        text.putString(50, 3, "Current dy: " + player.getGame().getSnake().getDy());
//    }
//
//    private void drawSnake() {
//        Snake snake = game.getSnake();
//
//        drawPosition(snake.getHead(), TextColor.ANSI.GREEN, '\u2588', true);
//
//        for (Position pos : snake.getBody()) {
//            drawPosition(pos, TextColor.ANSI.GREEN, '\u2588', true);
//        }
//    }
//
//    private void drawFood() {
//        Position pos = new Position(game.getFood().getFoodX(), game.getFood().getFoodY());
//        drawPosition(pos, TextColor.ANSI.RED, '\u2B24', false);
//    }
//
//    /**
//     * Draws a character in a given position on the terminal.
//     * If wide, it will draw the character twice to make it appear wide.
//     */
//    private void drawPosition(Position pos, TextColor color, char c, boolean wide) {
//        TextGraphics text = screen.newTextGraphics();
//        text.setForegroundColor(color);
//        text.putString(pos.getPosX() * 2, pos.getPosY() + 1, String.valueOf(c));
//
//        if (wide) {
//            text.putString(pos.getPosX() * 2 + 1, pos.getPosY() + 1, String.valueOf(c));
//        }
//    }
//}
