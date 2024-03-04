package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Direction;
import model.Game;
import model.Player;
import model.Snake;
import model.PlayerBase;

import java.util.Scanner;

import java.awt.*;
import java.io.IOException;

import static java.awt.Color.getColor;

// Represents a SnakeApp that runs a game of snake

public class SnakeAppLanterna {
    private Snake snake;
    private Screen screen;

    Scanner input = new Scanner(System.in);
    PlayerBase pb = new PlayerBase();
    Game game = new Game(0, 0);

    // Runs a game of snake
    public SnakeAppLanterna() throws IOException {
        run();
    }

    // EFFECTS: runs program
    public void run() throws IOException {
        setup();

        screen = new DefaultTerminalFactory().createScreen();
        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();

        while (true) {
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                System.out.println(keyStroke);
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    System.out.println("Closing game");
                    break;
                }
                snake.turn(keyStroke);
                tg.setForegroundColor(TextColor.ANSI.CYAN);
                tg.putString(1, 1, "Current direction: " + snake.getDirection());
                tg.putString(1, 2, "Current dx: " + snake.getDx());
                tg.putString(1, 3, "Current dy: " + snake.getDy());
                screen.refresh();
                screen.clear();
                System.out.println(snake.getDirection());
            }
        }
        screen.stopScreen();
    }

    // EFFECTS: set up the game by initializing the board, snake, and player
    public void setup() {
        initPlayer();
        initGame();
    }

    // EFFECTS: gets player name and displays player profile
    private void initPlayer() {
        System.out.println("Welcome to Snake!");
        System.out.println("Enter your player name: ");
        String playerName = input.nextLine();
        //players.add(getPlayerProfile(playerName));
        //displayProfile(getPlayerProfile(playerName));
        displayProfile(pb.getPlayerProfile(playerName));
    }

    // EFFECTS: gets snake colour and board dimensions, then sets up game
    public void initGame() {
        System.out.println("Pick your snake colour: ");
        Color color = getColor(input.nextLine());
        System.out.println("How wide do you want the board to be?");
        int width = input.nextInt();
        System.out.println("How tall do you want the board to be?");
        int height = input.nextInt();
        initSnakeAndBoard(color, width, height);
    }

    // EFFECTS: creates a new snake with given color and board with given dimensions
    public void initSnakeAndBoard(Color color, int width, int height) {
        snake = new Snake(new Direction(1), 1, 1, color);
        System.out.println("Starting game with " + width + " x " + height + " board!");
        game.setBoardWidth(width);
        game.setBoardHeight(height);
    }

    // EFFECTS: prints out player name and their past high scores
    public void displayProfile(Player p) {
        System.out.println("Welcome back " + p.getName() + "!");
        System.out.println("Here are your high scores: ");
        for (int s : p.getScores()) {
            System.out.println(s);
        }
    }
}
