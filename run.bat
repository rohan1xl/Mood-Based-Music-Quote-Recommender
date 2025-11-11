@echo off
echo Starting Mood-Based Music & Quote Recommender...
echo.

REM Check if Maven is installed
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11+ and try again
    pause
    exit /b 1
)

echo Compiling application...
mvn clean compile

if %errorlevel% neq 0 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo Starting application...
mvn javafx:run

if %errorlevel% neq 0 (
    echo ERROR: Application failed to start
    pause
    exit /b 1
)

echo.
echo Application closed.
pause
