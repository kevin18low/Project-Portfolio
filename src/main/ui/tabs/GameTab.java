package ui.tabs;

import ui.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTab extends Tab implements ActionListener {

    private static final String INIT_GAME = "Are you a new or returning player?";
    private JLabel gameIntro;
    private JLabel resultLabel;
    private JButton newPlayer;
    private JButton returning;
    private JButton players;
    private JButton start = new JButton("Start game");
    private JButton home = new JButton("Return home");
    private JTextField text;
    private CardLayout cardLayout;
    private JPanel introPanel = createIntroPanel();
    private JPanel profilePanel = createProfilePanel();
    private JPanel gamePanel = createGamePanel();

    //EFFECTS: constructs a home tab for console with buttons and a greeting
    public GameTab(SnakeUI controller) {
        super(controller);
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        placeIntroButtons(introPanel);
        initButtonListeners();

        this.add(introPanel, "intro");
        this.add(gamePanel, "game");
        this.add(profilePanel, "profile");

        cardLayout.show(this, "intro");

        controller.add(this);
    }

    private JPanel createIntroPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        placeIntro(panel);

        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Label profile = new Label("Profile");
        profile.setBounds(10, 10, 100, 40);
        panel.add(profile);
        start.setBounds(390, 10, 100, 20);
        panel.add(start);

        text = new JTextField();
        text.setBounds(10, 50, 250, 20);
        panel.add(text);
        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        Label game = new Label("Game");
        game.setBounds(10, 10, 100, 20);
        panel.add(game);
        home.setBounds(10, 30, 120, 20);
        panel.add(home);
        return panel;
    }

    private void placeIntro(JPanel panel) {
        gameIntro = new JLabel(INIT_GAME, JLabel.CENTER);
        gameIntro.setSize(WIDTH, HEIGHT / 3);
        panel.add(gameIntro);
    }

    //EFFECTS: creates Arrive and Leave buttons that change greeting message when clicked
    private void placeIntroButtons(JPanel buttons) {
        newPlayer = new JButton("New player");
        returning = new JButton("Returning player");
        players = new JButton("Player list");
        resultLabel = new JLabel("");
        resultLabel.setBounds(20, 100, 250, 20);
        this.add(resultLabel);

        JPanel buttonRow = formatButtonRow(newPlayer);
        buttonRow.add(returning);
        buttonRow.setSize(WIDTH, HEIGHT / 6);
        buttons.add(buttonRow);
        JPanel buttonRow2 = formatButtonRow(players);
        buttonRow2.setSize(WIDTH, HEIGHT / 6);
        buttons.add(buttonRow2);
    }

    private void initButtonListeners() {
        newPlayer.addActionListener(this);
        returning.addActionListener(this);
        home.addActionListener(this);
        start.addActionListener(this);
        players.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newPlayer) {
            cardLayout.show(this, "profile");
//            String inputValue = JOptionPane.showInputDialog("Please input a value:");
//            resultLabel.setText("You entered: " + inputValue);
        } else if (e.getSource() == returning) {
            cardLayout.show(this, "profile");
//            try {
//                new SnakeAppLanterna();
//            } catch (Exception exception) {
//                // nothing
//            }
        } else if (e.getSource() == home) {
            cardLayout.show(this, "intro");
        } else if (e.getSource() == start) {
            cardLayout.show(this, "game");
        } else if (e.getSource() == players) {
            getController().getTabbedPane().setSelectedIndex(SnakeUI.PLAYER_TAB_INDEX);
        }
    }

}