package model;

import org.json.JSONObject;
import persistence.Writable;

public class Position implements Writable {
    private int posX;
    private int posY;

    public Position(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", posX);
        json.put("y", posY);
        return json;
    }

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
