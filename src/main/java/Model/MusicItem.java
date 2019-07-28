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

    MusicItem(int itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        this.itemID = itemID;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.price = price;
    }

    int getItemID() {
        return itemID;
    }

    String getTitle() {
        return title;
    }

    String getGenre() {
        return genre;
    }

    Date getReleaseDate() {
        return releaseDate;
    }

    String getArtist() {
        return artist;
    }

    BigDecimal getPrice() {
        return price;
    }
}
