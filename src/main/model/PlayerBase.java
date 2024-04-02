package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Represents all players that have played the game

public class PlayerBase implements Writable {
    private ArrayList<Player> players;

    // Make a PlayerBase with an empty list of players
    public PlayerBase() {
        players = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds player to list of all players
    public void addPlayer(Player p) {
        players.add(p);
    }

    //EFFECTS: returns player if player with given name already exists
    //         returns new player if name is not in list of players
    public Player getPlayerProfile(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        // should not be reached
        return null;
    }

    // MODIFIES: this
    // EFFECTS: removes player from playerbase
    public void removePlayer(Player p) {
        players.remove(p);
        EventLog.getInstance().logEvent(new Event("Player " + p.getName() + " removed from player base"));
    }

    // MODIFIES: this
    // EFFECTS: sorts players alphabetically
    public void sortAlphabetically(List<Player> playerList) {
        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        EventLog.getInstance().logEvent(new Event("Players sorted alphabetically"));
    }

    // EFFECTS: put in JSON
    // Credit: JSonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Players", playersToJson());
        return json;
    }

    // EFFECTS: returns items as a JSON array
    // Credit: JSonSerializationDemo
    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p : players) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    //*************** getters and setters **************

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
