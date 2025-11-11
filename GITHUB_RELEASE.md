# 🎭 Mood-Based Music & Quote Recommender

[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://adoptium.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17-blue)](https://openjfx.io/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A beautiful JavaFX desktop application that recommends personalized quotes and music based on your current mood.

![App Demo](https://via.placeholder.com/800x500/667eea/ffffff?text=Mood+Recommender+Demo)

## ✨ Features

- 🎭 **10 Mood Categories** - Happy, Sad, Calm, Excited, and more
- 📝 **50+ Curated Quotes** - Inspirational content for every mood
- 🎵 **Music Recommendations** - YouTube links for mood-matching songs
- ❤️ **Favorites System** - Save your preferred quotes and songs
- 🌐 **Online Mode** - Fetch fresh quotes from the internet
- 🎨 **Modern UI** - Beautiful Material Design interface
- 🖥️ **Cross-Platform** - Works on Windows, Mac, and Linux

## 🚀 Quick Start

### Download & Run (Easiest)

1. **Download** the latest release ZIP file
2. **Extract** to any folder
3. **Double-click** the launcher for your platform:
   - **Windows**: `launch-app.bat`
   - **Mac/Linux**: `launch-app.sh`
4. **Wait** for automatic setup (first run only)
5. **Enjoy** your mood-based recommendations!

### Requirements

- **Java 11+** ([Download here](https://adoptium.net/))
- **512 MB RAM** minimum
- **Internet connection** (optional, for online quotes)

## 📱 How to Use

1. **Select Your Mood** - Click on any mood card (😊 Happy, 😢 Sad, etc.)
2. **Browse Recommendations** - View curated quotes and music
3. **Save Favorites** - Click ❤️ to save items you love
4. **Open Music Links** - Click 🔗 to open songs in your browser
5. **Try Different Moods** - Each has unique content!

## 🛠️ For Developers

### Build from Source

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/mood-based-recommender.git
cd mood-based-recommender

# Compile and run
mvn compile
mvn dependency:copy-dependencies
java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "target/classes;target/dependency/*" app.MoodApp
```

### Project Structure

```
src/main/java/app/
├── MoodApp.java              # Main application (JavaFX UI)
├── model/                    # Data models (Mood, Quote, Song)
├── service/                  # Business logic
└── util/                     # Utilities

src/main/resources/
├── data/                     # JSON data files
├── css/                      # Styling
└── ui/                       # FXML layouts
```

## 🎨 Customization

### Add Your Own Content

**Quotes**: Edit `src/main/resources/data/quotes.json`
```json
{
  "happy": [
    {
      "text": "Your custom quote here",
      "author": "Author Name",
      "mood": "happy",
      "source": "local"
    }
  ]
}
```

**Music**: Edit `src/main/resources/data/songs.json`
```json
{
  "calm": [
    {
      "title": "Your Song",
      "artist": "Artist Name",
      "pathOrUrl": "https://youtube.com/watch?v=...",
      "isLocal": false
    }
  ]
}
```

## 📊 Tech Stack

- **Java 11+** - Core language
- **JavaFX 17** - Modern UI framework
- **JFoenix** - Material Design components
- **Jackson** - JSON processing
- **Maven** - Build management

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Quote API: [Quotable.io](https://quotable.io)
- Icons: Unicode Emoji
- Design: Material Design principles
- Music: Sample links for demonstration

## 📞 Support

- 🐛 **Bug Reports**: [Create an issue](https://github.com/YOUR_USERNAME/mood-based-recommender/issues)
- 💡 **Feature Requests**: [Start a discussion](https://github.com/YOUR_USERNAME/mood-based-recommender/discussions)
- 📧 **Contact**: your-email@example.com

---

**Made with ❤️ for mood-based discovery**

⭐ **Star this repo if you found it helpful!**
