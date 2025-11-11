# 🚀 Mood-Based Recommender - Deployment Guide

## 📦 Deployment Options

### Option 1: Standalone Executable (Recommended)

**Current Working Command:**
```bash
java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "target/classes;target/dependency/*" app.MoodApp
```

### Option 2: Create Distribution Package

1. **Create deployment folder structure:**
```
mood-recommender-app/
├── lib/                    # All JAR dependencies
├── app/                    # Compiled classes
├── data/                   # JSON data files
├── run-windows.bat         # Windows launcher
├── run-unix.sh            # Linux/Mac launcher
└── README.txt             # User instructions
```

2. **Copy required files:**
   - `target/classes/*` → `app/`
   - `target/dependency/*.jar` → `lib/`
   - `src/main/resources/*` → `data/`

### Option 3: JAR with Dependencies (Fat JAR)

Create a single JAR file containing all dependencies using Maven Shade plugin (already configured in pom.xml).

## 🖥️ Platform-Specific Deployment

### Windows Deployment

**Create `mood-recommender.bat`:**
```batch
@echo off
title Mood-Based Recommender
echo Starting Mood-Based Recommender...
java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "target/classes;target/dependency/*" app.MoodApp
pause
```

### Linux/Mac Deployment

**Create `mood-recommender.sh`:**
```bash
#!/bin/bash
echo "Starting Mood-Based Recommender..."
java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "target/classes:target/dependency/*" app.MoodApp
```

**Make executable:**
```bash
chmod +x mood-recommender.sh
```

## 🌐 Web Deployment Options

### Option 1: JavaFX Web Start (Deprecated)
- Not recommended for modern deployment

### Option 2: Convert to Web App
- Rewrite frontend in HTML/CSS/JavaScript
- Keep Java backend as REST API
- Deploy backend to cloud (Heroku, AWS, etc.)

### Option 3: Desktop App Packaging

**Using jpackage (Java 14+):**
```bash
jpackage --input target/classes \
         --name "Mood Recommender" \
         --main-jar mood-recommender.jar \
         --main-class app.MoodApp \
         --type exe \
         --win-shortcut \
         --win-menu
```

## 📱 Distribution Methods

### 1. Direct Distribution
- Zip the entire project folder
- Include run scripts for different platforms
- Provide installation instructions

### 2. Installer Creation
- **Windows**: Use Inno Setup or NSIS
- **Mac**: Create .dmg with create-dmg
- **Linux**: Create .deb or .rpm packages

### 3. App Store Distribution
- **Microsoft Store**: Package as MSIX
- **Mac App Store**: Code signing required
- **Linux**: Snap Store or Flatpak

## 🔧 Current Deployment Status

✅ **Ready to Deploy:**
- Application compiles successfully
- All dependencies resolved
- JavaFX runtime configured
- Data files included
- Cross-platform compatible

## 📋 Deployment Checklist

### Pre-Deployment
- [ ] Test on target platforms
- [ ] Verify all dependencies included
- [ ] Check file permissions
- [ ] Test with different Java versions
- [ ] Validate data file paths

### Deployment Package
- [ ] Create run scripts for each platform
- [ ] Include README with requirements
- [ ] Package all necessary files
- [ ] Test deployment package
- [ ] Create installation guide

### Post-Deployment
- [ ] User acceptance testing
- [ ] Performance monitoring
- [ ] Error logging setup
- [ ] Update mechanism planning
- [ ] User feedback collection

## 🎯 Quick Deploy Commands

**For immediate deployment:**

1. **Compile and prepare:**
```bash
mvn compile
mvn dependency:copy-dependencies
```

2. **Create launcher (Windows):**
```batch
echo @echo off > launch.bat
echo java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "target/classes;target/dependency/*" app.MoodApp >> launch.bat
```

3. **Run deployed app:**
```bash
launch.bat
```

## 📊 System Requirements

**Minimum Requirements:**
- Java 11 or higher
- 512 MB RAM
- 100 MB disk space
- Windows 10/macOS 10.14/Linux (any modern distro)

**Recommended:**
- Java 17 or higher
- 1 GB RAM
- 200 MB disk space
- Internet connection (for online quotes)

## 🔒 Security Considerations

- Code signing for Windows/Mac distribution
- Virus scanner whitelisting
- Network permissions for online features
- File system permissions for data storage

## 📞 Support Information

**For deployment issues:**
- Check Java version compatibility
- Verify JavaFX runtime availability
- Confirm file permissions
- Test network connectivity
- Review system logs

---

**Your Mood-Based Recommender is ready for deployment! 🎭✨**
