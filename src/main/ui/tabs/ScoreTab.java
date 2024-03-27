package ui.tabs;

import ui.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreTab extends Tab {
    private static final String SCORE_LIST = "List of top 10 high scores";

    private JScrollPane scorePane;
    private JTextArea scoreText;
    private JLabel scoreMessage;

    public ScoreTab(SnakeUI controller) {
        super(controller);

        placeScoreButton();

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(SnakeUI.WIDTH - (SnakeUI.WIDTH / 5),
                SnakeUI.HEIGHT - (SnakeUI.HEIGHT / 5));
        scoreMessage = new JLabel("");
        scorePane = new JScrollPane(new JTextArea(6, 40));
        scoreText = new JTextArea("", 6, 40);
        scoreText.setVisible(true);

        reportBlock.add(scoreMessage);
        reportBlock.add(scorePane);

        add(reportBlock);
    }

    //MODIFIES: this
    //EFFECTS: adds a generate report button that prints app status when clicked
    private void placeScoreButton() {
        JButton b1 = new JButton("You (If you're on the leaderboard!)");
        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
//                getController().getGame().getScore();
                if (buttonPressed.equals("You (If you're on the leaderboard!)")) {
                    scoreMessage.setText(SCORE_LIST);
//                    scoreText.setText(String.valueOf(getController().getGame().getScore()));
                    scoreText.setText("pressed");
                    scorePane.setViewportView(scoreText);
                }
            }
        });

        this.add(buttonRow);
    }
}
