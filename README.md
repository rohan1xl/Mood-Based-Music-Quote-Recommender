# Mood-Based Music & Quote Recommender

A modern JavaFX desktop application that recommends personalized quotes and music based on your current mood. Built with Java 11+, JavaFX, and Material Design components.

![App Screenshot](https://via.placeholder.com/800x500?text=Mood+Recommender+Screenshot)

## ✨ Features

- **10 Mood Categories**: Happy, Sad, Stressed, Excited, Calm, Anxious, Angry, Romantic, Energetic, Melancholic
- **Smart Recommendations**: Get 3-5 curated quotes and songs for each mood
- **Local & Online Content**: Offline-first with optional online quote fetching
- **Music Playback**: Play local audio files with built-in media controls
- **External Links**: Open YouTube/Spotify links in your browser
- **Favorites System**: Save your favorite quotes and songs locally
- **Modern UI**: Material Design with smooth animations and responsive layout
- **Settings**: Toggle online mode, adjust volume, and customize preferences

## 🚀 Quick Start

### Prerequisites

- **Java 11+** (Java 17+ recommended)
- **Maven 3.6+**
- **JavaFX SDK** (included via Maven dependencies)

### Installation & Running

1. **Clone or download** this project
2. **Navigate** to the project directory
3. **Build and run**:

```bash
# Build the project
mvn clean compile

# Run the application
mvn javafx:run
```

**Alternative**: Open in your IDE (IntelliJ IDEA, Eclipse, VS Code) and run the `app.Main` class.

### Creating Executable JAR

```bash
# Create executable JAR with dependencies
mvn clean package

# Run the JAR (requires JavaFX runtime)
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.media -jar target/mood-based-recommender-1.0.0.jar
```

## 🎵 Adding Your Music

### Local Music Files

1. Place your music files (`.mp3`, `.wav`, `.m4a`, `.flac`, `.aac`) in a folder
2. Update the songs in `src/main/resources/data/songs.json`:

```json
{
  "happy": [
    {
      "title": "Your Song Title",
      "artist": "Artist Name",
      "mood": "happy",
      "pathOrUrl": "C:/Music/happy/your-song.mp3",
      "isLocal": true,
      "genre": "Pop",
      "duration": 180
    }
  ]
}
```

### Online Music Links

Add YouTube, Spotify, or other streaming links:

```json
{
  "title": "Song Title",
  "artist": "Artist Name",
  "mood": "excited",
  "pathOrUrl": "https://www.youtube.com/watch?v=VIDEO_ID",
  "isLocal": false,
  "genre": "Rock",
  "duration": 240
}
```

## 📝 Customizing Quotes

Edit `src/main/resources/data/quotes.json` to add your own quotes:

```json
{
  "calm": [
    {
      "text": "Your inspirational quote here.",
      "author": "Author Name",
      "mood": "calm",
      "source": "local"
    }
  ]
}
```

## 🏗️ Project Structure

```
MoodRecommender/
├── src/main/java/
│   ├── app/Main.java                    # Application entry point
│   ├── app/controller/MainController.java # Main UI controller
│   ├── app/model/                       # Data models
│   │   ├── Mood.java                   # Mood enumeration
│   │   ├── Quote.java                  # Quote model
│   │   └── Song.java                   # Song model
│   ├── app/service/                    # Business logic
│   │   ├── QuoteService.java           # Quote management
│   │   ├── MusicService.java           # Music playback
│   │   └── LocalStorageService.java    # Data persistence
│   ├── app/ui/components/              # Custom UI components
│   │   └── ResultCard.java
│   └── app/util/                       # Utilities
│       └── HttpUtil.java               # HTTP client
├── src/main/resources/
│   ├── ui/main.fxml                    # Main UI layout
│   ├── css/style.css                   # Application styling
│   └── data/                           # Data files
│       ├── quotes.json                 # Quote database
│       └── songs.json                  # Song database
├── pom.xml                             # Maven configuration
└── README.md                           # This file
```

## 🛠️ Technical Details

### Architecture

- **MVC Pattern**: Separation of concerns with Models, Views, and Controllers
- **Service Layer**: Business logic encapsulated in service classes
- **Repository Pattern**: Local storage abstraction for data persistence
- **Observer Pattern**: UI updates through JavaFX property bindings

### Key Technologies

- **JavaFX 17**: Modern UI framework with FXML and CSS styling
- **JFoenix**: Material Design components for JavaFX
- **Jackson**: JSON parsing and serialization
- **Java HTTP Client**: Built-in HTTP client for API calls
- **Maven**: Dependency management and build automation

### Supported Audio Formats

- MP3, WAV, M4A, FLAC, AAC
- Requires appropriate codecs installed on the system

## 🌐 Online Features

### Quote API Integration

The app can fetch quotes from [Quotable.io](https://quotable.io) when online mode is enabled:

- Automatic fallback to local quotes if API is unavailable
- Mood-based tag mapping for relevant quotes
- Asynchronous loading to keep UI responsive

### Network Configuration

- HTTP requests timeout after 30 seconds
- Connection timeout: 10 seconds
- Graceful degradation when offline

## 💾 Data Storage

### User Data Location

- **Windows**: `%USERPROFILE%\.mood-recommender\`
- **macOS**: `~/.mood-recommender/`
- **Linux**: `~/.mood-recommender/`

### Stored Files

- `favorite-quotes.json` - User's favorite quotes
- `favorite-songs.json` - User's favorite songs
- `settings.json` - Application preferences

## 🎨 Customization

### Themes

The app supports light and dark themes. To enable dark theme, modify the CSS:

```css
.root.dark-theme {
    -fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e);
}
```

### Adding New Moods

1. Add to `Mood.java` enum:
```java
NEW_MOOD("New Mood", "🎭", "#FF5733")
```

2. Add corresponding data to `quotes.json` and `songs.json`

3. Update mood grid layout in `MainController.java` if needed

## 🧪 Testing

### Running Tests

```bash
mvn test
```

### Manual Testing Checklist

- [ ] All mood cards display correctly
- [ ] Quotes load for each mood
- [ ] Songs load for each mood
- [ ] Local music playback works
- [ ] External links open in browser
- [ ] Favorites can be added/removed
- [ ] Settings persist between sessions
- [ ] Online mode toggle works
- [ ] Volume control functions
- [ ] Responsive design on different screen sizes

## 🚀 Performance Tips

### Optimizing Startup Time

- Preload commonly used resources
- Use lazy loading for heavy components
- Minimize initial data loading

### Memory Management

- Dispose MediaPlayer instances when switching songs
- Use weak references for event listeners
- Clear unused collections periodically

## 🔧 Troubleshooting

### Common Issues

**JavaFX Runtime Not Found**
```bash
# Solution: Add JavaFX modules to classpath
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.media -jar app.jar
```

**Audio Playback Issues**
- Ensure audio codecs are installed
- Check file permissions
- Verify file path format (use forward slashes)

**Network Connection Errors**
- Check internet connectivity
- Verify firewall settings
- Try disabling online mode

### Debug Mode

Enable debug logging by setting JVM property:
```bash
java -Djava.util.logging.level=FINE -jar app.jar
```

## 📈 Future Enhancements

### Planned Features

- [ ] Spotify API integration
- [ ] Last.fm scrobbling
- [ ] Playlist creation and management
- [ ] Machine learning mood detection
- [ ] Social sharing capabilities
- [ ] Plugin system for custom mood providers
- [ ] Mobile companion app
- [ ] Voice control integration

### Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Quote API**: [Quotable.io](https://quotable.io) for providing inspirational quotes
- **Icons**: Emoji icons from Unicode Consortium
- **Design**: Inspired by Material Design principles
- **Music**: Sample music links from various artists (for demonstration only)

## 📞 Support

For questions, issues, or suggestions:

- Create an issue on GitHub
- Email: your-email@example.com
- Documentation: [Project Wiki](https://github.com/your-username/mood-recommender/wiki)

---

**Made with ❤️ for mood-based discovery**
