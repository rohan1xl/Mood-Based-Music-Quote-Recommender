package app.service;

import app.model.Mood;
import app.model.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MusicService {
    private static final Logger LOGGER = Logger.getLogger(MusicService.class.getName());
    private static final String SONGS_FILE = "/data/songs.json";
    
    private final ObjectMapper objectMapper;
    private final Map<String, List<Song>> localSongs;
    private MediaPlayer currentPlayer;

    public MusicService() {
        this.objectMapper = new ObjectMapper();
        this.localSongs = loadLocalSongs();
    }

    public List<Song> getSongsForMood(Mood mood, boolean onlineMode) {
        List<Song> songs = new ArrayList<>();
        
        // Get local songs for the mood
        songs.addAll(getLocalSongsForMood(mood));
        
        // If online mode is enabled, you could add streaming service integration here
        // For now, we'll include some sample online links
        if (onlineMode) {
            songs.addAll(getOnlineSongsForMood(mood));
        }
        
        // Shuffle and limit results
        Collections.shuffle(songs);
        return songs.stream().limit(5).collect(Collectors.toList());
    }

    private List<Song> getLocalSongsForMood(Mood mood) {
        String moodKey = mood.name().toLowerCase();
        return localSongs.getOrDefault(moodKey, new ArrayList<>())
                .stream()
                .filter(Song::isLocal)
                .filter(song -> new File(song.getPathOrUrl()).exists())
                .collect(Collectors.toList());
    }

    private List<Song> getOnlineSongsForMood(Mood mood) {
        String moodKey = mood.name().toLowerCase();
        return localSongs.getOrDefault(moodKey, new ArrayList<>())
                .stream()
                .filter(song -> !song.isLocal())
                .collect(Collectors.toList());
    }

    public MediaPlayer playLocalSong(Song song) {
        if (!song.isLocal()) {
            throw new IllegalArgumentException("Song is not a local file");
        }

        try {
            stopCurrentPlayer();
            
            File file = new File(song.getPathOrUrl());
            if (!file.exists()) {
                throw new IOException("Music file not found: " + song.getPathOrUrl());
            }

            String uri = file.toURI().toString();
            Media media = new Media(uri);
            currentPlayer = new MediaPlayer(media);
            
            currentPlayer.setOnError(() -> {
                LOGGER.log(Level.SEVERE, "Media player error: " + currentPlayer.getError());
            });
            
            currentPlayer.setOnReady(() -> {
                LOGGER.info("Playing: " + song.getDisplayText());
            });
            
            currentPlayer.play();
            return currentPlayer;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to play song: " + song.getDisplayText(), e);
            throw new RuntimeException("Failed to play song", e);
        }
    }

    public void openExternalLink(Song song) {
        if (song.isLocal()) {
            throw new IllegalArgumentException("Song is a local file, not an external link");
        }

        try {
            URI uri = new URI(song.getPathOrUrl());
            java.awt.Desktop.getDesktop().browse(uri);
            LOGGER.info("Opened external link: " + song.getPathOrUrl());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to open external link: " + song.getPathOrUrl(), e);
            throw new RuntimeException("Failed to open external link", e);
        }
    }

    public void stopCurrentPlayer() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer.dispose();
            currentPlayer = null;
        }
    }

    public void pauseCurrentPlayer() {
        if (currentPlayer != null && currentPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            currentPlayer.pause();
        }
    }

    public void resumeCurrentPlayer() {
        if (currentPlayer != null && currentPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            currentPlayer.play();
        }
    }

    public MediaPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    private Map<String, List<Song>> loadLocalSongs() {
        try (InputStream inputStream = getClass().getResourceAsStream(SONGS_FILE)) {
            if (inputStream != null) {
                TypeReference<Map<String, List<Song>>> typeRef = new TypeReference<Map<String, List<Song>>>() {};
                Map<String, List<Song>> songs = objectMapper.readValue(inputStream, typeRef);
                LOGGER.info("Loaded local songs from " + SONGS_FILE);
                return songs;
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load local songs", e);
        }
        
        // Return default songs if file not found
        return createDefaultSongs();
    }

    private Map<String, List<Song>> createDefaultSongs() {
        Map<String, List<Song>> defaultSongs = new HashMap<>();
        
        // Happy songs
        List<Song> happySongs = Arrays.asList(
            new Song("Happy", "Pharrell Williams", "happy", "https://www.youtube.com/watch?v=ZbZSe6N_BXs", false, "Pop", 233),
            new Song("Walking on Sunshine", "Katrina & The Waves", "happy", "https://www.youtube.com/watch?v=iPUmE-tne5U", false, "Pop", 239),
            new Song("Good as Hell", "Lizzo", "happy", "https://www.youtube.com/watch?v=SmbmeOgWsqE", false, "Pop", 219)
        );
        defaultSongs.put("happy", happySongs);
        
        // Sad songs
        List<Song> sadSongs = Arrays.asList(
            new Song("Mad World", "Gary Jules", "sad", "https://www.youtube.com/watch?v=4N3N1MlvVc4", false, "Alternative", 192),
            new Song("Hurt", "Johnny Cash", "sad", "https://www.youtube.com/watch?v=8AHCfZTRGiI", false, "Country", 218),
            new Song("Black", "Pearl Jam", "sad", "https://www.youtube.com/watch?v=5ZH2it92ZmA", false, "Grunge", 348)
        );
        defaultSongs.put("sad", sadSongs);
        
        // Calm songs
        List<Song> calmSongs = Arrays.asList(
            new Song("Weightless", "Marconi Union", "calm", "https://www.youtube.com/watch?v=UfcAVejslrU", false, "Ambient", 485),
            new Song("Clair de Lune", "Claude Debussy", "calm", "https://www.youtube.com/watch?v=CvFH_6DNRCY", false, "Classical", 300),
            new Song("Aqueous Transmission", "Incubus", "calm", "https://www.youtube.com/watch?v=eQK7KSTQfaw", false, "Alternative", 443)
        );
        defaultSongs.put("calm", calmSongs);
        
        // Energetic songs
        List<Song> energeticSongs = Arrays.asList(
            new Song("Eye of the Tiger", "Survivor", "energetic", "https://www.youtube.com/watch?v=btPJPFnesV4", false, "Rock", 245),
            new Song("Pump It", "Black Eyed Peas", "energetic", "https://www.youtube.com/watch?v=ZaI2IlHwmgQ", false, "Hip Hop", 213),
            new Song("Thunder", "Imagine Dragons", "energetic", "https://www.youtube.com/watch?v=fKopy74weus", false, "Pop Rock", 187)
        );
        defaultSongs.put("energetic", energeticSongs);
        
        LOGGER.info("Created default songs");
        return defaultSongs;
    }

    public List<Song> searchSongs(String searchTerm) {
        return localSongs.values().stream()
                .flatMap(List::stream)
                .filter(song -> song.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                               song.getArtist().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void scanMusicFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            LOGGER.warning("Invalid music folder path: " + folderPath);
            return;
        }

        // Scan for supported audio files
        String[] supportedExtensions = {".mp3", ".wav", ".m4a", ".flac", ".aac"};
        scanDirectory(folder, supportedExtensions);
    }

    private void scanDirectory(File directory, String[] extensions) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file, extensions);
            } else if (file.isFile()) {
                String fileName = file.getName().toLowerCase();
                for (String ext : extensions) {
                    if (fileName.endsWith(ext)) {
                        // Add discovered music file to appropriate mood category
                        // This is a simplified implementation
                        LOGGER.info("Found music file: " + file.getAbsolutePath());
                        break;
                    }
                }
            }
        }
    }
}
