package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Game;
import model.Position;
import model.Direction;
import model.Snake;
import model.Food;
import model.Player;

import java.io.IOException;

public class SnakeGUI {
    private Player player;
    private Game game;
    private Screen screen;
    private WindowBasedTextGUI endGui;
    private static final int TICKS_PER_SECOND = 10;

    public SnakeGUI(Player player, Screen screen) throws IOException, InterruptedException {
        this.player = player;
        this.screen = screen;
        start();
    }

    /**
     * Begins the game and method does not leave execution
     * until game is complete.
     */
    public void start() throws IOException, InterruptedException {
//        screen = new DefaultTerminalFactory().createScreen();
//        screen.startScreen();
//
//        TerminalSize terminalSize = screen.getTerminalSize();

        game = player.getGame();

        beginTicks();
    }

    /**
     * Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until
     * game has ended and the endGui has been exited.
//     */
//    private void saveAndQuit() throws IOException {
//        KeyStroke keyStroke = screen.pollInput();
//        if (keyStroke != null) {
//            System.out.println(keyStroke);
//            if (keyStroke.getKeyType() == KeyType.Escape) {
//                System.out.println("Saving and quitting...");
//                savePlayerBase();
//            }
//        }
//    }

    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isGameOver() || endGui.getActiveWindow() != null) {
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    System.out.println("Saving and quitting...");
                }
            }
            tick();
            Thread.sleep(1000L / TICKS_PER_SECOND);
            //saveAndQuit();
        }

        System.exit(0);
    }

    /**
     * Handles one cycle in the game by taking user input,
     * ticking the game internally, and rendering the effects
     */
    private void tick() throws IOException {
        handleUserInput();

        game.tick();

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
    }

    /**
     * Sets the snake's direction corresponding to the
     * user's keystroke
     */
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }

        if (stroke.getCharacter() != null) {
            return;
        }
        game.getSnake().keyDirection(stroke);
    }

    /**
     * Renders the current screen.
     * Draws the end screen if the game has ended, otherwise
     * draws the score, snake, and food.
     */
    private void render() {
        if (game.isGameOver()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        drawScore();
        drawSnake();
        drawFood();
    }

    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                .setText("You finished with a score of " + game.getScore() + "!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

    private void drawScore() {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(1, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getScore()));
    }

    private void drawSnake() {
        Snake snake = game.getSnake();

        drawPosition(snake.getHead(), TextColor.ANSI.GREEN, '\u2588', true);

        for (Position pos : snake.getBody()) {
            drawPosition(pos, TextColor.ANSI.GREEN, '\u2588', true);
        }
    }

    private void drawFood() {
        Position pos = new Position(game.getFood().getFoodX(), game.getFood().getFoodY());
        drawPosition(pos, TextColor.ANSI.RED, '\u2B24', false);
    }

    /**
     * Draws a character in a given position on the terminal.
     * If wide, it will draw the character twice to make it appear wide.
     */
    private void drawPosition(Position pos, TextColor color, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(pos.getPosX() * 2, pos.getPosY() + 1, String.valueOf(c));

        if (wide) {
            text.putString(pos.getPosX() * 2 + 1, pos.getPosY() + 1, String.valueOf(c));
        }
    }
}
