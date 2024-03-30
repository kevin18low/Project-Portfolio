package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Credit: JsonSerializationDemo

// Represents a reader that reads PlayerBase from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PlayerBase from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayerBase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayerBase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PlayerBase from JSON object and returns it
    private PlayerBase parsePlayerBase(JSONObject jsonObject) {
        PlayerBase pb = new PlayerBase();
        addPlayers(pb, jsonObject);
        return pb;
    }

    // MODIFIES: pb
    // EFFECTS: parses Players from JSON object and adds them to PlayerBase
    private void addPlayers(PlayerBase pb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(pb, nextPlayer);
        }
    }

    // MODIFIES: pb
    // EFFECTS: parses Player from JSON object and adds it to PlayerBase
    private void addPlayer(PlayerBase pb, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        Player player = new Player(name);
        addGame(jsonObject, player);
        pb.addPlayer(player);
    }

    // MODIFIES: p
    // EFFECTS: adds a game to the player's profile to be saved
    public void addGame(JSONObject jsonObject, Player p) {
        int width = jsonObject.getInt("Width");
        int height = jsonObject.getInt("Height");

        String c = jsonObject.getString("Color");

        int d = jsonObject.getInt("Direction");
        int score = jsonObject.getInt("Score");

        List<Integer> scores = makeScores(p, jsonObject);

        ArrayList<Position> body = makeBody(p, jsonObject);
        Position head = makeHead(jsonObject);
        Position food = makeFood(jsonObject);
        int tileSize = jsonObject.getInt("Tile Size");
        Game g = new Game(width, height, new Snake(new Direction(d), head, body, c), score, food, tileSize);
        p.setGame(g);
        p.setScores(scores);
    }

    // EFFECTS: parses JSON into a list of integers for the scores
    public List<Integer> makeScores(Player p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Scores");
        for (Object json : jsonArray) {
            p.addScore((int) json);
        }
        return p.getScores();
    }

    // EFFECTS: parses JSON into a position list for the body
    public ArrayList<Position> makeBody(Player p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Body");
        for (Object json : jsonArray) {
            JSONObject nextPos = (JSONObject) json;
            addBody(p, nextPos);
        }
        return p.getGame().getSnake().getBody();
    }

    // EFFECTS: adds one segment of the body to the rest of the body
    public void addBody(Player p, JSONObject jsonObject) {
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");
        Position pos = new Position(x, y);
        p.getGame().getSnake().getBody().add(pos);
    }

    // EFFECTS: parses JSON into a position object for the head
    public Position makeHead(JSONObject jsonObject) {
        JSONObject head = (JSONObject) jsonObject.get("Head");
        int x = head.getInt("x");
        int y = head.getInt("y");
        Position pos = new Position(x, y);
        return pos;
    }

    // EFFECTS: parses JSON into a position object for the food
    public Position makeFood(JSONObject jsonObject) {
        JSONObject food = (JSONObject) jsonObject.get("Food");
        int x = food.getInt("x");
        int y = food.getInt("y");
        Position pos = new Position(x, y);
        return pos;
    }

}