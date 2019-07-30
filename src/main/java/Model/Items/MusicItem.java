package Model.Items;
import Model.Helpers.Date;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class MusicItem {
    private String itemID;
    private String title;
    private String genre;
    private Date releaseDate;
    private String artist;
    private BigDecimal price;

    MusicItem(String itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        this.itemID = itemID;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.price = price;
    }

    MusicItem(String title, String genre, Date releaseDate, String artist, BigDecimal price) {
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
