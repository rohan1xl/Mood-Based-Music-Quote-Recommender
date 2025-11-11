# Getting Started with Mood-Based Recommender

## 🚀 Quick Setup (5 minutes)

### Step 1: Prerequisites
Ensure you have:
- **Java 11+** installed ([Download here](https://adoptium.net/))
- **Maven 3.6+** installed ([Download here](https://maven.apache.org/download.cgi))

### Step 2: Run the Application

**Windows:**
```cmd
# Double-click run.bat or execute in command prompt:
run.bat
```

**Mac/Linux:**
```bash
# Make the script executable and run:
chmod +x run.sh
./run.sh
```

**Manual (any OS):**
```bash
mvn clean compile
mvn javafx:run
```

### Step 3: First Launch
1. The app will open with a mood selection screen
2. Click on any mood card (e.g., "😊 Happy")
3. View recommended quotes and music
4. Click "▶️ Play" for local files or "🔗 Open" for online links
5. Use "🤍" to favorite items

## 🎵 Adding Your Music

### Option 1: Update JSON Files
Edit `src/main/resources/data/songs.json`:

```json
{
  "happy": [
    {
      "title": "Your Song",
      "artist": "Your Artist",
      "mood": "happy",
      "pathOrUrl": "C:/Music/your-song.mp3",
      "isLocal": true,
      "genre": "Pop",
      "duration": 180
    }
  ]
}
```

### Option 2: Use Absolute Paths
Place music files anywhere and use full paths:
- Windows: `"C:/Users/YourName/Music/song.mp3"`
- Mac: `"/Users/YourName/Music/song.mp3"`
- Linux: `"/home/yourname/Music/song.mp3"`

## 📝 Adding Custom Quotes

Edit `src/main/resources/data/quotes.json`:

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

## 🎯 Key Features to Try

1. **Mood Selection**: Try different moods to see varied content
2. **Online Mode**: Toggle the switch to fetch quotes from the internet
3. **Favorites**: Heart your favorite quotes and songs
4. **Music Player**: Use play/pause/stop controls and volume slider
5. **External Links**: Click "🔗 Open" to open YouTube/Spotify in browser

## 🔧 Troubleshooting

### "JavaFX Runtime Not Found"
If you get JavaFX errors, try:
```bash
# Download JavaFX SDK and run with:
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.media -jar target/mood-based-recommender-1.0.0.jar
```

### "Cannot Play Audio File"
- Ensure the file path is correct
- Check file format (MP3, WAV, M4A supported)
- Verify file permissions

### "No Internet Connection"
- Disable online mode toggle
- App works fully offline with local content

## 📁 Project Structure Overview

```
mood based rohaN MINI PROJECT/
├── src/main/java/app/           # Java source code
├── src/main/resources/          # UI, CSS, and data files
├── target/                      # Compiled files (auto-generated)
├── pom.xml                      # Maven configuration
├── README.md                    # Full documentation
├── run.bat / run.sh            # Quick start scripts
└── GETTING_STARTED.md          # This file
```

## 🎨 Customization Ideas

1. **Add New Moods**: Edit `Mood.java` enum
2. **Change Colors**: Modify `style.css`
3. **Add More Quotes**: Expand `quotes.json`
4. **Local Music Library**: Update `songs.json` with your collection

## 📞 Need Help?

- Check the full [README.md](README.md) for detailed documentation
- Look at the code comments for implementation details
- Create an issue if you find bugs

## 🎉 Enjoy Your Mood-Based Experience!

The app is designed to be intuitive and responsive to your emotional state. Explore different moods and discover new quotes and music that resonate with how you're feeling.

**Happy coding and mood exploring! 🎵✨**
