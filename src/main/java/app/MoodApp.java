package app;

import app.model.Mood;
import app.model.Quote;
import app.model.Song;
import app.service.QuoteService;
import app.service.MusicService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class MoodApp extends Application {
    
    private QuoteService quoteService;
    private MusicService musicService;
    private VBox quotesContainer;
    private VBox songsContainer;
    private StackPane contentPane;
    private ScrollPane moodSelectionView;
    private ScrollPane resultsView;
    private Label selectedMoodLabel;

    @Override
    public void start(Stage primaryStage) {
        // Initialize services
        quoteService = new QuoteService();
        musicService = new MusicService();
        
        // Create main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");
        
        // Header
        VBox header = createHeader();
        root.setTop(header);
        
        // Content area
        contentPane = new StackPane();
        
        // Mood selection view
        moodSelectionView = createMoodSelectionView();
        
        // Results view
        resultsView = createResultsView();
        resultsView.setVisible(false);
        
        contentPane.getChildren().addAll(moodSelectionView, resultsView);
        root.setCenter(contentPane);
        
        // Status bar
        HBox statusBar = new HBox();
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.setPadding(new Insets(10, 20, 10, 20));
        statusBar.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1 0 0 0;");
        Label statusLabel = new Label("Ready - Select your mood to get started");
        statusBar.getChildren().add(statusLabel);
        root.setBottom(statusBar);
        
        // Create scene
        Scene scene = new Scene(root, 1200, 800);
        
        // Configure stage
        primaryStage.setTitle("🎭 Mood-Based Music & Quote Recommender");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.centerOnScreen();
        primaryStage.show();
        
        System.out.println("✅ Full Mood-Based Recommender started successfully!");
    }
    
    private VBox createHeader() {
        VBox header = new VBox(20);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30));
        header.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2);");
        
        Label titleLabel = new Label("🎭 Mood-Based Recommender");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        
        Label subtitleLabel = new Label("Discover quotes and music that match your mood");
        subtitleLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.9); -fx-font-size: 16px;");
        
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        
        CheckBox onlineMode = new CheckBox("Online Mode");
        onlineMode.setStyle("-fx-text-fill: white;");
        onlineMode.setSelected(true);
        
        Button settingsBtn = new Button("⚙️");
        settingsBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        
        Button favoritesBtn = new Button("❤️");
        favoritesBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;");
        
        controls.getChildren().addAll(onlineMode, settingsBtn, favoritesBtn);
        
        header.getChildren().addAll(titleLabel, subtitleLabel, controls);
        return header;
    }
    
    private ScrollPane createMoodSelectionView() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        
        Label titleLabel = new Label("How are you feeling today?");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        GridPane moodGrid = new GridPane();
        moodGrid.setAlignment(Pos.CENTER);
        moodGrid.setHgap(20);
        moodGrid.setVgap(20);
        
        Mood[] moods = Mood.values();
        int columns = 3;
        
        for (int i = 0; i < moods.length; i++) {
            Mood mood = moods[i];
            VBox moodCard = createMoodCard(mood);
            
            int row = i / columns;
            int col = i % columns;
            
            moodGrid.add(moodCard, col, row);
        }
        
        content.getChildren().addAll(titleLabel, moodGrid);
        scrollPane.setContent(content);
        
        return scrollPane;
    }
    
    private VBox createMoodCard(Mood mood) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(150, 120);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 15; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
            "-fx-cursor: hand;"
        );
        
        Label emojiLabel = new Label(mood.getEmoji());
        emojiLabel.setStyle("-fx-font-size: 36px;");
        
        Label nameLabel = new Label(mood.getDisplayName());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        card.getChildren().addAll(emojiLabel, nameLabel);
        
        // Add hover effect
        card.setOnMouseEntered(e -> {
            card.setStyle(card.getStyle() + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;");
        });
        
        card.setOnMouseExited(e -> {
            card.setStyle(card.getStyle().replace("-fx-scale-x: 1.05; -fx-scale-y: 1.05;", ""));
        });
        
        // Click handler
        card.setOnMouseClicked(e -> selectMood(mood));
        
        return card;
    }
    
    private ScrollPane createResultsView() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        
        // Back button and selected mood
        HBox topRow = new HBox(15);
        topRow.setAlignment(Pos.CENTER_LEFT);
        
        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 15;");
        backButton.setOnAction(e -> showMoodSelection());
        
        selectedMoodLabel = new Label();
        selectedMoodLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #667eea;");
        
        topRow.getChildren().addAll(backButton, selectedMoodLabel);
        
        // Results content
        HBox resultsContent = new HBox(30);
        
        // Quotes section
        VBox quotesSection = new VBox(15);
        Label quotesTitle = new Label("📝 Inspirational Quotes");
        quotesTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        quotesContainer = new VBox(10);
        quotesSection.getChildren().addAll(quotesTitle, quotesContainer);
        
        // Songs section
        VBox songsSection = new VBox(15);
        Label songsTitle = new Label("🎵 Recommended Music");
        songsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        songsContainer = new VBox(10);
        songsSection.getChildren().addAll(songsTitle, songsContainer);
        
        resultsContent.getChildren().addAll(quotesSection, songsSection);
        HBox.setHgrow(quotesSection, Priority.ALWAYS);
        HBox.setHgrow(songsSection, Priority.ALWAYS);
        
        content.getChildren().addAll(topRow, resultsContent);
        scrollPane.setContent(content);
        
        return scrollPane;
    }
    
    private void selectMood(Mood mood) {
        selectedMoodLabel.setText(mood.getDisplayText());
        
        // Load quotes
        List<Quote> quotes = quoteService.getQuotesForMood(mood, true);
        populateQuotes(quotes);
        
        // Load songs
        List<Song> songs = musicService.getSongsForMood(mood, true);
        populateSongs(songs);
        
        // Show results
        showResults();
        
        System.out.println("✨ Loaded recommendations for: " + mood.getDisplayName());
    }
    
    private void populateQuotes(List<Quote> quotes) {
        quotesContainer.getChildren().clear();
        
        for (Quote quote : quotes) {
            VBox quoteCard = createQuoteCard(quote);
            quotesContainer.getChildren().add(quoteCard);
        }
    }
    
    private VBox createQuoteCard(Quote quote) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setMaxWidth(350);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);"
        );
        
        Label textLabel = new Label("\"" + quote.getText() + "\"");
        textLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444; -fx-font-style: italic;");
        textLabel.setWrapText(true);
        
        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_LEFT);
        bottomRow.setSpacing(10);
        
        Label authorLabel = new Label("- " + quote.getAuthor());
        authorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-font-weight: bold;");
        
        Button favoriteBtn = new Button("🤍");
        favoriteBtn.setStyle("-fx-background-color: transparent; -fx-font-size: 16px;");
        favoriteBtn.setOnAction(e -> {
            favoriteBtn.setText("❤️");
            System.out.println("❤️ Favorited quote: " + quote.getText().substring(0, Math.min(30, quote.getText().length())) + "...");
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        bottomRow.getChildren().addAll(authorLabel, spacer, favoriteBtn);
        card.getChildren().addAll(textLabel, bottomRow);
        
        return card;
    }
    
    private void populateSongs(List<Song> songs) {
        songsContainer.getChildren().clear();
        
        for (Song song : songs) {
            VBox songCard = createSongCard(song);
            songsContainer.getChildren().add(songCard);
        }
    }
    
    private VBox createSongCard(Song song) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setMaxWidth(350);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);"
        );
        
        Label titleLabel = new Label(song.getTitle());
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        Label artistLabel = new Label(song.getArtist());
        artistLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        HBox buttonRow = new HBox(10);
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        
        Button playButton = new Button(song.isLocal() ? "▶️ Play" : "🔗 Open");
        playButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 15; -fx-font-size: 12px;");
        playButton.setOnAction(e -> {
            if (song.isLocal()) {
                System.out.println("🎵 Playing: " + song.getDisplayText());
            } else {
                System.out.println("🔗 Opening: " + song.getPathOrUrl());
                try {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(song.getPathOrUrl()));
                } catch (Exception ex) {
                    System.out.println("❌ Could not open link: " + ex.getMessage());
                }
            }
        });
        
        Button favoriteBtn = new Button("🤍");
        favoriteBtn.setStyle("-fx-background-color: transparent; -fx-font-size: 16px;");
        favoriteBtn.setOnAction(e -> {
            favoriteBtn.setText("❤️");
            System.out.println("❤️ Favorited song: " + song.getDisplayText());
        });
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        buttonRow.getChildren().addAll(playButton, spacer, favoriteBtn);
        card.getChildren().addAll(titleLabel, artistLabel, buttonRow);
        
        return card;
    }
    
    private void showMoodSelection() {
        moodSelectionView.setVisible(true);
        resultsView.setVisible(false);
    }
    
    private void showResults() {
        moodSelectionView.setVisible(false);
        resultsView.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("🚀 Starting Full Mood-Based Music & Quote Recommender...");
        launch(args);
    }
}
