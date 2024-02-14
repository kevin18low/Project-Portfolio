package model;

import java.util.ArrayList;
import java.util.List;

// Represents a player of the snake game> Each player has a player name and a list of their high scores

public class Player {
    private String name;
    private List<Integer> scores;

    public Player(String name) {
        this.name = name;
        this.scores = new ArrayList<>();
        scores.add(0);
    }

    public void addScore(int score) {
        scores.add(score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

}
