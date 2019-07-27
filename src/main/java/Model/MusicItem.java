package Model;

import java.math.BigDecimal;

// Package Level Private
abstract class MusicItem {
    private int itemID;
    private String title;
    private String genre;
    private Date releaseDate;
    private String artist;
    private BigDecimal price;

    public MusicItem(int itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        this.itemID = itemID;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.price = price;
    }

    public int getIteitemID() {
        return itemID;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getArtist() {
        return artist;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
