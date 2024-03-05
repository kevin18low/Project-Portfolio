package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Represents a player of the snake game. Each player has a player name and a list of their high scores

public class Player implements Writable {
    private String name;
    private List<Integer> scores;
    private Game game;

    // Create a player  object with given name and scores
    public Player(String name) {
        this.name = name;
        this.scores = new ArrayList<>();
        scores.add(0);
        game = new Game(0,0, Color.black);
    }

    public void addScore(int score) {
        scores.add(score);
    }

    // EFFECTS: put in JSON
    // Credit: JSonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Scores", scores);
        json.put("Game", game.toString());
        return json;
    }

    //*************** getters and setters **************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game g) {
        this.game = g;
    }

}