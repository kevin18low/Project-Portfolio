package model;

import org.json.JSONObject;
import persistence.Writable;

// Credit: SnakeConsoleLanterna
// Represents an x y location
public class Position implements Writable {
    private int posX;
    private int posY;

    // Create a position object with location x,y
    public Position(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    // EFFECTS: put position in JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", posX);
        json.put("y", posY);
        return json;
    }

    //*************** getters and setters **************

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int x) {
        this.posX = x;
    }

    public void setPosY(int y) {
        this.posY = y;
    }
}
