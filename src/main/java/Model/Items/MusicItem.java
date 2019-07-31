package Model.Items;
import Helpers.Date;

import java.math.BigDecimal;
import java.util.UUID;

/*
    Music Item abstract class
 */
public abstract class MusicItem {
    private String itemID;
    private String title;
    private String genre;
    private Date releaseDate;
    private String artist;
    private BigDecimal price;

    // This constructor is used to map data from the database to MusicItem Object (As OMR)
    MusicItem(String itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        this.itemID = itemID;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.price = price;
    }

    MusicItem(String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        // Item ID is Randomly Generated 128bits UUID that guarantees unique ID
        // Getting this ID from the user can be dangerous and can't guarantee uniqueness
        this.itemID = UUID.randomUUID().toString().replace("-", "");
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.price = price;
    }

    public String getItemID() {
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
