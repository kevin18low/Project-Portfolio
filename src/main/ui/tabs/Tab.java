package ui.tabs;

import ui.*;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Tab extends JPanel {
    private final SnakeUI controller;

    //REQUIRES: SmartHomeUI controller that holds this tab
    public Tab(SnakeUI controller) {
        this.controller = controller;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the SmartHomeUI controller for this tab
    public SnakeUI getController() {
        return controller;
    }
}
