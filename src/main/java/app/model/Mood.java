package app.model;

public enum Mood {
    HAPPY("Happy", "😊", "#FFD700"),
    SAD("Sad", "😢", "#4682B4"),
    STRESSED("Stressed", "😰", "#FF6347"),
    EXCITED("Excited", "🤩", "#FF69B4"),
    CALM("Calm", "😌", "#98FB98"),
    ANXIOUS("Anxious", "😟", "#DDA0DD"),
    ANGRY("Angry", "😠", "#DC143C"),
    ROMANTIC("Romantic", "😍", "#FF1493"),
    ENERGETIC("Energetic", "⚡", "#FFA500"),
    MELANCHOLIC("Melancholic", "😔", "#708090");

    private final String displayName;
    private final String emoji;
    private final String color;

    Mood(String displayName, String emoji, String color) {
        this.displayName = displayName;
        this.emoji = emoji;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayText() {
        return emoji + " " + displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
