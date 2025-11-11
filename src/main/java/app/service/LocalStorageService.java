package app.service;

import app.model.Quote;
import app.model.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalStorageService {
    private static final Logger LOGGER = Logger.getLogger(LocalStorageService.class.getName());
    private static final String DATA_FOLDER = "data";
    private static final String FAVORITES_FILE = "favorites.json";
    private static final String SETTINGS_FILE = "settings.json";
    
    private final ObjectMapper objectMapper;
    private final Path dataPath;

    public LocalStorageService() {
        this.objectMapper = new ObjectMapper();
        this.dataPath = Paths.get(System.getProperty("user.home"), ".mood-recommender");
        initializeDataFolder();
    }

    private void initializeDataFolder() {
        try {
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
                LOGGER.info("Created data directory: " + dataPath);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to create data directory", e);
        }
    }

    public void saveFavoriteQuotes(List<Quote> favoriteQuotes) {
        try {
            Path favoritesPath = dataPath.resolve("favorite-quotes.json");
            objectMapper.writeValue(favoritesPath.toFile(), favoriteQuotes);
            LOGGER.info("Saved " + favoriteQuotes.size() + " favorite quotes");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save favorite quotes", e);
        }
    }

    public List<Quote> loadFavoriteQuotes() {
        try {
            Path favoritesPath = dataPath.resolve("favorite-quotes.json");
            if (Files.exists(favoritesPath)) {
                return objectMapper.readValue(favoritesPath.toFile(), 
                    new TypeReference<List<Quote>>() {});
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load favorite quotes", e);
        }
        return new ArrayList<>();
    }

    public void saveFavoriteSongs(List<Song> favoriteSongs) {
        try {
            Path favoritesPath = dataPath.resolve("favorite-songs.json");
            objectMapper.writeValue(favoritesPath.toFile(), favoriteSongs);
            LOGGER.info("Saved " + favoriteSongs.size() + " favorite songs");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save favorite songs", e);
        }
    }

    public List<Song> loadFavoriteSongs() {
        try {
            Path favoritesPath = dataPath.resolve("favorite-songs.json");
            if (Files.exists(favoritesPath)) {
                return objectMapper.readValue(favoritesPath.toFile(), 
                    new TypeReference<List<Song>>() {});
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load favorite songs", e);
        }
        return new ArrayList<>();
    }

    public void saveSettings(AppSettings settings) {
        try {
            Path settingsPath = dataPath.resolve(SETTINGS_FILE);
            objectMapper.writeValue(settingsPath.toFile(), settings);
            LOGGER.info("Settings saved successfully");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save settings", e);
        }
    }

    public AppSettings loadSettings() {
        try {
            Path settingsPath = dataPath.resolve(SETTINGS_FILE);
            if (Files.exists(settingsPath)) {
                return objectMapper.readValue(settingsPath.toFile(), AppSettings.class);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load settings", e);
        }
        return new AppSettings(); // Return default settings
    }

    public static class AppSettings {
        private boolean onlineMode = true;
        private String musicFolderPath = "";
        private boolean darkTheme = false;
        private double volume = 0.5;

        public AppSettings() {}

        public boolean isOnlineMode() {
            return onlineMode;
        }

        public void setOnlineMode(boolean onlineMode) {
            this.onlineMode = onlineMode;
        }

        public String getMusicFolderPath() {
            return musicFolderPath;
        }

        public void setMusicFolderPath(String musicFolderPath) {
            this.musicFolderPath = musicFolderPath;
        }

        public boolean isDarkTheme() {
            return darkTheme;
        }

        public void setDarkTheme(boolean darkTheme) {
            this.darkTheme = darkTheme;
        }

        public double getVolume() {
            return volume;
        }

        public void setVolume(double volume) {
            this.volume = volume;
        }
    }

    public Path getDataPath() {
        return dataPath;
    }
}
