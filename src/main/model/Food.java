package model;

// Represents a food item with an x position and y position, and a boolean indicating
// whether it has been eaten or not

public class Food {
    private int foodX;
    private int foodY;
    private boolean eaten;

    // Create a food object at (x,y)
    public Food(int x, int y) {
        this.foodX = x;
        this.foodY = y;
        this.eaten = false;
    }

    //*************** getters and setters **************

    public int getFoodX() {
        return foodX;
    }

    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }

    public boolean getEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}
