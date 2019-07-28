package Model;

import java.math.BigDecimal;
import java.util.List;

public class CD extends MusicItem {
    private List<Song> songs;
    private int totalDuration;

    public CD(String title, String genre, Date releaseDate, String artist, BigDecimal price) {
        super(title, genre, releaseDate, artist, price);
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    private void updateDotalDuration(Song song){
        this.totalDuration += song.getDuration();
    }
}
