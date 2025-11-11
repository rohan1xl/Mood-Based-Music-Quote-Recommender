#!/bin/bash

echo "Starting Mood-Based Music & Quote Recommender..."
echo

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 11+ and try again"
    exit 1
fi

echo "Compiling application..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "ERROR: Compilation failed"
    exit 1
fi

echo
echo "Starting application..."
mvn javafx:run

if [ $? -ne 0 ]; then
    echo "ERROR: Application failed to start"
    exit 1
fi

echo
echo "Application closed."
