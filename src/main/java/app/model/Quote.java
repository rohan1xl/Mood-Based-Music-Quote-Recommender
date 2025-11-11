package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {
    private String text;
    private String author;
    private String mood;
    private String source;
    private boolean isFavorite;

    public Quote() {}

    @JsonCreator
    public Quote(@JsonProperty("text") String text, 
                 @JsonProperty("author") String author,
                 @JsonProperty("mood") String mood,
                 @JsonProperty("source") String source) {
        this.text = text;
        this.author = author;
        this.mood = mood;
        this.source = source != null ? source : "local";
        this.isFavorite = false;
    }

    public Quote(String text, String author, Mood mood) {
        this(text, author, mood.name().toLowerCase(), "local");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getFormattedQuote() {
        return "\"" + text + "\" - " + author;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", mood='" + mood + '\'' +
                ", source='" + source + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quote quote = (Quote) obj;
        return text.equals(quote.text) && author.equals(quote.author);
    }

    @Override
    public int hashCode() {
        return text.hashCode() + author.hashCode();
    }
}
