package Model;

public class Song {
    private String songTitle;
    private int duration;

    public Song(String songTitle, int duration) {
        this.songTitle = songTitle;
        this.setDuration(duration);
    }

    public String getSongTitle() {
        return songTitle;
    }

    public int getDuration() {
        return duration;
    }

    private void setDuration(int duration) {
        if(duration >= 0)
            this.duration = duration;
        else
            throw new IllegalArgumentException("Duration of a song can't be negative");
    }
}
