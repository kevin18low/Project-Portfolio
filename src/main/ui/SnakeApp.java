package ui;

import model.Game;
import model.Player;
import model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.awt.Color.getColor;

// Represents a snake app that runs a game of snake

public class SnakeApp extends JFrame implements KeyListener {

    private Snake snake;
    private Game snakeGame;
    private List<Player> players = new ArrayList<>();

    public SnakeApp() {
        runSnake();
    }

    //Player p1 = new Player("p1");
    //Player p2 = new Player("p2");
    Scanner input = new Scanner(System.in);

    public void runSnake() {
        setup();
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this); // Register the KeyListener with the JFrame
        frame.setVisible(true);

    }

    public void setup() {
        initPlayer();
        initGame();
    }

    // EFFECTS: gets player name and displays player profile
    private void initPlayer() {
        System.out.println("Welcome to Snake!");
        System.out.println("Enter your player name: ");
        String playerName = input.nextLine();
        players.add(getPlayerProfile(playerName));
        displayProfile(getPlayerProfile(playerName));
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
        snake = new Snake(1, 0, 0, color);
        System.out.println("Starting game with " + width + " x " + height + " board!");
        snakeGame = new Game(0, 0);
    }

    //EFFECTS: returns player if player with given name already exists
    //         returns new player if name is not in list of players
    public Player getPlayerProfile(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return new Player(name);
    }

    // EFFECTS: prints out player name and their past high scores
    public void displayProfile(Player p) {
        System.out.println("Welcome back " + p.getName() + "!");
        System.out.println("Here are your high scores: ");
        for (int s : p.getScores()) {
            System.out.println(s);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        snakeGame.pauseGame(e);
        System.out.println("PAUSED");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        snake.turn(e.getKeyCode());
        System.out.println("Current Direction: " + snake.getDirection());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
