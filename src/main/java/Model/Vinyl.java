package Model;

import java.math.BigDecimal;

public class Vinyl extends MusicItem {
    private int speed;
    private float diameter;

    public Vinyl(int itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price, int speed, float diameter) {
        super(itemID, title, genre, releaseDate, artist, price);
        this.speed = speed;
        this.diameter = diameter;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if(speed >= 0)
            this.speed = speed;
        else
            throw new IllegalArgumentException("Speed of the Vinyl can't be negative");
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        if(diameter >= 0)
            this.diameter = diameter;
        else
            throw new IllegalArgumentException("Diameter of the Vinyl can't be negative");
    }
}
