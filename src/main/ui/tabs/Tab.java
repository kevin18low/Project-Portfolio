package ui.tabs;

import model.PlayerBase;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents all tabs that are a part of the main panel
public abstract class Tab extends JPanel {
    private final SnakeUI controller;

    protected static final String JSON_STORE = "./data/playerbase.json";
    protected JsonReader jsonReader;
    protected JsonWriter jsonWriter;
    protected PlayerBase pb;

    // Credit: SmartHome
    //REQUIRES: SnakeUI controller that holds this tab
    public Tab(SnakeUI controller) {
        this.controller = controller;
        pb = new PlayerBase();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loadPlayerBase();
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // Credit: JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads PlayerBase from file
    public void loadPlayerBase()  {
        try {
            pb = jsonReader.read();
            System.out.println("Loaded PlayerBase from " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Credit: JsonSerializationDemo
    // EFFECTS: saves the PlayerBase to file
    public void savePlayerBase() {
        try {
            jsonWriter.open();
            jsonWriter.write(pb);
            jsonWriter.close();
            System.out.println("Saved PlayerBase to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public SnakeUI getController() {
        return controller;
    }
}
