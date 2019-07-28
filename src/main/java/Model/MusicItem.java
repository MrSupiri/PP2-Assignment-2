package Model;
import java.math.BigDecimal;
import java.util.UUID;

public abstract class MusicItem {
    private String itemID;
    private String title;
    private String genre;
    private Date releaseDate;
    private String artist;
    private BigDecimal price;

    MusicItem(String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        this.itemID = UUID.randomUUID().toString().replace("-", "");
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.price = price;
    }

    String getItemID() {
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
