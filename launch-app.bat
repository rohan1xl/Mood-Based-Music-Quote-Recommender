@echo off
title Mood-Based Music & Quote Recommender
color 0A
echo.
echo  ========================================
echo    🎭 Mood-Based Recommender v1.0
echo  ========================================
echo.
echo  Starting application...
echo  Please wait while JavaFX initializes...
echo.

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo  ❌ ERROR: Java is not installed or not in PATH
    echo  Please install Java 11+ and try again
    echo.
    pause
    exit /b 1
)

REM Check if compiled classes exist
if not exist "target\classes" (
    echo  ⚠️  Compiled classes not found. Compiling...
    mvn compile
    if %errorlevel% neq 0 (
        echo  ❌ Compilation failed
        pause
        exit /b 1
    )
)

REM Check if dependencies exist
if not exist "target\dependency" (
    echo  ⚠️  Dependencies not found. Downloading...
    mvn dependency:copy-dependencies
    if %errorlevel% neq 0 (
        echo  ❌ Failed to download dependencies
        pause
        exit /b 1
    )
)

echo  ✅ All requirements satisfied
echo  🚀 Launching Mood-Based Recommender...
echo.

REM Launch the application
java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "target/classes;target/dependency/*" app.MoodApp

echo.
echo  Application closed.
echo  Thank you for using Mood-Based Recommender! 🎵
echo.
pause
