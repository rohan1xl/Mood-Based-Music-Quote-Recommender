package app.controller;

import app.model.Mood;
import app.model.Quote;
import app.model.Song;
import app.service.LocalStorageService;
import app.service.MusicService;
import app.service.QuoteService;
import app.ui.components.ResultCard;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @FXML private StackPane contentPane;
    @FXML private ScrollPane moodSelectionView;
    @FXML private ScrollPane resultsView;
    @FXML private GridPane moodGrid;
    @FXML private VBox quotesContainer;
    @FXML private VBox songsContainer;
    @FXML private Label selectedMoodLabel;
    @FXML private Label statusLabel;
    @FXML private Label nowPlayingLabel;
    @FXML private VBox playerControls;
    @FXML private JFXButton playPauseButton;
    @FXML private JFXButton stopButton;
    @FXML private JFXSlider volumeSlider;
    @FXML private JFXToggleButton onlineModeToggle;
    @FXML private JFXProgressBar loadingProgress;

    private QuoteService quoteService;
    private MusicService musicService;
    private LocalStorageService storageService;
    private Mood currentMood;
    private List<Quote> favoriteQuotes;
    private List<Song> favoriteSongs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeServices();
        setupMoodGrid();
        setupPlayerControls();
        loadUserPreferences();
        
        statusLabel.setText("Ready - Select your mood to get started");
    }

    private void initializeServices() {
        quoteService = new QuoteService();
        musicService = new MusicService();
        storageService = new LocalStorageService();
        
        favoriteQuotes = storageService.loadFavoriteQuotes();
        favoriteSongs = storageService.loadFavoriteSongs();
    }

    private void setupMoodGrid() {
        moodGrid.getChildren().clear();
        
        Mood[] moods = Mood.values();
        int columns = 3;
        
        for (int i = 0; i < moods.length; i++) {
            Mood mood = moods[i];
            VBox moodCard = createMoodCard(mood);
            
            int row = i / columns;
            int col = i % columns;
            
            moodGrid.add(moodCard, col, row);
            GridPane.setMargin(moodCard, new Insets(10));
        }
    }

    private VBox createMoodCard(Mood mood) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setSpacing(10);
        card.getStyleClass().add("mood-card");
        card.setPrefSize(150, 120);
        
        // Emoji label
        Label emojiLabel = new Label(mood.getEmoji());
        emojiLabel.getStyleClass().add("mood-emoji");
        emojiLabel.setFont(Font.font(36));
        
        // Mood name label
        Label nameLabel = new Label(mood.getDisplayName());
        nameLabel.getStyleClass().add("mood-name");
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        card.getChildren().addAll(emojiLabel, nameLabel);
        
        // Set background color based on mood
        card.setStyle("-fx-background-color: " + mood.getColor() + "22;");
        
        // Click handler
        card.setOnMouseClicked(event -> selectMood(mood));
        
        return card;
    }

    private void selectMood(Mood mood) {
        this.currentMood = mood;
        selectedMoodLabel.setText(mood.getDisplayText());
        
        statusLabel.setText("Loading recommendations for " + mood.getDisplayName() + "...");
        loadingProgress.setVisible(true);
        
        // Load recommendations in background
        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                loadRecommendations(mood);
                return null;
            }
            
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    showResults();
                    loadingProgress.setVisible(false);
                    statusLabel.setText("Showing recommendations for " + mood.getDisplayName());
                });
            }
            
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    loadingProgress.setVisible(false);
                    statusLabel.setText("Failed to load recommendations");
                    LOGGER.log(Level.SEVERE, "Failed to load recommendations", getException());
                });
            }
        };
        
        new Thread(loadTask).start();
    }

    private void loadRecommendations(Mood mood) {
        boolean onlineMode = onlineModeToggle.isSelected();
        
        // Load quotes
        List<Quote> quotes = quoteService.getQuotesForMood(mood, onlineMode);
        Platform.runLater(() -> populateQuotes(quotes));
        
        // Load songs
        List<Song> songs = musicService.getSongsForMood(mood, onlineMode);
        Platform.runLater(() -> populateSongs(songs));
    }

    private void populateQuotes(List<Quote> quotes) {
        quotesContainer.getChildren().clear();
        
        for (Quote quote : quotes) {
            VBox quoteCard = createQuoteCard(quote);
            quotesContainer.getChildren().add(quoteCard);
        }
    }

    private VBox createQuoteCard(Quote quote) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.getStyleClass().add("quote-card");
        card.setPadding(new Insets(15));
        
        // Quote text
        Label textLabel = new Label("\"" + quote.getText() + "\"");
        textLabel.getStyleClass().add("quote-text");
        textLabel.setWrapText(true);
        textLabel.setMaxWidth(300);
        
        // Author
        Label authorLabel = new Label("- " + quote.getAuthor());
        authorLabel.getStyleClass().add("quote-author");
        
        // Favorite button
        JFXButton favoriteBtn = new JFXButton(quote.isFavorite() ? "❤️" : "🤍");
        favoriteBtn.getStyleClass().add("favorite-button");
        if (quote.isFavorite()) {
            favoriteBtn.getStyleClass().add("favorited");
        }
        favoriteBtn.setOnAction(e -> toggleQuoteFavorite(quote, favoriteBtn));
        
        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_LEFT);
        bottomRow.setSpacing(10);
        bottomRow.getChildren().addAll(authorLabel, new Region(), favoriteBtn);
        HBox.setHgrow(bottomRow.getChildren().get(1), Priority.ALWAYS);
        
        card.getChildren().addAll(textLabel, bottomRow);
        return card;
    }

    private VBox createSongCard(Song song) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.getStyleClass().add("song-card");
        card.setPadding(new Insets(12));
        
        // Song title
        Label titleLabel = new Label(song.getTitle());
        titleLabel.getStyleClass().add("song-title");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        // Artist
        Label artistLabel = new Label(song.getArtist());
        artistLabel.getStyleClass().add("song-artist");
        
        // Duration (if available)
        if (song.getDuration() > 0) {
            Label durationLabel = new Label(song.getFormattedDuration());
            durationLabel.getStyleClass().add("song-duration");
            artistLabel.setText(artistLabel.getText() + " • " + song.getFormattedDuration());
        }
        
        // Action buttons
        HBox buttonRow = new HBox();
        buttonRow.setSpacing(10);
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        
        JFXButton playButton = new JFXButton(song.isLocal() ? "▶️ Play" : "🔗 Open");
        playButton.getStyleClass().add("play-button");
        playButton.setOnAction(e -> {
            if (song.isLocal()) {
                playLocalSong(song);
            } else {
                openExternalLink(song);
            }
        });
        
        JFXButton favoriteBtn = new JFXButton(song.isFavorite() ? "❤️" : "🤍");
        favoriteBtn.getStyleClass().add("favorite-button");
        if (song.isFavorite()) {
            favoriteBtn.getStyleClass().add("favorited");
        }
        favoriteBtn.setOnAction(e -> toggleSongFavorite(song, favoriteBtn));
        
        buttonRow.getChildren().addAll(playButton, new Region(), favoriteBtn);
        HBox.setHgrow(buttonRow.getChildren().get(1), Priority.ALWAYS);
        
        card.getChildren().addAll(titleLabel, artistLabel, buttonRow);
        return card;
    }

    private void populateSongs(List<Song> songs) {
        songsContainer.getChildren().clear();
        
        for (Song song : songs) {
            VBox songCard = createSongCard(song);
            songsContainer.getChildren().add(songCard);
        }
    }

    private void playLocalSong(Song song) {
        try {
            musicService.stopCurrentPlayer();
            MediaPlayer player = musicService.playLocalSong(song);
            
            nowPlayingLabel.setText("Now Playing: " + song.getDisplayText());
            playerControls.setVisible(true);
            playPauseButton.setText("⏸️");
            
            // Bind volume
            player.volumeProperty().bind(volumeSlider.valueProperty());
            
            statusLabel.setText("Playing: " + song.getDisplayText());
        } catch (Exception e) {
            statusLabel.setText("Error playing song: " + e.getMessage());
            LOGGER.log(Level.WARNING, "Failed to play song: " + song.getDisplayText(), e);
        }
    }

    private void openExternalLink(Song song) {
        try {
            musicService.openExternalLink(song);
            statusLabel.setText("Opened: " + song.getDisplayText());
        } catch (Exception e) {
            statusLabel.setText("Error opening link: " + e.getMessage());
            LOGGER.log(Level.WARNING, "Failed to open external link: " + song.getPathOrUrl(), e);
        }
    }

    private void toggleQuoteFavorite(Quote quote, JFXButton button) {
        quote.setFavorite(!quote.isFavorite());
        button.setText(quote.isFavorite() ? "❤️" : "🤍");
        
        if (quote.isFavorite()) {
            button.getStyleClass().add("favorited");
            if (!favoriteQuotes.contains(quote)) {
                favoriteQuotes.add(quote);
            }
        } else {
            button.getStyleClass().remove("favorited");
            favoriteQuotes.remove(quote);
        }
        
        storageService.saveFavoriteQuotes(favoriteQuotes);
    }

    private void toggleSongFavorite(Song song, JFXButton button) {
        song.setFavorite(!song.isFavorite());
        button.setText(song.isFavorite() ? "❤️" : "🤍");
        
        if (song.isFavorite()) {
            button.getStyleClass().add("favorited");
            if (!favoriteSongs.contains(song)) {
                favoriteSongs.add(song);
            }
        } else {
            button.getStyleClass().remove("favorited");
            favoriteSongs.remove(song);
        }
        
        storageService.saveFavoriteSongs(favoriteSongs);
    }

    private void setupPlayerControls() {
        volumeSlider.setValue(0.5);
        playerControls.setVisible(false);
    }

    private void loadUserPreferences() {
        LocalStorageService.AppSettings settings = storageService.loadSettings();
        onlineModeToggle.setSelected(settings.isOnlineMode());
        volumeSlider.setValue(settings.getVolume());
    }

    @FXML
    private void showMoodSelection() {
        moodSelectionView.setVisible(true);
        resultsView.setVisible(false);
        statusLabel.setText("Select your mood to get started");
    }

    private void showResults() {
        moodSelectionView.setVisible(false);
        resultsView.setVisible(true);
    }

    @FXML
    private void togglePlayPause() {
        MediaPlayer player = musicService.getCurrentPlayer();
        if (player != null) {
            if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                musicService.pauseCurrentPlayer();
                playPauseButton.setText("▶️");
                statusLabel.setText("Paused");
            } else if (player.getStatus() == MediaPlayer.Status.PAUSED) {
                musicService.resumeCurrentPlayer();
                playPauseButton.setText("⏸️");
                statusLabel.setText("Playing");
            }
        }
    }

    @FXML
    private void stopMusic() {
        musicService.stopCurrentPlayer();
        playerControls.setVisible(false);
        statusLabel.setText("Stopped");
    }

    @FXML
    private void showSettings() {
        statusLabel.setText("Settings feature coming soon!");
    }

    @FXML
    private void showFavorites() {
        statusLabel.setText("Favorites feature coming soon!");
    }
}
