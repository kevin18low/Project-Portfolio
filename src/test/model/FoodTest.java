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
        s1 = new Snake(new Direction(1), 50, 50, Color.green);
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

    @Test
    public void setFoodXTest() {
        assertEquals(f1.getFoodX(), 50);
        f1.setFoodX(30);
        assertEquals(f1.getFoodX(), 30);
        assertEquals(f2.getFoodX(), 15);
        f2.setFoodX(45);
        assertEquals(f2.getFoodX(), 45);
    }

    @Test
    public void setFoodYTest() {
        assertEquals(f1.getFoodY(), 50);
        f1.setFoodY(30);
        assertEquals(f1.getFoodY(), 30);
        assertEquals(f2.getFoodY(), 45);
        f2.setFoodY(10);
        assertEquals(f2.getFoodY(), 10);
    }
}
