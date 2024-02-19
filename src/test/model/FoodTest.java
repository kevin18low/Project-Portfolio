package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {
    private Snake s1;
    private Food f1;
    private Food f2;

    @BeforeEach
    public void setup() {
        s1 = new Snake(1, 50, 50, Color.green);
        f1 = new Food(50, 50);
        f2 = new Food(15, 45);
    }

    @Test
    public void wasEatenTest() {
        assertTrue(s1.ateFood(f1));
        assertTrue(f1.getEaten());
        assertFalse(s1.ateFood(f2));
        assertFalse(f2.getEaten());
    }
}
