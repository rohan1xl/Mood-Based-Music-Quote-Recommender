#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}"
echo "  ========================================"
echo "    🎭 Mood-Based Recommender v1.0"
echo "  ========================================"
echo -e "${NC}"
echo "  Starting application..."
echo "  Please wait while JavaFX initializes..."
echo

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo -e "  ${RED}❌ ERROR: Java is not installed or not in PATH${NC}"
    echo "  Please install Java 11+ and try again"
    echo
    exit 1
fi

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo -e "  ${RED}❌ ERROR: Maven is not installed or not in PATH${NC}"
    echo "  Please install Maven and try again"
    echo
    exit 1
fi

# Check if compiled classes exist
if [ ! -d "target/classes" ]; then
    echo -e "  ${YELLOW}⚠️  Compiled classes not found. Compiling...${NC}"
    mvn compile
    if [ $? -ne 0 ]; then
        echo -e "  ${RED}❌ Compilation failed${NC}"
        exit 1
    fi
fi

# Check if dependencies exist
if [ ! -d "target/dependency" ]; then
    echo -e "  ${YELLOW}⚠️  Dependencies not found. Downloading...${NC}"
    mvn dependency:copy-dependencies
    if [ $? -ne 0 ]; then
        echo -e "  ${RED}❌ Failed to download dependencies${NC}"
        exit 1
    fi
fi

echo -e "  ${GREEN}✅ All requirements satisfied${NC}"
echo -e "  ${GREEN}🚀 Launching Mood-Based Recommender...${NC}"
echo

# Launch the application
java --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "target/classes:target/dependency/*" app.MoodApp

echo
echo -e "  ${BLUE}Application closed.${NC}"
echo -e "  ${BLUE}Thank you for using Mood-Based Recommender! 🎵${NC}"
echo
