package Model.Items;

import Model.Helpers.Date;

import java.math.BigDecimal;

public class Vinyl extends MusicItem {
    private int speed;
    private double diameter;

    public Vinyl(String itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price, int speed, double diameter) {
        super(itemID, title, genre, releaseDate, artist, price);
        this.speed = speed;
        this.diameter = diameter;
    }
    public Vinyl(String title, String genre, Date releaseDate, String artist, BigDecimal price, int speed, double diameter) {
        super(title, genre, releaseDate, artist, price);
        this.setSpeed(speed);
        this.setDiameter(diameter);
    }

    public int getSpeed() {
        return speed;
    }

    private void setSpeed(int speed) {
        if(speed >= 0)
            this.speed = speed;
        else
            throw new IllegalArgumentException("Speed of the Vinyl can't be negative");
    }

    public double getDiameter() {
        return diameter;
    }

    private void setDiameter(double diameter) {
        if(diameter >= 0)
            this.diameter = diameter;
        else
            throw new IllegalArgumentException("Diameter of the Vinyl can't be negative");
    }
}
