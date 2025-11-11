package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Song {
    private String title;
    private String artist;
    private String mood;
    private String pathOrUrl;
    private boolean isLocal;
    private boolean isFavorite;
    private String genre;
    private int duration; // in seconds

    public Song() {}

    @JsonCreator
    public Song(@JsonProperty("title") String title,
                @JsonProperty("artist") String artist,
                @JsonProperty("mood") String mood,
                @JsonProperty("pathOrUrl") String pathOrUrl,
                @JsonProperty("isLocal") boolean isLocal,
                @JsonProperty("genre") String genre,
                @JsonProperty("duration") int duration) {
        this.title = title;
        this.artist = artist;
        this.mood = mood;
        this.pathOrUrl = pathOrUrl;
        this.isLocal = isLocal;
        this.genre = genre;
        this.duration = duration;
        this.isFavorite = false;
    }

    public Song(String title, String artist, Mood mood, String pathOrUrl, boolean isLocal) {
        this(title, artist, mood.name().toLowerCase(), pathOrUrl, isLocal, null, 0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getPathOrUrl() {
        return pathOrUrl;
    }

    public void setPathOrUrl(String pathOrUrl) {
        this.pathOrUrl = pathOrUrl;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDisplayText() {
        return title + " - " + artist;
    }

    public String getFormattedDuration() {
        if (duration <= 0) return "Unknown";
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", mood='" + mood + '\'' +
                ", pathOrUrl='" + pathOrUrl + '\'' +
                ", isLocal=" + isLocal +
                ", isFavorite=" + isFavorite +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return title.equals(song.title) && artist.equals(song.artist);
    }

    @Override
    public int hashCode() {
        return title.hashCode() + artist.hashCode();
    }
}
