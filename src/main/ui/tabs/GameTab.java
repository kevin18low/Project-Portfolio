package ui.tabs;

import ui.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameTab extends Tab implements ActionListener {

    private static final String INIT_GAME = "Welcome to Snake! \n Are you a new or returning player?";
    private JLabel gameIntro;
    private JButton newPlayer = new JButton("New player");
    private JButton returning = new JButton("Returning player");
    private JButton players = new JButton("Player list");
    private JButton enter = new JButton("Enter");
    private JButton start = new JButton("Start game");
    private JButton home = new JButton("Return home");
    private JTextField userInput;
    private CardLayout cardLayout;
    private JPanel introPanel = createIntroPanel();
    private JPanel profilePanel = createProfilePanel();
    private JPanel gamePanel = createGamePanel();

    //EFFECTS: constructs a home tab for console with buttons and a greeting
    public GameTab(SnakeUI controller) {
        super(controller);
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        initButtonListeners();

        this.add(introPanel, "intro");
        this.add(gamePanel, "game");
        this.add(profilePanel, "profile");

        cardLayout.show(this, "intro");

        controller.add(this);
    }

    private JPanel createIntroPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        gameIntro = new JLabel(INIT_GAME, JLabel.CENTER);
        gameIntro.setSize(WIDTH, HEIGHT / 3);
        panel.add(gameIntro);
        placeIntroButtons(panel);

        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel profile = new JLabel("Profile", JLabel.LEFT);
        profile.setSize(WIDTH, HEIGHT / 2);
        profile.setBounds(10, 10, 100, 40);
        panel.add(profile);

        start.setBounds(390, 10, 100, 20);
        panel.add(start);

        Label enterName = new Label("Enter your player name:");
        enterName.setBounds(10, 50, 130, 20);
        panel.add(enterName);

        userInput = new JTextField();
        userInput.setBounds(150, 50, 150, 20);
        panel.add(userInput);

        enter.setBounds(310, 50, 70, 20);
        panel.add(enter);

        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel game = new JLabel("Game", JLabel.LEFT);
        game.setSize(WIDTH, HEIGHT / 2);
        game.setBounds(10, 10, 100, 20);
        panel.add(game);

        home.setBounds(10, 30, 120, 20);
        panel.add(home);
        return panel;
    }

    //EFFECTS: creates Arrive and Leave buttons that change greeting message when clicked
    private void placeIntroButtons(JPanel buttons) {

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
        enter.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = userInput.getText();
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
        } else if (e.getSource() == enter) {
            System.out.println(input);
        }
    }

}