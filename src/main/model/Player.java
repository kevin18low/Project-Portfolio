package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a player of the snake game. Each player has a player name,
// a list of their high scores, and a game that they're playing

public class Player implements Writable {
    private String name;
    private List<Integer> scores;
    private Game game;
    private static final int CAPACITY = 5;

    // Create a player  object with given name and scores
    public Player(String name) {
        this.name = name;
        this.scores = new ArrayList<>();
        game = new Game("green", 25);
    }

    // MODIFIES: this
    // EFFECTS: add given score to player's score list
    public void addScore(int score) {
        if (scores.size() >= CAPACITY) {
            int minValue = Collections.min(scores);
            scores.remove(Integer.valueOf(minValue));
        }
        scores.add(score);
    }

    // EFFECTS: creates a string of the player's scores
    public String scoresToString() {
        String scoreString = "";
        for (Integer i : scores) {
            scoreString += (i + ", ");
        }
        return scoreString;
    }

    // EFFECTS: put in JSON
    // Credit: JSonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Scores", getScores());
        json.put("Width", game.getBoardWidth());
        json.put("Height", game.getBoardHeight());
        json.put("Color", game.getSnake().getColor());
        json.put("Direction", game.getSnake().getDirection());
        json.put("Food", game.getFood().toJson());
        json.put("Head", headToJson());
        json.put("Body", bodyToJson());
        json.put("Score", game.getScore());
        json.put("Tile Size", game.getTileSize());
        return json;
    }

    // EFFECTS: puts the head of the snake to JSON
    public JSONObject headToJson() {
        return game.getSnake().getHead().toJson();
    }

    // EFFECTS: puts the body of the snake to JSON
    public JSONArray bodyToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Position p : game.getSnake().getBody()) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    //*************** getters and setters **************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getScores() {
        Collections.sort(scores, Collections.reverseOrder());
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game g) {
        this.game = g;
    }

}