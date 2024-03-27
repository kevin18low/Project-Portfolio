package ui.tabs;

import ui.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerTab extends Tab {
    private static final String PLAYER_LIST = "List of all existing players:";

    private JScrollPane playerPane;
    private JTextArea playerText;
    private JLabel playerMessage;

    //REQUIRES: SmartHomeUI controller that holds this tab
    //EFFECTS: creates report tab with buttons and application status functionality
    public PlayerTab(SnakeUI controller) {
        super(controller);

        placePlayersButton();

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(SnakeUI.WIDTH - (SnakeUI.WIDTH / 5),
                SnakeUI.HEIGHT - (SnakeUI.HEIGHT / 5));
        playerMessage = new JLabel("");
        playerPane = new JScrollPane(new JTextArea(6, 40));
        playerText = new JTextArea("", 6, 40);
        playerText.setVisible(true);

        reportBlock.add(playerMessage);
        reportBlock.add(playerPane);

        add(reportBlock);
    }

    //MODIFIES: this
    //EFFECTS: adds a generate report button that prints app status when clicked
    private void placePlayersButton() {
        JButton b1 = new JButton("Button");
        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                //getController().getGame().toString();
                if (buttonPressed.equals("Button")) {
                    String message = PLAYER_LIST;
                    playerMessage.setText(message);
//                    playerText.setText(getController().getGame().toString());
                    playerText.setText("pressed");
                    playerPane.setViewportView(playerText);
                }
            }
        });

        this.add(buttonRow);
    }
}
