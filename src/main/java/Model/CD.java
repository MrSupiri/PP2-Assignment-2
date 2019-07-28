package Model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CD extends MusicItem {
    private ArrayList<String> songs = new ArrayList<>();
    private int totalDuration;

    public CD(String itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price, ArrayList<String> songs, int totalDuration) {
        super(itemID, title, genre, releaseDate, artist, price);
        this.songs = songs;
        this.totalDuration = totalDuration;
    }

    public CD(String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        super(title, genre, releaseDate, artist, price);
    }

    public void addSong(String song, int duration) {
        this.songs.add(song);
        this.totalDuration += duration;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

}
