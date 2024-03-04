package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Direction;
import model.Snake;

import java.awt.*;
import java.io.IOException;

public class SnakeAppLanterna {
    private Screen screen;

    public SnakeAppLanterna() throws IOException {
        run();
    }

    public void run() throws IOException {
        screen = new DefaultTerminalFactory().createScreen();
        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();
        Snake snake = new Snake(new Direction(1), 1, 1, Color.blue);

        while (true) {
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                System.out.println(keyStroke);
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    System.out.println("Closing game");
                    break;
                }
                snake.turn(keyStroke);
                tg.setForegroundColor(TextColor.ANSI.CYAN);
                tg.putString(1, 1, "Current direction: " + snake.getDirection());
                tg.putString(1, 2, "Current dx: " + snake.getDx());
                tg.putString(1, 3, "Current dy: " + snake.getDy());
                screen.refresh();
                screen.clear();
                System.out.println(snake.getDirection());
            }
        }
        screen.stopScreen();
    }
}
