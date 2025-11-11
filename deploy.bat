@echo off
echo ========================================
echo   Mood-Based Recommender - Deployment
echo ========================================
echo.

REM Check if Maven is installed
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

echo [1/4] Compiling application...
mvn compile
if %errorlevel% neq 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo [2/4] Copying dependencies...
mvn dependency:copy-dependencies
if %errorlevel% neq 0 (
    echo ERROR: Failed to copy dependencies
    pause
    exit /b 1
)

echo.
echo [3/4] Creating deployment package...
if not exist "deployment" mkdir deployment
if not exist "deployment\lib" mkdir deployment\lib
if not exist "deployment\data" mkdir deployment\data

REM Copy compiled classes
xcopy /E /I /Y "target\classes\*" "deployment\"

REM Copy dependencies
xcopy /Y "target\dependency\*.jar" "deployment\lib\"

REM Copy resources
xcopy /E /I /Y "src\main\resources\*" "deployment\"

echo.
echo [4/4] Creating run scripts...

REM Create Windows run script
echo @echo off > deployment\run-mood-app.bat
echo echo Starting Mood-Based Recommender... >> deployment\run-mood-app.bat
echo java --module-path "lib" --add-modules javafx.controls,javafx.fxml,javafx.media -cp ".;lib\*" app.MoodApp >> deployment\run-mood-app.bat
echo pause >> deployment\run-mood-app.bat

REM Create Linux/Mac run script
echo #!/bin/bash > deployment\run-mood-app.sh
echo echo "Starting Mood-Based Recommender..." >> deployment\run-mood-app.sh
echo java --module-path "lib" --add-modules javafx.controls,javafx.fxml,javafx.media -cp ".:lib/*" app.MoodApp >> deployment\run-mood-app.sh

REM Create README for deployment
echo # Mood-Based Recommender - Deployment Package > deployment\README.txt
echo. >> deployment\README.txt
echo ## Requirements: >> deployment\README.txt
echo - Java 11 or higher >> deployment\README.txt
echo. >> deployment\README.txt
echo ## To Run: >> deployment\README.txt
echo Windows: Double-click run-mood-app.bat >> deployment\README.txt
echo Linux/Mac: ./run-mood-app.sh >> deployment\README.txt
echo. >> deployment\README.txt
echo ## Features: >> deployment\README.txt
echo - 10 mood categories with curated content >> deployment\README.txt
echo - 50+ inspirational quotes >> deployment\README.txt
echo - 50+ music recommendations >> deployment\README.txt
echo - Favorites system >> deployment\README.txt
echo - Online quote fetching >> deployment\README.txt

echo.
echo ========================================
echo   DEPLOYMENT SUCCESSFUL! 
echo ========================================
echo.
echo Package created in: deployment\
echo.
echo To run the application:
echo   Windows: deployment\run-mood-app.bat
echo   Linux/Mac: deployment\run-mood-app.sh
echo.
echo To distribute: Zip the 'deployment' folder
echo.
pause
