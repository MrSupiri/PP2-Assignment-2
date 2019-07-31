package Model.Items;

import Helpers.Date;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CD extends MusicItem {
    private ArrayList<String> songs = new ArrayList<>();
    private int totalDuration;

    // This constructor is used to map data from the database to CD Object (As OMR)
    public CD(String itemID, String title, String genre, Date releaseDate, String artist, BigDecimal price, ArrayList<String> songs, int totalDuration) {
        super(itemID, title, genre, releaseDate, artist, price);
        this.songs = songs;
        this.totalDuration = totalDuration;
    }

    // Create a new Instance of CD class
    public CD(String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        super(title, genre, releaseDate, artist, price);
    }

    /**
     * Adds a song to CD and updates the total duration of the CD
     * @param song - Song Name
     * @param duration - duration of the Song
     */
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
